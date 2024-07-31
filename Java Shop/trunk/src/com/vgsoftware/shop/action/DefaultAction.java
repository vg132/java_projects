package com.vgsoftware.shop.action;

import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.model.CustomerModel;
import com.vgsoftware.shop.model.ProductGroupModel;
import com.vgsoftware.vaction.Action;

public class DefaultAction extends Action
{
	public String execute(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			//load data needed for the menus
			request.setAttribute("menuProductGroups",ProductGroupModel.getProductGropus(getDataSource(request)));
			//check if user has a cookie to autologin. Only check once for every session.
			if((request.getSession().getAttribute("guest")==null)&&(request.getSession().getAttribute("customer")==null)&&(request.getCookies()!=null)&&(request.getSession().getAttribute("logout")==null))
			{
				for(Cookie c : request.getCookies())
				{
					if(c.getName().equals("customer"))
					{
						StringTokenizer st=new StringTokenizer(c.getValue(),"|");
						CustomerData cd=CustomerModel.getCookieCustomer(getDataSource(request),st.nextToken(),st.nextToken());
						if(cd!=null)
						{
							request.getSession().setAttribute("customer",cd);
						}
					}
				}
				if(request.getSession().getAttribute("customer")==null)
					request.getSession().setAttribute("guest",true);
			}
			else if(request.getSession().getAttribute("logout")!=null)
			{
				request.getSession().setAttribute("customer",null);
				request.getSession().invalidate();
				Cookie c=new Cookie("customer",null);
				c.setPath("/");
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(null);
	}
}
