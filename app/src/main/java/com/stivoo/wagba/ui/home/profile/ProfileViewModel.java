package com.stivoo.wagba.ui.home.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.repositories.AuthRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final AuthRepository appRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;
    private final FirebaseAuth firebaseAuth;
    private final LiveData<UserModel> user_data;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AuthRepository(application);
        firebaseAuth = FirebaseAuth.getInstance();
        user_data = appRepository.getUsers(firebaseAuth.getCurrentUser().getUid());
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

    public void insert(UserModel user){
        appRepository.insert(user);
    }

    public void update(UserModel user) {
        appRepository.update(user);
    }

    public void updateName(String username, String idd){
        appRepository.updateName(username, idd);

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("/Users"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ref.child("name").setValue(username);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.child("name").addListenerForSingleValueEvent(eventListener);
    }

    public void updateEmail(String email, String idd){
        appRepository.updateEmail(email, idd);

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("/Users"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ref.child("mail").setValue(email);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.child("mail").addListenerForSingleValueEvent(eventListener);
    }

    public void updatePhone(String phone, String idd){
        appRepository.updatePhone(phone, idd);

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("/Users"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ref.child("phone").setValue(phone);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.child("phone").addListenerForSingleValueEvent(eventListener);
    }

    public void updatePic(String phone, String idd){
        appRepository.updatePic(phone, idd);
    }


    public LiveData<UserModel> getUsers(String id) {
        return user_data;
    }
}
