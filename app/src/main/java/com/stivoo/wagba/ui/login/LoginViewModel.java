package com.stivoo.wagba.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.stivoo.wagba.repositories.AuthRepository;

public class LoginViewModel extends AndroidViewModel {
    private AuthRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AuthRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
    }

    public void login (String mail, String password){
        appRepository.login(mail, password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }



}
