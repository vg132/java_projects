package com.vgsoftware.appengine.polisenrss.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.vgsoftware.appengine.polisenrss.Cache;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Recipient implements Serializable
{
	private final static String CacheKeyFormat = "__Recipient_List_Service_Id:%s__";

	@PrimaryKey
	private String _deviceId = null;
	@Persistent
	private int _serviceId = 0;
	@Persistent
	private String _regions = null;
	@Persistent
	private String _feeds = null;

	public Recipient(String deviceId, int serviceId, String regions, String feeds)
	{
		_deviceId = deviceId;
		_serviceId = serviceId;
		_regions = regions;
		_feeds = feeds;
	}

	public void save()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			pm.makePersistent(this);
			List<Recipient> recipients = Cache.get(Recipient.getCacheKey(getServiceId()));
			if (recipients != null)
			{
				recipients.remove(this);
				recipients.add(this);
				Cache.put(Recipient.getCacheKey(getServiceId()), recipients);
			}
		}
		finally
		{
			pm.close();
		}
	}

	public static void remove(String deviceId)
	{
		Recipient device = load(deviceId);
		if (device != null)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				Query query = pm.newQuery(Recipient.class);
				query.setFilter("_deviceId == deviceId");
				query.declareParameters("String deviceId");
				List<Recipient> recipients = (List<Recipient>) query.execute(deviceId);
				if (recipients != null && recipients.size() > 0)
				{
					Recipient recipient = recipients.get(0);

					List<Recipient> cachedList = Cache.get(Recipient.getCacheKey(recipient.getServiceId()));
					if (cachedList != null)
					{
						cachedList.remove(recipient);
						Cache.put(Recipient.getCacheKey(recipient.getServiceId()), cachedList);
					}
					pm.deletePersistent(recipient);
				}
			}
			finally
			{
				pm.close();
			}
		}
	}

	public static Recipient load(String deviceId)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			Query query = pm.newQuery(Recipient.class);
			query.setFilter("_deviceId == deviceId");
			query.declareParameters("String deviceId");
			List<Recipient> recipients = (List<Recipient>) query.execute(deviceId);
			if (recipients != null && recipients.size() > 0)
			{
				return recipients.get(0);
			}
		}
		finally
		{
			pm.close();
		}
		return null;
	}

	public static List<Recipient> list(int serviceId)
	{
		List<Recipient> recipients = Cache.get(Recipient.getCacheKey(serviceId));
		if (recipients == null)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				Query query = pm.newQuery(Recipient.class);
				query.setFilter("_serviceId == serviceId");
				query.declareParameters("int serviceId");
				query.getFetchPlan().setFetchSize(5000);
				List<Recipient> recipientsList = (List<Recipient>) query.execute(serviceId);
				if (recipientsList != null && recipientsList.size() > 0)
				{
					recipients = new ArrayList<Recipient>();
					for (Recipient recipient : recipientsList)
					{
						recipients.add(recipient);
					}
					Cache.put(Recipient.getCacheKey(serviceId), recipients);
				}
			}
			finally
			{
				pm.close();
			}
		}
		return recipients != null ? recipients : new ArrayList<Recipient>();
	}

	public String getDeviceId()
	{
		return _deviceId;
	}

	public List<String> getRegions()
	{
		return Arrays.asList(_regions.split("\\|"));
	}

	public void setRegions(String regions)
	{
		_regions = regions;
	}

	public List<String> getFeeds()
	{
		return Arrays.asList(_feeds.split("\\|"));
	}

	public void setFeeds(String feeds)
	{
		_feeds = feeds;
	}

	public int getServiceId()
	{
		return _serviceId;
	}

	public void setServiceId(int serviceId)
	{
		_serviceId = serviceId;
	}

	private static String getCacheKey(int serviceId)
	{
		return String.format(Recipient.CacheKeyFormat, serviceId);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Recipient)
		{
			Recipient device = (Recipient) obj;
			return this.getDeviceId().equals(device.getDeviceId());
		}
		return false;
	}
}