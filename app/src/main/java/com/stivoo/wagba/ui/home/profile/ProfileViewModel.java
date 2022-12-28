package com.stivoo.wagba.ui.home.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.repositories.AuthRepository;
import com.stivoo.wagba.repositories.UserRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private AuthRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;


    private UserRepository repository ;
    private LiveData<List<UserModel>> users;




    public ProfileViewModel(@NonNull Application application) {
        super(application);

        repository = new UserRepository(application);
        users = repository.getUsers();

        appRepository = new AuthRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = appRepository.getLogOutMutableLiveData();
    }

    public void logOut(){
        appRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }


    private static final DatabaseReference USERS_REF =
            FirebaseDatabase.getInstance().getReference("/Users");

    private final LiveDataListener liveData = new LiveDataListener(USERS_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

    public String getUid() {
        return appRepository.getUid();
    }

    public void insert(UserModel user) {
        repository.insert(user);
    }
    public void update(UserModel user) {
        repository.update(user);
    }

    public LiveData<List<UserModel>> getUsers() {
        return users;
    }
}
