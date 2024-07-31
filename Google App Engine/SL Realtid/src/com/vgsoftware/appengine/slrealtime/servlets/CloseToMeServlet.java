package com.vgsoftware.appengine.slrealtime.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.vgsoftware.appengine.slrealtime.dataabstraction.Station;

public class CloseToMeServlet extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		JSONArray json = new JSONArray();
		try
		{
			double latitude = Double.parseDouble(request.getParameter("lat"));
			double longitude = Double.parseDouble(request.getParameter("lng"));
			for (Station station : Station.list())
			{
				double distance = getDistance(latitude, longitude, station.getLatitude(), station.getLongitude());
				if (distance < 3)
				{
					json.add(station.toJSONString());
				}
			}
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(json.toJSONString());
		}
		catch (NumberFormatException ex)
		{
		}
	}

	private static double getDistance(double latitude1, double longitude1, double latitude2, double longitude2)
	{
		double earthRadius = 6378.7;
		double distanceLatitude = Math.toRadians(latitude2 - latitude1);
		double distanceLongitude = Math.toRadians(longitude2 - longitude1);
		double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2) + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}
}
