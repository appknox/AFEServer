package com.afe.socket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //Assign this variable to toggle switch
        mToggleButton = (ToggleButton) findViewById(R.id.ServerToggle);
        
        //Assign state of the toggle button
        mToggleButton.setChecked(Server.running);
        
        //Handle server on/off when toggle is clicked
        mToggleButton.setOnClickListener(new OnClickListener()
        {
			public void onClick(View arg1)
			{
				if(mToggleButton.isChecked())
					startServerService();
				else
					stopServerService();
			}
		});


    }


	//Stop service containing server
    private void stopServerService()
    {    	
    	stopService(new Intent(MainActivity.this, Server.class));	
    }
    
    //Start service containing server
    private void startServerService()
    {
    	try
    	{
            //Formulate intent and start server service
            Intent intent = new Intent(MainActivity.this, Server.class);
            //intent.putExtra("port", port);
            getApplicationContext().startService(intent);
    	}
    	catch (Exception e)
    	{
    		//Log error and turn toggle off
    		
    		mToggleButton.setChecked(false);
    	}
    	
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
