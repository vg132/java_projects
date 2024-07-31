package com.vgsoftware.web.data.gt4;

public class SeasonResultData
{
	private int memberId=-1;
	private String username=null;
	private int eventId=-1;
	private int bestTime=-1;
	
	public SeasonResultData()
	{
	}
	
	/**
	 * Gets the bestTime.
	 * @return Returns the bestTime.
	 */
	public int getBestTime()
	{
		return (bestTime);
	}

	/**
	 * Sets the bestTime.
	 * @param bestTime The bestTime to set.
	 */
	public void setBestTime(int bestTime)
	{
		this.bestTime=bestTime;
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
	 * Gets the memberId.
	 * @return Returns the memberId.
	 */
	public int getMemberId()
	{
		return (memberId);
	}

	/**
	 * Sets the memberId.
	 * @param memberId The memberId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
	}

	/**
	 * Gets the username.
	 * @return Returns the username.
	 */
	public String getUsername()
	{
		return (username);
	}

	/**
	 * Sets the username.
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username=username;
	}
}
