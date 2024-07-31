package com.vgsoftware.appengine.polisenrss.servlet;

import java.io.IOException;
import java.net.URL;
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

public class LybeckNotifier extends NotifierBase
{
	@Override
	public void checkAndNotify()
		throws IOException
	{		
		boolean sendMessage = false;

		String lybeckFeedUrl = "http://www.lybeckeffekten.se/Quotes/Feed";

		Message.Builder messageBuilder = new Message.Builder();
		messageBuilder.collapseKey("LybeckRSSNotifier");
		messageBuilder.timeToLive(3600);

		sendMessage = getFeedChanges(lybeckFeedUrl, "lybeck", messageBuilder) || sendMessage;

		if (sendMessage || alwaysSend)
		{
			Message message = messageBuilder.build();
			sendMessage(message, Services.LYBECK_RSS);
			sendMessageToWindowsPhone(message, Services.LYBECK_RSS_WINDOWS_PHONE);
		}
		else
		{
			addLogMessage("No new data to send");
		}
	}

	private int getEvents(Map<String, String> data, String key)
	{
		int events = 0;
		if (data.containsKey(key))
		{
			return data.get(key).split("\\|").length;
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
				int events = getEvents(data, "lybeck");
				if (events > 0)
				{
					HTTPRequest request = new HTTPRequest(new URL(recipient.getDeviceId()), HTTPMethod.POST);
					request.setHeader(new HTTPHeader("content-type", "text/xml"));
					request.addHeader(new HTTPHeader("X-WindowsPhone-Target", "toast"));
					request.addHeader(new HTTPHeader("X-NotificationClass", "2"));
					String toastMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Toast><wp:Text1>Lybeckeffekten</wp:Text1><wp:Text2>" + events + " nya citaten från Lybeck</wp:Text2></wp:Toast></wp:Notification>";
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
