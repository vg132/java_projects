package com.vgsoftware.appengine.slrealtime.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.vgsoftware.appengine.slrealtime.Cache;
import com.vgsoftware.appengine.slrealtime.dataabstraction.Station;

public class SearchServlet extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		String cacheKey = "__search_cashe_key__";
		String jsonData = Cache.get(cacheKey);
		if (jsonData == null)
		{
			JSONArray json = new JSONArray();
			for (Station station : Station.list())
			{
				json.add(station.toJSONString());
			}
			jsonData = json.toJSONString();
			Cache.put(cacheKey, jsonData);
			response.setHeader("realtime-cache", "false");
		}
		else
		{
			response.setHeader("realtime-cache", "true");
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonData);
	}
}
