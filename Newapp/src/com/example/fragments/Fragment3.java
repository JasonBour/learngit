package com.example.fragments;



import com.example.newapp.MainActivity;
import com.example.newapp.R;
import com.example.utils.MyApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment3 extends Fragment implements OnClickListener {
   public MyApplication myApplication;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_4, container, false);
		
	
		TextView text = (TextView)view.findViewById(R.id.tvInNew);
		text.setOnClickListener( this );
		
		
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), MainActivity.class);
		startActivity(intent);
		
	}

}
