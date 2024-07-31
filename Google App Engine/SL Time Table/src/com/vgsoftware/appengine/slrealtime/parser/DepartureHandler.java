package com.vgsoftware.appengine.slrealtime.parser;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import com.vgsoftware.appengine.slrealtime.data.Departure;

public class DepartureHandler extends DefaultHandler
{
	private static final String BASE_URL = "https://api.trafiklab.se/sl/realtid/GetDpsDepartures.XML?key=&siteId=";
	private final int DESTINATION = 1;
	private final int TIMETABLEDATETIME = 2;

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

	private List<Departure> _departures = null;
	private Departure _departure = null;

	private int _currentState = 0;
	private int _siteId = 0;

	public static List<Departure> getDepartures(int siteId)
	{
		try
		{
			URL url = new URL(DepartureHandler.BASE_URL + siteId);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			XMLReader xmlReader = parser.getXMLReader();
			DepartureHandler rssHandler = new DepartureHandler(siteId);
			xmlReader.setContentHandler(rssHandler);
			InputSource feedStream = new InputSource(url.openStream());
			xmlReader.parse(feedStream);
			return rssHandler.getDepartures();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/*
	 * Constructor
	 */
	private DepartureHandler(int siteId)
	{
		_siteId = siteId;
	}

	/*
	 * getFeed - this returns our feed when all of the parsing is complete
	 */
	public List<Departure> getDepartures()
	{
		return _departures;
	}

	public void startDocument()
		throws SAXException
	{
		_departures = new ArrayList<Departure>();
	}

	public void endDocument()
		throws SAXException
	{
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
		throws SAXException
	{
		_currentState = 0;
		if (qName.equals("DpsTrain"))
		{
			_departure = new Departure();
			_departure.setTransportationTypeId(1);
			_departure.setSiteId(_siteId);
			_departure.setDayOfWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		}
		else if (_departure != null && qName.equals("Destination"))
		{
			_currentState = DESTINATION;
		}
		else if (_departure != null && qName.equals("TimeTabledDateTime"))
		{
			_currentState = TIMETABLEDATETIME;
		}
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException
	{
		if (qName.equals("DpsTrain"))
		{
			_departures.add(_departure);
			_departure = null;
			return;
		}
	}

	public void characters(char ch[], int start, int length)
	{
		String theString = new String(ch, start, length);
		switch (_currentState)
		{
		case DESTINATION:
			_departure.setDestination(theString);
			_currentState = 0;
			break;
		case TIMETABLEDATETIME:
			try
			{
				_departure.setTime(formatter.parse(theString));
			}
			catch (Exception ex)
			{
			}
			_currentState = 0;
			break;
		}
	}
}
