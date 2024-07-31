package com.vgsoftware.vaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.vgsoftware.vaction.data.Globals;

public abstract class Action
{
	public abstract String execute(HttpServletRequest request, HttpServletResponse response);

	public String getParam(HttpServletRequest request, String name)
	{
		return(request.getParameter(name));
	}

	public int getIntParam(HttpServletRequest request, String name)
	{
		try
		{
			return(Integer.parseInt(request.getParameter(name)));
		}
		catch(NumberFormatException nfe)
		{
			return(-1);
		}
	}

	public boolean isParamSet(HttpServletRequest request,String name)
	{
		if(request.getParameter(name)!=null)
			return(true);
		return(false);
	}

	public DataSource getDataSource(HttpServletRequest request)
	{
		if(request.getAttribute(Globals.DATA_SOURCE)!=null)
			return((DataSource)request.getAttribute(Globals.DATA_SOURCE));
		return(null);
	}
}
