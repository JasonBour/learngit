package com.example.utils;

import android.app.Application;

/*
 * 这个类的目的是为了方便设置以及修改一些全局变量
 */
public class MyApplication extends Application {
  
	/* Login_state 登录状态  false为未登录
	 * Install_state 安装状态 是否为第一次安装  true为第一次安装
	 */
	
			
	
	boolean Login_state =  false;
   	boolean Install_state = true ;
	
	public void set_Login_state(boolean state ){
		this.Login_state=state;
	}
	public void set_Install_state(boolean state){
		this.Install_state=state;
	}
	
	
	public boolean get_Login_state(){
		
		return Login_state;
	} 
	public boolean get_Install_state(){
		return Install_state;
	}
	
	
	
	
}
