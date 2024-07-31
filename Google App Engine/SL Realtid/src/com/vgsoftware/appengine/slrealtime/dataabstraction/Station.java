package com.vgsoftware.appengine.slrealtime.dataabstraction;

import java.io.Serializable;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.json.simple.JSONObject;

import com.vgsoftware.appengine.slrealtime.Cache;
import com.vgsoftware.appengine.slrealtime.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Station implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private Long _id;
	@Persistent
	private int _siteId;
	@Persistent
	private String _name;
	@Persistent
	private int _transportationTypeId;
	@Persistent
	private double _latitude;
	@Persistent
	private double _longitude;

	public Station()
	{
	}

	public Station(String name, int siteId, double latitude, double longitude, int transportationTypeId)
	{
		setName(name);
		setSiteId(siteId);
		setLatitude(latitude);
		setLongitude(longitude);
		setTransportationTypeId(transportationTypeId);
	}

	public long getId()
	{
		return _id;
	}

	public int getSiteId()
	{
		return _siteId;
	}

	public void setSiteId(int siteId)
	{
		_siteId = siteId;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public int getTransportationTypeId()
	{
		return _transportationTypeId;
	}

	public void setTransportationTypeId(int transportationTypeId)
	{
		_transportationTypeId = transportationTypeId;
	}

	public double getLatitude()
	{
		return _latitude;
	}

	public void setLatitude(double latitude)
	{
		_latitude = latitude;
	}

	public double getLongitude()
	{
		return _longitude;
	}

	public void setLongitude(double longitude)
	{
		_longitude = longitude;
	}

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

	@Override
	public String toString()
	{
		return getName();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONString()
	{
		JSONObject json = new JSONObject();

		json.put("id",getId());
		json.put("siteId",getSiteId());
		json.put("name",getName());
		json.put("transportationType",getTransportationTypeId());
		json.put("lng",getLongitude());
		json.put("lat",getLatitude());

		return json;
	}
	
	public static List<Station> list()
	{
		List<Station> stations = Cache.get("allStations");
		if(stations == null)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				Query q = pm.newQuery(Station.class);
				q.setOrdering("_name");
				stations = (List<Station>) q.execute();
				stations.size();
				//Cache.put("allStations", stations);
			}
			finally
			{
				pm.close();
			}
		}
		return stations;
	}
}
