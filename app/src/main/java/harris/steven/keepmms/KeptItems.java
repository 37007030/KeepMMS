package harris.steven.keepmms;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class KeptItems extends AppCompatActivity {

    private LinkChecker linkChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kept_items);

        //Get reference to the singleton instance of the database helper
        final KeepMMSDatabaseHelper keepMMSDatabaseHelper = KeepMMSDatabaseHelper.getInstance(this.getApplicationContext());
        final SQLiteDatabase db = keepMMSDatabaseHelper.getWritableDatabase();

        linkChecker = linkChecker.getLinkCheckerInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int showSentLinksSetting = keepMMSDatabaseHelper.querySettingSentLinks(db);
        Log.e("harris", String.valueOf(showSentLinksSetting));

        //Get and display all saved links
        final List<Link> links = LinkChecker.getLinks();
        final List<Link> sentLinks = new ArrayList<>();
        final List<Link> receivedLinks = new ArrayList<>();

        //Separate links based on whether they were sent or received
        for(Link link : links) {
            if(link.getFrom_ID().equals("0"))
                sentLinks.add(link);
            else
                receivedLinks.add(link);
        }

        //Used in next section
        ListView listView = findViewById(R.id.linkList);
        TextView textViewSentLinks = findViewById(R.id.labelLinksIsent);

        //Only show the sent links section if the user has this setting enabled
        if(showSentLinksSetting == 0) {
            //User doesn't want sent links displayed, so hide those views
            listView.setVisibility(View.GONE);
            textViewSentLinks.setVisibility(View.GONE);
        }

        //User wants sent links to display
        else {

            //Adapter for sent links, and onClick event to open a sent link in the browser
            ArrayAdapter<Link> sentLinkAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    sentLinks);


            listView.setAdapter(sentLinkAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView, View view, int pos, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listView.getItemAtPosition(pos).toString()));
                    startActivity(browserIntent);
                }
            });
        }

        //Show links received by user, enable onClick to open link in browser
        ArrayAdapter<Link> receivedLinkAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                receivedLinks
        );

        ListView receivedLinkListView = findViewById(R.id.listLinksSentToMe);
        receivedLinkListView.setAdapter(receivedLinkAdapter);

        receivedLinkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View view, int pos, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listView.getItemAtPosition(pos).toString()));
                startActivity(browserIntent);
            }
        });
    }
}
