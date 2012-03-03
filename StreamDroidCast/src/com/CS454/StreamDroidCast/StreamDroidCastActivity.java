package com.CS454.StreamDroidCast;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * Main Activity
 * Turns on Wifi
 * Once network connected enable scan button
 *
 */

public class StreamDroidCastActivity extends Activity implements OnClickListener{
    
	private Button start;
	private EditText ipAddressET;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WifiManager wifi = (WifiManager) this.getSystemService(StreamDroidCastActivity.WIFI_SERVICE);
        if( !wifi.isWifiEnabled() )
        	wifi.setWifiEnabled(true);
        
        start = (Button)this.findViewById(R.id.start);
        ipAddressET = (EditText)this.findViewById(R.id.ipAddr);
        
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) { 
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i=0; i<splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
            return null;
            }
        };
        ipAddressET.setFilters(filters);
        
        // Once connected
        start.setOnClickListener(this);
        start.setClickable(true);
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
    
}