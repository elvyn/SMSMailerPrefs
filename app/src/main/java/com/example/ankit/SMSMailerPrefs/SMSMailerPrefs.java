package com.example.ankit.SMSMailerPrefs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SMSMailerPrefs extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String email_address = (new SettingsStorage(getApplicationContext())).getSenderEmail();
        TextView text1 = (TextView)findViewById(R.id.sender_email_value);
        text1.setText(email_address);
        text1.setTextSize(19);
        text1.setTextColor(Color.BLACK);

        final List<String> receivers_list = new ArrayList<String>((new SettingsStorage(getApplicationContext())).getReceiversList());
        ListView email_list = (ListView)findViewById(R.id.receiver_email_list);

        if (receivers_list != null) {
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list,
                    R.id.textViewRegisteredReceivers, receivers_list);
            email_list.setAdapter(adapter);

            /* Add listener for item click event. */
            email_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(SMSMailerPrefs.this);
                        final String item_to_remove = receivers_list.get(position);
                        final int positionToRemove = position;

                        adb.setTitle("Delete?");
                        adb.setMessage("Do you want to delete " + item_to_remove + "?");
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                receivers_list.remove(positionToRemove);
                                adapter.notifyDataSetChanged();

                                /* Update storage */
                                (new SettingsStorage(getApplicationContext())).removeReceiver(item_to_remove);
                            }
                        });
                        adb.show();
                    }
                }
            );
            Intent intent_service = new Intent(this, SMSMailerService.class);
            stopService(intent_service);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent_service = new Intent(this, SMSMailerService.class);
        stopService(intent_service);

        switch (id) {
            case R.id.update_sender:
                Intent intent = new Intent(this, UpdateSenderActivity.class);
                startActivity(intent);
                startService(intent_service);
                return true;
            case R.id.add_receiver:
                Intent addintent = new Intent(this, AddReceiverActivity.class);
                startService(intent_service);
                startActivity(addintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
