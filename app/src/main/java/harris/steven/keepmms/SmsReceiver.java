package harris.steven.keepmms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsReceiver";
    String msg, phoneNo = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "intent received: " + intent.getAction());

        if(intent.getAction() == ("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();

            if(bundle != null){
                Object[] pdu = (Object[])bundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[pdu.length];

                for(int i = 0; i<pdu.length; i++){
                    String format = bundle.getString("format");
                    message[i] = SmsMessage.createFromPdu((byte[])pdu[i], format);

                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getDisplayOriginatingAddress();

                }
                Toast.makeText(context, "Message: " + msg, Toast.LENGTH_LONG).show();
                Log.e("SMS Receive", "Message: " + msg);

            }
        }

    }
}
