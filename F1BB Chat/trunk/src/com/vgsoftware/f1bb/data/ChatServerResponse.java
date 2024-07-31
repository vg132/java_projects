package com.vgsoftware.f1bb.data;

import java.io.Serializable;

/**
 * SJRC
 * ServerData.java
 * 
 * This is the object sent from the server to the client
 * 
 * @author Viktor
 */
public class ChatServerResponse implements Serializable
{
	private int command=-1;
	private String value=null;
	
	public static final int CHAT=0;
	public static final int CONNECTED=1;
	public static final int LOGGED_IN=2;
	public static final int USER_LIST=3;
	public static final int ERROR=4;
	public static final int INFO=5;
	public static final int SERVER_CLOSED=6;
	
	public ChatServerResponse()
	{
	}
	
	public ChatServerResponse(int command, String value)
	{
		this.command=command;
		this.value=value;
	}

	public int getCommand()
	{
		return(command);
	}

	public String getValue()
	{
		return(value);
	}

	public void setCommand(int command)
	{
		this.command=command;
	}

	public void setValue(String value)
	{
		this.value=value;
	}
	
	public String toString()
	{
		return("Command: "+command+", Value: "+value);
	}
}
