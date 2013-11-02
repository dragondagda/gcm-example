package de.steveliedtke.gcm.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;

import com.squareup.okhttp.OkHttpClient;

public abstract class ServerComm extends
		AsyncTask<JSONObject, Void, JSONObject> {

	static {
		SERVERURL = "dragondagda-gcm.appspot.com/";
	}

	public static final String SERVERURL;

	private static final String HTTPS = "https://";

	private OkHttpClient client = new OkHttpClient();

	protected String servletUrl;

	protected String getParams = "";

	protected String appVersionName = "?";

	/**
	 * 0 -> no error, -1 -> connection error, -2 -> json parsing error.
	 */
	protected int errorcode = 0;

	protected int httpState = 0;

	/**
	 * params for POST request.
	 */
	protected Map<String,String> postParams;

	public ServerComm() {
//		postParams = new ArrayList<NameValuePair>(2);
		postParams = new HashMap<String, String>();
	}

	protected final JSONObject doInBackground(final JSONObject... data) {
		final String userAgent = "HackerSpaceBremen/" + this.appVersionName
				+ "; Android/" + Build.VERSION.RELEASE + "; "
				+ Build.MANUFACTURER + "; " + Build.DEVICE + "; " + Build.MODEL;

		HttpClient httpclient = new DefaultHttpClient();
		HttpParams httpBodyParams = httpclient.getParams();
		httpBodyParams.setParameter(CoreProtocolPNames.USER_AGENT, userAgent);

		int responseCode = 0;
		String httpOrS = HTTPS;
		
			try {
				HttpURLConnection connection = client.open(new URL(httpOrS
						+ SERVERURL + this.servletUrl));
				OutputStream out = null;
				InputStream in = null;
				try {
					// Write the request.
					connection.setRequestMethod("POST");
					out = connection.getOutputStream();
					out.write(createBody().getBytes("UTF-8"));
					out.close();

					responseCode = connection.getResponseCode();
				} finally {
					// Clean up.
					if (out != null)
						out.close();
					if (in != null)
						in.close();
				}
			} catch (IOException e) {
				errorcode = -1;
				cancel(false);
				return null;
			}
		

		httpState = responseCode;

		JSONObject resData = new JSONObject();
		if (httpState != 200) {
			cancel(false);
			return null;
		}

		return resData;
	}
	
	private String createBody(){
		final StringBuilder sBuilder = new StringBuilder();
		boolean first = true;
		for(final String key : postParams.keySet()){
			if(first){
				first = false;
			}else{
				sBuilder.append("&");
			}
			sBuilder.append(key+"="+postParams.get(key));
		}
		return sBuilder.toString();
	}
}
