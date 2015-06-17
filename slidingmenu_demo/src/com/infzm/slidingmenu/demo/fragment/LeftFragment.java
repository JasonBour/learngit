package com.infzm.slidingmenu.demo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.infzm.slidingmenu.demo.MainActivity;
import com.infzm.slidingmenu.demo.R;
/**
 * 
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener{
	private View ImageView;
	//private View todayView;
	private View lastListView;
	private View discussView;
	private View favoritesView;
	private View commentsView;
	private View settingsView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, null);
		findViews(view);
		
		return view;
	}
	
	
	public void findViews(View view) {
		ImageView = view.findViewById(R.id.profile_image);    //个人头像
		//todayView = view.findViewById(R.id.tvToday);
		lastListView = view.findViewById(R.id.tvLastlist);
		discussView = view.findViewById(R.id.tvDiscussMeeting);
		favoritesView = view.findViewById(R.id.tvMyFavorites);
		commentsView = view.findViewById(R.id.tvMyComments);
		settingsView = view.findViewById(R.id.tvMySettings);
		ImageView.setOnClickListener(this);
		//todayView.setOnClickListener(this);
		lastListView.setOnClickListener(this);
		discussView.setOnClickListener(this);
		favoritesView.setOnClickListener(this);
		commentsView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		switch (v.getId()) {
		
		case R.id.profile_image :   //头像
			newContent = new personalInfo();
			title = getString(R.string.personal);
		   break;
		
		//case R.id.tvToday: // 工作群聊
		//	newContent = new TodayFragment();
		//	title = getString(R.string.today);
	    //		break;
		case R.id.tvLastlist:// 所有的笔记
			newContent = new AllNote();
			title = getString(R.string.lastList);
			break;
		case R.id.tvDiscussMeeting: // 讨论集会
			newContent = new DiscussFragment();
			title = getString(R.string.discussMeetting);
			break;
		case R.id.tvMyFavorites: // 我的收藏
			newContent = new MyFavoritesFragment();
			title = getString(R.string.myFavorities);
			break;
		case R.id.tvMyComments: // 新建笔记
			newContent = new newNote();
			title = getString(R.string.newNote);
			break;
		case R.id.tvMySettings: // 设置
			newContent = new MySettingsFragment();
			title = getString(R.string.settings);
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}
	
	/**
	 * 切换fragment
	 * @param fragment
	 */
	public void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchConent(fragment, title);
		}
	}
	
}
