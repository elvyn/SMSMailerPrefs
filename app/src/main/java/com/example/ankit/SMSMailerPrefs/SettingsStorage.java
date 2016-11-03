package com.example.ankit.SMSMailerPrefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ankit on 03/04/16.
 */
public class SettingsStorage {
    private static final String SETTINGS_FILE_NAME = "SMSMailerConf.data";
    private static final String SETTINGS_SENDER_EMAIL = "SenderEmail";
    private static final String SETTINGS_SENDER_PASSWD = "SenderPasswd";
    private static final String SETTINGS_RECEIVERS_LIST = "ReceiversList";

    public static final String DISPLAY_SENDER_ADDRESS = "add@sender.address";
    public static final String DISPLAY_SENDER_PASSWD = "000000";
    public static final String DISPLAY_RECEIVER_ADDRESS = "add@receiver.address";

    private SharedPreferences mAppPrefs;

    public SettingsStorage(Context context) {
        mAppPrefs = context.getSharedPreferences(SETTINGS_FILE_NAME, 0);
    }

    public void updateSender(String senderEmail, String senderPasswd) {
        SharedPreferences.Editor editor = mAppPrefs.edit();

        editor.putString(SETTINGS_SENDER_EMAIL, senderEmail);
        editor.putString(SETTINGS_SENDER_PASSWD, senderPasswd);
        editor.commit();
        System.gc();
    }

    public void addReceivers(String receiverEmail) {
        Set<String> receivers_list = new HashSet<String>(mAppPrefs.getStringSet(
                SETTINGS_RECEIVERS_LIST, new HashSet<String>()));
        receivers_list.add(receiverEmail);
        SharedPreferences.Editor editor = mAppPrefs.edit();
        editor.putStringSet(SETTINGS_RECEIVERS_LIST, receivers_list);
        editor.commit();
        System.gc();
    }

    public void removeReceiver(String receiverEmail) {
        Set<String> receivers_list = new HashSet<String>(mAppPrefs.getStringSet(
                SETTINGS_RECEIVERS_LIST, new HashSet<String>()));
        receivers_list.remove(receiverEmail);
        SharedPreferences.Editor editor = mAppPrefs.edit();
        editor.putStringSet(SETTINGS_RECEIVERS_LIST, receivers_list);
        editor.commit();
        System.gc();
    }

    public String getSenderEmail() {
        return mAppPrefs.getString(SETTINGS_SENDER_EMAIL, DISPLAY_SENDER_ADDRESS);
    }

    public void getSenderDetails(String email_address, String email_passwd) {
        email_address = this.getSenderEmail();
        email_passwd = this.getSenderPasswd();
    }

    public String getSenderPasswd() {
        return mAppPrefs.getString(SETTINGS_SENDER_PASSWD, DISPLAY_SENDER_PASSWD);
    }

    public HashSet<String> getReceiversList() {
        return new HashSet<String>(mAppPrefs.getStringSet(SETTINGS_RECEIVERS_LIST, new HashSet<String>()));
    }

    public String[] getReceiversStringList() {
        HashSet<String>  tmp_hashset = new HashSet<String>(mAppPrefs.getStringSet(SETTINGS_RECEIVERS_LIST, new HashSet<String>()));
        return tmp_hashset.toArray(new String[tmp_hashset.size()]);
    }
}
