package com.example.rojad.projectlogin.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Answer;
import com.example.rojad.projectlogin.model.Question;
import com.example.rojad.projectlogin.user.adapter.AnswersAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class ViewAnswersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answer_layout);
        getSupportActionBar().setTitle("View Answers");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Question data = new Gson().fromJson(getIntent().getStringExtra("data"), Question.class);
        ((TextView)findViewById(R.id.textview_question)).setText(data.question);
        AnswersAdapter answersAdapter = new AnswersAdapter(this);
        recyclerView.setAdapter(answersAdapter);
        answersAdapter.addData(data.answers);
    }
}
