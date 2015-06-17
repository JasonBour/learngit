package com.infzm.slidingmenu.demo.fragment;

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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.tools.person;
import com.infzm.slidingmenu.demo.tools.sql;
import com.infzm.slidingmenu.demo.tools.sqlOperation;

/*
 * 个人资料页
 */
public class personalInfo extends Fragment {
	public Spinner spin ;
	public EditText name,phone,address ;
	public Button submit ;
	public View view ;
   public person person ;
    public sql sql ;
    public sqlOperation operation ;
    private HttpResponse response;  
   
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.personal, null);
		
			 sql = new sql(this.getActivity().getApplicationContext());
			
	initView();	
		
	
		return view;
	}
	

	private void initView() {
		select();
		submit = (Button)view.findViewById(R.id.button);		
	    name = (EditText)view.findViewById(R.id.name);
	   name.setText(person.getName());
	    address = (EditText)view.findViewById(R.id.editText);
	    address.setText(person.getAddress());
	    phone = (EditText)view.findViewById(R.id.adress);
	   phone.setText(person.getPhone());
	    submit.setOnClickListener(new OnClickListener(){
       // 提交按钮
			@Override
			public void onClick(View v) {
				 
				final HttpClient httpclient = new DefaultHttpClient();  
	            final HttpPost httppost = new HttpPost("http://1.jasonandart.sinaapp.com/Jason.php");  
	   new Thread(){
	         public void run(){
	            try {  
	                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);  
	                nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));  
	                nameValuePairs.add(new BasicNameValuePair("address",address.getText().toString()));  
	                nameValuePairs.add(new BasicNameValuePair("phone",phone.getText().toString()));  
	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));    //设置编码很重要 
	           person = new person(name.getText().toString(),phone.getText().toString(),address.getText().toString());
	              Log.i("jason", person.toString());
	          	save();
	               response = httpclient.execute(httppost);  
	              //  tmpString = EntityUtils.toString(response.getEntity());  
	                
	            
	            } catch (ClientProtocolException e) {  
	                
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }}
	       }.start();
				
				
			
			}

		});
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public  void save(){
		operation =  new sqlOperation(this.getActivity().getApplicationContext());
		
		operation.insert(person);
		
	} 
	public  void select(){
		
		operation =  new sqlOperation(this.getActivity().getApplicationContext());
		person =operation.select(1);
		
		
	}


}
