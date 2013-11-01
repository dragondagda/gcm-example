package de.steveliedtke.servlets;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.steveliedtke.data.ChromeUserDAO;
import de.steveliedtke.data.DeviceDAO;
import de.steveliedtke.gcm.GCMAndroidSender;
import de.steveliedtke.gcm.GCMChromeSender;

@Singleton
public class GCMPushServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 705333067225388914L;
	
	@Inject
	private GCMAndroidSender gcmAndroidSender;
	
	@Inject
	private GCMChromeSender gcmChromeSender;
	
	@Inject
	private DeviceDAO deviceDAO;
	
	@Inject
	private ChromeUserDAO chromeUserDAO;
	
	// TODO check size of message

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final Queue queue = QueueFactory.getQueue("pull-queue");
		// TODO is this correct?
		final List<TaskHandle> taskResults = queue.leaseTasks(1, TimeUnit.SECONDS, 1);
		
		if(!taskResults.isEmpty()){
			final String message = String.valueOf(taskResults.get(0).getPayload());
			final List<String> registrationIds = deviceDAO.findAllRegistrationIds(); 
			gcmAndroidSender.sendSyncPayload(message, registrationIds);
			final List<String> userChannelIds = chromeUserDAO.findAllUserChannelIds();
			gcmChromeSender.sendMessage(message, userChannelIds);
			queue.deleteTask(taskResults.get(0).getName());
		}
	}
}