package com.infzm.slidingmenu.demo;

import android.app.Activity;
import android.content.Intent;



import android.os.Bundle;


import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.infzm.slidingmenu.demo.fragment.LeftFragment;
import com.infzm.slidingmenu.demo.fragment.newNote;
import com.infzm.slidingmenu.demo.fragment.TodayFragment;
import com.infzm.slidingmenu.demo.view.searchable;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 
 *  主界面
 */

public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {
	
    private Button search ,more,add ;
	private ImageView topButton;
	private Fragment mContent;
	private TextView topTextView;
	private PopupWindow popupwindow ;
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlidingMenu(savedInstanceState);
		//initKey();
		topButton = (ImageView) findViewById(R.id.topButton);
		topButton.setOnClickListener(this);
		topTextView = (TextView) findViewById(R.id.topTv);
		search = (Button)findViewById(R.id.search);
		more = (Button)findViewById(R.id.more);
		add = (Button)findViewById(R.id.add);
		initButton();
	}

	/*
	 * 初始化head上的两个按钮  以及popupwindow
	 */
	private void initButton (){
		
		//search按钮      点击跳转至搜索页面
		search.setOnClickListener(new OnClickListener(){

			@Override   
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,com.infzm.slidingmenu.demo.view.searchable.class);
				
				startActivity(intent);
				
				
				
				
			}});
		
	//more按钮  点击之后是popwindow
		 more.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			getPopupWindow();
			 // 这里是位置显示方式,在屏幕的左侧  
            popupwindow.showAtLocation(v,  Gravity.RIGHT | Gravity.TOP, 0, 20);    //注意这里  位置可以有两个gravity
			
			}

			});
		 
		 add.setOnClickListener(new OnClickListener(){
          //初始化那个大的新建按钮
			@Override
			public void onClick(View v) {
				
				Fragment newContent = new newNote();
				String title = getString(R.string.newNote);
				FragmentManager	 fc = 	getSupportFragmentManager();;
				FragmentTransaction transaction = fc.beginTransaction();
				transaction.replace(R.id.content_frame,newContent);
				topTextView.setText(title);
				transaction.addToBackStack(null);
				//提交修改
				transaction.commit();
			}
			 
		 });
		 
		
		
		
	}
	
	/*
	 * 初始化Popupwindow
	 */
	
	 private void initPopuptWindow(){
		 View popView = getLayoutInflater().inflate(R.layout.popwindow, null);
		// 创建PopupWindow实例,100,LayoutParams.MATCH_PARENT分别是宽度和高度 
		 popupwindow = new PopupWindow(popView,250,LayoutParams.WRAP_CONTENT,true);
		// 设置动画效果  
		 popupwindow.setAnimationStyle(R.style.AnimationFade);  
	        // 点击其他地方消失  
		 popView.setOnTouchListener(new OnTouchListener() {  
	            @Override  
	            public boolean onTouch(View v, MotionEvent event) {  
	                // TODO Auto-generated method stub  
	                if (popupwindow != null && popupwindow.isShowing()) {  
	                    popupwindow.dismiss();  
	                    popupwindow = null;  
	                }  
	                return false;  
	            }});

	 }
	 
	 
	 
	 /*
	  * 获取popupwindow
	  */
	 private void getPopupWindow() {
			if (null != popupwindow) {  
				popupwindow.dismiss();  
	            return;  
	        } else {  
	            initPopuptWindow();  
	        }  
		}
	
	
	/**
	 * 初始化侧边栏
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new TodayFragment();
		}

		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new LeftFragment()).commit();

		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式,这里设置为全屏
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.0f);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	/**
	 * 切换Fragment
	 * 
	 * @param fragment
	 */
	public void switchConent(Fragment fragment, String title) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
		topTextView.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topButton:
			toggle();
			break;
		default:
			break;
		}
	}
	
/**
 * 这里是定义的百度推送的方法
 */
	public void  initKey(){
		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"scE7mMxPcTt8nODKcUB1Z3QG");
	}


}
