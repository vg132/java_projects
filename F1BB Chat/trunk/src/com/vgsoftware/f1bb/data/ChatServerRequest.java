package com.vgsoftware.f1bb.data;

import java.io.Serializable;

/**
 * This class holds the data sent from the client to the server.
 */
public class ChatServerRequest implements Serializable
{
	private int command=-1;
	private String value=null;

	/**
	 * The commands that can be sent to the server.
	 */
	public static final int CHAT=0;
	public static final int LOGIN=1;
	public static final int BAN_SESSION=2;
	public static final int BAN_CHAT=3;

	/**
	 * Creates a empty <code>ClientData</code> object
	 */
	public ChatServerRequest()
	{
	}

	/**
	 * Creates a <code>ClientData</code> object with all values set.
	 * 
	 * @param userName the username of the sender.
	 * @param command the command for this message.
	 * @param value the value for this message.
	 */
	public ChatServerRequest(int command, String value)
	{
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
	 * Sets the value for this message.
	 * 
	 * @param value the value for this message.
	 */
	public void setValue(String value)
	{
		this.value=value;
	}
}
