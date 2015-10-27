package com.brianmccabe.yournews;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class ClassifiedsTab extends Fragment {
	
	ListView listView;
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> location = new ArrayList<String>();
	ArrayList<String> price = new ArrayList<String>();
	ArrayList<String> contact = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();
	//ArrayList<byte[]> imgint = new ArrayList<byte[]>();
   // Bitmap bitmap;
    static ProgressDialog dialog;
    static AnimationDrawable frameAnimation;
	static ProgressBar mProgressBar;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	titles = new ArrayList<String>();
    	location = new ArrayList<String>();
    	price = new ArrayList<String>();
    	//imgint = new ArrayList<byte[]>();
    	contact = new ArrayList<String>();
    	description = new ArrayList<String>();
    	
    	View rootView = inflater.inflate(R.layout.activity_classifieds, container, false);
        return rootView;
        
        
    }
    
    @Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        
        //if(titles.isEmpty()){
    		mProgressBar = (ProgressBar) getActivity().findViewById(R.id.my_progress);
            mProgressBar.setBackgroundResource(R.drawable.ani_icon);

            frameAnimation = (AnimationDrawable) mProgressBar.getBackground();

            frameAnimation.start();
    	//}
        
        Firebase ref = new Firebase("https://dazzling-inferno-7627.firebaseio.com/yourLocal/classifieds/");
    	ref.addValueEventListener(new ValueEventListener() {
  	      
    		@Override
    	      public void onDataChange(DataSnapshot snapshot) {
    			System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
    	        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
    	        
    				Classifieds post = postSnapshot.getValue(Classifieds.class);
    				if(post.getTown().equals(EntryScreen.town)){
    					
    				
    	            titles.add(post.getTitle());
    	            //String img = post.getImage();
    	          //  byte[] data = Base64.decode(img, Base64.DEFAULT);
    	           // bitmap  = BitmapFactory.decodeByteArray(data, 0, data.length);
    	            //imgint.add(data);
    	            price.add(post.getPrice());
    	            location.add(post.getLocation());
    	            contact.add(post.getContact());
    	            description.add(post.getDescription());
    				}
    	          }
    	          
    	        	listView = (ListView) getActivity().findViewById(R.id.listView4);
    	    		
    	            CustomList adapter = new  CustomList(getActivity(), titles, price);
    	    		
    	    		listView.setAdapter(adapter);
    	    		
    	    		listView.setOnItemClickListener(new OnItemClickListener() {
    	                public void onItemClick(AdapterView<?> parent, View v,
    	                    int position, long id) {
    	                	
    	                	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	                	//imgint.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
    	                	//byte[] byteArray = imgint.get(position);
    	                	
    	                	Intent intent = new Intent(getActivity(), ShowClassified.class);
    	                	intent.putExtra("title", titles.get(position));
    	                	//intent.putExtra("image", byteArray);
    	                	intent.putExtra("location", location.get(position));
    	                	intent.putExtra("price", price.get(position));
    	                	intent.putExtra("contact", contact.get(position));
    	                	intent.putExtra("description", description.get(position));
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
    
    
}