package com.example.rojad.projectlogin.account;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Contact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private Snackbar mSnackBar;
    private RelativeLayout mRelativeLayout;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_sign_up);
        EditText email = (EditText) findViewById(R.id.editText4);
    }

    public void OnSignUpClick(View view) {

        EditText name = (EditText) findViewById(R.id.editText3);
        EditText email = (EditText) findViewById(R.id.editText4);
        EditText uname = (EditText) findViewById(R.id.editText5);
        EditText pass1 = (EditText) findViewById(R.id.editText6);
        EditText pass2 = (EditText) findViewById(R.id.editText7);


        Contact c = new Contact();

        c.name = name.getText().toString();
        c.email = email.getText().toString();
        c.uname = uname.getText().toString();
        String pass1tr = pass1.getText().toString();
        String pass2str = pass2.getText().toString();


        if (!pass1tr.equals(pass2str)) {
            mSnackBar = Snackbar.make(mRelativeLayout, "password dont match", Snackbar.LENGTH_LONG);
            mSnackBar.show();
        } else if (CounselorApplication.getInstance().getDBHelperInstance().IsContactExist(c)) {
            mSnackBar = Snackbar.make(mRelativeLayout, "User already exist.Try different username and email.", Snackbar.LENGTH_LONG);
            mSnackBar.show();
        } else {
            c.password = pass1tr;
            if (CounselorApplication.getInstance().getDBHelperInstance().insertContact(c) > 0) {
                Toast.makeText(SignUp.this, "User Account Created Successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }


    }
    public boolean validateEmail(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }


}