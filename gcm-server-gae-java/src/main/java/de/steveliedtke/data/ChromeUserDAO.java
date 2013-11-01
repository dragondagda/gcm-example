package de.steveliedtke.data;

import static de.steveliedtke.data.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;

public class ChromeUserDAO {

	public ChromeUser create(final String userChannelId){
		ChromeUser user = this.findByUserChannelId(userChannelId);
		if(user == null){
			final ChromeUser newUser = new ChromeUser();
			newUser.setUserChannelId(userChannelId);
			final Key<ChromeUser> keyStatus = ofy().save().entity(newUser).now();
			user = ofy().load().key(keyStatus).now();
		}
		
		return user;
	}
	
	public void delete(final String userChannelId){
		ofy().delete().entity(this.findByUserChannelId(userChannelId));
	}
	
	private ChromeUser findByUserChannelId(final String userChannelId){
		return ofy().load().type(ChromeUser.class).filter("userChannelId", userChannelId).first().now();
	}
	
	public List<String> findAllUserChannelIds(){
		final List<ChromeUser> users = ofy().load().type(ChromeUser.class).list();
		final List<String> userChannelIds = new ArrayList<String>(users.size());
		for(final ChromeUser user : users){
			userChannelIds.add(user.getUserChannelId());
		}
		return userChannelIds;
	}
}