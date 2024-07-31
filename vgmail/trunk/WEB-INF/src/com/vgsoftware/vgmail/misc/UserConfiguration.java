/*
 * Created on: 2003-nov-05
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-05 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class UserConfiguration
{
	private Map settings=new HashMap();
	
	public UserConfiguration(ServletContext context)
	{
	}

	public void setUserSetting(String name, Object value)
	{
		settings.put(name,value);
	}
	
	public Object getUserSetting(String name)
	{
		return(settings.get(name));
	}
}
