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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class
MainActivity extends AppCompatActivity implements View.OnClickListener {

    ToggleButton togglehotspot;
    Button togglewifi;
    WifiManager wifiManager;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    List<String> listOfProvider;
    ListAdapter adapter;
    ListView listViwProvider;

    public static final String IP_SERVER = "192.168.49.1";
    public static int PORT = 8988;
    private static boolean server_running = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        togglehotspot = (ToggleButton) findViewById(R.id.toggleButton);

        togglewifi = (Button) findViewById(R.id.togglewifi);


        togglehotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotspotManager.isApOn(getApplicationContext()); // check Ap state :boolean
                hotspotManager.configApState(getApplicationContext()); // change Ap state :boolean
            }
        });


        listOfProvider = new ArrayList<String>();

		/*setting the resources in class*/
        listViwProvider = (ListView) findViewById(R.id.listView);

        togglewifi.setOnClickListener(this);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		/*checking wifi connection
		 * if wifi is on searching available wifi provider*/
        if (wifiManager.isWifiEnabled() == true) {
            togglewifi.setText("ON");
            scaning();
        }
        listViwProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemValue = (String) listViwProvider.getItemAtPosition(position);





            }
        });
    }

    private void scaning() {
        // wifi scaned value broadcast receiver
        receiverWifi = new WifiReceiver();
        // Register broadcast receiver
        // Broacast receiver will automatically call when number of wifi
        // connections changed
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();

    }
    @Override
    public void onClick(View arg0) {
		/* if wifi is ON set it OFF and set button text "OFF" */
        if (wifiManager.isWifiEnabled() == true) {
            wifiManager.setWifiEnabled(false);
            togglewifi.setText("OFF");
            listViwProvider.setVisibility(ListView.GONE);
        }
		/* if wifi is OFF set it ON
		 * set button text "ON"
		 * and scan available wifi provider*/
        else if (wifiManager.isWifiEnabled() == false) {
            wifiManager.setWifiEnabled(true);
            togglewifi.setText("ON");
            listViwProvider.setVisibility(ListView.VISIBLE);
            scaning();
        }
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverWifi);
    }

    protected void onResume() {
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class WifiReceiver extends BroadcastReceiver {

        // This method call when number of wifi connections changed
        public void onReceive(Context c, Intent intent) {
            wifiList = wifiManager.getScanResults();

			/* sorting of wifi provider based on level */
            Collections.sort(wifiList, new Comparator<ScanResult>() {
                @Override
                public int compare(ScanResult lhs, ScanResult rhs) {
                    return (lhs.level > rhs.level ? -1
                            : (lhs.level == rhs.level ? 0 : 1));
                }
            });
            listOfProvider.clear();
            String providerName;
            for (int i = 0; i < wifiList.size(); i++) {
				/* to get SSID and BSSID of wifi provider*/
                providerName = (wifiList.get(i).SSID).toString()
                        + "\n" + (wifiList.get(i).BSSID).toString();
                listOfProvider.add(providerName);
            }
			/*setting list of all wifi provider in a List*/
            adapter = new ListAdapter(MainActivity.this, listOfProvider);
            listViwProvider.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }


    }
}
