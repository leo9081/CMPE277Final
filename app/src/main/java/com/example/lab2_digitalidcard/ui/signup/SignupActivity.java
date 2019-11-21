package com.example.lab2_digitalidcard.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.ui.NavigationActivity;
import com.example.lab2_digitalidcard.ui.card.CardActivity;


import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SignupActivity extends AppCompatActivity {

    private SignupViewModel signupViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(!isNetworkAvailable()){
            finish();
            System.exit(0);
        }

        signupViewModel = ViewModelProviders.of(this, new SignupViewModelFactory(getApplication())).get(SignupViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText greEditText = findViewById(R.id.gre);
        final EditText gpaEditText = findViewById(R.id.gpa);
        final EditText school_nameEditText = findViewById(R.id.school_name);
        final EditText current_idEditText = findViewById(R.id.current_id);
        final EditText genderEditText = findViewById(R.id.gender);
        final EditText statusEditText = findViewById(R.id.status);
        final EditText ageEditText = findViewById(R.id.age);
        final EditText lastnameEditText = findViewById(R.id.lastname);
        final EditText firstnameEditText = findViewById(R.id.firstname);
        final Button loginButton = findViewById(R.id.register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        signupViewModel.getLoginFormState().observe(this, new Observer<SignupFormState>() {
            @Override
            public void onChanged(@Nullable SignupFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        signupViewModel.getLoginResult().observe(this, new Observer<SignupResult>() {
            @Override
            public void onChanged(@Nullable SignupResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    loginToMain(loginResult);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                signupViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signupViewModel.signup(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            firstnameEditText.getText().toString(),
                            lastnameEditText.getText().toString(),
                            ageEditText.getText().toString(),
                            statusEditText.getText().toString(),
                            genderEditText.getText().toString(),
                            current_idEditText.getText().toString(),
                            school_nameEditText.getText().toString(),
                            greEditText.getText().toString(),
                            gpaEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                signupViewModel.signup(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstnameEditText.getText().toString(),
                        lastnameEditText.getText().toString(),
                        ageEditText.getText().toString(),
                        statusEditText.getText().toString(),
                        genderEditText.getText().toString(),
                        current_idEditText.getText().toString(),
                        school_nameEditText.getText().toString(),
                        greEditText.getText().toString(),
                        gpaEditText.getText().toString());
            }
        });
    }

    public void loginToMain(SignupResult log){
        Intent i = new Intent(this, NavigationActivity.class);
        i.putExtra("UserProfile",log.getSuccess().getUserProfile());
        startActivity(i);
    }

    public void signUpforUser(String userName, String password){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    private void updateUiWithUser(SignupUserView model) {
        String welcome = getString(R.string.welcome) + model.getUserProfile().getUserName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
