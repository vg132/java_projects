/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Start class for the server.<br/>
 * Creates a server socket, listen on port 2000 if no other port was provided via
 * the command line, <code>server [PORT]</code>.<br/>
 * The server then listen for new connections, when one is recived it is sent to
 * the {@link ServerMessageCenter} where its added and taken care of.
 */
public class Server extends Thread
{
	private ServerSocket serverSocket=null;
	private ServerMessageCenter mc=null;

	public static void main(String args[])
	{
		new Server(args);
	}
	
	public Server(String args[])
	{
		try
		{
			if(args.length==1)
			{
				serverSocket=new ServerSocket(Integer.parseInt(args[0]));
				mc=new ServerMessageCenter();
			}
			else
			{
				serverSocket=new ServerSocket(2000);
				mc=new ServerMessageCenter();
			}
			start();
		}
		catch(IOException io)
		{
			
		}
		catch(NumberFormatException nfe)
		{
			System.out.println("VG Software JRC Server. Default port: 2000");
			System.out.println("Usage: java -jar Server.jar [port]");
		}
	}
	
	/**
	 * Listen for new connections, when one is made sent it to the {@link ServerMessageCenter#addUser(Socket socket)} function.
	 */
	public void run()
	{
		try
		{
			Socket s=null;
			while(true)
			{
				s=serverSocket.accept();
				mc.addUser(s);
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
}
