package com.example.lab2_digitalidcard.data;

import android.app.Application;
import android.os.AsyncTask;

import com.example.lab2_digitalidcard.classes.Operation;
import com.example.lab2_digitalidcard.classes.OperationListener;
import com.example.lab2_digitalidcard.data.DBDAO.UserProfileDao;
import com.example.lab2_digitalidcard.data.DBfunctions.UserProfileFunctions;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import java.io.IOException;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository extends Operation {

    private static volatile LoginRepository instance;
    private UserProfileDao mUserProfileDao;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private static UserProfile user = null;

    // private constructor : singleton access
    private LoginRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        mUserProfileDao = appDatabase.UserProfileDao();
    }

    public static LoginRepository getInstance(Application application) {
        if (instance == null) {
            instance = new LoginRepository(application);
        }
        return instance;
    }

    public LoginRepository setOperationListener(OperationListener listener) {
        mlistener = listener;
        return this;

    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        //dataSource.logout();
    }

    private static void setLoggedInUser(UserProfile u) {
        user = u;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void userLogin(String username, String password){
        get(username, password);
    }

    public void get (String username, String password) {
        new getAsyncTask(mUserProfileDao,username,password).execute();
        //new loginAsyncTask(username,password).execute();
    }

    private static class loginAsyncTask extends AsyncTask<Void, Void, Void> {
        private String mUsername;
        private String mPassword;

        loginAsyncTask( String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                new UserProfileFunctions().setListerner(mlistener).
                        userLogin(mUsername,  mPassword);
                //mUserProfile = mAsyncTaskDao.getUserProfile( mUsername,  mPassword);
            } catch (Exception e) {
                mlistener.onError(new Result.Error(new IOException("Error signup", e)));
            }
            return null;
        }
    }


    private static class getAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserProfileDao mAsyncTaskDao;
        private String mUsername;
        private String mPassword;
        private UserProfile mUserProfile;

        getAsyncTask( UserProfileDao dao, String username, String password) {
            mAsyncTaskDao = dao;
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mUserProfile = mAsyncTaskDao.getUserProfile( mUsername,  mPassword);
            } catch (Exception e) {
                mlistener.onError(new Result.Error(new IOException("Error signup", e)));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Result<UserProfile> result = null;
            if(mUserProfile==null){
                result = new Result.Error(new IOException("Error login", new Exception()));
            }else{
                result = new Result.Success<>(mUserProfile);
            }

            if (result instanceof Result.Success) {
                setLoggedInUser(((Result.Success<UserProfile>) result).getData());
            }
            mlistener.onPostExecution();
            mlistener.onSuccess(result);
        }
    }


    public Result<UserProfile> userSignup(UserProfile userProfile){
        final Result<UserProfile> result = insert(userProfile);

        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<UserProfile>) result).getData());
        }
        return result;
    }

    public Result<UserProfile> insert (UserProfile userProfile) {
        try {
            new insertAsyncTask(mUserProfileDao).execute(userProfile);;
            return new Result.Success<>(userProfile);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error signup", e));
        }

    }

    private static class insertAsyncTask extends AsyncTask<UserProfile, Void, Void> {

        private UserProfileDao mAsyncTaskDao;

        insertAsyncTask(UserProfileDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserProfile... params) {
            mAsyncTaskDao.insertUser(params[0]);
            return null;
        }
    }

/*    public void updateTask(final UserProfile userProfile) {
        //note.setModifiedAt(AppUtils.getCurrentDateTime());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.UserProfileDao().updateUser(userProfile);
                return null;
            }
        }.execute();
    }*/

    public void deleteUser(final UserProfile userProfile) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mUserProfileDao.deleteUser(userProfile);
                return null;
            }
        }.execute();
    }

    public UserProfile getUserProfile(int id) {
        return mUserProfileDao.getUserProfile(id);
    }

}
