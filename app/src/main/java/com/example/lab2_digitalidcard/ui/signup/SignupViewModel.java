package com.example.lab2_digitalidcard.ui.signup;

import android.util.Patterns;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.data.LoginRepository;
import com.example.lab2_digitalidcard.data.Result;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupViewModel extends ViewModel {

    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();
    private MutableLiveData<SignupResult> signupResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    SignupViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<SignupFormState> getLoginFormState() {
        return signupFormState;
    }

    LiveData<SignupResult> getLoginResult() {
        return signupResult;
    }

    public void signup(String username, String password, String firstname, String lastname,
                       String age, String status,String gender,String current_id,String school_name,
                       String gpa,String gre) {
        // can be launched in a separate asynchronous job
        UserProfile userProfile = new UserProfile(username, password,firstname,
                lastname,age,status,gender,current_id,school_name,gpa,gre);

        Result<UserProfile> result = loginRepository.userSignup(userProfile);

        if (result instanceof Result.Success) {
            UserProfile data = ((Result.Success<UserProfile>) result).getData();
            signupResult.setValue(new SignupResult(new SignupUserView(data)));
        } else {
            signupResult.setValue(new SignupResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignupFormState(null, R.string.invalid_password));
        } else {
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}
