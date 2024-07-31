package com.vgsoftware.web.action.gt4;

public class Event implements Comparable
{
	int eventId=-1;
	int pos=-1;
	int time=-1;
	
	/**
	 * @return Returns the time.
	 */
	public int getTime()
	{
		return time;
	}

	/**
	 * @param time The time to set.
	 */
	public void setTime(int time)
	{
		this.time=time;
	}

	public Event(int eventId, int pos, int time)
	{
		this.eventId=eventId;
		this.pos=pos;
		this.time=time;
	}

	public int compareTo(Object obj)
	{
		if(obj instanceof Event)
			return(this.pos-((Event)obj).pos);
		return(-1);
	}

	/**
	 * Gets the eventId.
	 * @return Returns the eventId.
	 */
	public int getEventId()
	{
		return (eventId);
	}

	/**
	 * Sets the eventId.
	 * @param eventId The eventId to set.
	 */
	public void setEventId(int eventId)
	{
		this.eventId=eventId;
	}

	/**
	 * Gets the pos.
	 * @return Returns the pos.
	 */
	public int getPos()
	{
		return (pos);
	}

	/**
	 * Sets the pos.
	 * @param pos The pos to set.
	 */
	public void setPos(int pos)
	{
		this.pos=pos;
	}
}
