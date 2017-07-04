package com.example.rojad.projectlogin.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.counselor.CounselorDashBoardActivity;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Contact;
import com.example.rojad.projectlogin.preference.CounselorPreference;
import com.example.rojad.projectlogin.user.UserDashBoardActivity;
import com.example.rojad.projectlogin.user.UserWelcomeActivity;
import com.google.gson.Gson;

public class LogIn extends AppCompatActivity {
    private RelativeLayout mRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_log_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
    }

    public void LogIn(View view) {
        String stringUserName = ((EditText) findViewById(R.id.edittext_username)).getText().toString();
        String stringPassword = ((EditText) findViewById(R.id.editText_password)).getText().toString();

        if (TextUtils.isEmpty(stringUserName) && TextUtils.isEmpty(stringPassword)) {
            Snackbar.make(mRelativeLayout, "Please enter valid details", Snackbar.LENGTH_LONG).show();

        } else {

            Contact contact = new Contact();
            contact.uname = stringUserName;
            contact.password = stringPassword;

            Contact validUser = CounselorApplication.getInstance().getDBHelperInstance().ValidateContact(contact);
            if (validUser != null) {
                System.out.println(new Gson().toJson(validUser));
                CounselorPreference.getInstance().setUserInfo(validUser);
                Intent intent = new Intent(LogIn.this, validUser.type == Contact.CONTACT_TYPE_USER ? UserDashBoardActivity.class : CounselorDashBoardActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(mRelativeLayout, "Username and password dont match", Snackbar.LENGTH_LONG).show();
            }
        }

    }

    public void GoTo(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
