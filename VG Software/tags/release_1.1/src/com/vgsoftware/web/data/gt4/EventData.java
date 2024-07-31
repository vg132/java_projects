package com.vgsoftware.web.data.gt4;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class EventData
{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private int id=-1;
	private int seasonId=-1;
	private String track=null;
	private String type=null;
	private Timestamp start=null;
	private Timestamp end=null;
	/**
	 * @return Returns the end.
	 */
	public Timestamp getEnd()
	{
		return end;
	}
	/**
	 * @param end The end to set.
	 */
	public void setEnd(Timestamp end)
	{
		this.end = end;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * @return Returns the seasonId.
	 */
	public int getSeasonId()
	{
		return seasonId;
	}
	/**
	 * @param seasonId The seasonId to set.
	 */
	public void setSeasonId(int seasonId)
	{
		this.seasonId = seasonId;
	}
	/**
	 * @return Returns the start.
	 */
	public Timestamp getStart()
	{
		return start;
	}
	/**
	 * @param start The start to set.
	 */
	public void setStart(Timestamp start)
	{
		this.start = start;
	}
	/**
	 * @return Returns the track.
	 */
	public String getTrack()
	{
		return track;
	}
	/**
	 * @param track The track to set.
	 */
	public void setTrack(String track)
	{
		this.track = track;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType()
	{
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	public String getFullName(int nr)
	{
		return("Round "+nr+" ("+sdf.format(getStart())+" - "+sdf.format(getEnd())+") "+getType()+" on "+getTrack());
	}

	public String getShortName(int nr)
	{
		return("Round "+nr+" ("+sdf.format(getStart())+" - "+sdf.format(getEnd())+") - "+getType());
	}
	
	public String getName()
	{
		return(getType()+" on "+getTrack());
	}
}
