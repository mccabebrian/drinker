package com.brianmccabe.yournews;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.firebase.client.Firebase;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddClassified extends Activity {

	private EditText title, description, contact, price, location;
	Context ctx = this;
	ImageView imgview;
	Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Firebase.setAndroidContext(this);
		setContentView(R.layout.activity_add_classified);
		
		ActionBar actionBar = getActionBar();
		 
        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(true);
 
        // Screen handling while hiding Actionbar title.
        actionBar.setDisplayShowTitleEnabled(true);
        
        View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home : R.id.disableHome);
        ((View) homeIcon.getParent()).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        ((View) homeIcon).setVisibility(View.GONE);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        ImageButton mImageButton = (ImageButton) mCustomView.findViewById(R.id.imageButton);
        mImageButton.setVisibility(View.INVISIBLE);
		mTitleTextView.setText("Localeyes'd");
		actionBar.setCustomView(mCustomView);
		actionBar.setDisplayShowCustomEnabled(true);
		
		title = (EditText) findViewById(R.id.title);
		description = (EditText) findViewById(R.id.description);
		contact = (EditText) findViewById(R.id.contact);
		price = (EditText) findViewById(R.id.price);
		location = (EditText) findViewById(R.id.location);
		imgview = (ImageView) findViewById(R.id.imageView1);
	}
	
	public void selectImage(View view)
	{
		Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(pickPhoto , 1);
		
	}
	
	public void goHome(View view){
		finish();
	}
		
	
	public void newClassified(View view){
		
		String fireTitle = title.getText().toString();
		String fireDescription = description.getText().toString();
		String fireContact = contact.getText().toString();
		String firePrice = price.getText().toString();
		String fireLocation = location.getText().toString();
		
		if(fireTitle.equals("") || fireDescription.equals("") || fireContact.equals("") || firePrice.equals("")
				|| fireLocation.equals("") || bitmap.equals(null)){
			Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
		}
		else{
			Firebase myFirebaseRef = new Firebase("https://dazzling-inferno-7627.firebaseio.com");
	
			Firebase classifiedRef = myFirebaseRef.child("yourLocal").child("classifieds").child(fireTitle);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			byte[] bArray = bos.toByteArray();
			String imageFile = Base64.encodeToString(bArray, Base64.DEFAULT); 
			Classifieds classified = new Classifieds(fireTitle, fireDescription, fireContact,
				firePrice,Home.town, fireLocation,  imageFile);
			classifiedRef.setValue(classified);
		
			Toast.makeText(this, "Success!!!", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) {
		case 0:
		    if(resultCode == RESULT_OK){  
		        Uri selectedImage = imageReturnedIntent.getData();
		        Toast.makeText(ctx, "" + selectedImage, Toast.LENGTH_LONG).show();
		    }

		break; 
		case 1:
		    if(resultCode == RESULT_OK){  
		        Uri selectedImage = imageReturnedIntent.getData();
		        try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
					Toast.makeText(ctx, "" + bitmap, Toast.LENGTH_LONG).show();
					imgview.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Toast.makeText(ctx, "" + selectedImage, Toast.LENGTH_LONG).show();
		    }
		break;
		}
		}

}
