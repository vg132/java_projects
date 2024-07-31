package com.vgsoftware.appengine.polisenrss.servlet;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.android.gcm.server.Message;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.vgsoftware.appengine.polisenrss.Services;
import com.vgsoftware.appengine.polisenrss.data.Recipient;

@SuppressWarnings("serial")
public class PolisenRSSNotifier extends NotifierBase
{
	public void checkAndNotify()
		throws IOException
	{
		boolean sendMessage = false;

		String eventsFeedUrl = "http://www.polisen.se/rss-handelser";
		String newsFeedUrl = "http://www.polisen.se/rss-nyheter";
		String trafficFeedUrl = "http://www.polisen.se/rss-trafikovervakning";
		String pressFeedUrl = "http://www.polisen.se/Aktuellt/RSS/Pressmeddelande-RSS-lank/";
		String publicationsFeedUrl = "http://www.polisen.se/rss-publikationer";

		Message.Builder messageBuilder = new Message.Builder();
		messageBuilder.collapseKey("PolisenRSSNotifier");
		messageBuilder.timeToLive(3600);

		sendMessage = getFeedChanges(eventsFeedUrl, "event", messageBuilder) || sendMessage;
		sendMessage = getFeedChanges(newsFeedUrl, "news", messageBuilder) || sendMessage;
		sendMessage = getFeedChanges(trafficFeedUrl, "traffic", messageBuilder) || sendMessage;
		sendMessage = getFeedChanges(pressFeedUrl, "press", messageBuilder) || sendMessage;
		sendMessage = getFeedChanges(publicationsFeedUrl, "publications", messageBuilder) || sendMessage;

		if (sendMessage || alwaysSend)
		{
			Message message = messageBuilder.build();
			sendMessage(message, Services.POLISEN_RSS);
			sendMessageToWindowsPhone(message, Services.POLISEN_RSS_WINDOWS_PHONE);
		}
		else
		{
			addLogMessage("No new data to send");
		}
	}

	private int getEvents(Map<String, String> data, String key, List<String> regions, List<String> feeds)
	{
		int events = 0;
		if (data.containsKey(key) && feeds.contains(key))
		{
			for (String region : data.get(key).split("\\|"))
			{
				if (regions.contains(region))
				{
					events++;
				}
			}
		}
		return events;
	}

	private void sendMessageToWindowsPhone(Message message, int serviceId)
	{
		Map<String, String> data = message.getData();
		for (Recipient recipient : Recipient.list(serviceId))
		{
			try
			{
				List<String> feeds = recipient.getFeeds();
				List<String> regions = recipient.getRegions();
				int events = getEvents(data, "traffic", regions, feeds);
				events += getEvents(data, "press", regions, feeds);
				events += getEvents(data, "event", regions, feeds);
				events += getEvents(data, "news", regions, feeds);
				events += getEvents(data, "publications", regions, feeds);
				if (events > 0)
				{
					HTTPRequest request = new HTTPRequest(new URL(recipient.getDeviceId()), HTTPMethod.POST);
					request.setHeader(new HTTPHeader("content-type", "text/xml"));
					request.addHeader(new HTTPHeader("X-WindowsPhone-Target", "toast"));
					request.addHeader(new HTTPHeader("X-NotificationClass", "2"));
					String toastMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Toast><wp:Text1>Polisen RSS</wp:Text1><wp:Text2>" + events + " nya händelser</wp:Text2></wp:Toast></wp:Notification>";
					request.setPayload(toastMessage.getBytes("UTF-8"));
					URLFetchService service = URLFetchServiceFactory.getURLFetchService();
					HTTPResponse response = service.fetch(request);
					addLogMessage("Sent to: " + recipient.getDeviceId());
					if (response.getResponseCode() == 404)
					{
						addLogMessage("Got 404 back, removed recipient");
						Recipient.remove(recipient.getDeviceId());
					}
				}
			}
			catch (Exception ex)
			{
				addLogMessage("Got error!" + ex.getMessage());
			}
		}
	}
}
