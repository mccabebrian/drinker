package com.example.drinker;


import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	TextView tv;
	String name = "";
	String description = "";
	Context ctx = this;
	ImageButton ib, ib2;
	int counter = 0;
	float x1,x2;
    float y1, y2;
	DatabaseOperations DB = new DatabaseOperations(ctx);
	ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView1);
        ib = (ImageButton) findViewById(R.id.imageButton1);
        ib2 = (ImageButton) findViewById(R.id.imageButton2);
        
        ib.setVisibility(View.GONE);
        ib2.setVisibility(View.GONE);
        
       
        
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        new getBeer().execute();
       
    }
    
    public void Tick(View view)
    {
    	 new getBeer().execute();
    	 
    	 DB.putInformation(DB, name, description);
    	 if(counter == 10){
    		 List();
    		 counter = 0;
    	 }
    }
    
    public void Cross(View view)
    {
    	 new getBeer().execute();
    }

    public void List()
    {
    	AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Beers liked: ");
        DB.getNames();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
        		android.R.layout.select_dialog_singlechoice, DB.names);
        
        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

       builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                       		 MainActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }
    
 // onTouchEvent () method gets called when User performs any touch event on screen 
    // Method to handle touch event like left to right swap and right to left swap
     public boolean onTouchEvent(MotionEvent touchevent) 
                 {
                   switch (touchevent.getAction())
                              {
                                     // when user first touches the screen we get x and y coordinate
                                      case MotionEvent.ACTION_DOWN: 
                                      {
                                          x1 = touchevent.getX();
                                          y1 = touchevent.getY();
                                          break;
                                     }
                                      case MotionEvent.ACTION_UP: 
                                      {
                                          x2 = touchevent.getX();
                                          y2 = touchevent.getY(); 

                                          //if left to right sweep event on screen
                                          if (x1 < x2) 
                                          {
                                              Toast.makeText(this, "Beer liked", Toast.LENGTH_LONG).show();
                                              new getBeer().execute();
                                         	 
                                         	 DB.putInformation(DB, name, description);
                                         	 if(counter == 10){
                                         		 List();
                                         		 counter = 0;
                                         	 }
                                           }
                                         
                                          // if right to left sweep event on screen
                                          if (x1 > x2)
                                          {
                                              Toast.makeText(this, "Beer disliked", Toast.LENGTH_LONG).show();
                                              new getBeer().execute();
                                          }
                                         
                                          // if UP to Down sweep event on screen
                                          if (y1 < y2) 
                                          {
                                             // Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                                          }
                                         
                                          //if Down to UP sweep event on screen
                                          if (y1 > y2)
                                          {
                                              //Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                                           }
                                          break;
                                      }
                              }
                              return false;
                 }

    
    private class getBeer extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			 progress = new ProgressDialog(ctx);
	      		progress.setTitle("Loading");
	      		progress.setMessage("Please wait...");
	      		progress.show();

		}

		@SuppressLint("DefaultLocale") protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			String jsonStr = sh.makeServiceCall("http://api.brewerydb.com/v2/beer/random/?key=e5c14921dc17c2a6a8ad27fb9d19edf6", ServiceHandler.GET);
			
			Log.d("Response: ", "> " + jsonStr);
			
				if (jsonStr != null) {
				
				try {
					JSONObject t = new JSONObject(jsonStr);
					
					JSONObject jo = t.getJSONObject("data");
					
					name = jo.getString("name");
					description = jo.getString("description");
					
					if(jo.getString("description").equals(null))
					{
						description = "";
					}
					Log.d("name", "> " + name);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				 Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.d("name", "> " + name);
			tv.setText(name + "\n\n" + description);
			description = "";
			ib.setVisibility(View.VISIBLE);
	        ib2.setVisibility(View.VISIBLE);
	        progress.dismiss();
	        counter ++;
		}

		
	}
}
