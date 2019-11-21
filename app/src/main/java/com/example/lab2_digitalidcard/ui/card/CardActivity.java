package com.example.lab2_digitalidcard.ui.card;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.data.model.UserProfile;
import com.example.lab2_digitalidcard.ui.login.LoginViewModel;
import com.example.lab2_digitalidcard.ui.login.LoginViewModelFactory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;

public class CardActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);

        UserProfile userProfile = (UserProfile) getIntent().getSerializableExtra("UserProfile");

        //getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setTitle("Welcome, "+ userProfile.getUserName());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Fragment newFragment = new CardFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.dynamic_fragment_frame_layout, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        quitDialog();
    }

    private void quitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.quit_app_notification);
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                        System.exit(0);
                    }
                });
        builder.show();
    }
}
