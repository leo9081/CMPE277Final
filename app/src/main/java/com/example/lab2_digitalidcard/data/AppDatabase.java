package com.example.lab2_digitalidcard.data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lab2_digitalidcard.data.DBDAO.UserProfileDao;
import com.example.lab2_digitalidcard.data.model.UserProfile;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserProfile.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserProfileDao UserProfileDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "SEERDB").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserProfileDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.UserProfileDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            List<UserProfile> userProfiles =  mDao.getAll();
            return null;
        }
    }
}
