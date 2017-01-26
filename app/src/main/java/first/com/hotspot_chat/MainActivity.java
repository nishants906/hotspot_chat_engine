package first.com.hotspot_chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    ToggleButton toggle;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggle= (ToggleButton) findViewById(R.id.toggleButton);
        list= (ListView) findViewById(R.id.listView);


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotspotManager.isApOn(getApplicationContext()); // check Ap state :boolean
                hotspotManager.configApState(getApplicationContext()); // change Ap state :boolean
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



    }
}

