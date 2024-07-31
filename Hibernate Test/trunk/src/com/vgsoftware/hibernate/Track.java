package com.vgsoftware.hibernate;

import java.io.Serializable;
import java.util.Date;

public class Track implements Serializable
{
	private Integer id=-1;
	private String title=null;
	private String filePath=null;
	private Date playTime=null;
	private Date added=null;
	private short volume=-1;

	public Track()
	{
	}

	public Track(String title, String filePath)
	{
		this.title=title;
		this.filePath=filePath;
	}
	
	public Track(String title, String filePath, Date playTime, Date added, short volume)
	{
		this.title=title;
		this.filePath=filePath;
		this.playTime=playTime;
		this.added=added;
		this.volume=volume;
	}

	/**
	 * @return Returns the added.
	 */
	public Date getAdded()
	{
		return added;
	}

	/**
	 * @param added The added to set.
	 */
	public void setAdded(Date added)
	{
		this.added=added;
	}

	/**
	 * @return Returns the filePath.
	 */
	public String getFilePath()
	{
		return filePath;
	}

	/**
	 * @param filePath The filePath to set.
	 */
	public void setFilePath(String filePath)
	{
		this.filePath=filePath;
	}

	/**
	 * @return Returns the id.
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Integer id)
	{
		this.id=id;
	}

	/**
	 * @return Returns the playTime.
	 */
	public Date getPlayTime()
	{
		return playTime;
	}

	/**
	 * @param playTime The playTime to set.
	 */
	public void setPlayTime(Date playTime)
	{
		this.playTime=playTime;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title)
	{
		this.title=title;
	}

	/**
	 * @return Returns the volume.
	 */
	public short getVolume()
	{
		return volume;
	}

	/**
	 * @param volume The volume to set.
	 */
	public void setVolume(short volume)
	{
		this.volume=volume;
	}

	public boolean equals(Object other)
	{
		if(!(other instanceof Track))
			return(false);
		if(((Track)other).getId()==this.getId())
			return(true);
		return(false);
	}

	public String toString()
	{
		return("id:"+getId());
	}

	public int hashCode()
	{
		return(this.getId().hashCode());
	}
}
