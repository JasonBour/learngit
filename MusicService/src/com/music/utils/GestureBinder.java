package com.music.utils;


import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;

public class GestureBinder {
	private GestureCallback callback;
	private GestureDetectorCompat compat;
	private static GestureBinder binder;

	private GestureBinder(GestureCallback callback) {
		this.callback = callback;
	}

	private GestureBinder() {

	}

	public static GestureBinder newInstance(GestureCallback callback) {
		if (binder == null) {
			synchronized (GestureBinder.class) {
				binder = new GestureBinder(callback);
			}
		}
		return binder;
	}

	public void bindView(View view, Context context) {
		if (callback != null) {
			compat = new GestureDetectorCompat(context, new GestureListener());
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					compat.onTouchEvent(event);
					return true;
				}
			});
		} else {
			throw new RuntimeException(
					"You  must implements the Interface \"GestureBinder.GestureCallback\"");
		}
	}

	public interface GestureCallback {
		public void callback(boolean down_to_up);
	}

	private class GestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			if (Math.abs(distanceX) * 1000
					/ (e2.getEventTime() - e1.getEventTime()) <= 10) {
				// Log.i("X:Y",
				// Math.round(Math.abs(distanceX)) + ":"
				// + Math.round(Math.abs(distanceY)));
				// Log.i("dx", e2.getRawX() - e1.getRawX() + "");
				if (Math.abs(e2.getRawX() - e1.getRawX()) <= 60)
					if (distanceY > 0) {
						Log.i("scroll", "从下往上"); // 与activity通信
						callback.callback(true);
					} else {
						Log.i("scroll", "从上向下"); // 与activity通信
						callback.callback(false);
					}
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}

}
