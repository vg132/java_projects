/*
 * Created on 2003-sep-18
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-18 Created by Viktor.
 */
package com.vgsoftware.sjrc.client;
/**
 * The class represents a {@link AddressBook} entery.
 */
public class AddressData
{
	private String name=null;
	private String address=null;
	private int port=0;
	private String nick=null;
	private String password=null;

	public AddressData()
	{	
	}

	public AddressData(String name, String address, int port, String nick, String password)
	{
		this.name=name;
		this.address=address;
		this.port=port;
		this.nick=nick;
		this.password=password;
	}

	public String getName()
	{
		return(name);
	}

	public String getNick()
	{
		return(nick);
	}

	public String getPassword()
	{
		return(password);
	}

	public int getPort()
	{
		return(port);
	}

	public String getAddress()
	{
		return(address);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public void setNick(String nick)
	{
		this.nick=nick;
	}

	public void setPassword(String password)
	{
		this.password=password;
	}

	public void setPort(int port)
	{
		this.port=port;
	}

	public void setAddress(String address)
	{
		this.address=address;
	}

	public String toString()
	{
		return(name);
	}
}
