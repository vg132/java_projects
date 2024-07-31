/*
 * Created on 2003-sep-09
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-09 Created by Viktor.
 * 2004-jun-02 Updated by Viktor, added stuff for secure JRC.
 */
package com.vgsoftware.sjrc.server;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.vgsoftware.sjrc.data.ClientData;
import com.vgsoftware.sjrc.data.ServerData;

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
	private KeyPair keyPair=null;

	/**
	 * Creates a new server message center with the public/private keys to use.
	 *
	 * @param keyPair The key to use for initial comunication with client.
	 */
	public ServerMessageCenter(KeyPair keyPair)
	{
		this.keyPair=keyPair;
	}

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
			ServerUser user=new ServerUser(this,socket,keyPair);
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
			channel.send(new ServerData(channel.getName(),ServerData.USER_LIST,channel.getUserList()));
			channel.send(new ServerData(channel.getName(),ServerData.SYSTEM,Color.RED.getRGB()+"|* "+user.getNick()+" has left "+channel.getName()));
			iter.remove();
		}
		if(user.getNick()!=null)
			users.remove(user.getNick().toUpperCase());
	}

	/**
	 * Decodes messages recived from clients and acts on the decoded informations.
	 *
	 * @param data the message that is recived from a client.
	 * @param user the object of the client that sent the message.
	 */
	public synchronized void decodeMessage(ClientData data, ServerUser user)
	{
		try
		{
			StringTokenizer st=null;
			if(data.getValue()!=null)
				st=new StringTokenizer(data.getValue(),"|");
			String nick=data.getUserName();
			int command=data.getCommand();

			if(command==ClientData.CONNECT)
			{
				if(users.containsKey(nick.toUpperCase()))
				{
					unconnectedUsers.remove(user);
					user.send(new ServerData("SYSTEM",ServerData.ERROR,"USERNAME_ALREADY_IN_USE"));
				}
				else
				{
					user.setNick(nick);
					unconnectedUsers.remove(user);
					users.put(nick.toUpperCase(),user);
					user.send(new ServerData("SERVER",ServerData.CONNECTED,null));
				}
			}
			else if(command==ClientData.JOIN_CHANNEL)
			{
				String name=st.nextToken();
				ServerChannel channel=(ServerChannel)channels.get(name.toUpperCase());
				if(channel!=null)
				{
					channel.send(new ServerData(channel.getName(),ServerData.SYSTEM,Color.BLUE.getRGB()+"|* "+nick+" has joind "+channel.getName()));
					channel.addUser((ServerUser)users.get(nick.toUpperCase()));
				}
				else
				{
					channel=new ServerChannel(name.toUpperCase(),name);
					channels.put(name.toUpperCase(),channel);
					channel.addUser((ServerUser)users.get(nick.toUpperCase()));
				}
				user.send(new ServerData(channel.getName(),ServerData.CONNECTED,null));
				user.addChannel(channel);
				channel.send(new ServerData(channel.getName(),ServerData.USER_LIST,channel.getUserList()));
			}
			else if(command==ClientData.QUIT_CHANNEL)
			{
				String name=st.nextToken();
				ServerChannel channel=(ServerChannel)channels.get(name.toUpperCase());
				if(channel!=null)
				{
					channel.removeUser(user);
					channel.send(new ServerData(channel.getName(),ServerData.SYSTEM,Color.RED.getRGB()+"|* "+user.getNick()+" has left "+channel.getName()));
					channel.send(new ServerData(channel.getName(),ServerData.USER_LIST,channel.getUserList()));
				}
			}
			else if(command==ClientData.WHOIS)
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
					user.send(new ServerData("SYSTEM",ServerData.WHOIS,info));
				}
				else
				{
					user.send(new ServerData("CHANNEL",ServerData.ERROR,"USER_NOT_FOUND"));
				}
			}
			else if(command==ClientData.CHAT)
			{
				String channel=st.nextToken();
				String color=st.nextToken();
				String m=st.nextToken();
				((ServerChannel)channels.get(channel.toUpperCase())).send(new ServerData(channel,ServerData.CHAT,nick+"|"+color+"|"+m));
			}
			else if(command==ClientData.QUIT)
			{
				user.closeConnection();
				users.remove(user);
			}
			else if(command==ClientData.LIST_USERS)
			{
				String info="";
				Iterator iter=users.values().iterator();
				while(iter.hasNext())
					info+="|"+((ServerUser)iter.next()).getNick();
				if(info.startsWith("|"))
					info=info.substring(1);
				user.send(new ServerData("SERVER",ServerData.LIST_USERS,info));
			}
			else if(command==ClientData.LIST_CHANNELS)
			{
				String info="";
				Iterator iter=channels.values().iterator();
				while(iter.hasNext())
					info+="|"+((ServerChannel)iter.next()).getName();
				if(info.startsWith("|"))
					info=info.substring(1);
				user.send(new ServerData("SERVER",ServerData.LIST_CHANNELS,info));
			}
		}
		catch(NoSuchElementException nsee)
		{
		}
	}
}
