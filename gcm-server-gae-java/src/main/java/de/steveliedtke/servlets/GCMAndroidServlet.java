package de.steveliedtke.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.steveliedtke.data.DeviceDAO;

@Singleton
public class GCMAndroidServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3272097623898787903L;
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(GCMAndroidServlet.class.getName());
	
	@Inject
	private DeviceDAO deviceDAO;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String requestURI = req.getRequestURI();
		logger.info("requestURI: " + requestURI);
		final String registrationId = req.getParameter("registrationId");
		if(registrationId==null || registrationId.isEmpty()){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			if(requestURI.equals("/gcm/android/register")){
				deviceDAO.create(registrationId);
			}else{
				deviceDAO.delete(registrationId);
			}
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}
}