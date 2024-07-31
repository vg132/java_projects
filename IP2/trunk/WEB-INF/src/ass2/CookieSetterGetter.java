/*
 * Created on: 2003-okt-21
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-21 Created by Viktor
 */
package ass2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mixer.Mixer;

/**
 * Adds a cookie to the client and then displays a list of all cookies set by
 * this server on the clients computer.
 */
public class CookieSetterGetter extends HttpServlet
{
	private String p2=null;
	private String p3=null;
	
	/**
	 * Load the html templates.
	 * 
	 * @param config
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		p2=Mixer.getContent(new File(config.getInitParameter("p2template")));
		p3=Mixer.getContent(new File(config.getInitParameter("p3template")));
		super.init(config);
	}
	
	/**
	 * Set a cookie and show the cookies.
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
		Mixer mix=new Mixer(p2);
		
		String name=request.getParameter("name");
		Cookie c=new Cookie("name",name);
		response.addCookie(c);
		mix.add("<!!--cookie-data-->","<!--name-->",c.getName());
		mix.add("<!!--cookie-data-->","<!--value-->",c.getValue());
		c=new Cookie("date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		response.addCookie(c);
		mix.add("<!!--cookie-data-->","<!--name-->",c.getName());
		mix.add("<!!--cookie-data-->","<!--value-->",c.getValue());
		setCookieData(mix,request.getCookies());
		response.getOutputStream().println(mix.getMix());
	}

	/**
	 * Show all cookies on this client set by this server.
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
		Mixer mix=new Mixer(p3);
		setCookieData(mix,request.getCookies());
		response.getOutputStream().println(mix.getMix());
	}

	/**
	 * Create the html response using the cookie array and html template.
	 * 
	 * @param mix The html template.
	 * @param cookies The cookie array.
	 */
	private void setCookieData(Mixer mix, Cookie[] cookies)
	{
		if(cookies!=null)
		{
			for (int i=0;i<cookies.length;i++)
			{
				Cookie cookie = cookies[i];
				mix.add("<!!--cookie-data-->","<!--name-->",cookie.getName());
				mix.add("<!!--cookie-data-->","<!--value-->",cookie.getValue());
			}
		}
	}
}
