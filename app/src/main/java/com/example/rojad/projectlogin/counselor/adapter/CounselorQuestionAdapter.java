package com.example.rojad.projectlogin.counselor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.counselor.CounselUserActivity;
import com.example.rojad.projectlogin.model.Question;
import com.google.gson.Gson;

import java.util.List;


public class CounselorQuestionAdapter extends RecyclerView.Adapter<CounselorQuestionAdapter.ViewHolder> {

    private Context mContext;
    private List<Question> mQuestionList;

    public CounselorQuestionAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_admin_question_layout, parent, false);
        return new ViewHolder(view);
    }

    public void addData(List<Question> list) {
        mQuestionList = list;
        notifyDataSetChanged();
    }

    public Question getItem(int position) {
        return mQuestionList.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question question = getItem(position);
        holder.question.setText(question.question);
        holder.postedBy.setText(String.format(mContext.getString(R.string.string_posted_by), question.user.name));
    }

    @Override
    public int getItemCount() {
        return mQuestionList != null ? mQuestionList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView question;
        TextView postedBy;
        TextView CounselUser;

        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.textview_question);
            postedBy = (TextView) itemView.findViewById(R.id.textview_posted_by);
            CounselUser = (TextView) itemView.findViewById(R.id.textview_answered_count);
            CounselUser.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, CounselUserActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtra("data", new Gson().toJson(mQuestionList.get(getAdapterPosition())));
            mContext.startActivity(intent);
        }
    }
}
