package com.example.lab2_digitalidcard.data.DBfunctions;

import com.example.lab2_digitalidcard.classes.Operation;
import com.example.lab2_digitalidcard.classes.OperationListener;
import com.example.lab2_digitalidcard.data.DBDAO.UserProfileDao;
import com.example.lab2_digitalidcard.data.DBDAO.UserProfileRDAO;
import com.example.lab2_digitalidcard.data.Result;
import com.example.lab2_digitalidcard.data.Retrofits;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileFunctions extends Operation {

    Retrofit retrofit;

    public UserProfileFunctions setListerner(OperationListener listerner){
        mlistener = listerner;
        return this;
    }

    public UserProfileFunctions(){
        this.retrofit = Retrofits.getInstance().retrofit;
    }

    public void userLogin(String username, String password){
        UserProfileRDAO userProfileRDAO = retrofit.create(UserProfileRDAO.class);
        Call call = userProfileRDAO.login(username, password);

        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response!=null){
                    //UserProfile.setInstance((UserProfile)response.body());
                    UserProfile u = (UserProfile)response.body();
                    //getActivieCard();
                    mlistener.onSuccess(u);
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
