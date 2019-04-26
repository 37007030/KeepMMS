package harris.steven.keepmms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import java.sql.Timestamp;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsReceiver";
    String msg, phoneNo = "";
    MessageAdapter messageAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.e(TAG, "intent received: " + intent.getAction());
         messageAdapter = MessageAdapter.getMessageAdapterInstance(context);
        if(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            Bundle bundle = intent.getExtras();

            if(bundle != null){
                Object[] pdu = (Object[])bundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[pdu.length];

                for(int i = 0; i<pdu.length; i++){
                    String format = bundle.getString("format");
                    message[i] = SmsMessage.createFromPdu((byte[])pdu[i], format);

                    msg = message[i].getMessageBody();
                }
                phoneNo = message[0].getOriginatingAddress();

                Message newMessage = new Message("0", phoneNo, msg, new Timestamp(System.currentTimeMillis()));
                messageAdapter.add(newMessage);
                LinkChecker.checkForLinksInMessage(newMessage);

//                Toast.makeText(context, "Message: " + msg + "From:" + phoneNo, Toast.LENGTH_LONG).show();
            }
        }

    }
}
