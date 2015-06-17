package com.infzm.slidingmenu.demo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.view.CornerListView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
/**
 * 
 * @author 
 * @description 设置页
 */
public class MySettingsFragment extends Fragment {
	 private CornerListView cornerListView = null;  
	    private List<Map<String, String>> listData = null;  
	    private SimpleAdapter adapter = null;  
	 
	
	
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
		
		View view = inflater.inflate(R.layout.frag_settings, null);
		
		cornerListView = (CornerListView)view.findViewById(R.id.setting_list);  
        setListData();  
        adapter = new SimpleAdapter(view.getContext(), listData, R.layout.main_tab_setting_list_item ,  
                new String[]{"text"}, new int[]{R.id.tv_system_title});  
                        cornerListView.setAdapter(adapter);  
                        Log.i("Jason", "error");
		return view;
	}
	
	
	
	private void setListData() {
		 listData = new ArrayList<Map<String,String>>();  
		   
	        Map<String,String> map = new HashMap<String, String>();  
	        map.put("text", "分享");  
	        listData.add(map);  
	   
	        map = new HashMap<String, String>();  
	        map.put("text", "检查新版本");  
	        listData.add(map);  
	   
	        map = new HashMap<String, String>();  
	        map.put("text", "反馈意见");  
	        listData.add(map);  
	   
	        map = new HashMap<String, String>();  
	        map.put("text", "关于我们");  
	        listData.add(map);  
	   
	        map = new HashMap<String, String>();  
	        map.put("text", "支持我们");  
	        listData.add(map);  
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
