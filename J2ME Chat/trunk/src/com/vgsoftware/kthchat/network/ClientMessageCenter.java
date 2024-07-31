/*
 * Created on 2004-dec-05 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.kthchat.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

import com.vgsoftware.kthchat.StringTokenizer;
import com.vgsoftware.kthchat.observer.Subject;

/**
 * ClientMessageCenter handles all network communication for this
 * application.
 * 
 * @author Viktor
 * @version 1.0
 */
public class ClientMessageCenter extends Subject implements Runnable
{
	private SocketConnection sc=null;
	private DataOutputStream dos=null;
	private DataInputStream dis=null;
	private String nick=null;
	private String server=null;
	private int port=0;
	private Listen l=null;
	private boolean connStatus=false;
	private Thread t=null;

	//-----------------------
	public static final int CHAT=5;
	//-----------------------

	/**
	 * Makes a socket connection to the specified server and port
	 * and creats a listener thread and loggs on to the server.
	 */
	public void run()
	{
		try
		{
			sc=(SocketConnection)Connector.open("socket://"+server+":"+port);
			dos=sc.openDataOutputStream();
			dis=sc.openDataInputStream();

			l=new Listen();
			l.start();
			sendMessage(nick+"|CONNECT|null");
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}
	
	/**
	 * Helper function to send a chat message to the server.
	 * 
	 * @param data the message
	 */
	public void chat(String data)
	{
		sendMessage(nick+"|CHAT|J2ME|"+data);
	}
	
	/**
	 * Class constructor specifying the server name, port and the nickname to use.
	 * 
	 * @param server the server name
	 * @param port the server port
	 * @param nick the nickname to use in this session
	 */
	public ClientMessageCenter(String server, int port, String nick)
	{
		this.server=server;
		this.port=port;
		this.nick=nick;
		t=new Thread(this);
	}

	/**
	 * Starts a new connection thread.
	 */
	public void connect()
	{
		t.start();
	}
	
	/**
	 * Returns the current status of the connection.
	 * 
	 * @return true if client is connected, otherwise false
	 */
	public boolean isConnected()
	{
		return(connStatus);
	}
	
	/**
	 * Decodes a message recived from the server.
	 * 
	 * @param str the message to decode
	 */
	public synchronized void decodeMessage(String str)
	{
		StringTokenizer st=new StringTokenizer(str,'|');
		String channel=st.nextToken().trim();
		String command=st.nextToken().trim();

		if(command.equals("CONNECTED"))
		{
			if(channel.equals("SERVER"))
				sendMessage(nick+"|JOIN_CHANNEL|J2ME");
			else if(channel.equals("J2ME"))
				connStatus=true;
		}
		else if(command.equals("DISCONNECTED"))
		{
			connStatus=false;
		}
		else if(command.equals("SYSTEM"))
		{
			notify(ClientMessageCenter.CHAT,st.nextToken());
		}
		else if(command.equals("CHAT"))
		{
			String user=st.nextToken();
			String message=st.nextToken();
			notify(ClientMessageCenter.CHAT,user+">"+message);
		}
	}
	
	/**
	 * Sends a command to the server. 
	 * 
	 * @param line the command that will be sent to the server
	 */
	public void sendMessage(String line)
	{
		try
		{
			line=line.trim()+"\n";
			dos.write(line.getBytes());
			dos.flush();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}

	/**
	 * Listener thread that listen for incoming messages.
	 * 
	 * @author Viktor
	 * @version 1.0
	 */
	class Listen extends Thread
	{
	  /**
	   * Listen for incoming messages and delegate them to ClientMessageCenter
	   * when a full message has been recied.
	   */
		public void run()
		{
			try
			{
				String line="";
				int ch;
				while((ch=dis.read())!=-1)
				{
					if(ch=='\n')
					{
						decodeMessage(line);
						line="";
					}
					else
					{
						line+=(char)ch;
					}
	      }
			}
			catch(IOException io)
			{
				io.printStackTrace();
			}
		}
	}
}
