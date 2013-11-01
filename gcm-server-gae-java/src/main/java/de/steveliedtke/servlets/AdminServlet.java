package de.steveliedtke.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.steveliedtke.data.Property;
import de.steveliedtke.data.PropertyDAO;

@Singleton
public class AdminServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -5669133122976174671L;
	
	// TODO don't use it like that :-P 
	@Inject
	private PropertyDAO propertyDAO;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/admin.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String gcmKey = req.getParameter("gcm_key");
		
		Property property = propertyDAO.findByKey("gcm.api.key");
		if(property == null){
			property = new Property();
			property.setKey("gcm.api.key");
			
		}
		property.setValue(gcmKey);
		propertyDAO.save(property);
		
		req.getRequestDispatcher("/admin.jsp").forward(req, resp);
	}
}
