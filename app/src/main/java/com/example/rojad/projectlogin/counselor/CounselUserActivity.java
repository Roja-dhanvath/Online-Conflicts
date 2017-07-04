package com.example.rojad.projectlogin.counselor;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Answer;
import com.example.rojad.projectlogin.model.Contact;
import com.example.rojad.projectlogin.model.Question;
import com.example.rojad.projectlogin.preference.CounselorPreference;
import com.google.gson.Gson;

public class CounselUserActivity extends AppCompatActivity {
    private Question question;
    private Contact mContact;
    private TextView questionText;
    private EditText editText_data;
    private Button mSubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = new Gson().fromJson(getIntent().getStringExtra("data"), Question.class);
        getSupportActionBar().setTitle("Counsel Screen");
        mContact = CounselorPreference.getInstance().getUserInfo();
        setContentView(R.layout.activity_counsel_user);
        questionText = (TextView) findViewById(R.id.question);
        questionText.setText(question.question);
        editText_data = (EditText) findViewById(R.id.edittext_data);
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText_data.getText().toString().trim();
                if (TextUtils.isEmpty(data)) {
                    Toast.makeText(CounselUserActivity.this, "Please enter your suggesstion", Toast.LENGTH_LONG).show();
                } else {
                    Answer answer = new Answer();
                    answer.answer = data;
                    answer.answerBy = mContact.id;
                    answer.questionId = question.questionId;
                    if (CounselorApplication.getInstance().getDBHelperInstance().insertAnswer(answer) > 0l) {
                        Toast.makeText(CounselUserActivity.this, "Your suggestion send.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}
