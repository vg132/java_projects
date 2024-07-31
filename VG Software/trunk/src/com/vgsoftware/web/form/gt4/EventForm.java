package com.vgsoftware.web.form.gt4;

import org.apache.struts.action.ActionForm;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-11 - Document created.
 */
public class EventForm extends ActionForm
{
	private int season=-1;
	private String track=null;
	private String type=null;
	private String start=null;
	private String end=null;

	public EventForm()
	{
	}
	
	/**
	 * @return Returns the end.
	 */
	public String getEnd()
	{
		return end;
	}
	/**
	 * @param end The end to set.
	 */
	public void setEnd(String end)
	{
		this.end = end;
	}
	/**
	 * @return Returns the season.
	 */
	public int getSeason()
	{
		return season;
	}
	/**
	 * @param season The season to set.
	 */
	public void setSeason(int season)
	{
		this.season = season;
	}
	/**
	 * @return Returns the start.
	 */
	public String getStart()
	{
		return start;
	}
	/**
	 * @param start The start to set.
	 */
	public void setStart(String start)
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
}
