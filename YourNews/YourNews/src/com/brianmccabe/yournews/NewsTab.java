package com.brianmccabe.yournews;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
 
public class NewsTab extends Fragment {
	
	String location;
	ListView listView;
	static ProgressDialog dialog;
	static ArrayAdapter<String> adapter;
	static AnimationDrawable frameAnimation;
	static ProgressBar mProgressBar;
	static String url;
	public static ArrayList<String> results;
	public static ArrayList<String> urlResults;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
        View rootView = inflater.inflate(R.layout.activity_news_tab, container, false);
        
        results = new ArrayList<String>();
        urlResults = new ArrayList<String>();
        return rootView;
        
        
    }
    
    @Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        if(results.isEmpty()){
       	 mProgressBar = (ProgressBar) getActivity().findViewById(R.id.my_progress);
            mProgressBar.setBackgroundResource(R.drawable.ani_icon);

            frameAnimation = (AnimationDrawable) mProgressBar.getBackground();

            frameAnimation.start();
            }
       
        listView = (ListView) getActivity().findViewById(R.id.listView2);
        
		new getNews(EntryScreen.town).execute();
        	
    }
    
    
  
    public class getNews extends AsyncTask<Void, Void, Void> {
    	String link;
    	String strAddress;
    	String title;
    	
    	public getNews(String strAddress){
    		this.strAddress = strAddress;
    	}

    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		results.clear();
    		urlResults.clear();
    	}

    	@Override
    	protected Void doInBackground(Void... params) {
    		StringBuffer buffer = new StringBuffer();
    		url = "https://www.google.ie/search?hl=en&gl=ie&tbm=nws&authuser=0&q="+strAddress+"&oq="+strAddress+"&gs_l=news-cc.3..43j43i53.2934.6778.0.7965.10.6.1.3.3.0.247.799.0j5j1.6.0...0.0...1ac.1.-Y98vYOgY9k";
    		try {
    			 
    			 Document document = Jsoup.connect(url).get();
    			 Elements topicList = document.select("a.l._HId");
                 buffer.append("Topic list\r\n");
                
                 System.out.println("url = " + url);
                 for (int i = 0; i < topicList.size(); i++) {
                	 
                	 Element heading = topicList.get(i);
                     title = heading.text();
                     
                      link = heading.attr("abs:href");
                      urlResults.add(link);
                      results.add(title);
                    	               
                 }
               

    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		return null;
    	}

    	@Override
    	protected void onPostExecute(Void result) {
    		 
    		//adapter = new ArrayAdapter<String>(getActivity(),
				//	android.R.layout.simple_list_item_1, results);
    		
    		NewsList adapter = new  NewsList(getActivity(), results);
			
			listView.setAdapter(adapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v,
	                int position, long id) {
	            		String link = urlResults.get(position);
	            		Intent intent = new Intent(getActivity(), Article.class);
	            		intent.putExtra("link", link);
	            		startActivity(intent);
	            	}
			});
			frameAnimation.stop();
			mProgressBar.setVisibility(View.INVISIBLE);
    	}
    }
    
		
		
	}
   