package com.brianmccabe.yournews;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class NewsList extends ArrayAdapter<String>{
 
private final Activity context;
private final ArrayList<String> article;

public NewsList(Activity context,ArrayList<String> article)
{
	super(context, R.layout.news_list, article);
	this.context = context;
	this.article = article;
	
}

@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.news_list, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
		txtTitle.setText(article.get(position));
		
		return rowView;
	}
}
