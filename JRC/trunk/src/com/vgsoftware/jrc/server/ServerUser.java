/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Server representation of a connected user.<br/>
 * The ServerUser class handles all communication between the server
 * and the user.
 */
public class ServerUser extends Thread
{
	private static long idCounter;

	private Socket socket=null;
	private String nick=null;
	private PrintWriter pw=null;
	private ServerMessageCenter mc=null;
	private Map channels=new HashMap();

	/**
	 * Constructs a new user with a name and socket.
	 * @param mc the message center that handels the messages for this user.
	 * @param socket the socket used for communication with this user.
	 * @throws IOException if an I/O error occurs when creating the output stream.
	 */
	public ServerUser(ServerMessageCenter mc, Socket socket)
	throws IOException
	{
		this.mc=mc;
		this.socket=socket;
		pw=new PrintWriter(socket.getOutputStream());		
	}

	/**
	 * Constructs a new user with a name and socket.
	 * @param mc the message center that handels the messages for this user.
	 * @param socket the socket used for communication with this user.
	 * @param nick the nick name of this user.
	 * @throws IOException if an I/O error occurs when creating the output stream.
	 */
	public ServerUser(ServerMessageCenter mc, Socket socket, String nick)
	throws IOException
	{
		this.mc=mc;
		this.socket=socket;
		this.nick=nick;
		pw=new PrintWriter(socket.getOutputStream());
	}

	/**
	 * Gets the nick name of this user.
	 * @return the nick name of this user.
	 */
	public String getNick()
	{
		return(nick);
	}

	/**
	 * Sets the nick name of this user.
	 * @param nick the nick name of this user.
	 */
	public void setNick(String nick)
	{
		this.nick=nick;
	}
	
	/**
	 * Gets the socket used for comunication with this user.
	 * @return the socket used for comunication with this user.
	 */
	public Socket getSocket()
	{
		return(socket);
	}
	
	/**
	 * Sets the socket used for communication with this user.
	 * @param socket the socket to use for communication with this user.
	 */
	public void setSocket(Socket socket)
	{
		this.socket=socket;
	}

	/**
	 * Sends a message to this user.
	 * @param message the message that will be sent.
	 */
	public void send(String message)
	{
		pw.println(message.replace('\n',' ').trim());
		pw.flush();
	}

	/**
	 * Close the connection to this user.
	 */
	public void closeConnection()
	{
		try
		{
			socket.close();
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * Overloaded toString method, will return a string in the format<br/>
	 * <code>nick@hostname</code>
	 */
	public String toString()
	{
		return(nick+"@"+socket.getInetAddress().getHostName());
	}

	/**
	 * Adds a channel to the list of channels that this user is connected to.
	 * 
	 * @param channel channel to be added.
	 */
	public void addChannel(ServerChannel channel)
	{
		channels.put(channel.getName(),channel);
	}

	/**
	 * Removes the user from the specified channel and removes the channel from the list
	 * of channels that this user is connected to.
	 * 
	 * @param channel the channel to remove.
	 */
	public void removeChannel(String channel)
	{
		((ServerChannel)channels.get(channel)).removeUser(this);
		channels.remove(channel);
	}
	
	/**
	 * Gets all chnnels that this user is connected to.
	 *
	 * @return a <code>Map</code> with all channels, null if there are no channels,  
	 */
	public Map getChannels()
	{
		return(channels);
	}

	/**
	 * Listen for incoming messages from the user. When a message is recived it sends it to
	 * the message center for decoding.<br/>
	 * When the connection is lost it calls the {@link ServerMessageCenter#quit(Map channels, ServerUser user)} function
	 * to close all communication with this user.
	 *  
	 */
	public void run()
	{
		try
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line=null;
			while((line=br.readLine())!=null)
			{
				mc.decodeMessage(line,this);
			}
			pw.close();
			br.close();
			socket.close();
		}
		catch(IOException io)
		{
			mc.quit(channels,this);
		}
	}
}
