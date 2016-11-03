package com.example.ankit.SMSMailerPrefs;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSMailerService extends Service {

    private class SMSreceiver extends BroadcastReceiver {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String strMsgSrc = "";
            String strMsgBody = "";

            String strMessage = "";

            try {
                if ( extras != null ) {
                    Object[] smsextras = (Object[]) extras.get("pdus");

                    for (int i = 0; i < smsextras.length; i++) {
                        SmsMessage smsmsg = SmsMessage.createFromPdu((byte[]) smsextras[i]);

                        strMsgBody = smsmsg.getMessageBody().toString();
                        strMsgSrc = smsmsg.getOriginatingAddress();

                        strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

                        Log.i(TAG, strMessage);
                    }

                    /* Send email
                     */
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "Sender: "+ strMsgSrc + ", Message: " + strMessage, duration);
                    toast.show();

                    final String tmp_senderNum = strMsgSrc;
                    final String tmp_msg = strMsgBody;


                    final String emailAddress = new SettingsStorage(getApplicationContext()).getSenderEmail();
                    final String emailPasswd = new SettingsStorage(getApplicationContext()).getSenderPasswd() ;
                    final String[] toList = (new SettingsStorage(getApplicationContext())).getReceiversStringList();

                    if ("".equals(emailAddress) || "".equals(emailPasswd) || toList.length == 0) {
                        throw new Exception("Sender/Receivers is not set.");
                    }

                    Thread email_thread = new Thread() {
                        @Override
                        public void run() {
                            Mail m = new Mail(emailAddress, emailPasswd);
                            m.setTo(toList);
                            m.setFrom(emailAddress);
                            m.setSubject("SMS: Message from " + tmp_senderNum);
                            m.setBody("Sender: " + tmp_senderNum + "\n Message: " + tmp_msg);

                            try {
                                m.send();
                            } catch(Exception e) {
                                Log.e("MailApp", "Could not send email", e);
                            }
                        }

                    };
                    // Send Email
                    email_thread.start();
                } // bundle is null

            } catch (Exception exc) {
                Log.e("SMSMailer", "Unable to send mail due to" + exc);
            }

        }

    }

    private SMSreceiver mSMSReceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        mSMSReceiver = new SMSreceiver();
        registerReceiver(mSMSReceiver, mIntentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mSMSReceiver);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
