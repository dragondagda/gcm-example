package de.steveliedtke.data;

import static de.steveliedtke.data.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;

public class DeviceDAO {

	public Device create(final String registrationId){
		final Device newDevice = new Device();
		newDevice.setRegistrationId(registrationId);
		final Key<Device> keyStatus = ofy().save().entity(newDevice).now();
		return ofy().load().key(keyStatus).now();
	}
	
	public void delete(final String registrationId){
		ofy().delete().entity(this.findByRegistrationId(registrationId));
	}
	
	private Device findByRegistrationId(final String registrationId){
		return ofy().load().type(Device.class).filter("registrationId", registrationId).first().now();
	}
	
	public List<String> findAllRegistrationIds(){
		final List<Device> devices = ofy().load().type(Device.class).list();
		final List<String> registrationIds = new ArrayList<String>(devices.size());
		for(final Device device : devices){
			registrationIds.add(device.getRegistrationId());
		}
		return registrationIds;
	}
}