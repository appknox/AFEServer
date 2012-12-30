package com.afe.socket;

import java.io.IOException;
import java.io.PrintWriter;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PathPermission;
import android.content.pm.ProviderInfo;
import android.os.Bundle;

public class ExportedContent extends Activity {

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        GetData();
	        this.finish();
	        }
	            
	    public void GetData(){
	    	String out = "";	
	    	int c = 0;
	    	PackageManager pm= this.getPackageManager();
	    	for (PackageInfo pack : pm.getInstalledPackages(PackageManager.GET_PROVIDERS)) {
		        ProviderInfo[] providers = pack.providers;
		        if (providers != null) {
		            for (ProviderInfo provider : providers) {
		            	out += "\npackage: " + provider.packageName;
		                out += "\nauthority: " + provider.authority;
		                out += "\nexported: " + provider.exported;
		                out += "\nreadPerm: " + provider.readPermission;
		                out += "\nwritePerm: " + provider.writePermission;
		                if(provider.pathPermissions != null) {
		                	c = 0;
		                   	for(PathPermission perm :provider.pathPermissions) {
		                   		out += "\nPath Permission" + c + ": " + perm.getPath();
		                   		out += "\nRead Permission" + c + ": " + perm.getReadPermission();
		                   		out += "\nWrite Permission" + c + ": " + perm.getWritePermission();
		                   		c++;
		                   		
		                   	} 	
		                }
		                out += "\n---------------------------------------------\n";   
		            }
		        }
		    }
	    	try {
				PrintWriter output1 = new PrintWriter(TCPServer.client.getOutputStream(), true);
				output1.print(out);
				output1.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    }
	    
	    
}

	    