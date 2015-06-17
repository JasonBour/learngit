package com.music.musicservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.music.constants.MessageFlag;
import com.music.constants.MusicOperationFlag;
import com.music.constants.UserOperationsFlag;
import com.music.entity.MusicEntity;
import com.music.http.HttpMusic;
import com.music.http.HttpUser;
import com.music.musicplay.player;
import com.music.utils.ReflectHttpHelper;
import com.music.utils.GestureBinder.GestureCallback;
import dialog.dialogActivity;
import android.support.v4.app.FragmentManager;

public class MainActivity extends  FragmentActivity  {
	// function
	private HttpUser httpUser;
	private HttpMusic httpMusic;
	private MessageHandler messageHandler;
	private List<Message> reads = Collections
			.synchronizedList(new ArrayList<Message>());
	private LocationManager manager;
	private boolean readThreadTag = false;
	 // UI
	// ActionBar
	private ActionBar actionBar;
	public ActionBar.Tab tab1, tab2;
	public Fragment leftFragment, rightFragment;

	// ViewPagerҳ��Ԫ��
	private ArrayList<Fragment> fragmentLists = new ArrayList<Fragment>();
	private ViewPager viewPager;
	private int currentIndex;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private int offset;// ͼƬ�ƶ���ƫ����
	/* very important */
	private MyFragmentPagerAdapter fragmentAdapter;
	// TAB�ײ������Ԫ��
	private ClosedBottomBoxFragment closeFragment;
	private OpenBottomBoxFragment openFragment;
	private Animation targtAnimation;
	private Animation upslidinganimation;
	private Animation downslidinganimation;
	private LinearLayout linearLayout;
	private int bottom_margin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initfunction();
		initView();
		createLocation();
	}

	// ��ʼ�����ܴ���
	private void initfunction() {
		messageHandler = new MessageHandler();
		String host = "http://192.168.1.122:8080";
		httpUser = new HttpUser(host);
		httpMusic = new HttpMusic(host);
	}

	// ��ʼ���������
	private void initView() {

		/*
		 * ��½���� button = (Button) findViewById(R.id.button);
		 * button.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub if (open(getApplicationContext())) { Location location = manager
		 * .getLastKnownLocation(LocationManager.GPS_PROVIDER); MusicUser
		 * musicUser = new MusicUser(); musicUser.setUsername("tangshiyao");
		 * musicUser.setPassword("123456"); if (location != null) {
		 * musicUser.getMusicUserAddress().setLat( location.getLatitude());
		 * musicUser.getMusicUserAddress().setLon( location.getLongitude()); }
		 * else { Log.i("warning", "location is null"); }
		 * 
		 * new Thread(new HttpThread("login", MessageFlag.USERMESSAGE,
		 * UserOperationsFlag.USERLOGIN, musicUser)).start(); } } });
		 */

		// ��ʼ������
		Log.i("init", "run1");
		initViewPager();
		initActionBar();
		initSlidingDrawing();
		Log.i("init", "run");
	}
	/* ��ʼ��ViewPager */
	private void initViewPager() {
		// ��ʼ��������
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		fragmentLists = new ArrayList<Fragment>();
		leftFragment = new LeftMainFragment(new TabGesture());
		rightFragment = new RightMainFragment(new TabGesture());
		fragmentLists.add(leftFragment);
		fragmentLists.add(rightFragment);
		// ViewPager������ ע������������Ƿǳ���Ҫ��
		fragmentAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentLists);
		viewPager.setAdapter(fragmentAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	// ��ʼ��ActionBar

	private void initActionBar() {
		
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);	
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		ActionBar.Tab tab1 = actionBar.newTab().setText("tab1");
		ActionBar.Tab tab2 = actionBar.newTab().setText("tab2");
		tab1.setTabListener(new Listener(leftFragment));
		tab2.setTabListener(new Listener(rightFragment));
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	/* ��ʼ���ײ����� �ͻ������� */
	private void initSlidingDrawing() {
		// ��ʼ���ײ�Tab��ؿؼ�
		
		linearLayout = (LinearLayout) findViewById(R.id.FragmentContainer);
		linearLayout.setOnClickListener(new TabOnclickListener());
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout
				.getLayoutParams();
		bottom_margin = params.bottomMargin;// ��¼�»��鱾����MARGIN��
		// ��ʼ������̓���
		
		closeFragment = new ClosedBottomBoxFragment();
		openFragment = new OpenBottomBoxFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.FragmentContainer, closeFragment);
		transaction.commit();
		// ��ʼ���ϻ������¼�
	
		upslidinganimation = new TranslateAnimation(0, 0, 0, bottom_margin);
		upslidinganimation.setDuration(400);
		upslidinganimation.setFillAfter(false);
		upslidinganimation.setAnimationListener(new UpSlidingAnimationListener());
	
		// ��ʼ���»������¼�
		
		downslidinganimation = new TranslateAnimation(0, 0, 0, bottom_margin
				* -1);
		downslidinganimation.setDuration(400);
		downslidinganimation.setFillAfter(false);
		downslidinganimation
				.setAnimationListener(new DownSlidingAnimationListener());
		
	}

	class Listener implements ActionBar.TabListener {
		public Fragment fra;

		@SuppressLint("NewApi")
		public Listener(Fragment Fragment) {
			this.fra = Fragment;
		}

		@SuppressLint("NewApi")
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			//viewPager.setCurrentItem(1);
			
		/**	FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.FragmentContainer, openFragment);
			transaction.commit();**/
		}

		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {

		}

		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		}

	}

	/**
	 * class Listener extends mListener{
	 * 
	 * public Listener(Fragment Fragment) { super(Fragment); this.fra=Fragment;
	 * // TODO Auto-generated constructor stub }
	 * 
	 * public void onTabSelected(Tab tab, FragmentTransaction ft) {
	 * //viewPager.setCurrentItem(1); ft.add(R.id.viewpager, fra,null);
	 * ft.attach(fra); Log.i("bianhuan", "diaoyong");
	 * 
	 * } public void onTabUnselected(Tab tab, FragmentTransaction ft) { // TODO
	 * Auto-generated method stub ft.detach(fra); } }
	 **/

	// ����TAB����¼�    ����������Ǹ�Сfragment �ĵ���¼�
	class TabOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// Activity��Fragmentͨ��
			//һ�����һ�������Ŀ�
			TextView textview = null;
			if (downslidinganimation.equals(targtAnimation)
					|| targtAnimation == null) {// δչ��
				textview = (TextView) v.findViewById(R.id.closettextView);
			} else {

				textview = (TextView) v.findViewById(R.id.opentextView);
			}
			Toast.makeText(getApplicationContext(), textview.getText(),
					Toast.LENGTH_SHORT).show();
		
		//	����������activity
			Intent it = new Intent();
			it.putExtra("id", '0');
			it.setClass(MainActivity.this,dialogActivity.class);
			
			//�����ﴫֵ��dialogActivity ���䲻Ҫ�Զ�����
			MainActivity.this.startActivity(it);
			
		}	
			
			
			
	}

	// �����¼��ص�
	
	private class TabGesture implements GestureCallback {
		
		@Override
		public void callback(boolean down_to_up) {
			// TODO Auto-generated method stub
			if (down_to_up && upslidinganimation.equals(targtAnimation)) {
				targtAnimation = downslidinganimation;
				linearLayout.startAnimation(targtAnimation);
			} else if (!down_to_up
					&& (downslidinganimation.equals(targtAnimation) || targtAnimation == null)) {
				targtAnimation = upslidinganimation;
				linearLayout.startAnimation(targtAnimation);
			}
		}

	}

	// ���ϻ����¼�
	class UpSlidingAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout
					.getLayoutParams();
			params.bottomMargin = 0;

			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.FragmentContainer, openFragment);
			transaction.commit();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

	}

	// ���»����¼�
	class DownSlidingAnimationListener implements AnimationListener {
		
		@Override
		public void onAnimationStart(Animation animation) {
		}
	@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout
					.getLayoutParams();
			params.bottomMargin = bottom_margin;

			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.FragmentContainer, closeFragment);
			transaction.commit();			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}
	}

	// viewPage�ı�������
	public class MyOnPageChangeListener implements OnPageChangeListener {
		private int one = offset * 2 + bmpW;// ��������ҳ���ƫ����

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@SuppressLint("NewApi")
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = new TranslateAnimation(currentIndex * one,
					arg0 * one, 0, 0);// ƽ�ƶ���
			currentIndex = arg0;
			animation.setFillAfter(true);// ������ֹʱͣ�������һ֡����Ȼ��ص�û��ִ��ǰ��״̬
			animation.setDuration(200);// ��������ʱ��0.2��
			int i = currentIndex + 1;
			Toast.makeText(MainActivity.this, "��ѡ���˵�" + i + "��ҳ��",
					Toast.LENGTH_SHORT).show();

			/* ע��������������AcationBar�Ĵ��� */
			ActionBar actionBar = getActionBar();
			if (arg0 == 1) {
				actionBar.setSelectedNavigationItem(1);
			} else {
				actionBar.setSelectedNavigationItem(0);
			}

		}

	}

	
	 
	//��Ҫ��Http�����߳���
	class HttpThread implements Runnable {
		private String methodName;
		private Object[] params;
		private int messageFlag;
		private int operationsFlag;

		/**
		 * 
		 * @param methodName
		 *            ��Ҫ���õ�Http��������
		 * @param messageFlag
		 *            ��������Ϣ��������
		 * @param operationsFlag
		 *            �����Ĳ�������
		 * @param params
		 *            Ҫ���뷽���еĲ���(��˳��)
		 */
		public HttpThread(String methodName, int messageFlag,
				int operationsFlag, Object... params) {
			this.methodName = methodName;
			this.params = params;
			this.messageFlag = messageFlag;
			this.operationsFlag = operationsFlag;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Object result = null;
			// reflect
			switch (messageFlag) {
			case MessageFlag.USERMESSAGE:
				result = ReflectHttpHelper.invoke(httpUser, methodName,
						HttpUser.class, params);
				break;

			case MessageFlag.MUSICMESSAGE:
				result = ReflectHttpHelper.invoke(httpMusic, methodName,
						HttpMusic.class, params);
				break;
			}

			Message message = new Message();
			message.arg1 = messageFlag;
			message.what = operationsFlag;
			message.obj = result;
			reads.add(message);
			if (changeReadTag()) {
				createReadThread();
			}
		}
	}

	/**
	 * 
	 * @category ��ȡ���е��߳���(�����ֶ�����)
	 * 
	 */
	class ReadThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (true) {
					if (reads.size() <= 0) {
						break;
					}
					Message message = reads.remove(reads.size() - 1);
					messageHandler.sendMessage(message);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				Log.e("readThread error", "Fatal Error");
				e.printStackTrace();
			}
			if (readThreadTag) {
				readThreadTag = false;
			}
		}
	}

	/**
	 * 
	 * @category Http����ļ��д����ࣨhandler���ƣ�
	 * 
	 */
	class MessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.arg1) {
			case MessageFlag.USERMESSAGE:
				switch (msg.what) {
				case UserOperationsFlag.USERLOGIN:
					System.out.println(msg.obj);
					break;
				}
				break;
			case MessageFlag.MUSICMESSAGE:
				switch (msg.what) {
				case MusicOperationFlag.GETMUSICS:
					;
					break;
				}
				break;
			}
			super.handleMessage(msg);
		}
	}

	/**
	 * ��Ƕ���Ϊ��д״̬
	 * 
	 * @return ������иı�Ϊ��д״̬Ϊtrue,����Ϊfalse
	 */
	private synchronized boolean changeReadTag() {
		boolean result = false;
		if (!readThreadTag) {
			Log.e("will change readThreadTag:", "" + readThreadTag);
			readThreadTag = true;
			result = true;
		}
		return result;
	}

	/**
	 * �����������߳�
	 */
	private synchronized void createReadThread() {
		new Thread(new ReadThread()).start();
	}

	// ����ΪGps��صĴ�����
	private void createLocation() {
		manager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000,
				0, new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						if (location != null) {
							Log.i("SuperMap", "Location changed : Lat: "
									+ location.getLatitude() + " Lng: "
									+ location.getLongitude());
						}
					}
				});

	}

	private boolean open(final Context context) {
		LocationManager manager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);
		boolean network = manager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean gps = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (network || gps) {
			Toast.makeText(this, "GPSģ������", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			Toast.makeText(this, "GPSģ�鲻����������������GPS", Toast.LENGTH_SHORT)
					.show();
		}
		// ������ǿ�ƴ�GPS
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));

		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void turnGPSOn() {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		this.sendBroadcast(intent);

		String provider = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.sendBroadcast(poke);
		}
	}

	

}

