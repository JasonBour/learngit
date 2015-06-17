package com.music.musicservice;



import com.music.entity.MusicEntity;
import com.music.http.HttpMusic;
import com.music.musicplay.player;
import com.music.utils.GestureBinder;
import com.music.utils.GestureBinder.GestureCallback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class LeftMainFragment extends Fragment implements OnClickListener {
	// imageveiw
	private ImageButton ima1, ima2, ima3, ima4, ima5, ima6, ima7;

	
	
	public player play;
	private GestureBinder binder;
 
    
    public LeftMainFragment(){}
	public LeftMainFragment(GestureCallback callback) {
		binder = GestureBinder.newInstance(callback);

	}
	private String INTENAL_ACTION_1 ="id";
   
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.left_main_fragment, container,
				false);

		binder.bindView(view, getActivity().getApplicationContext());
		// return view;
		//ͼƬ��ֹ��ô��  ֮��Ҫ�ټ�
		ima1 = (ImageButton) view.findViewById(R.id.pic1);
		//ima1.setId(1);
		ima1.setOnClickListener(this);
		ima2 = (ImageButton) view.findViewById(R.id.pic2);
		
		ima2.setOnClickListener(this);
		ima3 = (ImageButton) view.findViewById(R.id.pic3);
		
		ima3.setOnClickListener(this);
		ima4 = (ImageButton) view.findViewById(R.id.pic4);
		ima4.setOnClickListener(this);
		ima5 = (ImageButton) view.findViewById(R.id.pic5);
		ima5.setOnClickListener(this);
		
		return view;
		
	}
  //����͵ȵ��������Ǳ߿���д��
	public void onClick(final View v) {
		//��������������Ϣ ר��ͼƬ���ӱ��浽�����ı���֮�� ֮����ͨ���������ȡ    
				Intent intent = new Intent();
				intent.putExtra("id",v.getId()+"");
				
				//attention
				intent.setClass(getActivity().getApplicationContext(),dialog.dialogActivity.class);
				startActivity(intent);    //������Կ����ù㲥	
	}

	
}
