package com.music.musicservice;



import com.music.utils.GestureBinder;
import com.music.utils.GestureBinder.GestureCallback;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RightMainFragment extends Fragment {
	private ImageView image;
    private View view;
	private  Button button;
	private GestureBinder binder;
    public String url ="http://img3.douban.com/lpic/s4609654.jpg";
	public RightMainFragment(GestureCallback callback) {
		binder = GestureBinder.newInstance(callback);
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.right_main_fragment, container,
				false);
		
		binder.bindView(view, getActivity().getApplicationContext());
		image = (ImageView)view.findViewById(R.id.image);
		
		this.view=view;
	      // panduan(); 
    
		return view;
	
}
}
/**
private  void panduan() {
   try {
	     ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getApplicationContext() 
	                    .getSystemService(Context.CONNECTIVITY_SERVICE);
	     NetworkInfo net = connectivityManager.getActiveNetworkInfo();
	      if (net == null) {   //没网状态
	           button =(Button)view.findViewById(R.id.button);
	           button.setOnClickListener(new OnClickListener(){ 			@Override
	           	public void onClick(View v) {
	            panduan();
	            			}});
	            } else {
	            	
	            	button =(Button)view.findViewById(R.id.button);
	            	
	            	button.setVisibility(View.GONE);
	                Toast.makeText(getActivity().getApplicationContext(), "有网", 1).show();
	                
	            }

	        } catch (Exception e) {
	            
	            Log.v("程序出错:", e.getMessage());
	            
	        }
			
	}
	

	}




**/