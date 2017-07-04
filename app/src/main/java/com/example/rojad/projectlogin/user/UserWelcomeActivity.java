package com.example.rojad.projectlogin.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rojad.projectlogin.R;

public class UserWelcomeActivity extends AppCompatActivity {
    Button mybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page1);
        mybutton = (Button) findViewById(R.id.button4);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserWelcomeActivity.this, PostProblemActivity.class));
            }
        });
    }
}
