package de.steveliedtke.modules;

import com.google.inject.servlet.ServletModule;

import de.steveliedtke.servlets.AdminServlet;
import de.steveliedtke.servlets.GCMAndroidServlet;
import de.steveliedtke.servlets.GCMChromeServlet;
import de.steveliedtke.servlets.GCMPushServlet;
import de.steveliedtke.servlets.MessageServlet;

public class ServletsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/gcm/android/register").with(GCMAndroidServlet.class);
		serve("/gcm/android/unregister").with(GCMAndroidServlet.class);
		serve("/gcm/chrome/register").with(GCMChromeServlet.class);
		serve("/gcm/chrome/unregister").with(GCMChromeServlet.class);
		serve("/message").with(MessageServlet.class);
		// TODO make this only accessible by cron
		serve("/cron").with(GCMPushServlet.class);
		// TODO admin function to set the gcm api key
		
		// jsps
		serve("/admin").with(AdminServlet.class);
	}
}
