package harris.steven.keepmms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    KeepMMSDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MESSAGE);
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
}
