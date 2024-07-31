package com.vgsoftware.appengine.slrealtime.data;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Departure implements Serializable
{
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private Long _id;
	@Persistent
	private String _destination = null;
	@Persistent
	private Date _time = null;
	@Persistent
	private int _transportationTypeId = 0;
	@Persistent
	private int _siteId = 0;
	@Persistent
	private int _dayOfWeek = 0;

	public void save()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			pm.makePersistent(this);
		}
		finally
		{
			pm.close();
		}
	}

	public long getId()
	{
		return _id;
	}

	public String getDestination()
	{
		return _destination;
	}

	public void setDestination(String destination)
	{
		_destination = destination;
	}

	public Date getTime()
	{
		return _time;
	}

	public void setTime(Date time)
	{
		_time = time;
	}

	public int getTransportationTypeId()
	{
		return _transportationTypeId;
	}

	public void setTransportationTypeId(int transportationTypeId)
	{
		_transportationTypeId = transportationTypeId;
	}

	public int getSiteId()
	{
		return _siteId;
	}

	public void setSiteId(int siteId)
	{
		_siteId = siteId;
	}

	public int getDayOfWeek()
	{
		return _dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek)
	{
		_dayOfWeek = dayOfWeek;
	}
}