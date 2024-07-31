package com.vgsoftware.web.data.gt4;

import java.sql.Timestamp;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class TimeData
{
	private int id=-1;
	private int memberId=-1;
	private int eventId=-1;
	private int time=-1;
	private int controlTypeId=-1;
	private Timestamp added=null;
	private String memberName=null;
	private String controlTypeName=null;

	/**
	 * @return Returns the added.
	 */
	public Timestamp getAdded()
	{
		return(added);
	}

	/**
	 * @param added The added to set.
	 */
	public void setAdded(Timestamp added)
	{
		this.added=added;
	}
	/**
	 * @return Returns the controlType.
	 */
	public int getControlTypeId()
	{
		return controlTypeId;
	}
	/**
	 * @param controlType The controlType to set.
	 */
	public void setControlTypeId(int controlTypeId)
	{
		this.controlTypeId = controlTypeId;
	}
	/**
	 * @return Returns the controlTypeName.
	 */
	public String getControlTypeName()
	{
		return controlTypeName;
	}
	/**
	 * @param controlTypeName The controlTypeName to set.
	 */
	public void setControlTypeName(String controlTypeName)
	{
		this.controlTypeName = controlTypeName;
	}
	/**
	 * @return Returns the eventId.
	 */
	public int getEventId()
	{
		return eventId;
	}
	/**
	 * @param eventId The eventId to set.
	 */
	public void setEventId(int eventId)
	{
		this.eventId = eventId;
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
	 * @return Returns the memberId.
	 */
	public int getMemberId()
	{
		return memberId;
	}
	/**
	 * @param memberId The memberId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId = memberId;
	}
	/**
	 * @return Returns the memberName.
	 */
	public String getMemberName()
	{
		return memberName;
	}
	/**
	 * @param memberName The memberName to set.
	 */
	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}
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
		this.time = time;
	}
	
	public String getTextTime()
	{
		int tmpTime=time;
		int min=0;
		int sec=0;
		while(tmpTime>=60000)
		{
			min++;
			tmpTime-=60000;
		}
		while(tmpTime>=1000)
		{
			sec++;
			tmpTime-=1000;
		}

		String s=min+"";
		if(sec==0)
			s+=":00";
		else if(sec<10)
			s+=":0"+sec;
		else
			s+=":"+sec;
		if(tmpTime==0)
			s+=".000";
		else if(tmpTime<10)
			s+=".00"+tmpTime;
		else if(tmpTime<100)
			s+=".0"+tmpTime;
		else
			s+="."+tmpTime;
		return(s);		
	}
	
	public String getDiff(long bestTime)
	{
		long sec=0;
		long diff=0;
		if(time>bestTime)
			diff=time-bestTime;
		else
			diff=bestTime-time;
		while(diff>=1000)
		{
			sec++;
			diff-=1000;
		}
		String s=sec+"";
		if(diff==0)
			s+=".000";
		else if(diff<10)
			s+=".00"+diff;
		else if(diff<100)
			s+=".0"+diff;
		else
			s+="."+diff;
		if(time>bestTime)
			s="+"+s;
		else if(time<bestTime)
			s="-"+s;
		return(s);
	}
}
