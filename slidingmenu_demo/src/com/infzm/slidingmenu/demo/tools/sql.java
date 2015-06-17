package com.infzm.slidingmenu.demo.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {
	SQLiteDatabase db ; 
	SQLiteOpenHelper dbOpenHelper;
	
	/*
	 * 这个构造函数用来第一次建数据库
	 */
	
	public sql(Context context) {
		super(context, "jason", null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * 建表
		 */
		
		
        db.execSQL("CREATE TABLE person(id integer primary key autoincrement, name varchar(20), phone varchar(20),address varchar(30))");  
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	 @Override  
	    public void onOpen(SQLiteDatabase db) {  
	        // TODO 每次成功打开数据库后首先被执行  
	        super.onOpen(db);  
	    }  
	  
	 
	
	
	
	
}
