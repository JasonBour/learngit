package com.example.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class new_state_listener extends BroadcastReceiver {

	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo  mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiInfo =  manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo activeInfo = manager.getActiveNetworkInfo();  
		if(!isRunningForeground(context)){
		
			Toast.makeText(context, "your application is running background", 1).show();
			
		}else{
        Toast.makeText(context, "mobile:"+mobileInfo.isConnected()+"\n"+"wifi:"+wifiInfo.isConnected()  
                        +"\n"+"active:"+activeInfo.getTypeName(), 1).show();  
		}
	}
	
	
	
	
	
	
	private boolean isRunningForeground (Context context)  
	{  
	    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
	    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
	    String currentPackageName = cn.getPackageName();  
	    if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName()))  
	    {  
	        return true ;  
	    }  
	   
	    return false ;  
	}  

}
