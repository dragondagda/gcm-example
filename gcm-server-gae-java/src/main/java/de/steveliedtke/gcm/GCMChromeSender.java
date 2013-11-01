package de.steveliedtke.gcm;

import java.util.List;

public class GCMChromeSender {

	// TODO authentication
	// TODO api key?
	
	public void sendMessage(final String message, final String userChannelId){
		// TODO send message to one user
	}
	
	public void sendMessage(final String message, final List<String> userChannelIds){
		// TODO send message as batch ?!
	}
}