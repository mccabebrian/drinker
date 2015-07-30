package com.example.drinker;

import android.provider.BaseColumns;

public class TableData {
	
	public TableData()
	{
		
	}
	
	public static abstract class TableInfo implements BaseColumns
	{
		
		public static final String BEER_NAME = "beer_name";
		public static final String BEER_DESCRIPTION = "beer_description";
		public static final String DATABASE_NAME = "beers_db";
		public static final String TABLE_NAME = "beer_info";
		
	}

}