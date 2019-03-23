package harris.steven.keepmms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void onClickMessages(View view){
        Intent intent = new Intent(MainActivity.this, MessageFeed.class);
        startActivity(intent);
    }

    public void onClickKeptItems(View view){
        Intent intent = new Intent(MainActivity.this, KeptItems.class);
        startActivity(intent);
    }

    public void onClickSettings(View view){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


}
