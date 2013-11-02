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
 */
package de.steveliedtke.gcm.example.gcm;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

import de.steveliedtke.gcm.example.R;

/**
 * @author Steve
 *
 */
public class GCMReceiver extends GCMBroadcastReceiver{

	@Override
	protected String getGCMIntentServiceClassName(Context context) {
		return context.getString(R.string.gcm_service_class);
	}
}
