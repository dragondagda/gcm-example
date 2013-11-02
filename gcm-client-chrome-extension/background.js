function messageReceived(message) {
  console.log("Push Message payload = "+ message.payload + " Subchannel Id="+message.subchannelId);

  var notification = window.webkitNotifications.createNotification(
      'images/icon.png', 'Push Message',
       message.payload);
      
 	 notification.show();
}
function ListenForMessages() {
  console.log("Listening for messages");
  // Begin listening for Push Messages.
  chrome.pushMessaging.onMessage.addListener(messageReceived);
}

function channelIdCallback(message) {
  	console.log("Background Channel ID callback seen, channel Id is " + message.channelId);

	ListenForMessages();
	
	registerAtServer(message.channelId);
}

function registerAtServer(channelId){
	$.ajax({
	  type: "POST",
	  url: "https://dragondagda-gcm.appspot.com/gcm/chrome/register",
	  data: {userChannelId : channelId},
	  success: function() {
		console.log( "registration successful" );
	  }
	});
}

// This function gets called in the packaged app model on launch.
chrome.app.runtime.onLaunched.addListener(function(launchData) {
	console.log("onLaunched: called?")
  	chrome.pushMessaging.getChannelId(true, channelIdCallback);
});

//This is called when the extension is installed.
chrome.runtime.onInstalled.addListener(function() {
	console.log("Push Messaging Sample Client installed!");

	chrome.pushMessaging.getChannelId(true, channelIdCallback);
});