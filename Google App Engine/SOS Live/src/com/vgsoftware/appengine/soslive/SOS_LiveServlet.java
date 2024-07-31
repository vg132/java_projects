package com.vgsoftware.appengine.soslive;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

@SuppressWarnings("serial")
public class SOS_LiveServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		response.setContentType("text/xml; charset=ISO-8859-1");
		response.setCharacterEncoding("ISO-8859-1");
		String xml = Cache.get("Xml");
		if (xml == null)
		{
			List<Event> events = downloadEvents();
			response.setHeader("sos-cache","false");
			
			xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<r>";
			for (Event e : events)
			{
				xml += "<e>";
				xml+="<h><![CDATA[" + e.getHeading() + "]]></h>";
				xml+="<lo>" + e.getLongitude() + "</lo>";
				xml+="<la>" + e.getLatitude() + "</la>";
				xml+="<t><![CDATA[" + e.getTime() + "]]></t>";
				xml+="<c><![CDATA[" + e.getCallCenter() + "]]></c>";
				xml+="<l><![CDATA[" + e.getLocation() + "]]></l>";
				xml+="<i><![CDATA[" + e.getIssue() + "]]></i>";
				xml+="<p><![CDATA[" + e.getPriority() + "]]></p>";
				xml+="<co><![CDATA[" + e.getCounty() + "]]></co>";
				xml+="<li><![CDATA[" + e.getLink() + "]]></li>";
				xml+="</e>";
			}
			xml += "</r>";
			Cache.put("Xml", xml);
		}
		else
		{
			response.setHeader("sos-cache","true");
		}
		response.getWriter().println(xml);
	}

	private List<Event> downloadEvents()
	{
		List<Event> _events = new ArrayList<Event>();
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		try
		{
			URL url = new URL("http://div.dn.se/dn/sos/soslive.php");
			HTTPRequest request = new HTTPRequest(url);

			request.addHeader(new HTTPHeader("Referer", "http://div.dn.se/dn/sos/soslive.php"));

			HTTPResponse response = fetcher.fetch(request);
			String content = new String(response.getContent(), Charset.forName("ISO-8859-1"));

			content = content.substring(content.indexOf("// Read the data from example.xml") + 33).trim();
			content = content.substring(0, content.indexOf("// ========= If a parameter was passed, open the info window ==========")).trim();
			content = content.replaceAll("var ", "\nvar ");
			content = content.replaceAll("\t", "");
			content = content.replaceAll("\n\n", "\n");
			content = content.replace("var ikon =", "\nvar ikon =").trim();
			String[] contents = content.split("\n\n");
			for (int i = 0; i < contents.length; i++)
			{
				_events.add(new Event(contents[i].split("\n")));
			}
		}
		catch (IOException e)
		{
		}
		return _events;
	}
}
