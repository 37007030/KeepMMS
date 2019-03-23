package harris.steven.keepmms;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageFeed extends Activity {

    private static final int REQUEST_SEND_SMS = 1;
    private static final int REQUEST_READ_PHONE_STATE = 2;
    private static final int REQUEST_READ_SMS = 3;

    //for testing display of messages
    private ArrayList<String> messageListItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_feed);

        //for testing display of messages
        ArrayAdapter<String> adapter;
        ListView listView = findViewById(R.id.messages_view);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                messageListItems);
        listView.setAdapter(adapter);

        //request SEND_SMS permission
        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);

        //checks and displays permissions granted (for debugging). filter logcat to only show errors to see logs.
        checkPermission();

        Button sendMessage = findViewById(R.id.send_button);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextMessage = findViewById(R.id.editText);
                String message = editTextMessage.getText().toString();
                EditText editTextToPhoneNumber = findViewById(R.id.editTextToPhoneNumber);

                //Make sure we have all required permissions before attempting to send message. If we do, send the text
                if (checkPermission()) {
                    Log.e("permission", "Calling SmsManager.getDefault() next");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(editTextToPhoneNumber.getText().toString(), null, message, null, null);

                    //Show the message in the messages_view activity
                    addToFeed(message);

                } else {
                    Toast.makeText(MessageFeed.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
   // }

    private boolean checkPermission() {
        Log.e("permission", "Begin checkPermission");

        if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            Log.e("permission", "SEND_SMS not yet granted");
        }

        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            Log.e("permission", "READ_PHONE_STATE not yet granted");
        }

        int send_SMS = checkSelfPermission(Manifest.permission.SEND_SMS);
        Log.e("permission","send_sms = " + send_SMS);

        int read_phone_state = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        Log.e("permission", "read_phone_state = " + read_phone_state);

        if (send_SMS == PackageManager.PERMISSION_GRANTED && read_phone_state == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        switch (requestCode) {
            case REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e("permission", "REQUEST_SEND_SMS granted");

                    //after SEND_SMS has been granted, request READ_PHONE_STATE permission
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

                } else {
                    // permission denied
                    Log.e("permission", "REQUEST_SEND_SMS denied");
                }
                return;
            }
            case REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e("permission", "REQUEST_READ_PHONE_STATE granted");

                    //after READ_PHONE_STATE has been granted, request READ_SMS permission
                    requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS);

                } else {
                    // permission denied
                    Log.e("permission", "REQUEST_READ_PHONE_STATE denied");
                }
                return;
            }

            case REQUEST_READ_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e("permission", "REQUEST_READ_PHONE_STATE granted");
                } else {
                    // permission denied
                    Log.e("permission", "REQUEST_READ_PHONE_STATE denied");
                }
                return;
            }
        }
    }

    //displays sent SMS messages in the message feed
    private void addToFeed(String message){
        messageListItems.add(message);
    }
}
