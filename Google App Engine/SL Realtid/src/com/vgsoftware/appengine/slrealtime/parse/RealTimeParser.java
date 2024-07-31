package com.vgsoftware.appengine.slrealtime.parse;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.vgsoftware.appengine.slrealtime.DateParser;
import com.vgsoftware.appengine.slrealtime.XMLUtility;
import com.vgsoftware.appengine.slrealtime.dataabstraction.Departure;
import com.vgsoftware.appengine.slrealtime.dataabstraction.Station;

public class RealTimeParser
{
	private static final String REQUEST_URL = "https://api.trafiklab.se/sl/realtid/GetDpsDepartures.XML?key=&siteId=";
	private Station _station = null;

	public RealTimeParser(Station station)
	{
		_station = station;
	}

	public List<Departure> parse()
	{
		try
		{
			SLResponse response = null;
			List<Departure> data = new ArrayList<Departure>();
			if (_station.getTransportationTypeId() == 3)
			{
				response = new MetroParser().parse(_station.getSiteId());
				data = response.getMetro();
			}
			else
			{
				response = parse(_station.getSiteId());
				switch (_station.getTransportationTypeId())
				{
				case 1:
					data = response.getTrain();
					break;
				case 2:
				case 4:
					data = response.getTram();
					break;
				case 3:
					data = response.getMetro();
					break;
				case 5:
					data = response.getBus();
					break;
				}
			}
			return data;
		}
		catch (Exception ex)
		{
		}
		return null;
	}

	private SLResponse parse(int siteId)
	{
		Element element = XMLUtility.getRootElement(RealTimeParser.REQUEST_URL + siteId);
		if (element != null)
		{
			return parse(element);
		}
		return null;
	}

	private SLResponse parse(Element root)
	{
		// Buses
		// Metros
		// Trains
		// Trams
		SLResponse response = new SLResponse();
		NodeList nodeList = root.getElementsByTagName("Buses");
		if (nodeList != null && nodeList.getLength() > 0)
		{
			response.setBus(parseDepartures((Element) nodeList.item(0)));
		}
		else
		{
			response.setBus(new ArrayList<Departure>());
		}
		nodeList = root.getElementsByTagName("Metros");
		if (nodeList != null && nodeList.getLength() > 0)
		{
			response.setMetro(parseDepartures((Element) nodeList.item(0)));
		}
		else
		{
			response.setMetro(new ArrayList<Departure>());
		}
		nodeList = root.getElementsByTagName("Trains");
		if (nodeList != null && nodeList.getLength() > 0)
		{
			response.setTrain(parseDepartures((Element) nodeList.item(0)));
		}
		else
		{
			response.setTrain(new ArrayList<Departure>());
		}
		nodeList = root.getElementsByTagName("Trams");
		if (nodeList != null && nodeList.getLength() > 0)
		{
			response.setTram(parseDepartures((Element) nodeList.item(0)));
		}
		else
		{
			response.setTram(new ArrayList<Departure>());
		}
		return response;
	}

	private ArrayList<Departure> parseDepartures(Element element)
	{
		ArrayList<Departure> departures = new ArrayList<Departure>();

		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			if (nodes.item(i) instanceof Element)
			{
				Departure departure = new Departure();
				departure.setDestination(XMLUtility.getValue((Element) nodes.item(i), "Destination"));
				departure.setTimeTabledDateTime(DateParser.parse(XMLUtility.getValue((Element) nodes.item(i), "TimeTabledDateTime")));
				departure.setExpectedDateTime(DateParser.parse(XMLUtility.getValue((Element) nodes.item(i), "ExpectedDateTime")));
				departure.setLine(XMLUtility.getValueInt((Element) nodes.item(i), "LineNumber"));
				departure.setStopAreaName(XMLUtility.getValue((Element) nodes.item(i), "StopAreaName"));
				departure.setDisplayTime(XMLUtility.getValue((Element) nodes.item(i), "DisplayTime"));
				departure.setStopAreaNumber(XMLUtility.getValueInt((Element) nodes.item(i), "StopAreaNumber"));
				departure.setDirection(XMLUtility.getValueInt((Element) nodes.item(i), "JourneyDirection"));
				departures.add(departure);
			}
		}
		return departures;
	}

	class MetroParser
	{
		private static final String REQUEST_URL = "https://api.trafiklab.se/sl/realtid/GetDepartures.xml?key=&siteId=";

		public SLResponse parse(int siteId)
		{
			Element element = XMLUtility.getRootElement(MetroParser.REQUEST_URL + siteId);
			if (element != null)
			{
				return parse(element);
			}
			return null;
		}

		public SLResponse parse(Element root)
		{
			SLResponse response = new SLResponse();
			NodeList nodeList = root.getElementsByTagName("Metros");
			if (nodeList != null && nodeList.getLength() > 0)
			{
				response.setMetro(parseDepartures((Element) nodeList.item(0)));
			}
			else
			{
				response.setMetro(new ArrayList<Departure>());
			}
			return response;
		}

		private ArrayList<Departure> parseDepartures(Element element)
		{
			ArrayList<Departure> departures = new ArrayList<Departure>();
			NodeList nodes = element.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (nodes.item(i) instanceof Element)
				{
					Departure departure = new Departure();
					String row1 = XMLUtility.getValue((Element) nodes.item(i), "DisplayRow1");
					if (row1 != null && !row1.trim().equals(""))
					{
						departure.setDestination(fixDeparture(XMLUtility.getValue((Element) nodes.item(i), "DisplayRow1")));
						if (departure.getDestination() != null && !departure.getDestination().trim().equals("") && shouldAdd(departures, departure))
						{
							departure.setLine(getLine(departure.getDestination()));
							departures.add(departure);
						}
					}
					String row2 = XMLUtility.getValue((Element) nodes.item(i), "DisplayRow2");
					if (row2 != null && row2.trim() != "")
					{
						row2 = row2.trim().replace("min.", "min,");
						String[] rows = row2.split(",");
						for (String row : rows)
						{
							if (row.trim() != "")
							{
								departure = new Departure();
								departure.setDestination(fixDeparture(row.trim()));
								departure.setLine(getLine(row));
								if (departure.getDestination() != null && !departure.getDestination().trim().equals("") && shouldAdd(departures, departure))
								{
									departures.add(departure);
								}
							}
						}
					}
				}
			}

			return departures;
		}

		private boolean shouldAdd(ArrayList<Departure> departures, Departure newDeparture)
		{
			for (Departure departure : departures)
			{
				if (departure.getDestination().equals(newDeparture.getDestination()))
				{
					return false;
				}
			}
			return true;
		}

		private String fixDeparture(String departure)
		{
			departure = departure.trim();
			departure = departure.replace("min.", "min");
			for (int i = 0; i < 10; i++)
			{
				departure = departure.replace("  ", " ");
			}
			return departure;
		}

		private int getLine(String row)
		{
			if (row != null && row.trim().length() > 2)
			{
				try
				{
					return Integer.parseInt(row.trim().substring(0, 2));
				}
				catch (NumberFormatException ex)
				{
				}
			}
			return 0;
		}
	}
}
