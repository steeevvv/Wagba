package com.stivoo.wagba.repositories;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Random;

public class AuthRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> logOutMutableLiveData;
    private FirebaseAuth firebaseAuth;

    FirebaseDatabase database;
    DatabaseReference myRef;

    public AuthRepository(Application application) {
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        logOutMutableLiveData = new MutableLiveData<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://wagba-c9f53-default-rtdb.firebaseio.com/");

        if (firebaseAuth.getCurrentUser() != null) {
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            logOutMutableLiveData.postValue(false);
        }
    }

    public void register(String mail, String password, String name, String phone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            firebaseAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendVerificationEmail(mail, password, name, phone);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("mail").setValue(mail);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("pw").setValue(password);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("name").setValue(name);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("phone").setValue(phone);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("profile_img").setValue("");
                            } else {
                                Toast.makeText(application, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


//        byte[] array = new byte[7]; // length is bounded by 7
//        new Random().nextBytes(array);
//        String generatedString = new String(array, StandardCharsets.UTF_8);

//        String generatedString = myRef.push().getKey();


//                        myRef.child("Users").child(generatedString).child("uid").setValue(firebaseAuth.getCurrentUser().getUid().toString());
        firebaseAuth.signOut();
    }

    public void login(String mail, String password) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            firebaseAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (checkEmailVerification()) {
                                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                } else {
                                    Toast.makeText(application, "Please verify your Email First", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(application, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean checkEmailVerification() {
        if (firebaseAuth != null) {
            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                return true;
            }
            firebaseAuth.signOut();
            return false;
        }
        return false;
    }

    private void sendVerificationEmail(String mail, String password, String name, String phone) {
        if (firebaseAuth != null) {
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(application, "Email Has been sent to verify you!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(application, "Ooops! Problem registering you!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            firebaseAuth.signOut();
        }
    }

    public void logOut() {
        firebaseAuth.signOut();
        logOutMutableLiveData.postValue(true);
    }

    public MutableLiveData<Boolean> getLogOutMutableLiveData() {
        return logOutMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public String getUid(){
        if(firebaseAuth != null){
            return firebaseAuth.getCurrentUser().getUid();
        }
        return "null";
    }
}