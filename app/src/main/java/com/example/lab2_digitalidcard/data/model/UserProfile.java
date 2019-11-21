package com.example.lab2_digitalidcard.data.model;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "userProfile")
public class UserProfile implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "age")
    public String age;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "current_id")
    public String currentId;

    @ColumnInfo(name = "school_name")
    public String schoolName;

    @ColumnInfo(name = "gpa")
    public String gpa;

    @ColumnInfo(name = "gre")
    public String gre;

    @Ignore
    UserProfile(int userId, String email, String password, String firstName, String lastName, String age, String status, String gender, String currentId, String schoolName, String gpa, String gre) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.status = status;
        this.gender = gender;
        this.currentId = currentId;
        this.schoolName = schoolName;
        this.gpa = gpa;
        this.gre = gre;
    }

    public UserProfile(String email, String password, String firstName, String lastName, String age, String status, String gender, String currentId, String schoolName, String gpa, String gre) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.status = status;
        this.gender = gender;
        this.currentId = currentId;
        this.schoolName = schoolName;
        this.gpa = gpa;
        this.gre = gre;
    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName(){ return firstName+" " + lastName;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        schoolName = schoolName;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getGre() {
        return gre;
    }

    public void setGre(String gre) {
        this.gre = gre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
