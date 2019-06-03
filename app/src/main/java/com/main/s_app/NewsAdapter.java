package com.main.s_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mActivityContext;
    private ArrayList<NewsModel> mNewsList;

    NewsAdapter(ArrayList<NewsModel> list, Context context) {
        Collections.sort(list, new Comparator<NewsModel>() {
            @Override
            public int compare(NewsModel o1, NewsModel o2) {
                return o1.getNewsDate().compareToIgnoreCase(o2.getNewsDate());
            }
        });
        mNewsList = list;
        mActivityContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mNewsTitle.setText(mNewsList.get(i).getNewsTitle());
        //Set the url as the content description of the image to make it invisible
        viewHolder.mNewsImage.setContentDescription(mNewsList.get(i).getNewsUrl());
        viewHolder.mNewsDescription.setText(android.text.Html.fromHtml(mNewsList.get(i).getNewsDesc()));
        //viewHolder.mNewsDescription.setText(mNewsList.get(i).getNewsDesc());
        viewHolder.mNewsProvider.setText(mNewsList.get(i).getNewsProvider());
        setUrlImageDesc(mNewsList.get(i).getNewsUrl(), viewHolder.mNewsImage);


    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    private void setUrlImageDesc(String url, final ImageView urlImage) {
        TextCrawler textCrawler = new TextCrawler();
        LinkPreviewCallback callback = new LinkPreviewCallback() {
            @Override
            public void onPre() {

            }

            @Override
            public void onPos(SourceContent sourceContent, boolean b) {
                if(sourceContent.getImages().size() > 0)
                    GlideApp.with(mActivityContext).load(sourceContent.getImages().get(0)).into(urlImage);
            }
        };
        textCrawler.makePreview(callback, url);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNewsTitle, mNewsDescription, mNewsProvider;
        ImageView mNewsImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsTitle = itemView.findViewById(R.id.news_title);
            mNewsDescription = itemView.findViewById(R.id.news_description);
            mNewsImage = itemView.findViewById(R.id.news_image);
            mNewsProvider = itemView.findViewById(R.id.news_provider_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //The url of news model is saved in the content description of the image
            String url = mNewsImage.getContentDescription().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            mActivityContext.startActivity(intent);
        }
    }
}
