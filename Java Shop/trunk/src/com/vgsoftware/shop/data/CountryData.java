package com.vgsoftware.shop.data;

public class CountryData
{
	private int id=-1;
	private String name=null;
	
	public CountryData(int id, String name)
	{
		this.id=id;
		this.name=name;
	}

	public int getId()
	{
		return (id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public String getName()
	{
		return (name);
	}

	public void setName(String name)
	{
		this.name=name;
	}
}
