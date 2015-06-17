package com.music.musicplay;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;

public class StreamTool {

	public static byte[] read(InputStream input) throws Exception {
		ByteArrayOutputStream stream  = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		while((input.read(buffer)!=-1)){
			
			stream.write(buffer, 0, input.read(buffer));
		}
		
		input.close();
		
		return stream.toByteArray();
	}

}
