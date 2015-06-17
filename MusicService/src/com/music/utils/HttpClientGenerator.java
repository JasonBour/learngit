package com.music.utils;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.util.Log;

public class HttpClientGenerator {
	private static DefaultHttpClient httpClient;

	public static synchronized DefaultHttpClient getHttpClient() {
		if (httpClient == null) {
			Log.v("TAG", "->> httpClient is null ->> do getHttpClient");
			// �����������, HTTPЭ��İ汾,1.1/1.0/0.9
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
			HttpProtocolParams.setUseExpectContinue(params, true);

			// �������ӳ�ʱʱ��
			int REQUEST_TIMEOUT = 10 * 1000; // ��������ʱ10����
			int SO_TIMEOUT = 10 * 1000; // ���õȴ����ݳ�ʱʱ��10����
			HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
			ConnManagerParams.setTimeout(params, 1000); // �����ӳ���ȡ���ӵĳ�ʱʱ��

			// ���÷���Э��
			SchemeRegistry schreg = new SchemeRegistry();
			schreg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schreg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// ʹ���̰߳�ȫ�����ӹ���������HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schreg);
			httpClient = new DefaultHttpClient(conMgr, params);
		}
		return httpClient;
	}
}
