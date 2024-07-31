package com.vgsoftware.web.data.gallery;

public class AlbumData
{
	private int id=-1;
	private String name=null;
	private String description=null;
	
	public AlbumData()
	{
	}
	
	public AlbumData(int id, String name, String description)
	{
		this.id=id;
		this.name=name;
		this.description=description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public int getId()
	{
		return id;
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
