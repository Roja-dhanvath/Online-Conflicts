package com.example.rojad.projectlogin.counselor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.account.LogIn;
import com.example.rojad.projectlogin.counselor.adapter.CounselorQuestionAdapter;
import com.example.rojad.projectlogin.model.Contact;
import com.example.rojad.projectlogin.model.Question;
import com.example.rojad.projectlogin.preference.CounselorPreference;
import com.google.gson.Gson;

import java.util.List;

public class CounselorDashBoardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Contact mContact;
    private CounselorQuestionAdapter adapter;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        mContact = CounselorPreference.getInstance().getUserInfo();
        System.out.println(new Gson().toJson(mContact));
        actionBar.setTitle("Welcome " + mContact.name);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_questions);
        adapter = new CounselorQuestionAdapter(this);
        recyclerView.setAdapter(adapter);
        (findViewById(R.id.activity_user_page1)).setVisibility(View.GONE);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMyQuestions();
    }

    private void getMyQuestions() {
        List<Question> questionList = CounselorApplication.getInstance().getDBHelperInstance().getQuestionsByAdmin(mContact);
        if (questionList != null && questionList.size() > 0) {
            adapter.addData(questionList);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(mRelativeLayout, "No Data Found", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CounselorPreference.getInstance().clearPreference();
        Intent intent = new Intent(CounselorDashBoardActivity.this, LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
