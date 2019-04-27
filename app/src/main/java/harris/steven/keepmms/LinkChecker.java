package harris.steven.keepmms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Keep;
import android.util.Log;
import android.view.View;

import org.apache.commons.validator.routines.UrlValidator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LinkChecker {

    private static UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);

    private Context context;
    private static LinkChecker instance = null;
    private KeepMMSDatabaseHelper keepmmsDatabaseHelper;
    private static SQLiteDatabase db;


    public static LinkChecker getLinkCheckerInstance(Context context) {
        if (instance == null) {
            instance = new LinkChecker(context);
        }
        return instance;
    }

    private LinkChecker(Context context) {
        this.context = context;
        keepmmsDatabaseHelper = new KeepMMSDatabaseHelper(context);
        db = keepmmsDatabaseHelper.getWritableDatabase();

    }

    public static boolean validUrl(String url){
        return urlValidator.isValid(url);
    }


    public static List<Link> getLinks(){

        List<Link> links = new ArrayList<>();

        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM LINK", null);
        if (cur != null) {
            cur.moveToFirst();                       // Always one row returned.
            if (cur.getInt (0) == 0) {    // Zero count means empty table.
                Log.e("harris", "table has no data");
                cur.close();
            }

            else {
                cur.close();

                Cursor cursor = db.query("LINK",
                        new String[]{"ID", "TO_ID", "FROM_ID", "LINK", "TIMESTAMP"},
                        null,
                        null,
                        null,
                        null,
                        "ID ASC");
                cursor.moveToFirst();

                Link linkFromDatabase;

                do {
                    linkFromDatabase = new Link(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    );
                    links.add(linkFromDatabase);
                    Log.e("harris", "Got link from database: " + linkFromDatabase.toString());
                } while (cursor.moveToNext());
                cursor.close();
            }
        }

        else {
            Log.e("harris", "cursor is null");
        }
        return links;
    }

    public static void checkForLinksInMessage(Message message) {

        String msg = message.getMessage();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String substring = "";

        if(msg.contains(" ")){
            String[] words = msg.split("\\s+");

            for (String string : words) {
                if (validUrl(string)) {
                    Log.e("harris", "Contains URL: " + string);

                    if(string.endsWith(".") || string.endsWith("!") || string.endsWith("?")){
                        substring = string.substring(0, string.length()-1);
                        Log.e("harris", "valid URL: " + substring);

                        Link link = new Link(message.getTo_ID(), message.getFrom_ID(), substring, dateFormat.format(date));
                        Log.e("harris", "New link created: " + link.getTo_ID() + " " + link.getFrom_ID() + " " + link.getLink() + " " + link.getTimestamp());
                        KeepMMSDatabaseHelper.insertLink(db, link);
                    }

                    else {
                        Link link = new Link(message.getTo_ID(), message.getFrom_ID(), string, dateFormat.format(date));
                        Log.e("harris", "New link created: " + link.getTo_ID() + " " + link.getFrom_ID() + " " + link.getLink() + " " + link.getTimestamp());
                        KeepMMSDatabaseHelper.insertLink(db, link);
                    }
                }
            }
        }
        else if (validUrl(msg)){
            Log.e("harris", "message contained no spaces");
            if(msg.endsWith(".") || msg.endsWith("!") || msg.endsWith("?")){
                substring = msg.substring(0, msg.length()-1);
                Log.e("harris", "valid URL: " + substring);

                Link link = new Link(message.getTo_ID(), message.getFrom_ID(), substring, dateFormat.format(date));
                Log.e("harris", "New link created: " + link.getTo_ID() + " " + link.getFrom_ID() + " " + link.getLink() + " " + link.getTimestamp());
                KeepMMSDatabaseHelper.insertLink(db, link);

            }

            else {
                Log.e("harris", "valid URL: " + msg);
                Link link = new Link(message.getTo_ID(), message.getFrom_ID(), msg, dateFormat.format(date));
                Log.e("harris", "New link created: " + link.getTo_ID() + " " + link.getFrom_ID() + " " + link.getLink() + " " + link.getTimestamp());
                KeepMMSDatabaseHelper.insertLink(db, link);
            }
        }

    }
}
