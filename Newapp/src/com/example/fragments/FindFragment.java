package com.example.fragments;



import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.example.newapp.R;
import com.example.newapp.activity;
import com.example.newapp.login;
import com.example.newapp.viewpager;
import com.example.utils.DateTimePickDialogUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FindFragment extends Fragment 
   
	implements LocationSource,
	AMapLocationListener, OnCheckedChangeListener, InfoWindowAdapter, OnInfoWindowClickListener, OnMarkerClickListener{
	boolean expand_status  = false ; 
	public View view  ;
    public  ImageView image ;
	private LatLng person1  = new LatLng(30.421321 ,114.224161);
	private ListView list ;
	private Marker person ;
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return  view  = inflater.inflate(R.layout.main_1, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
       
		 //button = (Button)view.findViewById(R.id.button);		 
		// button.setOnClickListener(this);
		mapView = (MapView) view.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		
		initView();
		
		
		 
	}
	
	
	public void initView(){
		View expan_map = getActivity().getLayoutInflater().inflate(R.layout.expand_map, null);
		
		mapView.addView(expan_map);
		image = (ImageView)view.findViewById(R.id.expand);
		image.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(!expand_status){
				mapView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.0f));
				expand_status = !expand_status;
				}else{
					mapView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
					expand_status = !expand_status;
				}
				
				
			}
			
			
			
		});
		
		list = (ListView)view.findViewById(R.id.list);
		
		
		
		
		
		
		
		
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		
	
		
	}


	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		//我的位置的图标
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_room_grey600_48dp));
		myLocationStyle.strokeColor(Color.WHITE);
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
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	
	@Override
	public void onDestroy() {
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
			mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
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
		View view  = getActivity().getLayoutInflater().inflate(R.layout.marker, null);
		ImageView image = (ImageView)view.findViewById(R.id.image);
		TextView info = (TextView)view.findViewById(R.id.info);
		
		info.setText(person.getTitle());
		
		
		return view;
	}
	
	
	
	
/**
	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();
		//intent.setClass(getActivity(), viewpager.class);
		intent.setClass(getActivity(), activity.class);
		startActivity(intent);
		
	}
	**/
}
