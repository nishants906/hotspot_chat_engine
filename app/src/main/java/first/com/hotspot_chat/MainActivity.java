package first.com.hotspot_chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    ToggleButton togglehotspot,togglewifi;
    WifiManager wifi;
    ListView listview;
    ArrayAdapter adapter;

    StringBuilder sb = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        togglehotspot= (ToggleButton) findViewById(R.id.toggleButton);
        listview= (ListView) findViewById(R.id.listView);
        wifi= (WifiManager) getSystemService(WIFI_SERVICE);

        togglewifi= (ToggleButton) findViewById(R.id.togglewifi);


        togglehotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotspotManager.isApOn(getApplicationContext()); // check Ap state :boolean
                hotspotManager.configApState(getApplicationContext()); // change Ap state :boolean
            }
        });

        togglewifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && !wifi.isWifiEnabled()){
                    wifi.setWifiEnabled(true);
                }

                else if(!isChecked && wifi.isWifiEnabled()){
                    wifi.setWifiEnabled(false);
                }

            }
        });

        broadcast reciever=new broadcast();
        //register reciever
        registerReceiver(reciever,new IntentFilter(wifi.SCAN_RESULTS_AVAILABLE_ACTION));


        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    class broadcast extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

            StringBuffer stringBuffer=new StringBuffer();
            List<ScanResult> list= wifi.getScanResults();

            for(ScanResult scanResult:list){
                stringBuffer.append((scanResult));
            adapter.add(scanResult);
                adapter.notifyDataSetChanged();
            }


        }
    }



}

