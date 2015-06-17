package com.infzm.slidingmenu.demo.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.widget.Toast;

public class submit {
	 private HttpResponse response;  
	 private  String  tmpString ;
	
	public submit(final String title,final String detail,final String book){
		

		final HttpClient httpclient = new DefaultHttpClient();  
        final HttpPost httppost = new HttpPost("http://1.universities.sinaapp.com/index.php");  
   new Thread(){
     public void run(){
        try {  
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);  
            nameValuePairs.add(new BasicNameValuePair("title", title));  
            nameValuePairs.add(new BasicNameValuePair("detail",detail));  
            nameValuePairs.add(new BasicNameValuePair("book",book));  
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));    //设置编码很重要 
            response = httpclient.execute(httppost);  
            tmpString = EntityUtils.toString(response.getEntity());  
            
          
        } catch (ClientProtocolException e) {  
            
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }}
   }.start();
		
		
	}
	
}
