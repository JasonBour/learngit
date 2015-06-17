package com.infzm.slidingmenu.demo.tools;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class sqlOperation {
	  private sql helper;
		public sqlOperation(Context context){
			this.helper = new  sql(context);

		}	
		
		
		/*
		 * 直接进行覆盖
		 */
		public void insert(person person){
			SQLiteDatabase db = helper.getWritableDatabase();  //这个是要进行写
		
			db.execSQL("insert into person(name,phone,address)values( ?,?,? )",
					new Object[]{person.getName(),person.getPhone(),person.getAddress()});
				
		}
		
		public  void  update(person person){
			SQLiteDatabase db = helper.getWritableDatabase();  //这个是要进行写
			db.execSQL("update person set name= ?,phone =?,address=? where id= 1",new Object[]{person.getName(),person.getPhone(),person.getAddress()});
		} 
		
		public person  select(Integer personid){
			SQLiteDatabase  db = helper.getReadableDatabase();   //和getWritableDatabase();不同  
			/**android使用getWritableDatabase()和getReadableDatabase()方法都可以获取一个用于操作数据库的SQLiteDatabase实例。
	              其中getWritableDatabase() 方法以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，
	              倘若使用的是getWritableDatabase() 方法就会出错。
	             getReadableDatabase()方法则是先以读写方式打开数据库，如果数据库的磁盘空间满了，
	             就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。**/
			Cursor cursor = db.rawQuery("select * from person where id = ?",new String[]{personid.toString()});
		if(cursor.moveToFirst()){   //moveToFirst()返回的是布尔值   如果存在结果返回true   
			String address = cursor.getString(cursor.getColumnIndex("address"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
	 return new person(name,phone,address);
		}
		cursor.close();
		return null;
		}
		


}
