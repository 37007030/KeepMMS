package harris.steven.keepmms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {
    public static List<Message> messages;
    Context context;
    private static MessageAdapter instance = null;
    KeepMMSDatabaseHelper keepmmsDatabaseHelper;
    SQLiteDatabase db;

    public static MessageAdapter getMessageAdapterInstance(Context context) {
        if (instance == null) {
            instance = new MessageAdapter(context);
        }
            return instance;
    }

    private MessageAdapter(Context context){
        this.context = context;
        keepmmsDatabaseHelper = new KeepMMSDatabaseHelper(context);
        db = keepmmsDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("MESSAGE", new String[] {"ID", "TO_ID", "FROM_ID", "MESSAGE", "TIMESTAMP"}, null,null, null, null, "TIMESTAMP ASC");
        messages = new ArrayList<>();
        while (cursor.moveToNext()) {
            Message mFromDatabase = new Message(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    new Timestamp(cursor.getLong(4)
                    ));
            messages.add(mFromDatabase);
            Log.i("database", "Got message from database: " + mFromDatabase.toString());
        }
    }


    public void add(Message message) {
        messages.add(message);
        instance.notifyDataSetChanged(); // to render the list we need to notify
        KeepMMSDatabaseHelper.insertMessage(db, message);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);

        if (message.isMyMessage()) { // this message was sent by us so let's create a basic chat bubble on the right
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getMessage());
        } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getMessage());
        }

        return convertView;
    }

}

class MessageViewHolder {
    public TextView messageBody;
}
