package com.vgsoftware.appengine.slrealtime.dataabstraction;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class Departure
{
	private String _destination;
	private Date _timeTabledDateTime;
	private Date _expectedDateTime;
	private int _line;
	private String _stopAreaName;
	private String _displayTime;
	private int _stopAreaNumber;
	private int _direction;
	private final SimpleDateFormat _timeTableSimpleDateFormat = new SimpleDateFormat("HH:mm");
	private final SimpleDateFormat _delaySimpleDateFormat = new SimpleDateFormat("HH:mm:ss");

	public String getDestination()
	{
		return _destination;
	}

	public void setDestination(String destination)
	{
		_destination = destination;
	}

	public Date getTimeTabledDateTime()
	{
		return _timeTabledDateTime;
	}

	public void setTimeTabledDateTime(Date time)
	{
		_timeTabledDateTime = time;
	}

	public Date getExpectedDateTime()
	{
		return _expectedDateTime;
	}

	public void setExpectedDateTime(Date expectedTime)
	{
		_expectedDateTime = expectedTime;
	}

	public int getLine()
	{
		return _line;
	}

	public void setLine(int line)
	{
		_line = line;
	}

	public void setStopAreaName(String stopAreaName)
	{
		_stopAreaName = stopAreaName;
	}

	public String getStopAreaName()
	{
		return _stopAreaName;
	}

	public void setDisplayTime(String displayTime)
	{
		_displayTime = displayTime;
	}

	public String getDisplayTime()
	{
		return _displayTime;
	}

	public int getStopAreaNumber()
	{
		return _stopAreaNumber;
	}

	public void setStopAreaNumber(int stopAreaNumber)
	{
		_stopAreaNumber = stopAreaNumber;
	}

	public int getDirection()
	{
		return _direction;
	}

	public void setDirection(int direction)
	{
		_direction = direction;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONString()
	{
		JSONObject json = new JSONObject();

		json.put("name",getStopAreaName());
		json.put("number",getStopAreaNumber());
		json.put("direction",getDirection());
		json.put("line",getLine());
		if(getTimeTabledDateTime()!=null)
		{
			json.put("timeTableTime", _timeTableSimpleDateFormat.format(getTimeTabledDateTime()));
		}
		else
		{
			json.put("timeTableTime","");
		}
		json.put("destination",getDestination());
		json.put("displayTime",getDisplayTime());
		if(getExpectedDateTime()!=null)
		{
			json.put("expectedTime",_delaySimpleDateFormat.format(getExpectedDateTime()));
		}
		else
		{
			json.put("expectedTime","");
		}

		return json;
	}
}
