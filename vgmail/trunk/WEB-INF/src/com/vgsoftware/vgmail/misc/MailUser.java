/*
 * Created on: 2003-okt-24
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-24 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * This class holds information about the user and also provied fast and easy
 * access to configurations that are needed by the software during the visit. 
 */
public class MailUser
{
	private Map config=null;
	private String username=null;
	private String password=null;
	
	/**
	 * Creates a new user object, sets the configuration, username and password.
	 * 
	 * @param config The current server configuration.
	 * @param username The users username.
	 * @param password The users password.
	 * 
	 */
	public MailUser(Map config, String username, String password)
	{
		this.username=username;
		this.password=password;
		this.config=config;
	}

	/**
	 * Gets this users username.
	 * 
	 * @return The username.
	 */
	public String getUsername()
	{
		return(username);
	}
	
	/**
	 * Gets MTA configuration settings.
	 * 
	 * @param name The name of the setting.
	 * 
	 * @return The setting, or null if now setting with that name was found.
	 */
	public String getMTA(String name)
	{
		return((String)((Map)config.get("mta")).get(name));
	}
	
	/**
	 * Gets Post Office configuration settings.
	 * 
	 * @param name The name of the setting.
	 * 
	 * @return The setting, or null if now setting with that name was found.
	 */
	public String getPostOffice(String name)
	{
		return((String)((Map)config.get("postoffice")).get(name));
	}
	
	/**
	 * Gets User configuration settings.
	 * 
	 * @param name The name of the setting.
	 * 
	 * @return The setting, or null if now setting with that name was found.
	 */
	public String getUserConfiguration(String name)
	{
		return((String)((Map)config.get("user-configuration")).get(name));
	}
	
	/**
	 * Gets Template configuration settings.
	 * 
	 * @param name The name of the setting.
	 * 
	 * @return The setting, or null if now setting with that name was found.
	 */
	public String getTemplate(String name)
	{
		return((String)((Map)config.get("template")).get(name));
	}
	
	/**
	 * Gets Attachment configuration settings.
	 * 
	 * @param name The name of the setting.
	 * 
	 * @return The setting, or null if now setting with that name was found.
	 */
	public String getAttachment(String name)
	{
		return((String)((Map)config.get("attachment")).get(name));
	}
	
	/**
	 * Opens and returns a connection to the host.
	 * 
	 * @return The open connection.
	 * 
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
	public Store openConnection()
	throws NoSuchProviderException, MessagingException 
	{
		Properties props=new Properties();
		Session session=Session.getDefaultInstance(props,null);
		Store store=session.getStore(getPostOffice("protocol"));
		store.connect(getPostOffice("host"),Integer.parseInt(getPostOffice("port")),username,password);
		return(store);
	}
	
	/**
	 * Closes a connection to the host server.
	 * 
	 * @param store The store (connection) to close.
	 * 
	 * @throws MessagingException
	 */
	public void closeConnection(Store store)
	throws MessagingException
	{
		if((store!=null)&&(store.isConnected()))
			store.close();
	}
}
