package de.steveliedtke.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.steveliedtke.data.ChromeUserDAO;

@Singleton
public class GCMChromeServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -4926399511140315414L;
	
	@Inject
	private ChromeUserDAO userDAO;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String requestURI = req.getRequestURI();
		final String userChannelId = req.getParameter("userChannelId");
		if(userChannelId==null || userChannelId.isEmpty()){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			if(requestURI.equals("/gcm/chrome/register")){
				userDAO.create(userChannelId);
			}else{
				userDAO.delete(userChannelId);
			}
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}
}