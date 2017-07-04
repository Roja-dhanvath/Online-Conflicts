package com.example.rojad.projectlogin.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rojad.projectlogin.CounselorApplication;
import com.example.rojad.projectlogin.R;
import com.example.rojad.projectlogin.model.Answer;
import com.example.rojad.projectlogin.model.Question;

import java.util.List;


public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<Answer> answerList;
    private Context mContext;

    public AnswersAdapter(Context context) {
        mContext = context;
    }

    public void addData(List<Answer> data) {
        answerList = data;
        notifyDataSetChanged();
    }

    public Answer getItem(int position) {
        return answerList.get(position);
    }

    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_user_question_view_layout, parent, false);
        return new AnswersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswersAdapter.ViewHolder holder, int position) {
        Answer question = getItem(position);
        holder.question.setText(question.answer);
        holder.answeredBy.setText("Answered By " + CounselorApplication.getInstance().getDBHelperInstance().getUserDetails(question.answerBy).name);
    }

    @Override
    public int getItemCount() {
        return answerList!=null?answerList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView answeredBy;

        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.textview_question);
            answeredBy = (TextView) itemView.findViewById(R.id.textview_answered_count);
        }
    }
}
