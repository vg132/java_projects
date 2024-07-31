package com.vgsoftware.appengine.soslive;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Event implements Serializable
{
	private double _latitude = 0.0;
	private double _longitude = 0.0;
	private String _content = null;
	private String _heading = null;
	private String _time = null;
	private String _issue = null;
	private String _location = null;
	private String _priority = null;
	private String _callCenter = null;
	private String _link = null;
	private String _county = null;

	public Event(String[] parts)
	{
		for (String part : parts)
		{
			if (part.startsWith("var lat"))
			{
				_latitude = Double.parseDouble(part.substring(part.indexOf('"') + 1, part.lastIndexOf('"')));
			}
			else if (part.startsWith("var lng"))
			{
				_longitude = Double.parseDouble(part.substring(part.indexOf('"') + 1, part.lastIndexOf('"')));
			}
			else if (part.startsWith("var html"))
			{
				_content = part.substring(part.indexOf('"') + 1, part.lastIndexOf('"')).replace("\\\"", "\"").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ").replaceAll("  ", " ");
				_heading = _content.substring(_content.indexOf(">", _content.indexOf("<h1")) + 1);
				_heading = _heading.substring(0, _heading.indexOf("</h1>"));

				_time = _content.substring(_content.indexOf("<b>Tid:</b>") + 11);
				_time = _time.substring(0, _time.indexOf("<br"));

				_issue = _content.substring(_content.indexOf("<b>Händelse:</b>") + "<b>Händelse:</b>".length());
				_issue = _issue.substring(0, _issue.indexOf("<br"));

				_location = _content.substring(_content.indexOf("<b>Plats:</b>") + "<b>Plats:</b>".length());
				_location = _location.substring(0, _location.indexOf("(Kommun"));

				_county = _content.substring(_content.indexOf("(Kommun:") + "(Kommun:".length());
				_county = _county.substring(0, _county.indexOf(")"));

				_callCenter = _content.substring(_content.indexOf("<b>SOS-larmcentral:</b>") + "<b>SOS-larmcentral:</b>".length());
				_callCenter = _callCenter.substring(0, _callCenter.indexOf("<br"));

				_priority = _content.substring(_content.indexOf("<b>Uttryckningsprioritet:</b>") + "<b>Uttryckningsprioritet:</b>".length());
				_priority = _priority.substring(0, _priority.indexOf("<br"));

				_link = _content.substring(_content.indexOf("href=") + "href=".length());
				_link = _link.substring(0, _link.indexOf(" "));
			}
		}
	}

	public String getContent()
	{
		return _content != null ? _content.trim() : "";
	}

	public double getLongitude()
	{
		return _longitude;
	}

	public double getLatitude()
	{
		return _latitude;
	}

	public String getHeading()
	{
		return _heading != null ? _heading.trim() : "";
	}

	public String getTime()
	{
		return _time != null ? _time.trim() : "";
	}

	public String getIssue()
	{
		return _issue != null ? _issue.trim() : "";
	}

	public String getLocation()
	{
		return _location != null ? _location.trim() : "";
	}

	public String getPriority()
	{
		return _priority != null ? _priority.trim() : "";
	}

	public String getCallCenter()
	{
		return _callCenter != null ? _callCenter.trim() : "";
	}

	public String getLink()
	{
		return _link != null ? _link.trim() : "";
	}

	public String getCounty()
	{
		return _county != null ? _county.trim() : "";
	}

	public int getCacheKey()
	{
		return getContent().hashCode();
	}
}
