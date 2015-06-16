package com.example.view;
import java.util.List;
import java.util.Map;

import com.example.fragments.WeiXinFragment;
import com.example.newapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListAdapter  extends BaseAdapter{
	 private LayoutInflater layoutInflater; 
	 private List<Map<String, Object>> data;
 private  ImageLoader loader ;  
	    private Context context ;
	 public WeiXinFragment main;
	 public ListAdapter(Context context,List<Map<String, Object>> data){  
 Log.i("jason", "this is the adapter");
  this.context=context;  
        this.data=data;  
        this.layoutInflater=LayoutInflater.from(context); 
        
    }  
    
	 
	 
	 
	 
	 
	 @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		container contain = null;
		if(convertView==null){
			contain = new container();
			 convertView=layoutInflater.inflate(R.layout.item, null); 
			 contain.mIV =(ImageView)convertView.findViewById(R.id.person);
			 contain.mIB = (TextView)convertView.findViewById(R.id.detail);
			  contain.mTV = (TextView)convertView.findViewById(R.id.content);
			  
			  convertView.setTag(contain);
		}else{
			contain = (container)convertView.getTag();
		}
      main = new WeiXinFragment();
		loader = ImageLoader.getInstance();
		Log.i("jason", "imageLoader");
		loader.displayImage((String)data.get(position).get("image"), contain.mIV,main.options);
		contain.mTV.setText((String)data.get(position).get("name"));
		//contain.mIB.setText((String)data.get(position).get("detail"));
		//contain.mIB.setBackgroundResource((int)data.get(position).get("detail"));
		
		
		
		
		
		
		
		
		
		
		return convertView;
	}

	
 public final class container{
	    public ImageView  mIV ;
		public TextView mIB;
		public TextView  mTV ;
		
 }
	
}
