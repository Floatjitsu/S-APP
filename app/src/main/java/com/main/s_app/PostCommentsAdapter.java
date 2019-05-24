package com.main.s_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.s_app.com.main.s_app.firebase.Comment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.ViewHolder> {

    private ArrayList<Comment> mComments;

    public PostCommentsAdapter(ArrayList<Comment> mComments) {
        this.mComments = mComments;
    }

    @NonNull
    @Override
    public PostCommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.post_comment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCommentsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.username.setText(mComments.get(i).getUser().getUsername());
        viewHolder.date.setText(getPrettyDate(mComments.get(i).getTimeStamp()));
        viewHolder.comment.setText(mComments.get(i).getComment());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    private String getPrettyDate(long timeStamp) {
        Date date = new Date(timeStamp);
        return DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, date, comment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.comment_user);
            date = itemView.findViewById(R.id.comment_date);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
