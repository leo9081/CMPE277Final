package com.example.lab2_digitalidcard.ui.signup;

import android.app.Application;

import com.example.lab2_digitalidcard.data.LoginRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SignupViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;


    public SignupViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignupViewModel.class)) {
            return (T) new SignupViewModel(LoginRepository.getInstance(mApplication));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
