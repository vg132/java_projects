package com.vgsoftware.shop.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.vaction.Action;

public class LogoutAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession().setAttribute("customer",null);
		request.getSession().invalidate();
		Cookie c=new Cookie("customer",null);
		c.setPath("/");
		c.setMaxAge(0);
		response.addCookie(c);
		return(Globals.SUCCESS);
	}
}
