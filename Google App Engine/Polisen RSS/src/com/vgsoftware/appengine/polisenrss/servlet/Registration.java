package com.vgsoftware.appengine.polisenrss.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.vgsoftware.appengine.polisenrss.data.Recipient;

public class Registration extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		String action = request.getParameter("action");
		String deviceId = request.getParameter("deviceId");
		String feeds = request.getParameter("feeds");
		String regions = request.getParameter("regions");
		int serviceId = 0;
		try
		{
			serviceId = Integer.parseInt(request.getParameter("serviceId"));
		}
		catch (NumberFormatException ex)
		{
			serviceId = 0;
		}
		if (!StringUtils.isEmpty(action) && !StringUtils.isEmpty(deviceId))
		{
			if (action.equals("register"))
			{
				Recipient recipient = Recipient.load(deviceId);
				if (recipient == null)
				{
					recipient = new Recipient(deviceId, serviceId, regions, feeds);
					recipient.save();
				}
			}
			else if (action.equals("unregister"))
			{
				Recipient.remove(deviceId);
			}
			else if (action.equals("update"))
			{
				Recipient recipient = Recipient.load(deviceId);
				if (recipient != null)
				{
					recipient.setRegions(regions);
					recipient.setFeeds(feeds);
					recipient.save();
				}
			}
		}
	}
}
