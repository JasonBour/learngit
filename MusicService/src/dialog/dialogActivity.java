package dialog;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.music.entity.MusicEntity;
import com.music.http.HttpMusic;
import com.music.musicplay.player;
import com.music.musicservice.LeftMainFragment;
import com.music.musicservice.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class dialogActivity extends Activity {
    private static boolean OnOrOff = true;

	private TextView   text;   //歌曲信息
   
	private ImageView image,image1,image2,image3,volume;   //专辑图片
	private ProgressBar bar=null;
	private TextView SongTime;
	public MusicEntity entity = new MusicEntity();    //获得到的歌曲所有信息
	public final HttpMusic http = new HttpMusic("http://192.168.1.122:8080");

	private String iUrl= null;  //图片链接
	private String url = null;   //歌曲链接
    private int length = 0 ;   //  歌曲长度
    private  String title = null ; //歌曲名称
	private String tem ; 
	private int state=0; 
	 private int Volume = 0;
	 int prolength=0;//定义进程度  
	private Runnable runnable ;
	private Handler handler = new Handler();
    private Receiver mReceiver;
    private player play = null; 
    
    URL myFileUrl = null; 
       Bitmap bitmap  = null ;
	 protected void onCreate(Bundle savedInstanceState) { 
      super.onCreate(savedInstanceState); 
      requestWindowFeature(Window.FEATURE_LEFT_ICON); 
      setTitle("测试对话框式activity");// 设置标题 
      setContentView(R.layout.dialog);// 设置布局内容 
      // 设置左边图标 
      getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, 
              android.R.drawable.ic_dialog_alert); 
      setFinishOnTouchOutside(false);  
   
      
     InitView(); 
     
        	 
     ThisisanThread();
  
  
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
     
         
    // listen();
    
	 }
	 //获取组件   
	 public void InitView(){
		 mReceiver = new Receiver(this);  
		  mReceiver.registerAction("kaishi");
		  
		  SongTime = (TextView)findViewById(R.id.time);
		     text =(TextView)findViewById(R.id.inf);
		     image1=(ImageView)findViewById(R.id.last);
		     image2=(ImageView)findViewById(R.id.play); 
		     image3=(ImageView)findViewById(R.id.next); 
		     image = (ImageView)findViewById(R.id.image); 
		 
		  bar = (ProgressBar)findViewById(R.id.progressBar);  //进度条
	      bar.setIndeterminate(false);    //状态是否为来回滚动   false 为确定某一位置
	      bar.setVisibility(View.VISIBLE);   //设置可见
	      bar.setMax(100);   //这个是控制时间长短的
	      bar.setProgress(0);
	      Intent intent = getIntent();
	      tem =intent.getStringExtra("id");
	     
	 }
	 
	 
	 public void listen(){
      //上一首
     image1.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			
		}});
     //播放和暂停     
     image2.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			switch(state){
			case 0:
				image2.setImageResource(R.drawable.play);//点击之后就切换图标
				
				 handler.removeCallbacks(runnable); 
				break;
				 
                
			case  1:                                         //第二次点击的时候 会有延迟
			    	 image2.setImageResource(R.drawable.pause1);
			    	 
                     handler.post(runnable);     //这个导致线程阻塞 
			    	 break;

         default : break;
		}
			}});
     
     
     
     //下一首 
     image3.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {	
		}});
     //设置静音
     
     volume=(ImageView)findViewById(R.id.loud); 
     volume.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			AudioManager    audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        	int currentVolume =audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        	if(currentVolume!=0){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
			volume.setImageResource(R.drawable.silence);
			Volume = currentVolume;
        	}else{
        		//int maxVolume =audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,Volume , 0);
				volume.setImageResource(R.drawable.loud);
        	}			
		}});

    
	 }   
     //定义线程  
     

	 
	 



  /**  
   
   //  判断网络 
  public boolean  Internet (){
	 
	  try {
          ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext() 
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo net = connectivityManager.getActiveNetworkInfo();

          if (net == null) {   
        	  Toast.makeText(getApplicationContext(), "没有网络", 1).show();
        	 return false;
          }else{
        	  return true;
          }
      } catch (Exception e) {
          Log.v("程序出错:", e.getMessage());
          
      }
	  return false;
	 
  }
  **/
	 
	 
	 
	 
	 
	 public void ThisisanThread(){
		 new Thread(){          
             @Override  
             public void run() {  
    		 
        	 entity = http.getMusics(tem);
        	 iUrl = entity.getPicture();
        	 url = entity.getUrl();
        	 title = entity.getTitle();
        	 length= entity.getLength();
        	 
        	 
        	 func();  //调用设置图片的方法	 
        	 
        	 
    
        	 text.setText(title);
        	 SongTime.setText(length);
        	 
        	
        	 play =new player(url);
        	 play.play();
        	
             prolength=bar.getProgress()+1;  
             Log.i("length", prolength+"");
             bar.setProgress(prolength);  
             setTitle(String.valueOf(prolength));  
             //如果进度小于100,则延迟1000毫秒之后重复执行runnable  
             if(prolength<100)  
             {  
                 handler.postDelayed(runnable, 1000);  
             }  
             //否则，都置零，线程重新执行  
             else   
             {  
                 bar.setProgress(0);  
        
                  
             }  
         
             }}.start();
	 }

	 
	   public void func(){
        new Thread(){          
         @Override  
         public void run() {  

   		           
   		           
   		        try {   
   		            myFileUrl = new URL(iUrl);   
   		        } catch (MalformedURLException e) {   
   		            e.printStackTrace();   
   		        }   
   		        try {   
   		            HttpURLConnection conn = (HttpURLConnection) myFileUrl   
   		              .openConnection();   
   		            conn.setDoInput(true);   
   		            conn.connect();   
   		            InputStream is = conn.getInputStream();   
   		            bitmap = BitmapFactory.decodeStream(is);   
   		            is.close();  
   		            Bundle b = new Bundle(); 
   		            Message msg = Message.obtain(); 
   		            b.putParcelable("MyObject", (Parcelable) bitmap); 
   		            msg.setData(b); 
   		            handler.sendMessage(msg);
   		            
   		        } catch (IOException e) {   
   		              e.printStackTrace();   
   		        }   	 
   		    }  
         
   		 }.start();
   		 
   	
   		
   	 new Handler()
   			{
   			
		public void handleMessage(Message msg)
   				{ 
   					Bitmap objectRcvd = (Bitmap) msg.getData().getParcelable("MyObject"); 
   					image.setImageBitmap(objectRcvd);
   					
   				}
   			};
   			
    	
     }
     
   
     
   
		 @Override
 protected void onDestroy() {
   super.onDestroy();
	  unregisterReceiver(mReceiver);  
	   }
	 
		 /*
		  * 广播接收器 
		  */
		  class Receiver extends BroadcastReceiver { 
		      Context mContext;    		 
		      public Receiver(Context context){    
		          mContext = context;    
		      }  
			@Override
			public void onReceive(Context context, Intent intent) {
				 String action = intent.getAction();
		         Log.i("recive", "action:" + action);
		   
			}  
			 public void registerAction(String action){    
		         IntentFilter filter = new IntentFilter();    
		         filter.addAction(action);        
		         mContext.registerReceiver(this, filter);    
		     }  
		      
			}		 
		 
		 
	 
		 
		 
		 
		 
}
