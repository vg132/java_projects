/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.server;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * This is the main class in on the server side.<br/>
 * This class recives all messages sent to the server and it acts on them, relay them to
 * connected users, connect users, disconnect users, sends messages to users and more.
 * 
 */
public class ServerMessageCenter
{
	private Map channels=new HashMap();
	private Map users=new HashMap();
	private List unconnectedUsers=new LinkedList();
	
	/**
	 * Creates a new user and adds that user to the list of unconnected users.<br/>
	 * A connection between the client and the server is created but the client is still
	 * not connected to the server and will not recive any messages until he is connected.<br/>
	 * A user is connected first once he has sent a CONNECT message to the server.
	 * 
	 * @param socket
	 */
	public synchronized void addUser(Socket socket)
	{
		try
		{
			ServerUser user=new ServerUser(this,socket);
			unconnectedUsers.add(user);
			user.start();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	/**
	 * This function is called when a users connection is closed.<br/>
	 * It removes the user from all channels and informs the users of those channels that
	 * the user has left and send a new userlist. The user is then removed from the list of users.
	 * 
	 * @param channels a list of all channels this user is connected to.
	 * @param user the users server object.
	 */
	public synchronized void quit(Map channels, ServerUser user)
	{
		Iterator iter=channels.values().iterator();
		while(iter.hasNext())
		{
			ServerChannel channel=(ServerChannel)iter.next();
			channel.removeUser(user);
			channel.send(channel.getName()+"|USER_LIST"+channel.getUserList());
			channel.send(channel.getName()+"|SYSTEM|"+Color.RED.getRGB()+"|* "+user.getNick()+" has left "+channel.getName());
			iter.remove();
		}
		if(user.getNick()!=null)
			users.remove(user.getNick().toUpperCase());
	}

	/**
	 * Decodes messages recived from clients and acts on the decoded informations.
	 * 
	 * @param message the message that is recived from a client.
	 * @param user the object of the client that sent the message.
	 */
	public synchronized void decodeMessage(String message, ServerUser user)
	{
		try
		{
			StringTokenizer st=new StringTokenizer(message,"|");
			String nick=st.nextToken();
			String command=st.nextToken();
	
			if(command.equals("CONNECT"))
			{
				if(users.containsKey(nick.toUpperCase()))
				{
					unconnectedUsers.remove(user);
					user.send("SYSTEM|ERROR|USERNAME_ALREADY_IN_USE");
				}
				else
				{
					user.setNick(nick);
					unconnectedUsers.remove(user);
					users.put(nick.toUpperCase(),user);
					user.send("SERVER|CONNECTED");
				}
			}
			else if(command.equals("JOIN_CHANNEL"))
			{
				String name=st.nextToken();
				ServerChannel channel=(ServerChannel)channels.get(name.toUpperCase());
				if(channel!=null)
				{
					channel.send(channel.getName()+"|SYSTEM|"+Color.BLUE.getRGB()+"|* "+nick+" has joind "+channel.getName());
					channel.addUser((ServerUser)users.get(nick.toUpperCase()));
				}
				else
				{
					channel=new ServerChannel(name.toUpperCase(),name);
					channels.put(name.toUpperCase(),channel);
					channel.addUser((ServerUser)users.get(nick.toUpperCase()));
				}
				user.send(channel.getName()+"|CONNECTED");
				user.addChannel(channel);
				channel.send(channel.getName()+"|USER_LIST"+channel.getUserList());
			}
			else if(command.equals("QUIT_CHANNEL"))
			{
				String name=st.nextToken();
				ServerChannel channel=(ServerChannel)channels.get(name.toUpperCase());
				if(channel!=null)
				{
					channel.removeUser(user);
					channel.send(channel.getName()+"|SYSTEM|"+Color.RED.getRGB()+"|* "+user.getNick()+" has left "+channel.getName());
					channel.send(channel.getName()+"|USER_LIST"+channel.getUserList());
				}
			}
			else if(command.equals("WHOIS"))
			{
				ServerUser user2=(ServerUser)users.get(st.nextToken().toUpperCase());
				if(user2!=null)
				{
					String info="Whois info: "+user2.toString()+" - Channels: ";
					Iterator iter=user2.getChannels().values().iterator();
					while(iter.hasNext())
						info+=((ServerChannel)iter.next()).getName()+", ";
					if(info.endsWith(", "))
						info=info.substring(0,info.length()-2);
					user.send("SYSTEM|WHOIS|"+info);
				}
				else
				{
					user.send("CHANNEL|ERROR|USER_NOT_FOUND");
				}
			}
			else if(command.equals("CHAT"))
			{
				String channel=st.nextToken();
				String color=st.nextToken();
				String m=st.nextToken();
				((ServerChannel)channels.get(channel.toUpperCase())).send(channel+"|CHAT|"+nick+"|"+color+"|"+m);
			}
			else if(command.equals("QUIT"))
			{
				user.closeConnection();
				users.remove(user);
			}
			else if(command.equals("LIST_USERS"))
			{
				String info="SERVER|LIST_USERS";
				Iterator iter=users.values().iterator();
				while(iter.hasNext())
					info+="|"+((ServerUser)iter.next()).getNick();
				user.send(info);
			}
			else if(command.equals("LIST_CHANNELS"))
			{
				String info="SERVER|LIST_CHANNELS";
				Iterator iter=channels.values().iterator();
				while(iter.hasNext())
					info+="|"+((ServerChannel)iter.next()).getName();
				user.send(info);
			}
		}
		catch(NoSuchElementException nsee)
		{
		}
	}
}
