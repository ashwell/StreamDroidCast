package com.CS454.StreamDroidCast;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * Main Activity
 * Turns on Wifi
 * Once network connected enable scan button
 *
 */

public class StreamDroidCastActivity extends Activity implements OnClickListener{
    
	Button scan;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WifiManager wifi = (WifiManager) this.getSystemService(StreamDroidCastActivity.WIFI_SERVICE);
        if( !wifi.isWifiEnabled() )
        	wifi.setWifiEnabled(true);
        
        scan = (Button)this.findViewById(R.id.startScan);
        
        
        // Once connected
        scan.setOnClickListener(this);
        scan.setClickable(true);
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
    
}