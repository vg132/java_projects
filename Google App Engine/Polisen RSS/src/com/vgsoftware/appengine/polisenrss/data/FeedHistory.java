package com.vgsoftware.appengine.polisenrss.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.vgsoftware.appengine.polisenrss.Cache;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FeedHistory implements Serializable
{
	@PrimaryKey
	private String _feedUrl = null;
	@Persistent
	private Date _lastDate = null;

	public FeedHistory(String feedUrl)
	{
		_feedUrl = feedUrl;
	}

	public void save()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
		{
			pm.makePersistent(this);
			Cache.put(this.getFeedUrl(), this);
		}
		finally
		{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static FeedHistory getFeedHistoryItem(String feedUrl)
	{
		FeedHistory item = Cache.get(feedUrl);
		if (item == null)
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try
			{
				Query query = pm.newQuery(FeedHistory.class);
				query.setFilter("_feedUrl == urlParameter");
				query.declareParameters("String urlParameter");
				List<FeedHistory> feedHistoryList = (List<FeedHistory>) query.execute(feedUrl);
				if (feedHistoryList != null && feedHistoryList.size() > 0)
				{
					item = feedHistoryList.get(0);
					Cache.put(feedUrl, item);
				}
			}
			finally
			{
				pm.close();
			}
		}
		return item;
	}

	public String getFeedUrl()
	{
		return _feedUrl;
	}

	public Date getLastDate()
	{
		return _lastDate;
	}

	public void setLastDate(Date lastDate)
	{
		_lastDate = lastDate;
	}
}
