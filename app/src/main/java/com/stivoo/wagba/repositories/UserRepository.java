package com.stivoo.wagba.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.ui.home.profile.UserDao;
import com.stivoo.wagba.ui.home.profile.UserDatabase;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserModel>> users;

    public UserRepository(Application application) { //application is subclass of context
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        users = userDao.getUser();
    }

    public void insert(UserModel user) {
        new UserRepository.InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(UserModel user) {
        new UserRepository.UpdateUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<UserModel>> getUsers() {
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
