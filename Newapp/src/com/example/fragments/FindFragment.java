package com.example.fragments;



import com.example.newapp.R;
import com.example.newapp.activity;
import com.example.newapp.login;
import com.example.newapp.viewpager;
import com.example.utils.DateTimePickDialogUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FindFragment extends Fragment implements OnClickListener{
   public Button button ;
	public WebView web ;
	public View view  ;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return  view  = inflater.inflate(R.layout.find_fragment, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
       
		 button = (Button)view.findViewById(R.id.button);
		 
		 button.setOnClickListener(this);
		 
	}
	
	
	

	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();
		//intent.setClass(getActivity(), viewpager.class);
		intent.setClass(getActivity(), activity.class);
		startActivity(intent);
		
	}
}
