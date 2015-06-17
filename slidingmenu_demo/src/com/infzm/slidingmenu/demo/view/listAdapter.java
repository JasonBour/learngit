package com.infzm.slidingmenu.demo.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.fragment.AllNote;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listAdapter extends BaseAdapter{
	private List<Map<String, Object>> data;  
    private LayoutInflater layoutInflater;  
    private Context context ;
    private String[] ImageUrl = {"http://imgsize.ph.126.net/?imgurl=http://img7.ph.126.net/h9s20fOT2gUy6120E65Svg==/2536652490134414167.jpg_64x64x0.jpg",
    		"http://imgsize.ph.126.net/?imgurl=http://imglf0.ph.126.net/Do6jxWfABQPf3XlRY23n0Q==/6630680737606764045.jpg_68x68x1.jpg","http://imgsize.ph.126.net/?imgurl=http://imglf1.ph.126.net/vHgNzKU2yn5b2uaBoAp0zQ==/6630634558118404639.jpg_68x68x1.jpg","" +
    				"http://imgsize.ph.126.net/?imgurl=http://img0.ph.126.net/hGjILuyKxHRwQ0wM3k4dag==/6630938023327232562.jpg_64x64x0.jpg"} ;
   private ImageLoader loader ;
    private AllNote note = new AllNote();
    
    public listAdapter(Context context,List<Map<String, Object>> data){  
         this.context=context;  
        this.data=data;  
        this.layoutInflater=LayoutInflater.from(context); 
        
    }  
    
    
  

	


    	
   
    
    
    /** 
     * 组件集合，对应list.xml中的控件 
     * @author Administrator 
     */  
    public final class Zujian{  
        public TextView title;  
        public ImageView image ; 
        public TextView detail;  
    }  
    
	@Override
	public int getCount() {
		return data.size();  
		
	}

	@Override
	public Object getItem(int position) {
		
		 return data.get(position);  
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 Zujian zujian=null;  
	        if(convertView==null){  
	            zujian=new Zujian();  
	            //获得组件，实例化组件              
	            convertView=layoutInflater.inflate(R.layout.list, null);  
	            zujian.title=(TextView)convertView.findViewById(R.id.title);  
	            zujian.detail=(TextView)convertView.findViewById(R.id.detail);
	            zujian.image=(ImageView)convertView.findViewById(R.id.image);
	            convertView.setTag(zujian);  
	        }else{  
	            zujian=(Zujian)convertView.getTag();  
	        }  
	        //绑定数据  
	      //  zujian.image.setImageURI(uri);
	     //  zujian.image
	        loader = ImageLoader.getInstance();
	      // loader.displayImage("http://img32.mtime.cn/up/2013/07/20/142428.27146212_500.jpg", zujian.image,note.options);
	        loader.displayImage("http://imglf0.ph.126.net/iBtnn8c3nbfESWg_1avu2Q==/2788572594290449239.jpg", zujian.image,note.options);
	      
	      // zujian.image.setImageBitmap(returnBitMap("http://imglf0.ph.126.net/iBtnn8c3nbfESWg_1avu2Q==/2788572594290449239.jpg"));
	        zujian.title.setText((String)data.get(position).get("title"));  
	        zujian.detail.setText((String)data.get(position).get("detail"));  
	        return convertView;  
	}

	
	 public Bitmap returnBitMap(String url){
	        URL myFileUrl = null;  
	        Bitmap bitmap = null; 
	        try {  
	            myFileUrl = new URL(url);  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	            HttpURLConnection conn = (HttpURLConnection) myFileUrl  
	              .openConnection();  
	            conn.setDoInput( true);  
	            conn.connect();  
	            InputStream is = conn.getInputStream();  
	            bitmap = BitmapFactory. decodeStream(is);  
	            is.close();  
	        } catch (IOException e) {  
	              e.printStackTrace();  
	        }  
	              return bitmap;  
	    }   


	
	 
	 
	
}
