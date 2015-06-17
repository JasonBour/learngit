package com.infzm.slidingmenu.demo.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.tools.Json;
import com.infzm.slidingmenu.demo.view.Details;
import com.infzm.slidingmenu.demo.view.listAdapter;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author 
 * @description 所有笔记
 */
public class AllNote extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
 private View view ;
 private ListView listView=null;  
 private Context context; 
 public TextView text ;
 private SwipeRefreshLayout mSwipeLayout;
 public ArrayList<String > title = new ArrayList<String>();
 public ArrayList<Integer>  detail = new ArrayList<Integer>();
 public  int length = 0 ;
 public String URL = "http://1.universities.sinaapp.com/index.php";    
 public String result = "";
 public Handler handler = null;
 public     final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
 private ImageLoader loader ;
 public  DisplayImageOptions options; 
 public listAdapter adapter ;
	
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
		view = inflater.inflate(R.layout.frag_lastlist, null);
		context = view.getContext();
		
		InitImageLoader(context);  //初始化那个异步加载图片框架
		Toast.makeText(context, "下拉刷新", 1).show();
		 mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);  
		 mSwipeLayout.setOnRefreshListener(this);  
	        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,   android.R.color.holo_orange_light, android.R.color.holo_red_light);
		listView=(ListView)view.findViewById(R.id.list);
		 listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			Toast.makeText(context, "你点击了"+arg2, 1).show();
	   //switch语句里面不能用long   
			
			//在这里获取到list里面的数据
			Map<String ,Object> map = list.get(arg2);	
			Iterator<Entry<String, Object>> itor = map.entrySet().iterator(); 
			while(itor.hasNext()) { 
				/*
				 * 会取出两个String  第一个是关于detail  第二个是标题 
				 */
			Entry<String, Object> entry = itor.next(); 
		   Log.i("json",entry.getValue().toString()); 
			}
			
			
				//跳转到新的activity
				Intent intent = new Intent();
				intent.setClass(context, Details.class);
				Bundle	bundle = new Bundle();
				bundle.putString("title", "hhhhh");
				bundle.putString("books", " this is the book");
				bundle.putInt("id", 0);
				intent.putExtras(bundle);
				startActivity(intent);
					
				
				
			}	
	
	
			 
	
		 });
       
      
        
		
		
		
		
		return view;
	}
	
	private void InitImageLoader(Context context2) {
		
		 //缓存文件的目录  
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "universalimageloader/Cache");   
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)  
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽   
                .threadPoolSize(3) //线程池内线程的数量  
                .threadPriority(Thread.NORM_PRIORITY - 2)  
                .denyCacheImageMultipleSizesInMemory()  
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密  
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))  
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值  
                .diskCacheSize(50 * 1024 * 1024)  // SD卡缓存的最大值  
                .tasksProcessingOrder(QueueProcessingType.LIFO)  
                // 由原先的discCache -> diskCache  
                .diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径    
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间    
                .writeDebugLogs() // Remove for release app  
                .build();  
        //全局初始化此配置    
        ImageLoader.getInstance().init(config);  
        loader = ImageLoader.getInstance();
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
        options = new DisplayImageOptions.Builder()  
                .showImageOnLoading(R.drawable.add) // 设置图片下载期间显示的图片  
                .showImageForEmptyUri(R.drawable.add) // 设置图片Uri为空或是错误的时候显示的图片  
                .showImageOnFail(R.drawable.add) // 设置图片加载或解码过程中发生错误显示的图片  
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中  
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中  
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片  
                .build(); // 构建完成  
    }  
		
		
		
		
	

	public int getCount(){
		
		return list.size();
	}
	
	
	public Object getItem(int position){
		
		
		
		return list.get(position);
		
		
	}
	
	
	public long getItemId(int position){
		return position;
		
		
	}
	
	
	
	


	/*
	 *   获取到信息的地方  
	 */
	public void getData(){  
		

      
        thread.start();
        handler = new Handler(){
		       @Override
		      public void handleMessage(Message msg){
		        super.handleMessage(msg);
		           if(msg.what == 1){
		           try {         
		        	   //json解析
			              JSONObject jo = new JSONObject(result);
			              JSONArray jsonArray = jo.getJSONArray("students");
			               for(int i =0 ;i<jsonArray.length();i++){
			            	   final Map<String, Object>    map=new HashMap<String, Object>();
				            JSONObject jo1 = (JSONObject) jsonArray.get(i);
				            map.put("title", jo1.getString("name"));
				            map.put("detail", jo1.getString("age")); 
				            list.add(map);   
				            
			               }
			               adapter = new listAdapter(context,list);
			               listView.setAdapter(adapter);
			               //话说我为什么要从新写一个adapter啊！
			              // listView.setAdapter(new SimpleAdapter(context, list,R.layout.list,new String[] {"title", "detail"},    new int[] {R.id.title,R.id.detail})); 
		 }catch (Exception e ){
			 e.printStackTrace(); 
			 Log.i("json", "error");
		 }
		                }
		 
		            }
			  };
  
 
       
    }  
	
	 
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}



	 //handler来实现控制
	 
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

	@Override
	public void onRefresh() {
		getData();
		 
		                 new Handler().postDelayed(new Runnable() {
		                    
		                     @Override
		                    public void run() {
		                         // TODO Auto-generated method stub
		                    	
		                        mSwipeLayout.setRefreshing(false);
		                     }
		               }, 6000);
		             }
		
	}
		 
		
	  
		 
	 






