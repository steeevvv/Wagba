package com.stivoo.wagba.ui.home.profile;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.stivoo.wagba.pojo.UserModel;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(UserModel user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void Update(UserModel user);

    @Query("UPDATE user_data SET name = :username WHERE id =:idd")
    void updateName(String username, String idd);

    @Query("UPDATE user_data SET email = :email WHERE id =:idd")
    void updateEmail(String email, String idd);

    @Query("UPDATE user_data SET phone = :phone WHERE id =:idd")
    void updatePhone(String phone, String idd);

    @Query("SELECT * FROM user_data where id = :idd")
    LiveData<UserModel> getUser(String idd);
}