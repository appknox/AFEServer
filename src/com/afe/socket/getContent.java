package com.afe.socket;

import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class getContent extends Activity{
	
	String mydata = "";
	Cursor c;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); // click through-able
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("cont");
        String vuri = null;
        String[] proj = new String[50];
        String[] tokens = str.split("--");
        //Log.e("Token[0]", tokens[0]);
        Log.e("Token[1]", tokens[1]);
        Log.e("Length: ", ""+tokens.length);
        for (int i=1; i<tokens.length; i++)
        {
        	if(tokens[i].split("\\s+")[0].equalsIgnoreCase("url")) {
        		vuri = tokens[i].split("\\s+")[1];
        		Log.e("tot: ", ""+vuri);
        	}
        	else if(tokens[i].split("\\s+")[0].equalsIgnoreCase("proj")) {
        		for(int j=1;j<tokens[i].split("\\s+").length;j++) {
        			proj[j-1] = tokens[i].split("\\s+")[j];
        			//Log.e("projections", ""+tokens[i].split("\\s+")[j]);
        		}
        	}
        }
        Log.e("projlength :", ""+proj[0]);
        
        Uri vulnuri = Uri.parse(vuri);
        try{
        	
    	
    	if (proj[0] == null) {
    		c = managedQuery(vulnuri, null, null, null, null);
    		Log.e("here :", ""+vuri);
        } else
        {
        	c = managedQuery(vulnuri, proj, null, null, null);
        	Log.e("there :", ""+vuri);
        }
		
	        if (c != null) {
	           
	        	Log.e("Column-count","lol: "+c.getColumnCount());
	            c.moveToFirst(); // it's very important to do this action otherwise your Cursor object did not get work
	            do {
	            for (int i=1; i<c.getColumnCount(); i++){
	            
	            	 mydata = mydata + c.getColumnName(i)+ " : "+c.getString(i) + " ** ";
	            }
	            mydata = mydata + "\n";
	            } while(c.moveToNext());
	        }
	        try {
				PrintWriter output = new PrintWriter(TCPServer.client.getOutputStream(), true);
				output.print(mydata);
				output.flush();
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
