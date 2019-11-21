package com.example.lab2_digitalidcard.ui.signup;

import com.example.lab2_digitalidcard.data.model.UserProfile;

public class SignupUserView {
    private UserProfile userProfile;
    //... other data fields that may be accessible to the UI

    SignupUserView(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    UserProfile getUserProfile() {
        return userProfile;
    }
}
