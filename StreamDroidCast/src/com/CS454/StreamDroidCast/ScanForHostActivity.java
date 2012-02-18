package com.CS454.StreamDroidCast;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Ashwell
 * This class will scan the network for the Speaker Host
 * Once the host is found it will make the startButton clickable.
 * Start onClick will launch the streaming activity
 *
 */

public class ScanForHostActivity extends Activity implements OnClickListener{

	Button start;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning);
        
        start = (Button)this.findViewById(R.id.startStream);
        
        WifiManager wifiMangr = (WifiManager) this.getSystemService(StreamDroidCastActivity.WIFI_SERVICE);
        
        
        
        // Once Host Found
        start.setOnClickListener(this);
        start.setClickable(true);
        
        
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
