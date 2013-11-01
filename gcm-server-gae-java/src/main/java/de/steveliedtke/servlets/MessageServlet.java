package de.steveliedtke.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.inject.Singleton;

@Singleton
public class MessageServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -7175224258423096237L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String message = req.getParameter("message");
		if(message==null){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			final Queue queue = QueueFactory.getQueue("pull-queue");
			queue.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL)
			                                     .payload(message));
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
