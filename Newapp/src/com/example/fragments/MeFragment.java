package com.example.fragments;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.newapp.R;
import com.example.view.CornerListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;

import android.widget.Toast;

public class MeFragment extends Fragment{
    public GridView grid ;
    public View view ;
    private CornerListView cornerListView = null;  
    private TableRow table ;
    private List<Map<String,String>> listData = null;  
    private SimpleAdapter adapter = null;  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//set布局文件 是关于各种设置的
		
		//view = inflater.inflate(R.layout.set,container,false);
	   view = inflater.inflate(R.layout.me_fragment, container,false);	
		
		
		/* 这个只是一种实现
		cornerListView = (CornerListView)view.findViewById(R.id.setting_list);  
        setListData();  
          
        adapter = new SimpleAdapter(view.getContext(), listData, R.layout.main_tab_setting_list_item , new String[]{"text"}, new int[]{R.id.setting_list_item_text});  
        cornerListView.setAdapter(adapter);  
		
		*/
		
		
		
		//这个是关于TableRow的监听事件
	   /**
		table = (TableRow)view.findViewById(R.id.more_page_row0);
		table.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "jason", 1).show();
				
			}
			
		});
		
		**/
		
		
		
		//initView();
		return view ;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
	}
	
	
	public  void initView(){
		 ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();   
		grid = (GridView)view.findViewById(R.id.grid);
		for(int i =1 ; i<=9;i++){
			HashMap<String ,Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.pic1);
			meumList.add(map);  
			
			
			
		}
		
		
	 SimpleAdapter saItem = new SimpleAdapter(this.getActivity(),meumList, R.layout.grid, new String[]{"image"},new int[]{R.id.image}); 
	 grid.setAdapter(saItem);
	 grid.setOnItemClickListener(new OnItemClickListener(){
		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) { 
                
				 Toast.makeText(getActivity(), "你按下了选项："+arg2+1, 0).show();   
                }   
          
			
		});
		
	}
	
	
	
	/**  
     * 设置列表数据  
     */  
    private void setListData(){  
        listData = new ArrayList<Map<String,String>>();  
          
        Map<String,String> map = new HashMap<String, String>();  
        map.put("text", "图库更新");  
        listData.add(map);  
          
        map = new HashMap<String, String>();  
        map.put("text", "收藏图片");  
        listData.add(map);  
          
        map = new HashMap<String, String>();  
        map.put("text", "下载目录");  
        listData.add(map);  
    }  
	
}
