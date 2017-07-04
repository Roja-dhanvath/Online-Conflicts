package com.example.rojad.projectlogin.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Question;
import com.example.rojad.projectlogin.user.ViewAnswersActivity;
import com.google.gson.Gson;

import java.util.List;

public class UserQuestionsAdapter extends RecyclerView.Adapter<UserQuestionsAdapter.ViewHolder> {
    private List<Question> mQuestionList;
    private Context mContext;

    public UserQuestionsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addData(List<Question> question) {
        mQuestionList = question;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_user_question_view_layout, parent, false);
        return new ViewHolder(view);
    }

    public Question getItem(int position) {
        return mQuestionList.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question question = getItem(position);
        holder.question.setText(question.question);
        holder.answeredBy.setText(question.answers != null && question.answers.size() > 0 ?
                String.format(mContext.getResources().getString(R.string.string_answer_count), question.answers.size())
                : "Yet to Answer");
    }

    @Override
    public int getItemCount() {
        return mQuestionList == null ? 0 : mQuestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView question;
        TextView answeredBy;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.textview_question);
            answeredBy = (TextView) itemView.findViewById(R.id.textview_answered_count);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mQuestionList.get(getAdapterPosition()).answers.size() > 0) {
                Intent intent = new Intent(mContext, ViewAnswersActivity.class);
                intent.putExtra("data", new Gson().toJson(mQuestionList.get(getAdapterPosition())));
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, "Yet to Answer", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
