/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass12;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import mixer.Mixer;

/**
 * RSS service.
 */
public class RSS extends HttpServlet
{
	private String htmlString=null;
	private String rssString=null;
	
	/**
	 * Load HTML templates.
	 * 
	 * @param config
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("htmltemplate")));
		rssString=Mixer.getContent(new File(config.getInitParameter("rsstemplate")));
		super.init(config);
	}

	/**
	 * Returns the cookie content.
	 * 
	 * @param request The users request object.
	 * 
	 * @return Cookie content, null if no cookie was found.
	 */
	private String getCookieString(HttpServletRequest request)
	{
		Cookie[] cookies=request.getCookies();
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("rss"))
			{
				return(cookies[i].getValue());
			}
		}
		return(null);
	}
	
	/**
	 * Check what the user want to do (add or remove channel) and then do it.
	 * If the user removes his last channel, remove cookie.
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		response.setContentType("text/html");
		String whattodo=request.getParameter("whattodo");
		String channelURL=request.getParameter("channel_url");
		String channels=getCookieString(request);

		if((channels!=null)&&(whattodo.equals("remove")))
		{
			StringTokenizer st=new StringTokenizer(channels,"|");
			String tmp="";
			while(st.hasMoreTokens())
			{
				String token=st.nextToken();
				if(!token.equals(channelURL))
					tmp+="|"+token;
			}
			Cookie c=new Cookie("rss",tmp);
			if(tmp.equals(""))
			{
				c.setMaxAge(0);
			}
			else
			{
				System.out.println("Innan: "+tmp);
				if(tmp.charAt(0)=='|')
					tmp=tmp.substring(1);
				if(tmp.charAt(tmp.length()-1)=='|')
					tmp=tmp.substring(0,tmp.length()-1);
				c.setMaxAge(31536000);	//1 year
				System.out.println("Efter: "+tmp);
			}
			response.addCookie(c);
		}
		else if(whattodo.equals("add"))
		{
			if(channels==null)
				channels=channelURL;
			else
				channels+="|"+channelURL;
			Cookie c=new Cookie("rss",channels);
			c.setMaxAge(31536000);	//1 year
			response.addCookie(c);
		}
		response.sendRedirect("/dsv/servlet/ass12");
	}
	
	/**
	 * Create the html document.
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		if(request.getParameter("whattodo")!=null)
		{
			doPost(request,response);
		}
		else
		{
			Mixer html=new Mixer(htmlString);
			Mixer rss=new Mixer(rssString);
			try
			{
				String channels=getCookieString(request);
				if(channels==null)
				{
					html.removeContext("<!!--rss-->");
				}
				else
				{
					StringTokenizer st=new StringTokenizer(channels,"|");
					SAXParser parser=SAXParserFactory.newInstance().newSAXParser();
					while(st.hasMoreTokens())
					{
						String url=st.nextToken();
						try
						{
							parser.parse(url,new RSSContentHandler(rss));
						}
						catch(FileNotFoundException fnfe)
						{
							fnfe.printStackTrace(System.err);
							rss.add("<!!--header-->","<!--filenotfound-->","RSS File Not Found.");
						}
						rss.add("<!!--header-->","<!--channel_url-->",url);
						rss.add("<!--title_color-->","#afc4d7");
						rss.add("<!--desc_color-->","#f1f1f1");
						html.add("<!!--rss-->","<!--data-->",rss.getMix());
						rss.clearAll();
					}
				}
				response.setContentType("text/html");
				response.getOutputStream().println(html.getMix());
			}
			catch(ParserConfigurationException pce)
			{
				pce.printStackTrace(System.err);
			}
			catch(FactoryConfigurationError fce)
			{
				fce.printStackTrace(System.err);
			}
			catch(SAXException se)
			{
				se.printStackTrace(System.err);
			}
		}
	}
}

/**
 * SAX Handler class.
 */
class RSSContentHandler extends DefaultHandler
{
	private Mixer mix=null;
	private String data=null;
	private Stack elementStack=new Stack();

	/**
	 * Set the html template mixer to use for this file.
	 */
	public RSSContentHandler(Mixer mix)
	{
		this.mix=mix;
	}

	/**
	 * Reset character data and add new element to stack.
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName Element name.
	 * @param atts Element attributes.
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
	throws SAXException
	{
		data="";
		elementStack.push(qName);
	}

	/**
	 * Add characters to data string.
	 * 
	 * @param ch Character array.
	 * @param start Indext of the first character in the array.
	 * @param length The length of the array.
	 * 
	 * @throws SAXException
	 */
	public void characters(char ch[], int start, int length)
	throws SAXException
	{
		data+=new String(ch,start,length);
	}

	/**
	 * Check if we are in the right position of the xml file, if we are
	 * check if we need this data, if we need it insert it into the html template.
	 * Remove element from stack.
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName Element name
	 * 
	 * @throws SAXException
	 */
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		data=data.trim();
		if((elementStack.size()>1)&&(elementStack.get(elementStack.size()-2).equals("item")))
		{
			if(qName.equals("link"))
				mix.add("<!!--item-->","<!--link-->",data);
			else if(qName.equals("title"))
				mix.add("<!!--item-->","<!--title-->",data);
			else if(qName.equals("pubDate"))
				mix.add("<!!--item-->","<!--pubDate-->",data);
			else if(qName.equals("description"))
				mix.add("<!!--item-->","<!--description-->",data);
		}
		else if((elementStack.size()>1)&&(elementStack.get(elementStack.size()-2).equals("channel")))
		{
			if(qName.equals("title"))
				mix.add("<!!--header-->","<!--channel-->",data);
			else if(qName.equals("link"))
				mix.add("<!!--header-->","<!--channel_link-->",data);
		}
		elementStack.pop();
	}
}