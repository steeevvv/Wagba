package com.stivoo.wagba.ui.signup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.repositories.AuthRepository;

public class SignupViewModel extends AndroidViewModel {

    private AuthRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public SignupViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AuthRepository(application);

        userMutableLiveData = appRepository.getUserMutableLiveData();
    }


    public void register(String mail, String password, String name, String phone) {
        appRepository.register(mail,password, name, phone);
    }

    public void insert(UserModel user) {
        appRepository.insert(user);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}