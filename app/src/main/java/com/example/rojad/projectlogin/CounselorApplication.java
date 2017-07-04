package com.example.rojad.projectlogin;

import android.app.Application;

import com.example.rojad.projectlogin.database.DatabaseHelper;



public class CounselorApplication extends Application {
    private static CounselorApplication mInstance;
    private DatabaseHelper mDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        getDBHelperInstance();
        mDBHelper.insertAdmins();
    }

    public static CounselorApplication getInstance() {
        return mInstance;
    }

    public DatabaseHelper getDBHelperInstance() {
        if (mDBHelper == null) {
            mDBHelper = new DatabaseHelper(mInstance);
        }
        return mDBHelper;
    }
}
