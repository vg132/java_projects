/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The connection class represents a connection to a server.
 * It opens the connection, closes the connection and listen for incoming messages.
 */
public class Connection
{
	private ClientMessageCenter cmc=null;
	private Listen l=null;
	private String password=null;
	private int port=-1;
	private PrintWriter pw=null;
	private String server=null;
	private Socket socket=null;
	private String nick=null;

	/**
	 * Constructs a connection object with the specified values.
	 * 
	 * @param server the address to the server.
	 * @param port the port on which to make the connection.
	 * @param nick the nick name to use for this connection.
	 * @param password the password to use for this connection.
	 */
	public Connection(String server, int port, String nick, String password)
	{
		this.server=server;
		this.port=port;
		this.nick=nick;
		this.password=password;
	}

	/**
	 * Close this connection.
	 * 
	 * @throws IOException
	 */
	public void closeConnection()
	throws IOException
	{
		socket.close();
	}
	
	/**
	 * Open a connection to a server based on the information in this object.
	 * When the connection is created creat a printwriter for the connection and start
	 * listen for incoming messages from the server.
	 * 
	 * @param cmc the {@link ClientMessageCenter} used to handle incoming messages.
	 * @return <code>true</code> if the connection was created, <code>false</code> otherwise.
	 * @throws IOException if the creation of a connection failed a IOException will be thrown.
	 */
	public boolean connect(ClientMessageCenter cmc)
	throws IOException
	{
		socket=new Socket(server,port);
		pw=new PrintWriter(socket.getOutputStream());
		this.cmc=cmc;
		l=new Listen();
		l.start();
		return(true);
	}
	
	/**
	 * Gets the port of this connection.
	 * @return the port.
	 */
	public int getPort()
	{
		return(port);
	}
	
	/**
	 * Checks if this connection is open.
	 * @return <code>true</code> if open, <code>false</code> otherwise.
	 */
	public boolean open()
	{
		if(socket==null)
			return(false);
		return(!socket.isClosed());
	}
	
	/**
	 * Gets the server address for this connection.
	 * @return the server address.
	 */
	public String getServer()
	{
		return(server);
	}
	
	/**
	 * Gets the nick name of the user for this connection.
	 * @return the nick name.
	 */
	public String getNick()
	{
		return(nick);
	}

	/**
	 * Gets the password assosiated with this connection.
	 * @return the password.
	 */
	public String getPassword()
	{
		return(password);
	}

	/**
	 * Sends a message string to the server. First it removes all line breakes.
	 * @param message the message string
	 */
	public void send(String message)
	{
		pw.println(message.replace('\n',' ').trim());
		pw.flush();
	}

	class Listen extends Thread
	{
		/**
		 * Listen for incoming messages from the server. When a message is recived
		 * send it for decoding to the {@link ClientMessageCenter}.
		 */
		public void run()
		{
			try
			{
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line=null;
				while((line=br.readLine())!=null)
				{
					cmc.decodeMessage(line);
				}
			}
			catch(IOException io)
			{
				System.err.println("Connection Closed.");
			}
		}
	}
}
