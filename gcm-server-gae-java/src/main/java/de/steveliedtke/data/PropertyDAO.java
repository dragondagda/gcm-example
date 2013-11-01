package de.steveliedtke.data;

import static de.steveliedtke.data.OfyService.*;

import com.googlecode.objectify.Key;


public class PropertyDAO {

	public String findValueByKey(final String key){
		final Property property = ofy().load().type(Property.class).filter("key", key).first().now();
		final String result;
		if(property==null){
			result = null;
		}else{
			result = property.getValue();
		}
		return result;
	}
	
	public Property save(final Property property){
		final Key<Property> keyStatus = ofy().save().entity(property).now();
		return ofy().load().key(keyStatus).now();
	}
}
