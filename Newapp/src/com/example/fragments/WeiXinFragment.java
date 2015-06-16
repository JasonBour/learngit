package com.example.fragments;





import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.newapp.R;
import com.example.view.ListAdapter;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;  
/*
 * 将所有信息显示出来
 */
public class WeiXinFragment extends Fragment implements OnRefreshListener{
	 private SwipeRefreshLayout mSwipeLayout;
    public String data = "" ;  //将服务器数据以字符串保存下来  之后通过Json解析
     public Handler handler = null;
	public ImageView  mIV ;
	public TextView mIB ;
	public TextView join;
	public ListView   mLV ;
	public TextView  mTV ;
	 private ImageLoader loader ;
	 public  DisplayImageOptions options; 
	 public Context context;
	 public ListAdapter adapter ;
	 public     final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	public View view;
    public Button image ;
    public PopupWindow  popup ;
    public String tag = "jason";
    public String destinationUrl = "http://1.universities.sinaapp.com/index.php";
	public View onCreateView( final LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
				view = inflater.inflate(R.layout.wei_xin_fragment, container, false);
				context = view.getContext();
				InitImageLoader(context); 
				initView();
		return view ;
	}
	
	 
		  
		  
	 

	  @SuppressWarnings("deprecation")
	public  void  initView(){
		  mIV  = (ImageView)view.findViewById(R.id.person);
		  mIB = (TextView)view.findViewById(R.id.detail);
		  mLV = (ListView)view.findViewById(R.id.list);
		  mTV = (TextView)view.findViewById(R.id.content);
		  
		 
		  
		  mLV.setBackgroundColor(0xffc0c0c0);
		  mLV.setCacheColorHint(0);
		 
		  mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);  
		  mSwipeLayout.setOnRefreshListener(this);  
		  mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,   android.R.color.holo_orange_light, android.R.color.holo_red_light);
		
		 
	     
		  //介个有效 MD
		//mLV.setAdapter(new SimpleAdapter(context, list,R.layout.item,new String[] {"image","title", "detail"},  new int[] {R.id.person,R.id.content,R.id.in}));
		  mLV.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				view.findViewById(R.id.attention_tv_go).setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Toast.makeText(view.getContext(), "jason", 1).show();
						View v1 = getLayoutInflater(null).inflate(R.layout.item, null);
						  join = (TextView)v1.findViewById(R.id.attention_tv_go);
					join.setBackgroundColor(0xFF6db7ff);
						
					}});
				
			}});
		
			  
			  
			  
		 
		  
		  
		  
		  
		  
		  
		  
	  }

	private void InitImageLoader(Context context2) {
		
		 //缓存文件的目录  
	   File cacheDir = StorageUtils.getOwnCacheDirectory(view.getContext(), "universalimageloader/Cache");   
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
	           .showImageOnLoading(R.drawable.bc) // 设置图片下载期间显示的图片  
	           .showImageForEmptyUri(R.drawable.bc) // 设置图片Uri为空或是错误的时候显示的图片  
	           .showImageOnFail(R.drawable.bc) // 设置图片加载或解码过程中发生错误显示的图片  
	           .cacheInMemory(true) // 设置下载的图片是否缓存在内存中  
	           .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中  
	           .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片  
	           .build(); // 构建完成  
	}


	
	
	
	final Thread thread = new Thread(new Runnable(){

		@Override
		public void run() {
			URL url;
			try {
				url = new URL(destinationUrl);
			
			HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
			InputStreamReader read = new InputStreamReader(conn.getInputStream());
			BufferedReader buffer = new BufferedReader(read);
			String readLine = null ;
			while((readLine=buffer.readLine())!=null){
				data += readLine ;
			
			}
			
			read.close();
			conn.disconnect();
		} catch (IOException e) {
			Log.i(tag, "something is wrong");
			e.printStackTrace();
		}	
			
			Message message = new Message();
			 message.what = 1;
			 handler.sendMessage(message);     
		}});
		
	  
	
	/*
	 * 解析数据并且将其载入map中
	 */
	public  void InitData(){
		
		thread.start() ;
		
		handler = new Handler(){
			
			 @Override
		      public void handleMessage(Message msg){
		        super.handleMessage(msg);
		          if(msg.what==1){
		        	  try {
		        		  Log.i(tag, data);
						JSONObject jo = new JSONObject(data);
						JSONArray ja = jo.getJSONArray("students");
						Log.i(tag, ja.length()+"");
						for(int i  = 0 ;i<ja.length();i++){
							final  Map<String ,Object> map =new HashMap<String ,Object>();
							JSONObject  jo1 = (JSONObject)ja.get(i);
							map.put("name", jo1.getString("name"));  //姓名
							
							Log.i(tag, jo1.getString("name")+"jason");
							map.put("image", jo1.getString("image")); //图片链接
							list.add(map);
							
							
						}
						
						
						
						
					} catch (JSONException e) {
						
						e.printStackTrace();
					} 
		        	
		        	  
		        	  
		          }
			 
		          adapter = new ListAdapter(context,list);
		 	     mLV.setAdapter(adapter);
			 }
		};
		
		
	}






	@Override
	public void onRefresh() {
		InitData();
		 
        new Handler().postDelayed(new Runnable() {
           
            @Override
           public void run() {
                // TODO Auto-generated method stub
           	
               mSwipeLayout.setRefreshing(false);
            }
      }, 6000);
    }

		
	}  
	  
	  
	  
	
	

