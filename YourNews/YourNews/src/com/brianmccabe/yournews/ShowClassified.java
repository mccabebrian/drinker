package com.brianmccabe.yournews;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowClassified extends Activity {
	
	TextView title, price, contact, location, description;
	ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_classified);
		
		title = (TextView) findViewById(R.id.title);
		price = (TextView) findViewById(R.id.price);
		contact = (TextView) findViewById(R.id.contact);
		location = (TextView) findViewById(R.id.location);
		description = (TextView) findViewById(R.id.description);
		img = (ImageView) findViewById(R.id.image);
		

		Intent intent = getIntent();
		String passTitle = intent.getExtras().getString("title");
		//byte[] byteArray = getIntent().getByteArrayExtra("image");
		//Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		String passPrice = intent.getExtras().getString("price");
		String passDescription = intent.getExtras().getString("description");
		String passContact = intent.getExtras().getString("contact");
		String passLocation = intent.getExtras().getString("location");
		
		title.setText(passTitle);
		price.setText(passPrice);
		contact.setText(passContact);
		location.setText(passLocation);
		description.setText(passDescription);
		//img.setImageBitmap(bmp);

	}
}
