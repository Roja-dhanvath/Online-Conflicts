package com.example.rojad.projectlogin.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.model.Contact;

public class CounselorPreference {
    private static SharedPreferences mSharedPreference;
    private static Object object = new Object();
    private static CounselorPreference mInstance;
    private static final String PREFERENCE_KEY_USER_UNAME = "PREFERENCE_KEY_USER_UNAME";
    private static final String PREFERENCE_KEY_USER_NAME = "PREFERENCE_KEY_USER_NAME";
    private static final String PREFERENCE_KEY_USER_EMAIL = "PREFERENCE_KEY_USER_EMAIL";
    private static final String PREFERENCE_KEY_USER_TYPE = "PREFERENCE_KEY_USER_TYPE";
    private static final String PREFERENCE_KEY_USER_ID = "PREFERENCE_KEY_USER_ID";

    public static CounselorPreference getInstance() {
        synchronized (object) {
            if (mInstance == null) {
                mSharedPreference = CounselorApplication.getInstance().getSharedPreferences("CounselorPreference", Context.MODE_PRIVATE);
                mInstance = new CounselorPreference();
            }
        }
        return mInstance;
    }

    public void setUserInfo(Contact contact) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putInt(PREFERENCE_KEY_USER_ID, contact.id);
        editor.putString(PREFERENCE_KEY_USER_UNAME, contact.uname);
        editor.putString(PREFERENCE_KEY_USER_NAME, contact.name);
        editor.putString(PREFERENCE_KEY_USER_EMAIL, contact.email);
        editor.putInt(PREFERENCE_KEY_USER_TYPE, contact.type);
        editor.apply();
    }

    public Contact getUserInfo() {
        Contact contact = new Contact();
        contact.uname = mSharedPreference.getString(PREFERENCE_KEY_USER_UNAME, "");
        contact.name = mSharedPreference.getString(PREFERENCE_KEY_USER_NAME, "");
        contact.email = mSharedPreference.getString(PREFERENCE_KEY_USER_EMAIL, "");
        contact.type = mSharedPreference.getInt(PREFERENCE_KEY_USER_TYPE, 0);
        contact.id = mSharedPreference.getInt(PREFERENCE_KEY_USER_ID, 0);
        return contact;
    }

    public void clearPreference() {
        mSharedPreference.edit().clear().apply();
    }
}
