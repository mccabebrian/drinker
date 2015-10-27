package com.brianmccabe.yournews;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
 
public class EventsTab extends Fragment {
	
	ListView listView;
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> descriptions = new ArrayList<String>();
	ArrayList<String> dates = new ArrayList<String>();
	ArrayList<String> urls = new ArrayList<String>();
	int counter = 0;
	float x1,x2;
    float y1, y2;
   
    static ProgressDialog dialog;
    static AnimationDrawable frameAnimation;
	static ProgressBar mProgressBar;
	CustomList adapter;
	
    @SuppressWarnings("static-access")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	//Thread.currentThread().setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    	
    	titles = new ArrayList<String>();
    	urls = new ArrayList<String>();
    	dates = new ArrayList<String>();
    	descriptions = new ArrayList<String>();
    	
    	View rootView = inflater.inflate(R.layout.activity_events_tab, container, false);
        return rootView;
        
    }
    
    @Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        Thread.currentThread().setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        	listView = (ListView) getActivity().findViewById(R.id.listView3);
        	adapter = new  CustomList(getActivity(), titles, dates);
    		mProgressBar = (ProgressBar) getActivity().findViewById(R.id.my_progress);
            mProgressBar.setBackgroundResource(R.drawable.ani_icon);

            frameAnimation = (AnimationDrawable) mProgressBar.getBackground();

            frameAnimation.start();
    	
        
        Firebase ref = new Firebase("https://dazzling-inferno-7627.firebaseio.com/yourLocal/events/");
    	ref.addValueEventListener(new ValueEventListener() {
  	      
    		@Override
    	      public void onDataChange(DataSnapshot snapshot) {
    			System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
    			
    			
    			for (DataSnapshot postSnapshot: snapshot.getChildren()) {
    				Event post = postSnapshot.getValue(Event.class);
    	            titles.add(post.getTitle());
    	            dates.add(post.getDate());
    	            descriptions.add(post.getDescription());
    	            
    	          }
    	          
    	        	
    	    		
    	           
    	    		
    	    		listView.setAdapter(adapter);
    	    		
    	    		listView.setOnItemClickListener(new OnItemClickListener() {
    	                public void onItemClick(AdapterView<?> parent, View v,
    	                    int position, long id) {
    	                	
    	                	Intent intent = new Intent(getActivity(), ShowEvent.class);
    	                	intent.putExtra("title", titles.get(position));
    	                	intent.putExtra("date", dates.get(position));
    	                	intent.putExtra("description", descriptions.get(position));
    	                	startActivity(intent);	
    	                	
    	                }
    	    		});
    	    		frameAnimation.stop();
    	    		mProgressBar.setVisibility(View.INVISIBLE);
    	      }
    	      @Override
    	      public void onCancelled(FirebaseError firebaseError) {
    	          System.out.println("The read failed: " + firebaseError.getMessage());
    	      }
    	  });
    	
    	
		
    }
    
    public static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    	 @Override
    	 public void uncaughtException(Thread thread, Throwable ex) {
    	 if(ex.getClass().equals(OutOfMemoryError.class))
    	 {
    	 try {
    	   android.os.Debug.dumpHprofData("/sdcard/dump.hprof");
    	 } 
    	 catch (IOException e) {
    	   e.printStackTrace();
    	 }
    	 }
    	 ex.printStackTrace();
    	 }
    }
    
    
}