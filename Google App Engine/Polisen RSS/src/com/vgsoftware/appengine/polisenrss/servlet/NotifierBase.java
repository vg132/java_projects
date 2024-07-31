package com.vgsoftware.appengine.polisenrss.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.vgsoftware.appengine.polisenrss.data.FeedHistory;
import com.vgsoftware.appengine.polisenrss.data.Recipient;
import com.vgsoftware.appengine.polisenrss.gcm.GCMService;
import com.vgsoftware.appengine.polisenrss.gcm.MessageResult;
import com.vgsoftware.appengine.polisenrss.gcm.SendResult;
import com.vgsoftware.appengine.polisenrss.parse.Feed;
import com.vgsoftware.appengine.polisenrss.parse.FeedItem;
import com.vgsoftware.appengine.polisenrss.parse.RssParser;

public abstract class NotifierBase extends HttpServlet
{
	private StringBuffer _debugBuffer = null;
	protected boolean debug = false;
	protected boolean alwaysSend = false;

	private static String key = "rgvVgRIaEOuUlMXo8dV2ataF0ruxqRT7EuxijCbND7DenRaqfa6072HaIPsEEBLL";

	public abstract void checkAndNotify()
		throws IOException;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		if (!key.equals(request.getParameter("key")))
		{
			throw new IOException("Unauthorized Access!");
		}
		_debugBuffer = new StringBuffer();
		debug = request.getParameter("debug") != null;
		alwaysSend = request.getParameter("alwayssend") != null;
		checkAndNotify();
		response.setContentType("text/plain");
		response.getWriter().write(getLogMessages());
	}

	protected void sendMessage(Message message, int serviceId)
		throws IOException
	{
		SendResult sendResult = GCMService.send(message, Recipient.list(serviceId));
		addLogMessage("Sent: " + sendResult.getMessageResult().size());
		addLogMessage("Errors: " + sendResult.getErrorResults().size());
		int deleteCount = 0;
		for (MessageResult errorResult : sendResult.getErrorResults())
		{
			addLogMessage("Error: '" + errorResult.getErrorName() + "', Device Id: '" + errorResult.getDeviceId());
			if (deleteCount < 100 && (errorResult.getErrorName().equals(Constants.ERROR_NOT_REGISTERED) || errorResult.getErrorName().equals(Constants.ERROR_INVALID_REGISTRATION)))
			{
				Recipient.remove(errorResult.getDeviceId());
				deleteCount++;
				addLogMessage("Deleted: '" + errorResult.getDeviceId() + "', count: " + (deleteCount));
			}
		}
	}

	protected boolean getFeedChanges(String feedUrl, String name, Message.Builder messageBuilder)
		throws IOException
	{
		String messageData = getFeedMessageData(feedUrl);
		boolean added = false;
		if (!messageData.equals(StringUtils.EMPTY))
		{
			messageBuilder.addData(name, messageData);
			added = true;
		}
		addLogMessage(name + " message data: " + messageData);
		return added;
	}

	protected String getFeedMessageData(String feedUrl)
	{
		String messageData = StringUtils.EMPTY;
		FeedHistory history = FeedHistory.getFeedHistoryItem(feedUrl);
		Feed feedContent = RssParser.parse(feedUrl);
		if (feedContent != null && feedContent.getFeedItems().size() > 0)
		{
			if (history == null)
			{
				history = new FeedHistory(feedUrl);
				history.setLastDate(new Date(0));
				history.save();
			}
			for (FeedItem item : feedContent.getFeedItems())
			{
				if (item.getDate().after(history.getLastDate()))
				{
					messageData += "|" + item.getCountyId();
				}
			}
			history.setLastDate(feedContent.getFeedItem(0).getDate());
			history.save();
		}
		if (!messageData.equals(StringUtils.EMPTY))
		{
			messageData = messageData.substring(1);
		}
		return messageData;
	}

	protected void addLogMessage(String message)
	{
		if (debug && _debugBuffer != null)
		{
			_debugBuffer.append(message + "\n");
		}
	}

	protected String getLogMessages()
	{
		if (debug && _debugBuffer != null)
		{
			return _debugBuffer.toString();
		}
		return "";
	}
}
