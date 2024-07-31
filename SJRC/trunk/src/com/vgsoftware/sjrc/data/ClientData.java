package com.vgsoftware.sjrc.data;

import java.io.Serializable;

/*
 * SJRC
 * Client.java
 * 
 * @author Viktor
 */

/**
 * This class holds the data sent from the client to the server.
 */
public class ClientData implements Serializable
{
	private String userName=null;
	private int command=-1;
	private String value=null;

	/**
	 * The commands that can be sent to the server.
	 */
	public static final int LIST_CHANNELS=1;
	public static final int LIST_USERS=2;
	public static final int PING=3;
	public static final int QUIT=4;
	public static final int CHAT=5;
	public static final int WHOIS=6;
	public static final int QUIT_CHANNEL=7;
	public static final int JOIN_CHANNEL=8;
	public static final int CONNECT=9;

	/**
	 * Creates a empty <code>ClientData</code> object
	 */
	public ClientData()
	{
	}

	/**
	 * Creates a <code>ClientData</code> object with all values set.
	 * 
	 * @param userName the username of the sender.
	 * @param command the command for this message.
	 * @param value the value for this message.
	 */
	public ClientData(String userName, int command, String value)
	{
		this.userName=userName;
		this.command=command;
		this.value=value;
	}

	/**
	 * Returns the command for this message.
	 * 
	 * @return the command.
	 */
	public int getCommand()
	{
		return(command);
	}

	/**
	 * Returns the username for this message.
	 * 
	 * @return the username.
	 */
	public String getUserName()
	{
		return(userName);
	}

	/**
	 * Returns the value for this message.
	 * 
	 * @return the value.
	 */
	public String getValue()
	{
		return(value);
	}

	/**
	 * Sets the command for this message.
	 * 
	 * @param command the command.
	 */
	public void setCommand(int command)
	{
		this.command=command;
	}

	/**
	 * Sets the username for this message.
	 * 
	 * @param userName the username.
	 */
	public void setUserName(String userName)
	{
		this.userName=userName;
	}

	/**
	 * Sets the value for this message.
	 * 
	 * @param value the value for this message.
	 */
	public void setValue(String value)
	{
		this.value=value;
	}
}
