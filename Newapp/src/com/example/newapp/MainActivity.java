package com.example.newapp;





import com.example.fragments.AddressFragment;
import com.example.fragments.FindFragment;
import com.example.fragments.MeFragment;
import com.example.fragments.WeiXinFragment;
import com.example.utils.MyApplication;
import com.example.utils.new_state_listener;


import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  implements OnMenuItemClickListener,
OnCreateContextMenuListener, OnItemClickListener, android.widget.PopupMenu.OnMenuItemClickListener{
 public MyApplication myApplication ;	
 public SharedPreferences preferences;
 public LocationManager lm; 
 public static FragmentManager fMgr ;
 PopupWindow pop ;
 Intent intent;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);    
		lm =  (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		fMgr = getSupportFragmentManager();		
		initFragment();
		dealBottomButtonsClickEvent();
		
	        
	}

	
	//判断登录状态以及安装状态
	public void state(){
		 preferences  = getSharedPreferences("count",MODE_WORLD_READABLE); 
		 preferences = getSharedPreferences("login_state",MODE_WORLD_READABLE);
		 int count = preferences.getInt("count", 0);   
		 //判断程序与第几次运行，如果是第一次运行则跳转到引导页面     \
		 int login_state = preferences.getInt("login_state", 0);
		//判断是否登录  0为未登录		 
		  if (count == 0) {    
			intent  = new Intent();
		  intent.setClass(getApplicationContext(),viewpager.class);    
		 startActivity(intent);    
		 this.finish();    
		 }else{
		  if(login_state == 0){			
			 intent = new Intent();
			 intent.setClass(getApplicationContext(), login.class);
			 startActivity(intent);
			 this.finish();			
		 } 
		  
		  Editor editor = preferences.edit();   
			 editor.putInt("login_state", ++login_state);
			 editor.commit();
		 }
		 Editor editor = preferences.edit();    
		   //存入数据      
		 editor.putInt("count", ++count);  
		 //提交修改      
		 editor.commit();
  
		
		
	}
	
	
	
	
	
	
	/*
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.option_menu, menu);
	        return true;
	    }
	
	public void showpop(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
 
    }
	  @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
	            ContextMenuInfo menuInfo) {
	        super.onCreateContextMenu(menu, v, menuInfo);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.option_menu, menu);
	    }
	 
	
	
	
	*/
	

	
	/*
	 * 初始化fragment
	 */
	
	
	/*
	 * 监听网络状态的广播
	 */
	private void registerReceiver(new_state_listener receiver, IntentFilter filter) {
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		intentfilter.setPriority(1000);
        registerReceiver(new new_state_listener(),intentfilter);
	}
	/*
	 * 初始化fragment
	 */
	public void initFragment(){
		
		FragmentTransaction ft = fMgr.beginTransaction();
		 Fragment weiXinFragment = new WeiXinFragment();  
		    ft.add(R.id.fragmentRoot, weiXinFragment, "weiXinFragment");  
		    ft.addToBackStack("weiXinFragment");  
		    ft.commit();   
		
	} 

	
	/**  
	 * 处理底部点击事件  
	 */  
	private void dealBottomButtonsClickEvent() {   
            findViewById(R.id.rbWeiXin).setOnClickListener(new OnClickListener() {  
	          
	        @Override  
	        public void onClick(View v) {  
	            // TODO Auto-generated method stub  
	            if(fMgr.findFragmentByTag("weiXinFragment")!=null && fMgr.findFragmentByTag("weiXinFragment").isVisible()) {  
	                return;  
	            }  
	            popAllFragmentsExceptTheBottomOne();  
	  
	        }  
	    });  
	    
              findViewById(R.id.rbAddress).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				AddressFragment sf = new AddressFragment();
				ft.add(R.id.fragmentRoot, sf, "AddressFragment");
				ft.addToBackStack("AddressFragment");
				ft.commit();
				
			}
		});
		findViewById(R.id.rbFind).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				FindFragment sf = new FindFragment();
				ft.add(R.id.fragmentRoot, sf, "AddressFragment");
				ft.addToBackStack("FindFragment");
				ft.commit();
			}
		});
		findViewById(R.id.rbMe).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				MeFragment sf = new MeFragment();
				ft.add(R.id.fragmentRoot, sf, "MeFragment");
				ft.addToBackStack("MeFragment");
				ft.commit();
			}
		});
	}
	
	
	
	
/**
 * 从back stack弹出所有的fragment，保留首页的那个
 */
public static void popAllFragmentsExceptTheBottomOne() {
	for (int i = 0, count = fMgr.getBackStackEntryCount() - 1; i < count; i++) {
		fMgr.popBackStack();
	}
}
//点击返回按钮
@Override
public void onBackPressed() {
	if(fMgr.findFragmentByTag("weiXinFragment")!=null && fMgr.findFragmentByTag("weiXinFragment").isVisible()) {
		MainActivity.this.finish();
	} else {
		super.onBackPressed();
	}
}	
	
	

/*
 * 获取GPS的状态 是否开启
 */
public  boolean  getState(){

	
	if(lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
		return true ;

	}else{
		/*
		 * 跳转到设置  暂时有问题
		 */
		 Intent GPSIntent = new Intent(); 
	      GPSIntent.setClassName("com.android.settings",  "com.android.settings.widget.SettingsAppWidgetProvider"); 
	      GPSIntent.addCategory("android.intent.category.ALTERNATIVE"); 
	      GPSIntent.setData(Uri.parse("custom:3")); 
	      try { 
	          PendingIntent.getBroadcast(getApplicationContext(), 0, GPSIntent, 0).send(); 
	      } catch (CanceledException e) { 
	          e.printStackTrace(); 
	          return false;
	      }
		//finish();
	}
return false;
			
}


/*
 * 获取位置
 */
public void getLocation(){
	
	Criteria criteria = new Criteria();
	criteria.setAccuracy(criteria.ACCURACY_FINE);
	criteria.setAltitudeRequired(false);
	criteria.setBearingRequired(false);
	criteria.setCostAllowed(true);
	criteria.setPowerRequirement(criteria.POWER_LOW);
	Location loc = lm.getLastKnownLocation(lm.getBestProvider(criteria, true));
	//lm.requestLocationUpdates(lm.getBestProvider(criteria, true), 100*1000, 500, locationListener)
	double la =loc.getLatitude();
	double lo =loc.getLongitude();

}


@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	
}





@Override
public boolean onMenuItemClick(MenuItem item) {
	// TODO Auto-generated method stub
	return false;
}














}
