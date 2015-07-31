package com.example.drinker;


import java.util.ArrayList;

import com.example.drinker.TableData.TableInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseOperations extends SQLiteOpenHelper {
	public static final int database_version = 1;
	public String CREATE_QUERY = "CREATE TABLE "+TableInfo.TABLE_NAME+"("+TableInfo.BEER_NAME+" TEXT,"+TableInfo.BEER_DESCRIPTION+ " TEXT );";
	ArrayList<String> names = new ArrayList<String>();
	
	public DatabaseOperations(Context context) {
		super(context, TableInfo.DATABASE_NAME, null, database_version);
		Log.d("Database operations", "Database created");
		
	}

	@Override
	public void onCreate(SQLiteDatabase sdb) 
	{
		
		sdb.execSQL(CREATE_QUERY);
		Log.d("Database operations", "Table created");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void getNames()
	{
        String selectQuery = "SELECT  * FROM " + TableInfo.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do{
            	Log.d("Database operations2", "tester");
                String name = cursor.getString(cursor.getColumnIndex(TableInfo.BEER_NAME));
                names.add(name);
                Log.d("Database operations2", name);
               // Adding contact to list
               
            } while (cursor.moveToNext());
        }
        else{
        	Log.d("Database operations2", "tester2344");
        }
        cursor.close();
	       db.close();
        
	}
	
	
	public String getDescByName(String name)
	{
		  String selectQuery = "SELECT  beer_description FROM " + TableInfo.TABLE_NAME + " WHERE beer_name = '" + name + "'";
		  //Log.d("query", selectQuery);
	        SQLiteDatabase db = this.getWritableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        String res = "";
	        Log.d("query", "tetttt");
	        if (cursor.moveToFirst()){
	        	//
	        	
	        	  return cursor.getString(cursor.getColumnIndex("beer_description"));
	       }
	        else{
	        	cursor.close();
	        	   db.close();
	        	   return null;
	        }
	      
	}
	
	public void putInformation(DatabaseOperations dop, String name, String description)
	{
		SQLiteDatabase SQ = dop.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TableInfo.BEER_NAME, name);
		cv.put(TableInfo.BEER_DESCRIPTION, description);
		long k = SQ.insert(TableInfo.TABLE_NAME, null, cv);
		Log.d("Database operations", "Onew raw inserted");
		
	}
	
	public Cursor getInformation(DatabaseOperations dop)
	{
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String[] columns = {TableInfo.BEER_NAME, TableInfo.BEER_DESCRIPTION};
		Cursor CR = SQ.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
		return CR;
		
		
	}

}

