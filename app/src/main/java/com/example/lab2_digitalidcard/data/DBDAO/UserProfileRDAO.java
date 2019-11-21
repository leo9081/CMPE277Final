package com.example.lab2_digitalidcard.data.DBDAO;

import com.example.lab2_digitalidcard.data.model.UserProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserProfileRDAO {

    @FormUrlEncoded
    @POST("loginProcess")
    Call<UserProfile> login(@Field("name") String username, @Field("password") String password);



}
