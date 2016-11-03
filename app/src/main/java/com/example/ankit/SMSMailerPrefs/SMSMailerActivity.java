package com.example.ankit.SMSMailerPrefs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SMSMailerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsmailer);
    }

    public void updateSenderReceiver() {
        Intent prefs_intent = new Intent(getApplicationContext(), SMSMailerPrefs.class);
        prefs_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(prefs_intent);
    }


    public void updateSenderReceiver(View tmpView) {
        this.updateSenderReceiver();
    }

    public void startSMSMailerService() {
        Intent intent = new Intent(getApplicationContext(), SMSMailerService.class);
        try {
            getApplicationContext().startService(intent);
        } catch (Exception exc) {
            Log.e("SMSMailer", "Unable to stop service due to " + exc);
        }
        getApplicationContext().startService(intent);
    }

    public void startSMSMailerService(View tmpView) {
        this.startSMSMailerService();
    }
}
