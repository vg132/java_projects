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
import java.io.IOException;
import java.io.StringWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mixer.Mixer;

/**
 * RSS service with xslt file.
 */
public class RSS2 extends HttpServlet
{
	private String htmlString=null;
	
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
			String channels=getCookieString(request);
			if(channels==null)
			{
				html.removeContext("<!!--rss-->");
			}
			else
			{
				try
				{
					TransformerFactory tff = TransformerFactory.newInstance();
					Transformer trans;
					StreamSource xslSource = new StreamSource(getServletContext().getRealPath("/data/rss.xslt"));
					trans = tff.newTransformer(xslSource);

					StringTokenizer st=new StringTokenizer(channels,"|");
					while(st.hasMoreTokens())
					{
						String url=st.nextToken();
						StringWriter rss=new StringWriter();
						StreamSource xmlSource = new StreamSource(url);
						StreamResult output = new StreamResult(rss);
						trans.transform(xmlSource,output);
						Mixer mix=new Mixer(rss.getBuffer().toString());
						mix.add("<!--channel_url-->",url);
						html.add("<!!--rss-->","<!--data-->",mix.getMix());
					}
				}
				catch(TransformerConfigurationException tce)
				{
					tce.printStackTrace(System.err);
				}
				catch(TransformerException te)
				{
					te.printStackTrace(System.err);
				}
			}
			response.setContentType("text/html");
			response.getOutputStream().println(html.getMix());
		}
	}
}