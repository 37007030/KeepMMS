package harris.steven.keepmms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class KeepMMSDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "KEEPMMS";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_USER = "CREATE TABLE USER ("
            + "UID TEXT PRIMARY KEY);";
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE MESSAGE ("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "TO_ID TEXT, "
            + "FROM_ID TEXT, "
            + "MESSAGE TEXT, "
            + "TIMESTAMP NUMERIC);";

    private static final String CREATE_TABLE_LINKS = "CREATE TABLE LINK (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TO_ID TEXT, " +
            "FROM_ID TEXT, " +
            "LINK TEXT, " +
            "TIMESTAMP TEXT);";

    KeepMMSDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_LINKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void insertUser(SQLiteDatabase db, String UID){
        ContentValues userValues = new ContentValues();
        userValues.put("UID", UID);
        db.insert("USER", null, userValues);
    }

    public static void insertMessage(SQLiteDatabase db, Message message){
        ContentValues messsageValues = new ContentValues();
        messsageValues.put("TO_ID", message.getTo_ID());
        messsageValues.put("FROM_ID", message.getFrom_ID());
        messsageValues.put("MESSAGE", message.getMessage());
        messsageValues.put("TIMESTAMP", message.getTimestamp().getTime());
        db.insert("MESSAGE", null, messsageValues);
    }

    public static void insertLink(SQLiteDatabase db, Link link){

        ContentValues linkValues = new ContentValues();
        linkValues.put("TO_ID", link.getTo_ID());
        linkValues.put("FROM_ID", link.getFrom_ID());
        linkValues.put("LINK", link.getLink());
        linkValues.put("TIMESTAMP", link.getTimestamp());

        printContentValues(linkValues);

        db.insert("LINK", null, linkValues);
    }

    public static void printContentValues(ContentValues vals)
    {
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        Log.e("DatabaseSync", "ContentValue Length :: " +vals.size());

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.e("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
        }
    }
}
