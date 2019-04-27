package harris.steven.keepmms;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Create action bar with back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get reference to the singleton instance of the database helper
        final KeepMMSDatabaseHelper keepMMSDatabaseHelper = KeepMMSDatabaseHelper.getInstance(this.getApplicationContext());
        final SQLiteDatabase db = keepMMSDatabaseHelper.getWritableDatabase();

        //Query the stored setting from the database
        int displaySentLinks = keepMMSDatabaseHelper.querySettingSentLinks(db);

        Log.e("harris", "int returned from settings table: " + displaySentLinks);

        //Make the switch appearance reflect the user's stored setting
        final Switch switchKeepSentLinks = findViewById(R.id.switchKeepSentLinks);
        if(displaySentLinks == 1)
            switchKeepSentLinks.setChecked(true);
        else if (displaySentLinks == 0)
            switchKeepSentLinks.setChecked(false);

        //If the user changes the setting, store this in the database
        switchKeepSentLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int switchValue = switchKeepSentLinks.isChecked() ? 1 : 0;
                Log.e("harris", "switch was toggled to: " + switchValue);
                keepMMSDatabaseHelper.changeSettingSentLinks(db, switchValue);
            }
        });

    }
}
