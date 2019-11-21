package com.example.lab2_digitalidcard.ui.signup;

import androidx.annotation.Nullable;

public class SignupResult {
    @Nullable
    private SignupUserView success;
    @Nullable
    private Integer error;

    SignupResult(@Nullable Integer error) {
        this.error = error;
    }

    SignupResult(@Nullable SignupUserView success) {
        this.success = success;
    }

    @Nullable
    SignupUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
