package com.infzm.slidingmenu.demo.view;

import com.infzm.slidingmenu.demo.R;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


/*
 * @description :this is the activity of Search 
 */
public class searchable extends ListActivity{
	
	public ListView list ;
	public String[] array = {"1","2","3","4","5"};
	//下面这个方法是错误的  原因在源 context.this必须是在onCreate之后才有的
    //public ListAdapter adapter = new ArrayAdapter<String>(this,R.layout.search_item,array);
	public ListAdapter adapter = null ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
        list =(ListView)findViewById(R.id.list);
	    // Get the intent, verify the action and get the query
	 /**
        Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      doMySearch(query);
	      setListAdapter(adapter);
	    }
	**/
        setListAdapter(adapter);
	}

	
	
    /*
     * 查询的方式是通过sqlite
     */
	private void doMySearch(String query) {
	 
		
		
		
	}
	
	
	public void  setListAdapter (ListAdapter adapter){
		 adapter = new ArrayAdapter<String>(this,android.
				 R.layout.simple_list_item_1,array);
		
		setListAdapter(adapter);
		
	}

}
