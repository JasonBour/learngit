package com.music.musicservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ClosedBottomBoxFragment extends Fragment {
	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.close_bottom_box_fragment,
				container, false);
		textView = (TextView) view.findViewById(R.id.closettextView);
		return view;
	}
}
