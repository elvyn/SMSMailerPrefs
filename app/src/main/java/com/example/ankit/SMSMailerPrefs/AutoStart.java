package com.example.ankit.SMSMailerPrefs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ankit on 2/11/16.
 */

public class AutoStart extends BroadcastReceiver {
    public void onReceive(Context arg0, Intent arg1)
    {
        Intent intent = new Intent(arg0, SMSMailerService.class);
        arg0.startService(intent);
        Log.i("Autostart", "started");
    }
}
