package com.stivoo.wagba.pojo;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserModel {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;
    private String email;
    private String phone;
    private String picture;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public UserModel(String name, String email, String phone, String picture) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public String getPicture() {
        return picture;
    }
}
