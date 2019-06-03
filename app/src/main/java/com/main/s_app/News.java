package com.main.s_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.common.util.IOUtils;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class News extends Fragment {

    SharedPreferences sharedPreferences;

    Toolbar mToolbar;
    RecyclerView mNews;
    RecyclerView.Adapter mNewsAdapter;
    ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_news, container, false);

        if(getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);
        }

        boolean soleCollector = sharedPreferences.getBoolean(Constants.SOLE_COLLECTOR, true);

        mToolbar = myView.findViewById(R.id.toolbar_news);
        mProgressBar = myView.findViewById(R.id.news_loading_spinner);

        mNews = myView.findViewById(R.id.rv_news);
        mNews.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(getActivity() != null) { //Parent Activity is available
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar); //Set Toolbar
        }

        setHasOptionsMenu(true);

        //String [] urls = getUrlsByPreferences();
        //new RssFeedBackgroundHandler().execute(urls);

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Start parsing the RSS Feed
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
                        mNewsAdapter = new NewsAdapter(newsModelList, getActivity());
                        mNews.setAdapter(mNewsAdapter);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Exception", "Error", ex);
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
