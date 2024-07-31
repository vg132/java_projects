package com.vgsoftware.appengine.slrealtime.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeTable extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		if (request.getParameter("siteId") != null)
		{
			int siteId = Integer.parseInt(request.getParameter("siteId"));
			response.getWriter().print(siteId);
		}
	}
}
