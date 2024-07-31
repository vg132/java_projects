/*
 * Created on 2005-mar-30
 */
package com.vgsoftware.web.data.f1video;

/**
 * @author viktor
 */
public class SimpleData
{
	private int id=-1;
	private int extra=-1;
	private String name=null;

	public SimpleData()
	{
	}
	
	public SimpleData(int id, String name)
	{
		this.id=id;
		this.name=name;
	}
	
	public SimpleData(int id, String name, int extra)
	{
		this.id=id;
		this.name=name;
		this.extra=extra;
	}

	public int getExtra()
	{
		return extra;
	}

	public void setExtra(int extra)
	{
		this.extra=extra;
	}

	public String getId()
	{
		return(""+id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}
}
