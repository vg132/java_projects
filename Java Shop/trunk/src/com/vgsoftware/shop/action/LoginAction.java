package com.vgsoftware.shop.action;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.CustomerModel;
import com.vgsoftware.vaction.Action;

public class LoginAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		if((request.getParameter("email")!=null)&&(request.getParameter("password")!=null))
		{
			try
			{
				CustomerData cd=CustomerModel.getCustomer(getDataSource(request),request.getParameter("email"),request.getParameter("password"));
				if(cd!=null)
				{
					request.getSession().setAttribute("customer",cd);
					if(getParam(request,"savelogin")!=null)
					{
						Cookie cookie=new Cookie("customer",cd.getEmail()+"|"+CustomerModel.getCookiePassword(getDataSource(request),cd.getId()));
						cookie.setPath("/");
						cookie.setMaxAge(Integer.parseInt(((Map<String,String>)request.getAttribute("config")).get("cookieage"))*3600);
						response.addCookie(cookie);
					}
					return(Globals.SUCCESS);
				}
				else
				{
					request.setAttribute("errormsg","Wrong email and/or password.");
				}
			}
			catch (SQLException sql)
			{
				request.setAttribute("errormsg","Database problem, please try again later");
				sql.printStackTrace(System.err);
			}
		}
		return(Globals.FAILURE);
	}
}
