package com.infzm.slidingmenu.demo.view;

import com.infzm.slidingmenu.demo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

public class CornerListView extends ListView{

	public CornerListView(Context context) {
		super(context);
		
	}
	public CornerListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public CornerListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
    @Override 
	public boolean  onInterceptTouchEvent(MotionEvent me ){
		switch(me.getAction()){
		case MotionEvent.ACTION_DOWN :
			int x = (int)me.getX();
			int y = (int)me.getY();
			int item = pointToPosition(x,y);
			if(item==AdapterView.INVALID_POSITION){
				break;
			}else{
				if(item==0){
					if (item == (getAdapter().getCount() - 1)) {  
                        //只有一项  
                        setSelector(R.drawable.app_list_corner_round);  
                    } else {  
                        //第一项  
                        setSelector(R.drawable.app_list_corner_round_top);  
                    }  
					
				}else if (item == (getAdapter().getCount() - 1))  
                    //最后一项  
                    setSelector(R.drawable.app_list_corner_round_bottom); 
				  else {  
	                    //中间项  
	                    setSelector(R.drawable.app_list_corner_round_center);  
	                } 
			}
				
			break ;
		case MotionEvent.ACTION_UP:	
		   break ;
		
		
		
		}
	
		return super. onInterceptTouchEvent(me);
		
		
	}
	
	

}
