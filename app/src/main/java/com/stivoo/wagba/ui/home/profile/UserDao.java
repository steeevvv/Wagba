package com.stivoo.wagba.ui.home.profile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.stivoo.wagba.pojo.UserModel;

import java.util.List;

@Dao

public interface UserDao {
    @Insert
    void Insert(UserModel user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void Update(UserModel user);

    @Query("SELECT * FROM user_data")
    LiveData<List<UserModel>> getUser();
}

