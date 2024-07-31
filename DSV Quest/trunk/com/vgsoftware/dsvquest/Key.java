/**
 * Copyright (C) VG Software 2003-apr-07
 *  
 * Document History
 * 
 * Created: 2003-apr-07 21:00:05 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;
/**
 * @author Viktor
 * 
 */
public class Key
{
	private String name=null;
	
	public Key(String name)
	{
		this.name=name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return(name);
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Key)
		{
			if(((Key)obj).getName().equals(name))
				return(true);
			else
				return(false);
		}
		return(false);
	}
	
	public String toString()
	{
		return(name);
	}
}
