package de.steveliedtke.gcm;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.inject.Inject;

import de.steveliedtke.data.PropertyDAO;

public class GCMChromeSender {
	
	@Inject
	private PropertyDAO propertyDAO;

	public void sendMessage(final String message, final String userChannelId){
		final String accessToken = this.getAccessToken();
		if(accessToken!=null){
			makeUrlFetch(message, userChannelId, accessToken);
		}
	}
	
	public void sendMessage(final String message, final List<String> userChannelIds){
		final String accessToken = this.getAccessToken();
		if(accessToken!=null){
			for(final String userChannelId : userChannelIds){
				makeUrlFetch(message, userChannelId, accessToken);
			}
		}
	}
	
	private void makeUrlFetch(final String message, final String userChannelId, final String accessToken){
		
        try {
        	String messageSent = URLEncoder.encode("my message", "UTF-8");
            URL url = new URL("https://www.googleapis.com/gcm_for_chrome/v1/messages");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Authorization", "Bearer " + accessToken);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Host", "www.googleapis.com");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("{'channelId': '"+userChannelId+",'subchannelId':'1','payload':'"+messageSent+"'}");
            writer.close();
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // OK
            } else {
                // TODO handle error
            }
        } catch (MalformedURLException e) {
            // TODO handle error
        } catch (IOException e) {
            // TODO handle error
        }
	}
	
	private String getAccessToken(){
		GoogleTokenResponse response;
		String accessToken = null;
		try {
			final String clientId = getClientId();
			final String clientSecret = getClientSecret();
			response = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(),
			        clientId, clientSecret,
			      "refreshToken?", // TODO change this 
			      "https://oauth2-login-demo.appspot.com/code")// TODO change this too
			      .execute();
			 accessToken = response.getAccessToken();
		} catch (IOException e) {
			// TODO handle IOException
		}
		return accessToken;
	}
	
	private String getClientId(){
		final String clientId = propertyDAO.findValueByKey("gcm.client.id");
		if(clientId == null){
			// TODO throw runtime exception, cause the application isn't usable
		}
		return clientId;
	}
	
	private String getClientSecret(){
		final String clientSecret = propertyDAO.findValueByKey("gcm.client.secret");
		if(clientSecret == null){
			// TODO throw runtime exception, cause the application isn't usable
		}
		return clientSecret;
	}
}