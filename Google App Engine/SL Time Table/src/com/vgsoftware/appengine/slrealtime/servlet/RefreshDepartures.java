package com.vgsoftware.appengine.slrealtime.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.vgsoftware.appengine.slrealtime.data.Departure;
import com.vgsoftware.appengine.slrealtime.parser.DepartureHandler;

@SuppressWarnings("serial")
public class RefreshDepartures extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		response.setContentType("text/html");
		int siteId = Integer.parseInt(request.getParameter("siteId"));
		List<Departure> departures = DepartureHandler.getDepartures(siteId);
		for (Departure departure : departures)
		{
			departure.save();
			response.getWriter().write(departure.getDestination() + " - Time: " + departure.getTime() + "<br />");
		}
	}
}
