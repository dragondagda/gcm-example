package de.steveliedtke.data;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {
        factory().register(Device.class);
        factory().register(Property.class);
        factory().register(ChromeUser.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
    
    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
