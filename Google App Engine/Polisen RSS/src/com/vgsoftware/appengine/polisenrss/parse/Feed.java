package com.vgsoftware.appengine.polisenrss.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Feed
{
	private String _title = null;
	private Date _date = null;
	private String _description = null;
	private List<FeedItem> _feedItems;

	public Feed()
	{
		_feedItems = new ArrayList<FeedItem>();
	}

	public void addItem(FeedItem item)
	{
		_feedItems.add(item);
	}

	public FeedItem getFeedItem(int pos)
	{
		if (pos >= 0 && _feedItems.size() > pos)
		{
			return _feedItems.get(pos);
		}
		return null;
	}

	public List<FeedItem> getFeedItems()
	{
		return _feedItems;
	}

	public void sortItems()
	{
		Collections.sort(_feedItems, new Comparator<FeedItem>()
		{
			public int compare(FeedItem item1, FeedItem item2)
			{
				return item2.getDate().compareTo(item1.getDate());
			}
		});
	}

	public void setTitle(String title)
	{
		_title = title;
	}

	public void setDate(Date date)
	{
		_date = date;
	}

	public String getTitle()
	{
		return _title;
	}

	public Date getDate()
	{
		return _date;
	}

	public String getDescription()
	{
		return _description;
	}

	public void setDescription(String description)
	{
		_description = description;
	}
}