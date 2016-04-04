package com.example.ankit.SMSMailerPrefs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;


public class AddReceiverActivity extends AppCompatActivity {
    private static final String DISPLAY_RECEIVER_ADDRESS = SettingsStorage.DISPLAY_RECEIVER_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receiver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveDetails() {
        EditText text = (EditText)findViewById(R.id.receiver_email);
        String email_address = text.getText().toString();

        if (!(email_address.equals(DISPLAY_RECEIVER_ADDRESS) || email_address.equals(""))) {
            (new SettingsStorage(getApplicationContext())).addReceivers(email_address);
        }

        /* Reset the text value to default address */
        text.setText("");
        text.setHint(DISPLAY_RECEIVER_ADDRESS);
    }

}
