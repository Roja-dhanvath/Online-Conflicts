package com.example.rojad.projectlogin.user;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Question;
import com.example.rojad.projectlogin.preference.CounselorPreference;

public class PostProblemActivity extends AppCompatActivity {
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_problems);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_user_post_problems);
    }

    public void transfer(View view) {
        String stringQuestion = ((EditText) findViewById(R.id.editText3)).getText().toString().trim();
        if (TextUtils.isEmpty(stringQuestion)) {//Validating the user input is empty string for what
            Snackbar.make(mRelativeLayout, "Please write your problem to post", Snackbar.LENGTH_SHORT).show();
        } else { //User entry is valid, we are inserting into db. Question is the model we are using here
            Question question = new Question();
            question.question = stringQuestion;
            question.postedBy = CounselorPreference.getInstance().getUserInfo().id;
            if(CounselorApplication.getInstance().getDBHelperInstance().insertQuestion(question)>0){
                Toast.makeText(PostProblemActivity.this, "Your problem posted to Consellors.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
}
