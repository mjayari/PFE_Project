package com.fst.myapplication.http;

import java.io.File;
import java.io.IOException;


public class MyHttpServer extends NanoHTTPD {

	public boolean status = false;
		
	/**
	 * Starts a HTTP server to given port.<p>
	 * Throws an IOException if the socket is already in use
	 */
	public MyHttpServer( int port, File wwwroot ) throws IOException
	{
		super(port, wwwroot);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int port = 12345;
		File wwwroot = new File("C:\\+Backup\\Journal").getAbsoluteFile();
		
		try {
			MyHttpServer myServer = new MyHttpServer( port, wwwroot );
			myServer.status = true;
			System.out.println( "Now serving files in port " + port + " from \"" + wwwroot + "\"" );
			
		} catch( Exception ioe ) {
			System.err.println( "Couldn't start server:\n" + ioe );
			System.exit( -1 );
		}				
		//MyHttpServer.startServer(args);
			
	}
	
		
	public void stopServer() {
		try {
			super.stop();
			this.status = false;
			System.out.println( "Server Stopped!" );
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
			
	
}
