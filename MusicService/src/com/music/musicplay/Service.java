package com.music.musicplay;


import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

public class Service {

	
	public static byte[] getImage(String url) throws Exception{
		
		URL path = new URL(url);
		
		HttpURLConnection  conn = (HttpURLConnection)path.openConnection();   //基于http协议链接对象
		conn.setReadTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() ==200){
			InputStream input = conn.getInputStream();
			return StreamTool.read(input);
			
		}
		
		return null;
	}
	
	
}
