package de.steveliedtke.gcm;

import java.io.IOException;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.inject.Inject;

import de.steveliedtke.data.PropertyDAO;

public final class GCMAndroidSender {
	
	@Inject
	private PropertyDAO propertyDAO;

	public void sendPayload(final String message, final String registrationId){
		Sender sender = new Sender(getApiKey());
		Message gcmMessage = new Message.Builder().addData("payload", message).build();
		try {
			final Result result = sender.send(gcmMessage, registrationId, 5);
		} catch (IOException e) {
			// TODO how handle IOException
		}
		
		// TODO check results (maybe unregistrate some)
	}
	
	public void sendPayload(final String message, final List<String> registrationIds){
		Sender sender = new Sender(getApiKey());
		Message gcmMessage = new Message.Builder().addData("payload", message).build();
		try {
			final MulticastResult result = sender.send(gcmMessage, registrationIds, 5);
		} catch (IOException e) {
			// TODO how handle IOException
		}
		
		// TODO check results (maybe unregistrate some)
	}
	
	public void sendSyncPayload(final String message, final List<String> registrationIds){
		Sender sender = new Sender(getApiKey());
		Message gcmMessage = new Message.Builder().addData("payload", message).collapseKey("0").build();
		try {
			final MulticastResult result = sender.send(gcmMessage, registrationIds, 5);
		} catch (IOException e) {
			// TODO how handle IOException
		}
		
		// TODO check results (maybe unregistrate some)
	}
	
	public void sendSyncPayload(final String message, final String registrationId){
		Sender sender = new Sender(getApiKey());
		Message gcmMessage = new Message.Builder().addData("payload", message).collapseKey("0").build();
		try {
			final Result result = sender.send(gcmMessage, registrationId, 5);
		} catch (IOException e) {
			// TODO how handle IOException
		}
		
		// TODO check results (maybe unregistrate some)
	}
	
	private String getApiKey(){
		final String apiKey = propertyDAO.findValueByKey("gcm.api.key");
		if(apiKey == null){
			// TODO throw runtime exception, cause the application isn't usable
		}
		return apiKey;
	}
}