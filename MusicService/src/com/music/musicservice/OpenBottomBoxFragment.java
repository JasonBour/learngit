package com.music.musicservice;

import com.music.musicplay.player;

import dialog.dialogActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OpenBottomBoxFragment extends Fragment {
	private TextView textView;
   private ImageView image;
   private int a = 1; //代表是开始按钮
   private player play ;
   private boolean state ;
   private String url ="http://m1.music.126.net/DoqcpoLl4sRwvnMMRSGYpQ==/3136906674099385.mp3";

   @Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.open_bottom_box_fragment,
				container, false);
		textView = (TextView) view.findViewById(R.id.opentextView);
		
	 image=(ImageView)view.findViewById(R.id.image);
	 play = new player("http://m1.music.126.net/mravV3Zqt3bPtoxK1OPtxQ==/7704277976851974.mp3");
	state = play.state;
	 if(state){
	        image.setImageResource(R.drawable.pause);
	 }else{
		 image.setImageResource(R.drawable.start);
	 }
	 image.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			//触发了
			//之后这里怎么播放 再说
			if(!state){
		        image.setImageResource(R.drawable.pause);
		        a=0;  
             play.play();
		       }else if(state==true){
		        image.setImageResource(R.drawable.start);
		        a=1;
		       play.onDestroy();
		       
		      }}
		});
	 
	 return view;
	 }
}
