<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.google.inject.Injector"%>
<%@ page import="com.google.inject.Guice"%>
<%@ page import="de.steveliedtke.data.PropertyDAO" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final String gcmApiKey = inj.getInstance(PropertyDAO.class).findValueByKey("gcm.api.key");
%>

<html>
  <jsp:include page="basicJSPs/head.jsp" />

  <body>
   	<jsp:include page="basicJSPs/nav.jsp" />
   	<div class="row">
   		<div class="large-9 push-3 columns">
	      
	      <h2>Admin-Area</h2>
				
			<p id="info">
				Welcome to the Admin area!</br></br>
				<b>Please insert the GCM API Key:</b>
			</p>
			
			<form action="/admin" method="POST">
			<div class="row">
			  	<div class="large-8 columns">
				  	<label>Google API Key:</label>
				  	<input type="password" id="gcm_key" name="gcm_key" value="<%=gcmApiKey %>">
				</div>
			</div>
			
			<div class="row">
			  	<div class="large-4 large-offset-8 columns">
			  		<input type="submit" value="Speichern" class="large button expand success">
			  	</div>
			  </div>
			</form>
	            
	    </div>
   	</div>
   	
   	<jsp:include page="basicJSPs/footer.jsp" />
  </body>
</html>
