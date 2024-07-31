/*
 * Created on: 2003-okt-20
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-20 Created by Viktor
 */
package ass1;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Prints out server and client information in plain text.
 */
public class PlainInfo extends HttpServlet
{
	/**
	 * Prints out server and request information to the client.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Enumeration e=null;
		ServletContext sc=getServletContext();
		ServletOutputStream out=response.getOutputStream();
		response.setContentType("text/plain");
		
		out.println("SERVER INFORMATION\n");
		out.println("\n---Servlet Info---\n");
		out.println("Servlet Name: "+getServletName());
		out.println("\n---Servlet Parameters---\n");
		e=getServletConfig().getInitParameterNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println(name+": "+getServletConfig().getInitParameter(name));
		}
		out.println("\n---Context Parameters---\n");
		e=sc.getInitParameterNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println(name+": "+sc.getInitParameter(name));
		}		
		out.println("\n---Servlet Information---\n");
		out.println("Server name: "+request.getServerName());
		out.println("Server port: "+Integer.toString(request.getServerPort()));
		out.println("Server info: "+sc.getServerInfo());		
		
		out.println("\n---Server Attributes---\n");
		e=sc.getAttributeNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println(name+": "+sc.getAttribute(name));
		}		

		out.println("\n---Runtime Version---\n");
		out.println("Major webserver version: "+Integer.toString(sc.getMajorVersion()));
		out.println("Minor webserver version: "+Integer.toString(sc.getMinorVersion()));
		out.println("JDK version: "+System.getProperty("java.version"));

		out.println("\n\nCLIENT INFORMATION\n");

		out.println("\n---Client Information---\n");
		out.println("Remote address: "+request.getRemoteAddr());
		out.println("Remote host: "+request.getRemoteHost());
		out.println("Remote user: "+request.getRemoteUser());
		out.println("Authentication type: "+request.getAuthType());
	
		out.println("\n---Headers---\n");	
		e=request.getHeaderNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println(name+": "+request.getHeader(name));
		}

		out.println("\n---Request Parameters---\n");
		out.println("Query string: "+request.getQueryString());
		out.println("Character encoding: "+request.getCharacterEncoding());
		out.println("Content length: "+Integer.toString(request.getContentLength()));
		out.println("Content type: "+request.getContentType());

		out.println("\n---Request Parameters: One by One---\n");
		e=request.getParameterNames();
		while (e.hasMoreElements())
		{
			 String name=(String)e.nextElement();
			 String[] values=request.getParameterValues(name);
			 for(int i=0;i<values.length;i++)
				out.println(name+": "+values[i]);
		}

		out.println("\n---Cookies: One by One---\n");
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			for (int i=0;i<cookies.length;i++)
			{
				Cookie cookie = cookies[i];
				out.println(cookie.getName()+": "+cookie.getValue());
			}
		}

		out.println("\n---Path and Connection Information---\n");
		out.println("Path info: "+request.getPathInfo());
		out.println("Translated path: "+request.getPathTranslated());
		out.println("Request URI: "+request.getRequestURI());
		out.println("Request URL: "+request.getRequestURL().toString());
		out.println("Request scheme: "+request.getScheme());
		out.println("Context path: "+request.getContextPath());
		out.println("Servlet path: "+request.getServletPath());
		out.println("Request protocol: "+request.getProtocol());
		out.println("Request method: "+request.getMethod());
		
		out.println("\n---Request Attributes---\n");
		e=request.getAttributeNames();
		while(e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println(name+": "+request.getAttribute(name).toString());
		}

		out.println("\n---Session Information---\n");
		out.println("Requested session ID: "+request.getRequestedSessionId());
		HttpSession session=request.getSession(true);
		out.println("Session creation time: "+Long.toString(session.getCreationTime()));
		out.println("Session ID: "+session.getId());
		out.println("Session last accessed: "+Long.toString(session.getLastAccessedTime()));
		out.println("Session max inactive interval: "+Integer.toString(session.getMaxInactiveInterval()));
		
		out.println("\n---Session Values---\n");
		e=session.getAttributeNames();
		while (e.hasMoreElements())
		{
			String name=(String)e.nextElement();
			out.println(name+": "+request.getAttribute(name).toString());
		}
	}
}
