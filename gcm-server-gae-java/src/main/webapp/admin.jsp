<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.google.inject.Injector"%>
<%@ page import="com.google.inject.Guice"%>
<%@ page import="de.steveliedtke.data.PropertyDAO" %>

<%
	final Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
	final PropertyDAO propertyDAO = inj.getInstance(PropertyDAO.class);
	String gcmApiKey = propertyDAO.findValueByKey("gcm.api.key");
	String gcmClientId = propertyDAO.findValueByKey("gcm.client.id");
	String gcmClientSecret = propertyDAO.findValueByKey("gcm.client.secret");
	if(gcmApiKey==null){
		gcmApiKey = "";
	}
	if(gcmClientId==null){
		gcmClientId = "";
	}
	if(gcmClientSecret==null){
		gcmClientSecret = "";
	}
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
				<b>Please insert your Google Cloud credentials:</b>
			</p>
			
			<form action="/admin" method="POST">
				<div class="row">
				  	<div class="large-8 columns">
					  	<label>Google API Key (for Android):</label>
					  	<input type="password" id="gcm_key" name="gcm_key" value="<%=gcmApiKey %>">
					</div>
				</div>
				
				<div class="row">
				  	<div class="large-8 columns">
					  	<label>Google ClientId (for Chrome):</label>
					  	<input type="password" id="gcm_clientid" name="gcm_clientid" value="<%=gcmClientId %>">
					</div>
				</div>
				
				<div class="row">
				  	<div class="large-8 columns">
					  	<label>Google ClientSecret (for Chrome):</label>
					  	<input type="password" id="gcm_clientsecret" name="gcm_clientsecret" value="<%=gcmClientSecret %>">
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
