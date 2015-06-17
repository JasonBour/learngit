package com.music.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.music.entity.JsonMessage;
import com.music.entity.MusicUser;
import com.music.entity.MusicUserAddress;
import com.music.utils.HttpClientGenerator;

/**
 * �����û���ص�http����
 * 
 * @author tdy
 * */
public class HttpUser {
	private String JSESSIONID = "";
	private String host;
	private final String checkUserUrl = "/TdyMusic/musicUser/checkUsername";
	private final String registerUrl = "/TdyMusic/musicUser/register";
	private final String loginUrl = "/TdyMusic/musicUser/login";
	private final String userInformationUrl = "/TdyMusic/musicUser/userInformation";
	private final String searchUserUrl = "/TdyMusic/musicUser/searchUser";

	/**
	 * 
	 * @param host
	 *           ������ַ
	 */
	public HttpUser(String host) {
		this.host = host;
	}

	/**
	 * �����Ƿ���ڸ��û�
	 * 
	 * @param username
	 * @return JsonMessage...{status:success}�û��Ѵ��� �� {status:fail}�û������� �� null
	 */
	public JsonMessage checkUsername(String username) {
		JsonMessage jsonMessage = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		// ��������
		String param = URLEncodedUtils.format(params, "UTF-8");
		// URLƴ��
		HttpGet httpGet = new HttpGet(host + checkUserUrl + "?" + param);
		Log.i("Http request \"checkUsername\":", host + checkUserUrl + "?"
				+ param);
		// ��������
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Gson gson = new Gson();
				String json = EntityUtils.toString(response.getEntity());
				jsonMessage = gson.fromJson(json, JsonMessage.class);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \" checkUsername\"", "end");
		}
		return jsonMessage;
	}

	/**
	 * �û�ע��
	 * 
	 * @param MusicUser
	 *            (Must Include "MusicUserAddress")
	 * 
	 * @return JsonMessage
	 */
	public JsonMessage register(MusicUser musicUser) {
		JsonMessage jsonMessage = null;
		HttpPost httpPost = new HttpPost(host + registerUrl);
		Log.i("Http request \"register\"", host + registerUrl);
		DefaultHttpClient httpclient = HttpClientGenerator.getHttpClient();
		Gson gson = new Gson();
		String request_json = gson.toJson(musicUser);
		try {
			httpPost.setEntity(new StringEntity(request_json));
			HttpResponse response = httpclient.execute(httpPost);
			String json = EntityUtils.toString(response.getEntity());
			jsonMessage = gson.fromJson(json, JsonMessage.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpPost.abort();
			Log.i("Http request \"register\"", "end");
		}
		return jsonMessage;
	}

	/**
	 * @param musicUser
	 *            �û���Ϣ.(������д�û����������Լ���ַ)
	 * @return JsonMessage
	 */
	public JsonMessage login(MusicUser musicUser) {
		JsonMessage jsonMessage = new JsonMessage();
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		HttpPost httppost = new HttpPost(host + loginUrl);
		Log.i("Http request \"login\"", host + loginUrl);
		Gson gson = new Gson();
		String request_json = gson.toJson(musicUser);
		try {
			httppost.setEntity(new StringEntity(request_json));
			HttpResponse response = httpClient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String json = EntityUtils.toString(response.getEntity());
				jsonMessage = gson.fromJson(json, JsonMessage.class);
				// ���cookie
				CookieStore cookiestore = httpClient.getCookieStore();
				List<Cookie> cookies = cookiestore.getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					Log.i("Cookie Name", cookies.get(i).getName());
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						JSESSIONID = cookies.get(i).getValue();
						Log.i("Get JSESSIONID:", JSESSIONID);
						break;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httppost.abort();
			Log.i("Http request \"login\"", "end");
		}
		return jsonMessage;
	}

	/**
	 * 
	 * ȡ�õ�ǰ��½����û���Ϣ
	 * @return  MusicUser || null(����в���ȫ�ֶ�,��֪ͨ�������˿ڽ����޸�)
	 */
	public MusicUser userInformation() { // ��Ҫ��½
		MusicUser musicUser = null;
		DefaultHttpClient httpclient = HttpClientGenerator.getHttpClient();
		HttpGet httpGet = new HttpGet(host + userInformationUrl);
		Log.i("Http request \"userInformation\"", host + userInformationUrl);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpclient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			musicUser = gson.fromJson(json, MusicUser.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"userInformation\"", "end");
		}
		return musicUser;
	}

	/**
	 * �����ܱߵ������û� (Ԥ��Ϊ50M��)
	 * @param:musicUserAddress
	 *                         �û���γ��
	 * @return List<MusicUser> || null
	 */
	public List<MusicUser> searchUser(MusicUserAddress musicUserAddress) { // ��Ҫ��½
		List<MusicUser> users = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		HttpPost httppost = new HttpPost(host + searchUserUrl);
		Log.i("Http request \"searchUser\"", host + searchUserUrl);
		if (!"".equals(JSESSIONID)) {
			httppost.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		Gson gson = new Gson();
		String request_json = gson.toJson(musicUserAddress);
		try {
			httppost.setEntity(new StringEntity(request_json));
			HttpResponse response = httpClient.execute(httppost);
			String json = EntityUtils.toString(response.getEntity());
			// ���Ϸ���List��ת��
			users = gson.fromJson(json, new TypeToken<List<MusicUser>>() {
			}.getType());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httppost.abort();
			Log.i("Http request \"searchUser\"", "end");
		}
		return users;
	}
}
