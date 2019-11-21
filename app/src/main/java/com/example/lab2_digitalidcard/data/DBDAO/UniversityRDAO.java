package com.example.lab2_digitalidcard.data.DBDAO;

import com.example.lab2_digitalidcard.data.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface UniversityRDAO {

    @GET("getSchoolInfo")
    Call<ArrayList<Object>> fetchUniversitiese(@QueryMap Map<String, String> schoolInfo);

}
