package com.brianmccabe.yournews;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomList extends ArrayAdapter<String>{
 
private final Activity context;
private final ArrayList<String> web;
private final ArrayList<String> dates;

public CustomList(Activity context,ArrayList<String> web,ArrayList<String> dates)
{
	super(context, R.layout.list_single, web);
	this.context = context;
	this.web = web;
	this.dates = dates;
 
}

@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		TextView txtDate = (TextView) rowView.findViewById(R.id.txt2);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(web.get(position));
		txtDate.setText(dates.get(position));
		//Bitmap bitmap;
		//bitmap  = BitmapFactory.decodeByteArray(imageId.get(position), 0, imageId.get(position).length);
		//imageView.setImageBitmap(bitmap);
		
		return rowView;
		
	}
}
