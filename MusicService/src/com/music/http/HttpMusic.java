package com.music.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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
import com.music.entity.ExChangeTemp;
import com.music.entity.JsonMessage;
import com.music.entity.MusicCollection;
import com.music.entity.MusicEntity;
import com.music.utils.HttpClientGenerator;

/**
 * ����������й��������
 * 
 * @author:tdy
 * */
public class HttpMusic {
	private String JSESSIONID = "";// ���ⲿע�� (һ��������½�¼�)
	private String host;
	private final String Get_Musics = "/TdyMusic/musics/getMusics";
	private final String Next_Musics = "/TdyMusic/musics/nextMusics";
	private final String Collecte_Music = "/TdyMusic/musics/collectMusic";
	private final String Delete_Music = "/TdyMusic/musics/deleteMusic";
	private final String Search_Aim_Collections = "/TdyMusic/musics/searchAimCollections";
	private final String Exchanging_Music = "/TdyMusic/musics/exchangingMusic";
	private final String Search_ExchangeRequest = "/TdyMusic/musics/searchExchangeRequest";
	private final String Exchanged_Music = "/TdyMusic/musics/exchangedMusic";

	/**
	 * 
	 * @param host
	 *            ������ַ
	 */
	public HttpMusic(String host) {
		this(host, "");
	}

	/**
	 * 
	 * @param host
	 *            ������ַ
	 * @param JSESSIONID
	 *            ��HttpUser�л�������JSESSIONID
	 */
	public HttpMusic(String host, String JSESSIONID) {
		this.host = host;
		this.JSESSIONID = JSESSIONID;
	}

