package com.stivoo.wagba.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.ui.home.profile.UserDao;
import com.stivoo.wagba.ui.home.profile.UserDatabase;

public class UserRepository {
    private UserDao userDao;
    private LiveData<UserModel> users;
    FirebaseAuth firebaseAuth;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        firebaseAuth = FirebaseAuth.getInstance();
        users = userDao.getUser(firebaseAuth.getCurrentUser().getUid());
    }

    public void insert(UserModel user) {
        new UserRepository.InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(UserModel user) {
        new UserRepository.UpdateUserAsyncTask(userDao).execute(user);
    }

    public LiveData<UserModel> getUsers(String id) {
        return users;
    }


    private static class InsertUserAsyncTask extends AsyncTask<UserModel, Void, Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(UserModel... users) {
            userDao.Insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserModel, Void, Void> {
        private UserDao userDao;
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
