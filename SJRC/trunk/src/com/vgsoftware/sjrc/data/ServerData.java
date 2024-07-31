package com.vgsoftware.sjrc.data;

import java.io.Serializable;

/**
 * SJRC
 * ServerData.java
 * 
 * This is the main object sent from the server to the client
 * 
 * @author Viktor
 */
public class ServerData implements Serializable
{
	private String channel=null;
	private int command=-1;
	private String value=null;
	
	public static final int LIST_CHANNELS=1;
	public static final int LIST_USERS=2;
	public static final int WHOIS=3;
	public static final int SYSTEM=4;
	public static final int CHAT=5;
	public static final int CONNECTED=6;
	public static final int DISCONNECTED=7;
	public static final int USER_LIST=8;
	public static final int ERROR=9;
	public static final int KEY_OK=10;
	
	public ServerData()
	{
	}
	
	public ServerData(String channel, int command, String value)
	{
		this.channel=channel;
		this.command=command;
		this.value=value;
	}

	public int getCommand()
	{
		return(command);
	}

	public String getChannel()
	{
		return(channel);
	}

	public String getValue()
	{
		return(value);
	}

	public void setCommand(int command)
	{
		this.command=command;
	}

	public void setChannel(String channel)
	{
		this.channel=channel;
	}

	public void setValue(String value)
	{
		this.value=value;
	}
	
	public String toString()
	{
		return("UserName: "+channel+", Command: "+command+", Value: "+value);
	}
}
