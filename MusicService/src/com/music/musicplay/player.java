package com.music.musicplay;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class player extends Service implements OnClickListener {

private MediaPlayer player = new MediaPlayer();    //不要把 new MediaPlayer() 放在 新的线程里  会导致 对象为空
private String str ="http://m1.music.126.net/DoqcpoLl4sRwvnMMRSGYpQ==/3136906674099385.mp3" ;
private String  Tag ="播放器";
public boolean state = false;
 public player(String str1){
	 
	 this.str = str1;
	 
 }
public void onStart(){   
	//str为音乐url
	
		new Thread(){
			public void run(){
				
				if (player == null) {
					 Log.i("错误","为空");               //important
					player.setLooping(false);
					
				}else{
					
					player.setAudioStreamType(AudioManager.STREAM_MUSIC);
					 try {
						player.setDataSource(str);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 try {
						player.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					player.start();
					state = true;
				}}}.start();
	
	
	
}
public void onDestroy()
{
if(player!=null&&player.isPlaying()){
    super.onDestroy();
    player.stop();
    state = false;
 }

}
public void onPause(){	
	if(player!=null&&player.isPlaying()){
		player.pause();	
		state = false;
	}
}

public void play(){
	if(player!=null){
	if(player.isPlaying()){
		player.start();
		state = true;
		Log.i("nill", "1" );
		}else{
			
			Log.i("nill", "3" );
			onStart();
			Log.i("nill", "2" );
		}}else{
			Log.i("player", "null");
		}
}

@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
}
public boolean isPlaying(){
	if(player.isPlaying()){
		return true;
	}else{
	return false;}
	}


}
