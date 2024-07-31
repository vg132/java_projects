package com.vgsoftware.appengine.polisenrss.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.vgsoftware.appengine.polisenrss.Cache;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class OldRecipient implements Serializable
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

	public OldRecipient(String deviceId, int serviceId, String regions, String feeds)
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
			List<String> recipients = Cache.get(OldRecipient.getCacheKey(getServiceId()));
			if (recipients != null)
			{
				recipients.remove(getDeviceId());
				recipients.add(getDeviceId());
				Cache.put(OldRecipient.getCacheKey(getServiceId()), recipients);
			}
		}
		finally
		{
			pm.close();
		}
	}

	public static void delete(String deviceId)
	{
		OldRecipient device = load(deviceId);
		if (device != null)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				Query query = pm.newQuery(OldRecipient.class);
				query.setFilter("_deviceId == deviceId");
				query.declareParameters("String deviceId");
				List<OldRecipient> recipients = (List<OldRecipient>) query.execute(deviceId);
				if (recipients != null && recipients.size() > 0)
				{
					OldRecipient recipient = recipients.get(0);

					List<String> cachedList = Cache.get(OldRecipient.getCacheKey(recipient.getServiceId()));
					if (cachedList != null)
					{
						cachedList.remove(recipient.getDeviceId());
						Cache.put(OldRecipient.getCacheKey(recipient.getServiceId()), cachedList);
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

	public static OldRecipient load(String deviceId)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			Query query = pm.newQuery(OldRecipient.class);
			query.setFilter("_deviceId == deviceId");
			query.declareParameters("String deviceId");
			List<OldRecipient> recipients = (List<OldRecipient>) query.execute(deviceId);
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

	public static List<String> list(int serviceId)
	{
		List<String> recipients = Cache.get(OldRecipient.getCacheKey(serviceId));
		if (recipients == null)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				Query query = pm.newQuery(OldRecipient.class);
				query.setFilter("_serviceId == serviceId");
				query.declareParameters("int serviceId");
				query.getFetchPlan().setFetchSize(5000);
				List<OldRecipient> recipientsList = (List<OldRecipient>) query.execute(serviceId);
				if (recipientsList != null && recipientsList.size() > 0)
				{
					recipients = new ArrayList<String>();
					for (OldRecipient recipient : recipientsList)
					{
						recipients.add(recipient.getDeviceId());
					}
					Cache.put(OldRecipient.getCacheKey(serviceId), recipients);
				}
			}
			finally
			{
				pm.close();
			}
		}
		return recipients != null ? recipients : new ArrayList<String>();
	}

	public String getDeviceId()
	{
		return _deviceId;
	}

	public String getRegions()
	{
		return _regions;
	}

	public void setRegions(String regions)
	{
		_regions = regions;
	}

	public String getFeeds()
	{
		return _feeds;
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
		return String.format(OldRecipient.CacheKeyFormat, serviceId);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof OldRecipient)
		{
			OldRecipient device = (OldRecipient) obj;
			return this.getDeviceId().equals(device.getDeviceId());
		}
		return false;
	}
}