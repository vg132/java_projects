/*
 * Created on 2004-okt-13 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 2005-sep-27 - Updated to new user model.
 * 
 */
package com.vgsoftware.web.helper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.vgutil.misc.CookieHelper;
import com.vgsoftware.web.data.member.MemberData;

public class AccessFilter implements Filter
{
	private List accessList=null;
	private Iterator iter=null;
	
	/**
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy()
	{
	}

	/**
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException
	{
		HttpServletRequest req=(HttpServletRequest)request;
		MemberData user=null;
		if(req.getSession().getAttribute(Constants.USER_KEY)!=null)
			user=(MemberData)req.getSession().getAttribute(Constants.USER_KEY);
		else if(req.getSession().getAttribute(Constants.GUEST_KEY)!=null)
			user=(MemberData)req.getSession().getAttribute(Constants.GUEST_KEY);
		if((user==null)&&(req.getSession().getAttribute(Constants.GUEST_KEY)==null))
		{
			CookieHelper ch=new CookieHelper(req,(HttpServletResponse)response);
			Cookie c=ch.getCookie("vgsoftware");
			if(c==null)
			{
				user=new MemberData();
				user.setId(-1);
				user.setName("guest");
				user.setGroupName("guest");
				user.setGroupId(-1);
				req.getSession().setAttribute(Constants.GUEST_KEY,user);
			}
			else
			{
				req.setAttribute("auto_login",c);
				req.setAttribute("forward_to",req.getServletPath());
				req.getRequestDispatcher("/member/autologin.do").forward(request,response);
				return;
			}
		}
		iter=accessList.iterator();
		String path=req.getRequestURI().substring(req.getContextPath().length());
		while(iter.hasNext())
		{
			if(!((Directory)iter.next()).hasAccess(path,user.getGroupId()))
			{
				req.getRequestDispatcher("/index.htm").forward(request,response);
				return;
			}
		}
		chain.doFilter(request,response);
	}
	
	/**
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config)
	throws ServletException
	{
		accessList=(List)config.getServletContext().getAttribute(config.getInitParameter("access-list"));
	}
}
