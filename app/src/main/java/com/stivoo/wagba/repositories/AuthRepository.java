package com.stivoo.wagba.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.ui.home.profile.UserDao;
import com.stivoo.wagba.ui.home.profile.UserDatabase;

public class AuthRepository {
    private final UserDao userDao;
    private final LiveData<UserModel> users;


    private final Application application;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> logOutMutableLiveData;
    private final FirebaseAuth firebaseAuth;

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

        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();

        users = userDao.getUser(firebaseAuth.getUid());
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
//                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("pw").setValue(password);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("name").setValue(name);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("phone").setValue(phone);
                                myRef.child("Users").child(task.getResult().getUser().getUid()).child("profile_img").setValue("https://firebasestorage.googleapis.com/v0/b/wagba-c9f53.appspot.com/o/default-user.png?alt=media&token=2d7b3cae-2ddd-4f95-a759-cbf28266309d");
                                UserModel x = new UserModel(task.getResult().getUser().getUid(), name, mail,phone,"");
                                insert(x);
                            } else {
                                Toast.makeText(application, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
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


    public void insert(UserModel user) {
        new AuthRepository.InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(UserModel user) {
        new AuthRepository.UpdateUserAsyncTask(userDao).execute(user);
    }

    public void updateName(String username, String idd){
        new AuthRepository.UpdateUserNameAsyncTask(userDao).execute(username,idd);
    }

    public void updateEmail(String email, String idd){
        new AuthRepository.UpdateUserEmailAsyncTask(userDao).execute(email,idd);
    }

    public void updatePhone(String phone, String idd){
        new AuthRepository.UpdateUserPhoneAsyncTask(userDao).execute(phone,idd);
    }

    public void updatePic(String photo, String idd){
        new AuthRepository.UpdateUserPhotoAsyncTask(userDao).execute(photo,idd);
    }



    public LiveData<UserModel> getUsers(String id) {
        return users;
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserModel, Void, Void> {
        private final UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(UserModel... users) {
            userDao.Insert(users[0]);
            return null;
        }
    }


    private static class UpdateUserNameAsyncTask extends AsyncTask<String, Void, Void> {
        private final UserDao userDao;
        private UpdateUserNameAsyncTask(UserDao userDao) { //constructor as the class is static
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            userDao.updateName(strings[0], strings[1]);
            return null;
        }
    }

    private static class UpdateUserEmailAsyncTask extends AsyncTask<String, Void, Void> {
        private final UserDao userDao;
        private UpdateUserEmailAsyncTask(UserDao userDao) { //constructor as the class is static
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            userDao.updateEmail(strings[0], strings[1]);
            return null;
        }
    }

    private static class UpdateUserPhoneAsyncTask extends AsyncTask<String, Void, Void> {
        private final UserDao userDao;
        private UpdateUserPhoneAsyncTask(UserDao userDao) { //constructor as the class is static
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            userDao.updatePhone(strings[0], strings[1]);
            return null;
        }
    }

    private static class UpdateUserPhotoAsyncTask extends AsyncTask<String, Void, Void> {
        private final UserDao userDao;
        private UpdateUserPhotoAsyncTask(UserDao userDao) { //constructor as the class is static
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            userDao.updatePic(strings[0], strings[1]);
            return null;
        }
    }


    private static class UpdateUserAsyncTask extends AsyncTask<UserModel, Void, Void> {
        private final UserDao userDao;
        private UpdateUserAsyncTask(UserDao userDao) { //constructor as the class is static
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(UserModel... users) {
            userDao.Update(users[0]);
            return null;
        }
    }

}