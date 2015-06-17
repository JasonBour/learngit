package com.infzm.slidingmenu.demo.fragment;

import com.infzm.slidingmenu.demo.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 
 * @author wuwenjie
 * @description 今日
 * 更改之后是 工作群聊
 */



public class TodayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  
 {
public View view ;
public TextView tv ;
public SwipeRefreshLayout 	swipeRefreshLayout;
	
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
			Log.i("jason", "oncreateView");
		view = inflater.inflate(R.layout.frag_today, null);	
		tv = (TextView)view.findViewById(R.id.textView1);
		 swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		 swipeRefreshLayout.setColorScheme(R.color.gold, R.color.bisque, R.color.beige, R.color.pink);
		 swipeRefreshLayout.setOnRefreshListener(this);
		
		return view;
	
	}
	
	
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onRefresh() {
		tv.setText("正在刷新");
		               // TODO Auto-generated method stub
		                 new Handler().postDelayed(new Runnable() {
		                    
	                   @Override
		                   public void run() {
	                       // TODO Auto-generated method stub
	                       tv.setText("刷新完成");
		                        swipeRefreshLayout.setRefreshing(false);
		                   }
		                 }, 6000);
		             }
		
	}

	

