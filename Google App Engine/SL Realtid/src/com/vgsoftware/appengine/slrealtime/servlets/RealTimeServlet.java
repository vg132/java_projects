package com.vgsoftware.appengine.slrealtime.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.vgsoftware.appengine.slrealtime.DepartureCache;
import com.vgsoftware.appengine.slrealtime.dataabstraction.Departure;
import com.vgsoftware.appengine.slrealtime.dataabstraction.Station;
import com.vgsoftware.appengine.slrealtime.parse.RealTimeParser;

public class RealTimeServlet extends HttpServlet
{
	private int siteId = 0;
	private int typeId = 0;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		loadSiteIdAndTypeId(request, response);
		String cacheKey = siteId + "-" + typeId;
		String json = DepartureCache.get(cacheKey);
		if(json == null)
		{
			json = loadDepartures();
			DepartureCache.put(cacheKey,json);
			response.setHeader("realtime-cache","false");
		}
		else
		{
			response.setHeader("realtime-cache","true");
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}

	private String loadDepartures()
	{
		Station station = null;
		for(Station s : Station.list())
		{
			if(s.getSiteId()==siteId && s.getTransportationTypeId() == typeId)
			{
				station = s;
				break;
			}
		}
		if (station!=null)
		{
			JSONObject obj = new JSONObject();
			obj.put("station", station.toJSONString());
			JSONArray json = new JSONArray();
			for (Departure departure : new RealTimeParser(station).parse())
			{
				json.add(departure.toJSONString());
			}
			obj.put("departures", json);
			return obj.toJSONString();
		}
		return null;
	}

	private void loadSiteIdAndTypeId(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			if (request.getParameter("siteId") != null && !request.getParameter("siteId").isEmpty() &&
					request.getParameter("typeId") != null && !request.getParameter("typeId").isEmpty())
			{
				siteId = Integer.parseInt(request.getParameter("siteId"));
				typeId = Integer.parseInt(request.getParameter("typeId"));
			}
		}
		catch (NumberFormatException ex)
		{
		}
	}
}
