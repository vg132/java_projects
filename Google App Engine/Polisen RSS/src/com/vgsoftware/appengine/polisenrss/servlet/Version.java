package com.vgsoftware.appengine.polisenrss.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.utils.SystemProperty;

public class Version extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		response.setContentType("text/plain");
		String applicationVersion = SystemProperty.applicationVersion.get();
		Date versionDate = new Date(Long.parseLong(applicationVersion.substring(applicationVersion.indexOf(".") + 1)) / (2 << 27) * 1000);
		response.getWriter().write("Version: " + applicationVersion + "\n");
		response.getWriter().write("Version date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm zzz").format(versionDate));
	}
}