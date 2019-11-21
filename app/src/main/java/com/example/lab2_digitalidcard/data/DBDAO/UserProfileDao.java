package com.example.lab2_digitalidcard.data.DBDAO;

import com.example.lab2_digitalidcard.data.Result;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserProfileDao {

    @Query("SELECT * FROM UserProfile")
    List<UserProfile> getAll();

    @Query("SELECT * FROM UserProfile WHERE userId IN (:userIds)")
    List<UserProfile> getUserProfiles(int[] userIds);

    @Query("SELECT * FROM UserProfile WHERE userId = :userId")
    UserProfile getUserProfile(int userId);

    @Query("SELECT * FROM UserProfile WHERE email = :username and password = :password")
    UserProfile getUserProfile(String username, String password);

    @Query("SELECT * FROM UserProfile WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    UserProfile findByName(String first, String last);

    @Insert
    public void insertUser(UserProfile user);

    @Insert
    void insertAll(UserProfile... users);

    @Delete
    void deleteUser(UserProfile user);

}
