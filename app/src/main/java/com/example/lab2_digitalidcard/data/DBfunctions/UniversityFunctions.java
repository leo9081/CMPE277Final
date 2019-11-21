package com.example.lab2_digitalidcard.data.DBfunctions;

import com.example.lab2_digitalidcard.classes.Operation;
import com.example.lab2_digitalidcard.classes.OperationListener;
import com.example.lab2_digitalidcard.data.DBDAO.UniversityRDAO;
import com.example.lab2_digitalidcard.data.DBDAO.UserProfileRDAO;
import com.example.lab2_digitalidcard.data.Result;
import com.example.lab2_digitalidcard.data.Retrofits;
import com.example.lab2_digitalidcard.data.model.University;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UniversityFunctions extends Operation {

    Retrofit retrofit;

    public UniversityFunctions setListerner(OperationListener listerner){
        mlistener = listerner;
        return this;
    }

    public UniversityFunctions(){
        this.retrofit = Retrofits.getInstance().retrofit;
    }

    public void getUniversityFromRequirement(String expenses,String sat_math,String sat_verbal,String control,String state){
        UniversityRDAO universityRDAO = retrofit.create(UniversityRDAO.class);

        Map<String, String> hm = new HashMap<>();
        hm.put("expenses", expenses);
        hm.put("sat_math", sat_math);
        hm.put("sat_verbal", sat_verbal);
        hm.put("control", control);
        hm.put("state", state);

        Call call = universityRDAO.fetchUniversitiese(hm);

        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response!=null){
                    mlistener.onSuccess(response);
                }else {
                    mlistener.onError(new Result.Error(new IOException("Error signup", new Exception())));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mlistener.onError(new Result.Error(new IOException("Error signup", new Exception())));
            }
        });
    }
}
