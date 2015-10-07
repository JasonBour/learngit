package com.example.newapp;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.fragments.FindFragment;
import com.example.utils.DateTimePickDialogUtil;

public class activity extends Activity  implements LocationSource,
AMapLocationListener, OnCheckedChangeListener, InfoWindowAdapter, OnInfoWindowClickListener, OnMarkerClickListener {
private AMap aMap;
private MapView mapView;
private OnLocationChangedListener mListener;
private LocationManagerProxy mAMapLocationManager;
private RadioGroup mGPSModeGroup;
private ImageView image ;
private Button button ;
	private EditText startDateTime;
	private EditText endDateTime;

	private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
	private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间

	private LatLng person1  = new LatLng(30.421321 ,114.224161);
	
	private Marker person ;
	
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		 image = (ImageView)findViewById(R.id.back);
		 button = (Button)findViewById(R.id.button);
	        image.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
				finish();//销毁当前Activity		
				}	});
	        button.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
			    //提交按钮  
					
					
				}});
		    mapView = (MapView) findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);// 此方法必须重写
			init();
	        // 两个输入框
			startDateTime = (EditText) findViewById(R.id.inputDate);
			endDateTime = (EditText)findViewById(R.id.inputDate2);

			startDateTime.setText(initStartDateTime);
			endDateTime.setText(initEndDateTime);

			startDateTime.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
							activity.this, initEndDateTime);
					dateTimePicKDialog.dateTimePicKDialog(startDateTime);

				}
			});

			endDateTime.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
							activity.this, initEndDateTime);
					dateTimePicKDialog.dateTimePicKDialog(endDateTime);
				}
			});
	
			
	}


	
	
	
	/**
	 * 初始化
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
		mGPSModeGroup.setOnCheckedChangeListener(this);
	
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_room_grey600_48dp));
		myLocationStyle.strokeColor(Color.BLUE);
		myLocationStyle.strokeWidth(5);
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		person = aMap.addMarker(new MarkerOptions()
				.position(person1)
				.title("我要吃水果")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_room_grey600_48dp))
				//.perspective(true)
                //.draggable(true)				
				);
		
		aMap.setOnMarkerClickListener(this);
		aMap.setOnInfoWindowClickListener(this);
		aMap.setInfoWindowAdapter(this);
		
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.gps_locate_button:
			// 设置定位的类型为定位模式
			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
			break;
		case R.id.gps_follow_button:
			// 设置定位的类型为 跟随模式
			aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
			break;
		case R.id.gps_rotate_button:
			// 设置定位的类型为根据地图面向方向旋转
			aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
			break;
		}

	}

	/**
	 * 以下方法必须重写
	 */
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}


	/**
	 * 此方法已经废弃
	 */
	
	@Override
	public void onLocationChanged(Location location) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}


	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		//Toast.makeText(getApplicationContext(), aMap.getMyLocation().getLatitude()+"all"+aMap.getMyLocation().getLongitude(), Toast.LENGTH_LONG).show();
		//在这里加东西会导致不能定位
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
			} else {
				Log.e("AmapErr","Location ERR:" + amapLocation.getAMapException().getErrorCode());
			}
		}
		//	Toast.makeText(getApplicationContext(), amapLocation.getLatitude()+"all"+amapLocation.getLongitude(), Toast.LENGTH_LONG).show();	
	   
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用destroy()方法
			// 其中如果间隔时间为-1，则定位只定一次
			// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	/*
	 * 重写点击返回物理按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		finish();
	return false;
		
	}





	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}





	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}




  
	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}




  /**
   * 显示特定信息  可以通过inflate  xml文件
   */
	@Override
	public View getInfoWindow(Marker arg0) {
		View view  = getLayoutInflater().inflate(R.layout.marker, null);
		ImageView image = (ImageView)view.findViewById(R.id.image);
		TextView info = (TextView)view.findViewById(R.id.info);
		
		info.setText(person.getTitle());
		
		
		return view;
	}
	
	
}


