/*
 * Created on 2004-nov-29 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.vgutil.misc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieHelper
{
	private HttpServletRequest request=null;
	private HttpServletResponse response=null;
	
	public CookieHelper(HttpServletRequest request, HttpServletResponse response)
	{
		this.request=request;
		this.response=response;
	}

	public void createCookie(String name,String path, int maxAge, String value)
	{
		Cookie c=new Cookie(name,value);
		c.setPath(path);
		c.setMaxAge(maxAge);
		response.addCookie(c);
	}

	public boolean removeCookie(String name)
	{
		return(removeCookie(getCookie(name)));
	}

	public boolean removeCookie(Cookie cookie)
	{
		if(cookie!=null)
		{
			Cookie c=new Cookie(cookie.getName(),null);
			c.setPath(cookie.getPath());
			c.setMaxAge(0);
			response.addCookie(c);
			return(true);
		}
		return(false);
	}

	public Cookie getCookie(String name)
	{
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			for(int i=0;i<cookies.length;i++)
			{
				if(cookies[i].getName().equals(name))
				{
					return(cookies[i]);
				}
			}
		}
		return(null);
	}
}