	/**
	 * ���һ��ָ��Ƶ�����������
	 * 
	 * @param channel
	 *            ����Ƶ��
	 * @return MusicEntity ����ʵ����Ϣ����null
	 */
	public MusicEntity getMusics(String channel) {
		MusicEntity musicEntity = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("channel", channel));
		String param = URLEncodedUtils.format(params, "UTF-8");
		HttpGet httpget = new HttpGet(host + Get_Musics + "?" + param);
		Log.i("Http request \"getMusics\"", host + Get_Musics + "?" + param);
		if (!"".equals(JSESSIONID)) {
			httpget.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIOND", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpget);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			musicEntity = gson.fromJson(json, MusicEntity.class);
			if ("".equals(JSESSIONID)) { // �����ǰ�û�δ��¼
				List<Cookie> cookies = httpClient.getCookieStore().getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						JSESSIONID = cookies.get(i).getValue();
						Log.i("Get JSESSIONID", JSESSIONID);
						break;
					}
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpget.abort();
			Log.i("Http request \"getMusics\"", "end");
		}
		Log.i("tangnima",musicEntity.toString() );
		return musicEntity;
	}

	/**
	 * ��������һ������
	 * 
	 * @return MusicEntity || null
	 */

	public MusicEntity nextMusic() {
		MusicEntity musicEntity = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		HttpGet httpGet = new HttpGet(host + Next_Musics);
		Log.i("Http request \"nextMusic\"", host + Next_Musics);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			musicEntity = gson.fromJson(json, MusicEntity.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"nextMusic\"", "end");
		}
		return musicEntity;
	}

	/**
	 * һ�·����ĵ��ö�������Ҫ�û���½ ��Ȼ��̨�Ѿ�������صĴ��� ������û����ڿͻ��˽���һЩ��½�ж�
	 */

	/**
	 * �ղص�ǰ���������� (��Ҫ��½ ��ҪJSESSIONID)
	 * 
	 * @return:JsonMessage || null
	 */
	public JsonMessage collectMusic() {
		JsonMessage jsonMessage = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		HttpGet httpGet = new HttpGet(host + Collecte_Music);
		Log.i("Http request \"collectMusic\"", host + Collecte_Music);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			jsonMessage = gson.fromJson(json, JsonMessage.class);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"collectMusic\"", "end");
		}
		return jsonMessage;
	}

	/**
	 * ɾ���ղ�����
	 * 
	 * @param:collection_id �ղ�����id
	 * @return:JsonMessage || null
	 */
	public JsonMessage deleteMusic(String collection_id) {
		JsonMessage jsonMessage = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		List<BasicNameValuePair> lists = new ArrayList<BasicNameValuePair>();
		lists.add(new BasicNameValuePair("collection_id", collection_id));
		String param = URLEncodedUtils.format(lists, "UTF-8");
		HttpGet httpGet = new HttpGet(host + Delete_Music + "?" + param);
		Log.i("Http request \"deleteMusic\"", host + Delete_Music + "?" + param);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			jsonMessage = gson.fromJson(json, JsonMessage.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"deleteMusic\"", "end");
		}
		return jsonMessage;
	}

	/**
	 * ���ָ���û����ղ��б�
	 * 
	 * @param user_id
	 *            �û�id
	 * @return Set<MusicCollection> || null
	 */
	public Set<MusicCollection> searchAimCollections(String user_id) {
		Set<MusicCollection> sets = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		List<BasicNameValuePair> lists = new ArrayList<BasicNameValuePair>();
		lists.add(new BasicNameValuePair("userId", user_id));
		String param = URLEncodedUtils.format(lists, "UTF-8");
		HttpGet httpGet = new HttpGet(host + Search_Aim_Collections + "?"
				+ param);
		Log.i("Http request \"searchAimCollections\"", host
				+ Search_Aim_Collections + "?" + param);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			sets = gson.fromJson(json, new TypeToken<Set<MusicCollection>>() {
			}.getType());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"searchAimCollections\"", "end");
		}
		return sets;
	}

	/**
	 * �ύ��������
	 * 
	 * @param:ExchangeTemp
	 * 
	 * @return:JsonMessage || null
	 */
	public JsonMessage exchanging_music(ExChangeTemp changeTemp) {
		JsonMessage jsonMessage = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		Gson gson = new Gson();
		String request_json = gson.toJson(changeTemp);
		HttpPost httpPost = new HttpPost(host + Exchanging_Music);
		Log.i("Http request \"exchanging_music\"", host + Exchanging_Music);
		try {
			httpPost.setEntity(new StringEntity(request_json));
			if (!"".equals(JSESSIONID)) {
				httpPost.setHeader("Cookie", JSESSIONID);
				Log.i("Current JSESSIONID", JSESSIONID);
			}
			HttpResponse response = httpClient.execute(httpPost);
			String json = EntityUtils.toString(response.getEntity());
			jsonMessage = gson.fromJson(json, JsonMessage.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpPost.abort();
			Log.i("Http request \"exchanging_music\"", "end");
		}

		return jsonMessage;
	}

	/**
	 * �鿴�Ƿ���ڽ�������
	 * 
	 * @return JsonMessage || null
	 */

	public List<ExChangeTemp> searchExchangeRequest() {
		List<ExChangeTemp> lists = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		HttpGet httpGet = new HttpGet(host + Search_ExchangeRequest);
		Log.i("Http request \"searchExchangeRequest\"", host
				+ Search_Aim_Collections);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			lists = gson.fromJson(json, new TypeToken<List<ExChangeTemp>>() {
			}.getType());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"searchExchangeRequest\"", "end");
		}
		return lists;
	}

	/**
	 * @param key
	 *            ����key
	 * @return JsonMessage || null
	 */
	public JsonMessage exchangeMusic(String key) {
		JsonMessage jsonMessage = null;
		DefaultHttpClient httpClient = HttpClientGenerator.getHttpClient();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("key", key));
		String param = URLEncodedUtils.format(params, "UTF-8");
		HttpGet httpGet = new HttpGet(host + Exchanged_Music + "?" + param);
		Log.i("Http request \"exchangeMusic\"", host + Exchanged_Music + "?"
				+ param);
		if (!"".equals(JSESSIONID)) {
			httpGet.setHeader("Cookie", JSESSIONID);
			Log.i("Current JSESSIONID", JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity());
			Gson gson = new Gson();
			jsonMessage = gson.fromJson(json, JsonMessage.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.abort();
			Log.i("Http request \"exchangeMusic\"", "end");
		}
		return jsonMessage;
	}

	public String getJSESSIONID() {
		return JSESSIONID;
	}

	// ͬ������
	public synchronized void setJSESSIONID(String jSESSIONID) {
		JSESSIONID = jSESSIONID;
	}

}
