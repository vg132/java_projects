/*
 * Created on 2005-mar-30
 */
package com.vgsoftware.web.data.member;

/**
 * @author viktor
 */
public class ServiceData implements Comparable<ServiceData>
{
	private int id=-1;
	private String name=null;
	
	public ServiceData(int id, String name)
	{
		this.id=id;
		this.name=name;
	}

	public int getId()
	{
		return(id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public String getName()
	{
		return(name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public int compareTo(ServiceData sd)
	{
		return(sd.getName().compareTo(name));
	}
}
