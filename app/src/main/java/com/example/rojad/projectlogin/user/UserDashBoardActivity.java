package com.example.rojad.projectlogin.user;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.account.LogIn;
import com.example.rojad.projectlogin.model.Contact;
import com.example.rojad.projectlogin.model.Question;
import com.example.rojad.projectlogin.preference.CounselorPreference;
import com.example.rojad.projectlogin.user.adapter.UserQuestionsAdapter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

public class UserDashBoardActivity extends AppCompatActivity {
    private Button mybutton;
    private RelativeLayout mRelativeLayout1;
    private Contact mContact;
    private UserQuestionsAdapter mUserQuestionAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        mContact = CounselorPreference.getInstance().getUserInfo();
        actionBar.setTitle("Welcome " + mContact.name);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_questions);
        mybutton = (Button) findViewById(R.id.button4);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashBoardActivity.this, PostProblemActivity.class));
            }
        });
        mRelativeLayout1 = (RelativeLayout) findViewById(R.id.activity_user_page1);
        mUserQuestionAdapter = new UserQuestionsAdapter(this);
        mRecyclerView.setAdapter(mUserQuestionAdapter);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingbutton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashBoardActivity.this, PostProblemActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMyQuestions();
        exportDatabase();
    }

    private void getMyQuestions() {
        List<Question> questionsList = CounselorApplication.getInstance().getDBHelperInstance().getQuestionsBy(mContact);
        System.out.println(new Gson().toJson(questionsList));
        if (questionsList != null && questionsList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mFloatingActionButton.setVisibility(View.VISIBLE);
            mRelativeLayout1.setVisibility(View.GONE);
            mUserQuestionAdapter.addData(questionsList);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mFloatingActionButton.setVisibility(View.GONE);
            mRelativeLayout1.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(UserDashBoardActivity.this, LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    //Will be removed, this code used for downloadin
    public void exportDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.example.rojad.projectlogin//databases//contact.db";
                String backupDBPath = "contacts.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                System.out.println("Exception:=" + currentDB.exists());
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception:=" + e);
        }
    }
}
