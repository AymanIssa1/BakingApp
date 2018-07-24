package com.backingapp.ayman.backingapp;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

public class BackingAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }

}
