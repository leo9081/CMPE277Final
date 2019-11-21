package com.example.lab2_digitalidcard.ui.login;

import com.example.lab2_digitalidcard.data.model.UserProfile;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private UserProfile userProfile;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    UserProfile getUserProfile() {
        return userProfile;
    }
}
