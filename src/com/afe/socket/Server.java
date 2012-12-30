package com.afe.socket;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Server extends Service {

	public static boolean running;
	private Thread thread = null;
    private TCPServer runnable = null;
    Context c;
	//private TCPServer server;
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		c = getApplicationContext();
		Toast.makeText(this,"Server started at port "+ TCPServer.SERVERPORT, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (thread != null) {
            runnable.terminate();
            try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Log.d("AFE","Thread successfully stopped.");
        }
		
		Toast.makeText(this, "Server stopped", Toast.LENGTH_LONG).show();
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		runnable = new TCPServer(c);
        thread = new Thread(runnable);
        Log.d("AFE","Starting thread: " + thread);
        thread.start();
        Log.d("AFE","Background process successfully started.");
		//Thread cThread = new Thread(new TCPServer()); 
		//cThread.start();
	    running = true;
	    //If the service dies, restart and re-issue intent to it
	    return START_REDELIVER_INTENT;
	}

	
	

	
}
