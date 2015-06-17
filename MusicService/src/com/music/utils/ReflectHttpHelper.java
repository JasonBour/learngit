package com.music.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

public class ReflectHttpHelper {
	public static Object invoke(Object object, String methodName,
			Class classes, Object... params) {
		Object result = null;
		Method[] methods = classes.getMethods();
		if (methods != null) {
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] types = method.getParameterTypes();
					if (params.length == types.length) {
						try {
							result = method.invoke(object, params);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					} else {
						Log.i("params length != paramterTypes length",
								"cant invoke");
						break;
					}
				}
			}
		}
		return result;
	}
}
