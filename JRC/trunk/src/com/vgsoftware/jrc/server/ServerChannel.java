/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the server channel, here all messages for a specific channel is
 * sent out to all connected users.<br/>
 */
public class ServerChannel
{
	private String title=null;
	private String name=null;
	private List users=new LinkedList();

	/**
	 * Constructs a new channel with a name and title.
	 * @param name the name of this channel.
	 * @param title the title of this channel.
	 */
	public ServerChannel(String name, String title)
	{
		this.name=name;
		this.title=title;
	}

	/**
	 * Adds the specified user to this channel.
	 * 
	 * @param user the ServerUser object to be added to this channel.
	 * @return true if the user was added, false otherwise.
	 */
	public boolean addUser(ServerUser user)
	{
		return(users.add(user));
	}

	/**
	 * Removes the specified user from this channel.
	 * 
	 * @param user the user to be removed from this channel.
	 * @return true if the user is connected to this channel.
	 */
	public boolean removeUser(ServerUser user)
	{
		if(users.contains(user))
		{
			return(users.remove(user));
		}
		else
		{
			return(false);
		}
	}
	
	/**
	 * Sends a message to all connected clients.
	 * 
	 * @param message the message.
	 */
	public void send(String message)
	{
		Iterator iter=users.iterator();
		while(iter.hasNext())
			((ServerUser)iter.next()).send(message);
	}
	
	/**
	 * Gets the name of this channel.
	 * 
	 * @return the name of this channel.
	 */
	public String getName()
	{
		return(name);
	}
	
	/**
	 * Gets the title of the channel.
	 * 
	 * @return the title of this channel, null if no title is set.
	 */
	public String getTitle()
	{
		return(title);
	}
	
	/**
	 * Gets a list of all users connected to this channel
	 * 
	 * @return A list with all connected users, seperated by |.
	 */
	public String getUserList()
	{
		String userList="";
		for(int i=0;i<users.size();i++)
			userList+="|"+((ServerUser)users.get(i)).getNick();
		return(userList);
	}
}
