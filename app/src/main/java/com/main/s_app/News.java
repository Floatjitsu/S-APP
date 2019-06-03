package com.main.s_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class News extends Fragment {

    SharedPreferences sharedPreferences;

    Toolbar mToolbar;
    RecyclerView mNews;
    RecyclerView.Adapter mNewsAdapter;
    ProgressBar mProgressBar;
    TextView mTextNoSubscriptions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_news, container, false);

        mToolbar = myView.findViewById(R.id.toolbar_news);
        mProgressBar = myView.findViewById(R.id.news_loading_spinner);
        mTextNoSubscriptions = myView.findViewById(R.id.text_no_subscriptions);

        mNews = myView.findViewById(R.id.rv_news);
        mNews.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(getActivity() != null) { //Parent Activity is available
            sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar); //Set Toolbar
        }

        setHasOptionsMenu(true);

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Start parsing the RSS Feed
        mNews.setVisibility(View.GONE);
        mTextNoSubscriptions.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        new RssFeedBackgroundHandler().execute(getUrlsByPreferences());
    }

    private String [] getUrlsByPreferences() {
        String [] urlKeys = Constants.getSharedPreferencesKeys();
        ArrayList<String> parseUrls = new ArrayList<>();
        for(String urlKey : urlKeys) {
            if(sharedPreferences.getBoolean(urlKey, false)) {
                parseUrls.add(Constants.getUrlByPreferenceKey(urlKey));
            }
        }
        String [] urls = new String [parseUrls.size()];
        urls = parseUrls.toArray(urls);
        return urls;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.manage_news_feed) {
            startActivity(new Intent(getActivity(), ManageRssFeed.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void parseXmlToRecyclerView(String [] urls) {
        try {
            //Set up ArrayList for RecyclerView
            final ArrayList<NewsModel> newsModelList = new ArrayList<>();

            for(String url : urls) {

                URL rssUrl = new URL(url);
                URLConnection connection = rssUrl.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                //Convert the inputStream from the connection and build a document out of it
                String s = convertInputStream(connection.getInputStream());
                Document doc = builder.parse(new ByteArrayInputStream(s.getBytes()));

                //Get the provider name outside of the item NodeList
                String provider = doc.getElementsByTagName("title").item(0).getTextContent();

                //Get all the items for the current rss feed url
                NodeList nodeList = doc.getElementsByTagName("item");
                for(int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String link = element.getElementsByTagName("link").item(0).getTextContent();
                    String date = element.getElementsByTagName("pubDate").item(0).getTextContent();
                    if(element.getElementsByTagName("description").getLength() > 0) {
                        String desc = element.getElementsByTagName("description").item(0).getTextContent();
                        newsModelList.add(new NewsModel(provider, title, link, date, desc));
                    } else {
                        newsModelList.add(new NewsModel(provider, title, link, date, ""));
                    }
                }
            }
            if(getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        if(newsModelList.size() > 0) {
                            mNews.setVisibility(View.VISIBLE);
                            mNewsAdapter = new NewsAdapter(newsModelList, getActivity());
                            mNews.setAdapter(mNewsAdapter);
                            mTextNoSubscriptions.setVisibility(View.GONE);
                        } else {
                            mNews.setVisibility(View.GONE);
                            mTextNoSubscriptions.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error while parsing RSS Information", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    Converts an InputStream to a new String without xml version tags
     */
    private String convertInputStream(InputStream inputStream) {
        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        if(result.contains("<?xml version=\"1.0\"?>")) {
            return result.replace("<?xml version=\"1.0\"?>", "");
        } else if(result.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
            return result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public class RssFeedBackgroundHandler extends AsyncTask<String, Void, Void> {

        @Override
        protected final Void doInBackground(String... urls) {
            parseXmlToRecyclerView(urls);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
