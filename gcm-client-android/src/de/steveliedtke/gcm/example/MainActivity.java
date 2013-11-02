package de.steveliedtke.gcm.example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText editText = (EditText)this.findViewById(R.id.editText1);
		
		this.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(editText.getText().toString().length()>0){
					final MessageCommunication comm = new MessageCommunication(
							editText.getText().toString());
					comm.execute();
				}
			}
		});
		
		this.register();
	}
	
	private void register() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, "270071428507");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO implement setting option
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class MessageCommunication extends ServerComm {
		public MessageCommunication(final String message) {
			super();
			this.servletUrl = "message";
			try {
				this.postParams.put("message", URLEncoder.encode(message, "UTF-8"));
				this.appVersionName = MainActivity.this.getPackageManager()
						.getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				this.appVersionName = "??";
			} catch (UnsupportedEncodingException e) {
				Log.e(MessageCommunication.class.getName(), "UnsupportedEncodingException occured: " + e.getMessage());
			}
		}

		@Override
		protected void onCancelled() {
			Log.e("GCM Register", "Message couldn't be sent to server");
			// TODO handle cancel
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			Log.i("GCM Register", "Sent message to server");
			// TODO handle result
		}
	}
}
