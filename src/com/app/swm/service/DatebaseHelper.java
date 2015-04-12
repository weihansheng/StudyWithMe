package com.app.swm.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatebaseHelper extends SQLiteOpenHelper {
	 
	 public final static String NAME="drafts.db";
	 public final static int VERSION=1;
	 public DatebaseHelper(Context context) {
	    super(context, NAME, null, VERSION);
	 }

	 @Override
	 public void onCreate(SQLiteDatabase db) {
	  // TODO Auto-generated method stub
	   db.execSQL("CREATE TABLE news (newsid integer primary key autoincrement, title varchar(20), content archar(20),time archar(20),newsPics archar(64))");
	 }

	 @Override
	 public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	  // TODO Auto-generated method stub
	    db.execSQL("DROP TABLE IF EXISTS news");
	    onCreate(db);
	 }

	}