package com.example.ankit.SMSMailerPrefs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class UpdateSenderActivity extends AppCompatActivity {
    private static final String DISPLAY_SENDER_ADDRESS = SettingsStorage.DISPLAY_SENDER_ADDRESS;
    private static final String DISPLAY_SENDER_PASSWD = SettingsStorage.DISPLAY_SENDER_PASSWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sender);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String email_address = "";
        String email_passwd = "";

        (new SettingsStorage(getApplicationContext())).getSenderDetails(email_address, email_passwd);

        EditText text = (EditText)findViewById(R.id.sender_email);
        text.setText(email_address);
        text = (EditText)findViewById(R.id.sender_passwd);
        text.setText(email_passwd);
    }

    public void saveDetails() {
        EditText text = (EditText)findViewById(R.id.sender_email);
        String email_address = text.getText().toString();
        text = (EditText)findViewById(R.id.sender_passwd);
        String email_passwd = text.getText().toString();

        if ((email_address.equals(DISPLAY_SENDER_ADDRESS) ||
                email_address.equals("")) && (email_passwd.equals(DISPLAY_SENDER_PASSWD) ||
                email_passwd.equals(""))) {
            return;
        }

        /* Save details to storage. */
        new SettingsStorage(getApplicationContext()).updateSender(email_address, email_passwd);
    }

}
