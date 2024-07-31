package com.vgsoftware.appengine.polisenrss.parse;

import java.util.Date;

public class FeedItem
{
	private String _title = null;
	private String _description = null;
	private String _link = null;
	private String _category = null;
	private Date _date = null;

	public void setTitle(String title)
	{
		_title = title;
	}

	public void setDescription(String description)
	{
		_description = description;
	}

	public void setLink(String link)
	{
		_link = link;
	}

	public void setCategory(String category)
	{
		_category = category;
	}

	public void setDate(Date date)
	{
		_date = date;
	}

	public String getTitle()
	{
		return _title;
	}

	public String getDescription()
	{
		return _description;
	}

	public String getLink()
	{
		return _link;
	}

	public String getCategory()
	{
		return _category;
	}

	public Date getDate()
	{
		return _date;
	}

	public int getCountyId()
	{
		String url = getLink().toLowerCase();
		if (url.contains("blekinge"))
		{
			return 1;
		}
		else if (url.contains("dalarna"))
		{
			return 2;
		}
		else if (url.contains("gotland"))
		{
			return 3;
		}
		else if (url.contains("gavleborg"))
		{
			return 4;
		}
		else if (url.contains("halland"))
		{
			return 5;
		}
		else if (url.contains("jamtland"))
		{
			return 6;
		}
		else if (url.contains("jonkoping"))
		{
			return 7;
		}
		else if (url.contains("kalmar"))
		{
			return 8;
		}
		else if (url.contains("kronoberg"))
		{
			return 9;
		}
		else if (url.contains("norrbotten"))
		{
			return 10;
		}
		else if (url.contains("skane"))
		{
			return 11;
		}
		else if (url.contains("stockholm"))
		{
			return 12;
		}
		else if (url.contains("sodermanland"))
		{
			return 13;
		}
		else if (url.contains("uppsala"))
		{
			return 14;
		}
		else if (url.contains("varmland"))
		{
			return 15;
		}
		else if (url.contains("vasterbotten"))
		{
			return 16;
		}
		else if (url.contains("vasternorrland"))
		{
			return 17;
		}
		else if (url.contains("vastmanland"))
		{
			return 18;
		}
		else if (url.contains("vastra-gotaland"))
		{
			return 19;
		}
		else if (url.contains("orebro"))
		{
			return 20;
		}
		else if (url.contains("ostergotland"))
		{
			return 21;
		}
		return 0;
	}
}
