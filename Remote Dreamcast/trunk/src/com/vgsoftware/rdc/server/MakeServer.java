package com.vgsoftware.rdc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Quick and very, very, very dirty make server.
 * 
 * @author Viktor 2005
 */
public class MakeServer
{
	private List<MakeClient> clientProcesses=new ArrayList<MakeClient>();

	public static void main(String args[])
	{
		int port=3699;
		if(args.length!=0)
			port=Integer.parseInt(args[0]);
		new MakeServer(port);
	}

	public void remove(MakeClient clientProcess)
	{
		clientProcesses.remove(clientProcess);
	}
	
	public MakeServer(int port)
	{
		System.out.println("Starting server on port: "+port);
		try
		{
			Socket s=null;
			ServerSocket ss=new ServerSocket(port);
			while(true)
			{
				s=ss.accept();
				clientProcesses.add(new MakeClient(this,s));
			}
		}
		catch(IOException io)
		{
		}
	}
}
