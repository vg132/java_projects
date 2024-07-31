package com.vgsoftware.appengine.polisenrss.parse;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.vgsoftware.appengine.polisenrss.DateParser;
import com.vgsoftware.appengine.polisenrss.Utilities;

public class RssParser
{
	public static Feed parse(String feedUrl)
	{
		Element element = Utilities.getRootElement(feedUrl);
		if (element != null)
		{
			return parse(element);
		}
		return null;
	}

	public static Feed parse(Element root)
	{
		Feed feed = new Feed();
		// feed.setTitle(Utilities.getValue(root.getChildNodes(), "title"));
		// feed.setDescription(Utilities.getValue(root.getChildNodes(), "description"));
		// feed.setDate(DateParser.parse(Utilities.getValue(root.getChildNodes(), "pubDate")));
		NodeList nodeList = root.getElementsByTagName("item");
		if (nodeList.getLength() > 0)
		{
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				FeedItem feedItem = parseItemElement((Element) nodeList.item(i));
				if (feedItem != null && feedItem.getLink() != null && !feedItem.getLink().equals(""))
				{
					feed.addItem(feedItem);
				}
			}
		}
		feed.sortItems();
		return feed;
	}

	public static boolean canHandle(Element root)
	{
		if (root.getNodeName() != null)
		{
			String nodeName = root.getNodeName();
			if (nodeName.equals("rss") || nodeName.equals("rdf:RDF"))
			{
				return true;
			}
		}
		return false;
	}

	private static FeedItem parseItemElement(Element itemElement)
	{
		FeedItem item = new FeedItem();
		// item.setTitle(Utilities.getValue(itemElement, "title"));
		// item.setDescription(Utilities.getValue(itemElement, "description"));
		item.setLink(Utilities.getValue(itemElement, "link"));
		item.setDate(DateParser.parse(Utilities.getValue(itemElement, "pubDate")));
		return item;
	}
}
