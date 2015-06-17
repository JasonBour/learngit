package com.infzm.slidingmenu.demo.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.io.*;  
import java.net.*;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Json {

  public ArrayList<String > title = new ArrayList<String>();
  public ArrayList<Integer>  detail = new ArrayList<Integer>();
  public  int length = 0 ;
  public String URL = "http://1.universities.sinaapp.com/index.php";    
  public String result = "";
  public Handler handler = null;
  
 public  Json(){
	  thread.start();
handler = new Handler(){
	       @Override
	      public void handleMessage(Message msg){
	        super.handleMessage(msg);
	           if(msg.what == 1){
	           try {         
	        	   Log.i("json", result);
		              JSONObject jo = new JSONObject(result);
		              JSONArray jsonArray = jo.getJSONArray("students");
		             length = jsonArray.length() ;
		              Log.i("json", length+"");
		 
		               for(int i =0 ;i<jsonArray.length();++i){
			            JSONObject jo1 = (JSONObject) jsonArray.get(i);
			           title.add(jo1.getString("name"));
			              detail.add(jo1.getInt("age"));
		 }        
	 }catch (Exception e ){
		 e.printStackTrace(); 
		 Log.i("json", "error");
	 }
	                }
	 
	            }
		  };
	 
	}
 
 
 final Thread thread =  new Thread(new Runnable(){
		 public void run(){
			 
			 try {
					URL url = new URL(URL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					InputStreamReader read = new InputStreamReader(conn.getInputStream());
					BufferedReader buffer = new BufferedReader(read);
					String readLine = null;
					while((readLine =buffer.readLine())!=null){
						result += readLine;
					}
					 Log.i("json", result+"null");
					read.close();
					conn.disconnect();   
				
					
					
				} catch (Exception e) {
					Log.i("json", "errorOccured");
					e.printStackTrace();
				}
			 
			 Message message = new Message();
			 message.what = 1;
			 handler.sendMessage(message);
		 }
 
		 
	 });
	 
	
  
	 
 }
 
 



