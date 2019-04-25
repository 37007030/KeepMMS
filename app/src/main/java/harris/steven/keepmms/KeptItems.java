package harris.steven.keepmms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;


public class KeptItems extends AppCompatActivity {

    private LinkChecker linkChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kept_items);

        linkChecker = linkChecker.getLinkCheckerInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final List<Link> links = LinkChecker.getLinks();

        ArrayAdapter<Link> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                links);

        ListView listView = findViewById(R.id.linkList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View view, int pos, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listView.getItemAtPosition(pos).toString()));
                startActivity(browserIntent);
            }
        });
    }
}
