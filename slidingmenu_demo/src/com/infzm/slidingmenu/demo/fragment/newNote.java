package com.infzm.slidingmenu.demo.fragment;

import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.tools.submit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * 新建笔记页
 * 
 */
public class newNote extends Fragment {
	public View view  ;
	public EditText title ,detail ;
	public Button button ;
	public Spinner spinner ;
	public submit sub  ;
	private TextView topTextView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.frag_new, null);
		initView();
		return view;
	}
	
	
	
	
	
	
	
	private void initView() {
		
		title = (EditText)view.findViewById(R.id.editText2);
		detail = (EditText)view.findViewById(R.id.editText);
		button = (Button)view.findViewById(R.id.button);
		spinner = (Spinner)view.findViewById(R.id.spinner);
		
		String[] cates=new String[]{
		        "未分类","学习"
		};
		ArrayAdapter adapter=new ArrayAdapter(view.getContext(),R.layout.spinner,cates);
		spinner.setAdapter(adapter);

		
		
		
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				sub = new submit(title.getText().toString(),detail.getText().toString(),spinner.getSelectedItem().toString());
			
				Toast.makeText(getActivity(), "保存成功", 1).show();
				/**Fragment newContent = new LastListFragment();
				String title = getString(R.string.lastList);
				FragmentManager	 fc = 	getActivity().getSupportFragmentManager();
				FragmentTransaction transaction = fc.beginTransaction();
				
				topTextView = (TextView) view.findViewById(R.id.topTv);	
				
				if(fc.equals(null)||transaction.equals(null)){
				Log.i("Json", "frag");
				}
				transaction.replace(R.id.content_frame,newContent);
				topTextView.setText(title);
				transaction.addToBackStack(null);
				//提交修改
				transaction.commit();
				**/
			}});
		
		
		
		
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
