package com.infzm.slidingmenu.demo.view;

import com.infzm.slidingmenu.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity{
    public EditText title ;
    public TextView book ;
    public WebView web ;
    public String tag = "json" ;
   
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		init();
	}

	
	/*
	 * 初始化各种wights
	 */
	private void init() {
		
		book = (TextView)findViewById(R.id.books);
		title = (EditText)findViewById(R.id.title);
		web =(WebView)findViewById(R.id.web);
		
		
		Bundle bundle = this.getIntent().getExtras();
		if(bundle.equals(null)){
			Log.i(tag, "bundle is null ");
			
			
		}
		//获取数据
		book.setText(bundle.getString("books"));
		title.setText(bundle.getString("title"));
		
	
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setBuiltInZoomControls(true);
		web.loadUrl("http://www.vvtor.com/201111221747.html");
		web.setWebViewClient(new WebViewClient(){
			  @Override
              public boolean shouldOverrideUrlLoading(WebView view, String url) {
                   // TODO Auto-generated method stub
                   view.loadUrl(url);// 使用当前WebView处理跳转
                   return true;//true表示此事件在此处被处理，不需要再广播
              }
			  @Override     //转向错误时的处理
              public void onReceivedError(WebView view, int errorCode,
                        String description, String failingUrl) {
                   // TODO Auto-generated method stub
                   Toast.makeText(getBaseContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
              }
         
    

			
			
		});
		

		
		
		
	}
	
	
	
	/*
	 * 返回键点击事件   点击之后 只能finish 当前的activity
	 */
	
	public  boolean onKayDown(int keyCode, KeyEvent event){
		
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
		    //记得写一个功能将当前的资料保存并上传 
			//或者先不考虑这个
			finish();  
			
			
			
		}
		
		
		
		return false;
		
		
		
		
	}
	
	
	
	
}
