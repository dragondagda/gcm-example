/*
 * Hackerspace Bremen Android App - An Open-Space-Notifier for Android
 * 
 * Copyright (C) 2012 Steve Liedtke <sliedtke57@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; either version 3 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 * the GNU General Public License for more details.
 * 
 * You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html.
 * 
 * Contributors:
 *     Steve Liedtke <sliedtke57@gmail.com>
 *     Matthias Friedrich <mtthsfrdrch@gmail.com>
 */
package de.steveliedtke.gcm.example.gcm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import de.steveliedtke.gcm.example.R;
import de.steveliedtke.gcm.example.ServerComm;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService(){
	    super("270071428507");
	}
	
	protected GCMIntentService(final String senderId) {
		super(senderId);
	}

	

	@Override
	protected void onRegistered(final Context context, final String regId) {
		Log.i("GCM", "Received registration ID");
		
		final RegisterCommunication comm = new RegisterCommunication(regId);
		comm.execute();
		
		final SharedPreferences settings = context.getSharedPreferences("gcm", 0);
	    final SharedPreferences.Editor editor = settings.edit();
	    editor.putString("registrationId", regId);
	    editor.commit();
	    Log.i("GCM", "Saved registration id to the shared preferences");
	}
	
	@Override
	protected void onError(final Context context, final String errorId) {
		// TODO handle error
		Log.e(this.getClass().getSimpleName(), errorId);
	}

	@Override
	protected void onMessage(final Context context, final Intent intent) {
		Log.w("GCM-Message", "Received message");
		final String payload = intent.getStringExtra("payload");
		Log.d("GCM-Message", "dmControl: payload = " + payload);
		try {
		    
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			
			Log.i("GCM-Message", "PAYLOAD: " + payload);
			if(settings.getBoolean("notification_preference", true)){
				this.displayNotification(context, payload,
						settings.getBoolean("vibration_preference", true), 
						settings.getBoolean("permanent_notification_preference", false));
			}
		} catch (JSONException e) {
			Log.e("GCM-Message", "JSON-Exception occured!");
		}
	}
	
	

	public void displayNotification(final Context context, final String message,
			final boolean vibrationEnabled, final boolean permanentNotification) throws JSONException {
		// TODO rewrite this method: throw notification with message
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		
		int icon;
		CharSequence notificationText;
		final CharSequence contentTitle;
		icon = R.drawable.ic_launcher;
		notificationText = context.getString(R.string.new_message);
		contentTitle = context.getString(R.string.new_message);
		
		long when = System.currentTimeMillis();

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(icon);
		builder.setTicker(notificationText);
		builder.setWhen(when);
		if(message!=null && message.length()>0){
			builder.setStyle(new NotificationCompat.BigTextStyle().setSummaryText(notificationText)
	        .bigText(message));
		}
		builder.setAutoCancel(true);
		builder.setContentTitle(contentTitle);
		builder.setContentText(notificationText);
		
		final Notification notification = builder.build();
		if(vibrationEnabled){
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		mNotificationManager.notify(82917, notification);
	}
	
	@Override
	protected void onUnregistered(final Context context, final String regId) {
		final UnregisterCommunication comm = new UnregisterCommunication(
				regId);
		comm.execute();
	}
	
	private class RegisterCommunication extends ServerComm {
		public RegisterCommunication(final String registrationId) {
			super();
			this.servletUrl = "gcm/android/register";
			try {
				this.postParams.put("registrationId", URLEncoder.encode(registrationId, "UTF-8"));
				this.appVersionName = GCMIntentService.this.getPackageManager()
						.getPackageInfo(GCMIntentService.this.getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				this.appVersionName = "??";
			} catch (UnsupportedEncodingException e) {
				Log.e(RegisterCommunication.class.getName(), "UnsupportedEncodingException occured: " + e.getMessage());
			}
		}

		@Override
		protected void onCancelled() {
			Log.e("GCM Register", "Registration ID couldn't be sent to server");
			// TODO handle cancel
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			Log.i("GCM Register", "Sent registration ID to server");
			// TODO handle result
		}
	}
	
	private class UnregisterCommunication extends ServerComm {
		public UnregisterCommunication(final String registrationId) {
			super();
			this.servletUrl = "gcm/android/unregister";
			try {
				this.postParams.put("registrationId", URLEncoder.encode(registrationId,"UTF-8"));
				this.appVersionName = GCMIntentService.this.getPackageManager()
						.getPackageInfo(GCMIntentService.this.getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				this.appVersionName = "??";
			} catch (UnsupportedEncodingException e) {
				Log.e(UnregisterCommunication.class.getName(), "UnsupportedEncodingException occured: " + e.getMessage());
			}
		}

		@Override
		protected void onCancelled() {
			Log.e("GCM Unregister", "Unregistering wasn't successfull!");
			// TODO handle cancel
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			Log.i("GCM Unregister", "You successfully unregistered!");
			// TODO handle result
		}
	}
}
