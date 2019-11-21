package com.example.lab2_digitalidcard.data;

import com.example.lab2_digitalidcard.R;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofits {
    public Retrofit retrofit;
    public static Retrofits retrofits;

    private Retrofits(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://34.67.106.136/seers/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofits getInstance(){
        if(retrofits == null){
            try{
                retrofits = new Retrofits();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return retrofits;
    }



}
