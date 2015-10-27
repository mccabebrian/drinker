package com.brianmccabe.yournews;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowEvent extends Activity {

	TextView title, date, description;
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_event);
		
		title = (TextView) findViewById(R.id.title);
		date = (TextView) findViewById(R.id.date);
		description = (TextView) findViewById(R.id.description);
		image = (ImageView) findViewById(R.id.image);
		
		Intent intent = getIntent();
		String passTitle = intent.getExtras().getString("title");
		//byte[] byteArray = getIntent().getByteArrayExtra("image");
		//Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	
		
		String passDate = intent.getExtras().getString("date");
		String passDescription = intent.getExtras().getString("description");
		
		title.setText(passTitle);
		//image.setImageBitmap(bmp);
		date.setText(passDate);
		description.setText(passDescription);
	}

	
}
