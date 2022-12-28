package com.stivoo.wagba.ui.signup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stivoo.wagba.repositories.AuthRepository;
import com.stivoo.wagba.repositories.UserRepository;

public class SignupViewModel extends AndroidViewModel {

    private AuthRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private UserRepository repository ;

    public SignupViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AuthRepository(application);
        repository = new UserRepository(application);

        userMutableLiveData = appRepository.getUserMutableLiveData();
    }


    public void register(String mail, String password, String name, String phone) {
        appRepository.register(mail,password, name, phone);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}