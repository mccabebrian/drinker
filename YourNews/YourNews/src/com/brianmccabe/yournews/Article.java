package com.brianmccabe.yournews;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Article extends ActionBarActivity {
	
	WebView webview;
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		ActionBar actionBar = getActionBar();
		 
        actionBar.setDisplayShowHomeEnabled(true);
 
        actionBar.setDisplayShowTitleEnabled(true);
        
        View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home : R.id.disableHome);
        ((View) homeIcon.getParent()).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        ((View) homeIcon).setVisibility(View.GONE);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        ImageButton mImageButton = (ImageButton) mCustomView.findViewById(R.id.imageButton);
        mImageButton.setVisibility(View.INVISIBLE);
		mTitleTextView.setText("Localeyesd");
		actionBar.setCustomView(mCustomView);
		actionBar.setDisplayShowCustomEnabled(true);
		
		url = getIntent().getStringExtra("link");
		Log.d("url", url);
		webview = new WebView(this);
		webview.setWebViewClient(new WebViewClient());
		setContentView(webview);
		webview.loadUrl(url);
	}

	public void goHome(View view){
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
