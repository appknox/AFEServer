package com.afe.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class TCPServer implements Runnable{
	public volatile static boolean running = true;
    public static final String SERVERIP = "127.0.0.1";
    public static final int SERVERPORT = 12346;
    public static String pubString ="";
    public static Socket client;
    Context context;
    public TCPServer(Context c) {
		// TODO Auto-generated constructor stub
    	context = c;
	}
	public void terminate() {
        running = false;
        stopServer();
    }
    public void run() {
         try {
              Log.d("TCP", "S: Connecting...");
              
              ServerSocket serverSocket = new ServerSocket(SERVERPORT);
              while (true) {
            	  client = serverSocket.accept();
            	  //socList.client();
            	  Log.d("TCP", "S: Receiving...");
            	  try {
                      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()), 8192);
                      String str = new String();
                      while (!in.ready());
                      str = in.readLine();
                      Log.e("TCP", "S: Received: '" + str + "'");
                      if(str != null || str.length() > 0 ) {
                      
                    	  if(str.equalsIgnoreCase("exported")) {
                    		  Log.e("Inside", "exported");
                        	  Intent abc = new Intent(context,ExportedContent.class);
                		      abc.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                		      abc.putExtra("cont", str);
                		      context.startActivity(abc);
                          }      
                          else if(str.startsWith("get")) {
                    	      Log.e("Inside", "getcontent");
                    	      Intent ijk = new Intent(context,getContent.class);
                    	      ijk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    	      ijk.putExtra("cont", str);
                    	      context.startActivity(ijk);
                          }
                    	  
                          else if(str.startsWith("ping")) {
                    	      Log.e("Inside", "ping");
                    	      try {
                  				PrintWriter output = new PrintWriter(TCPServer.client.getOutputStream(), true);
                  				output.print("pong");
                  				Log.e("sent", "pong");
                  				output.flush();
                  			   } catch (IOException e1) {
                  				// TODO Auto-generated catch block
                  				e1.printStackTrace();
                  			   }
                          }
                    	  
                          else {
                        	  PrintWriter output1 = new PrintWriter(TCPServer.client.getOutputStream(), true);
                        	  output1.print("Error in command!");
                        	  output1.flush();
                          }
                      }
                    } catch(Exception e) {
                        Log.e("TCP", "S: Error", e);
                    }

              }
              
         } catch (Exception e) {
        	 Log.e("TCP", "S: Error", e);
         }
    }

	public void stopServer() {
		// TODO Auto-generated method stub
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
