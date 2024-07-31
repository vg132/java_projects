/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass1;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mixer.Mixer;

/**
 * Prints out server and client information in html using html templates.
 */
public class HTMLInfo extends HttpServlet
{
	private String htmlString=null;
	private String blockString=null;
	private String color1="#f1f1f1";
	private String color2="#afc4d7";
	private boolean curColor=false;
	
	/**
	 * Load HTML templates.
	 * 
	 * @param config this servlet config object.
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("htmltemplate")));
		blockString=Mixer.getContent(new File(config.getInitParameter("blocktemplate")));
		super.init(config);
	}
	
	/**
	 * Prints out server and request information to the client in a nice
	 * html formated way.
	 * 
	 * @param request Users request object.
	 * @param response Users response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Enumeration e=null;
		ServletContext sc=getServletContext();
		ServletOutputStream out=response.getOutputStream();
		response.setContentType("text/html");
		
		Mixer block=new Mixer(blockString);
		Mixer html=new Mixer(htmlString);

		setBlockHeader(block,"Servlet Info");
		setBlockData(block,"Servlet Name",getServletName(),(curColor=!curColor)?color1:color2);
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());
		
		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Servlet Parameters");
		e=getServletConfig().getInitParameterNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			setBlockData(block,name,getServletConfig().getInitParameter(name),(curColor=!curColor)?color1:color2);
		}
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Context Parameters");
		e=sc.getInitParameterNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			setBlockData(block,name,sc.getInitParameter(name),(curColor=!curColor)?color1:color2);
		}
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Servlet Information");
		setBlockData(block,"Server name",request.getServerName(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Server port",Integer.toString(request.getServerPort()),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Server info",sc.getServerInfo(),(curColor=!curColor)?color1:color2);		
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());
		
		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Server Attributes");
		e=sc.getAttributeNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			setBlockData(block,name,sc.getAttribute(name).toString(),(curColor=!curColor)?color1:color2);
		}		
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Runtime Version");
		setBlockData(block,"Major webserver version",Integer.toString(sc.getMajorVersion()),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Minor webserver version",Integer.toString(sc.getMinorVersion()),(curColor=!curColor)?color1:color2);
		setBlockData(block,"JDK version",System.getProperty("java.version"),(curColor=!curColor)?color1:color2);

		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Client Information");
		setBlockData(block,"Remote address",request.getRemoteAddr(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Remote host",request.getRemoteHost(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Remote user",request.getRemoteUser(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Authentication type",request.getAuthType(),(curColor=!curColor)?color1:color2);
		html.add("<!!--block-->", "<!--block_data-->");

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Headers");	
		e=request.getHeaderNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			setBlockData(block,name,request.getHeader(name),(curColor=!curColor)?color1:color2);
		}
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Request Parameters");
		setBlockData(block,"Query string",request.getQueryString(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Character encoding",request.getCharacterEncoding(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Content length",Integer.toString(request.getContentLength()),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Content type",request.getContentType(),(curColor=!curColor)?color1:color2);
		html.add("<!!--block-->", "<!--block_data-->");

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Request Parameters: One by One");
		e=request.getParameterNames();
		while (e.hasMoreElements())
		{
			 String name=(String)e.nextElement();
			 String[] values=request.getParameterValues(name);
			 for(int i=0;i<values.length;i++)
			setBlockData(block,name,values[i],(curColor=!curColor)?color1:color2);
		}
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Cookies: One by One");
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			for (int i=0;i<cookies.length;i++)
			{
				Cookie cookie = cookies[i];
				setBlockData(block,cookie.getName(),cookie.getValue(),(curColor=!curColor)?color1:color2);
			}
		}
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Path and Connection Information");
		setBlockData(block,"Path info",request.getPathInfo(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Translated path",request.getPathTranslated(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Request URI",request.getRequestURI(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Request URL",request.getRequestURL().toString(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Request scheme",request.getScheme(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Context path",request.getContextPath(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Servlet path",request.getServletPath(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Request protocol",request.getProtocol(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Request method",request.getMethod(),(curColor=!curColor)?color1:color2);
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Request Attributes");
		e=request.getAttributeNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			setBlockData(block,name,request.getAttribute(name).toString(),(curColor=!curColor)?color1:color2);
		}
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Session Information");
		setBlockData(block,"Requested session ID",request.getRequestedSessionId(),(curColor=!curColor)?color1:color2);
		HttpSession session=request.getSession(true);
		setBlockData(block,"Session creation time",Long.toString(session.getCreationTime()),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Session ID",session.getId(),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Session last accessed",Long.toString(session.getLastAccessedTime()),(curColor=!curColor)?color1:color2);
		setBlockData(block,"Session max inactive interval",Integer.toString(session.getMaxInactiveInterval()),(curColor=!curColor)?color1:color2);
		html.add("<!!--block-->", "<!--block_data-->",block.getMix());

		block.clearAll();
		curColor=false;
		setBlockHeader(block,"Session Values");
		e=session.getAttributeNames();
		while (e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			setBlockData(block,name,request.getAttribute(name).toString(),(curColor=!curColor)?color1:color2);
		}
		out.print(html.getMix());
	}
	
	/**
	 * Replace the header tag with a header name.
	 * 
	 * @param mix The Mixer to use.
	 * @param header The header to insert into the Mixer text.
	 */
	private void setBlockHeader(Mixer mix,String header)
	{
		mix.add("<!!--info_block-->", "<!--header-->", header);
	}
	
	/**
	 * Replace a name/value block with real names and values. Also set a color to the block.
	 * 
	 * @param mix The Mixer to use.
	 * @param name The name of this block.
	 * @param value The value for this block.
	 * @param color The color for this block. 
	 */
	private void setBlockData(Mixer mix, String name, String value, String color)
	{
		mix.add("<!!--info-->","<!--name-->",name);
		mix.add("<!!--info-->","<!--value-->",value);
		mix.add("<!!--info-->","<!--color-->",color);
	}
}