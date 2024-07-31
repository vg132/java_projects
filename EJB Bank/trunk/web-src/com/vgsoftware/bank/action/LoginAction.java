package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.admin.AdminHome;
import com.vgsoftware.bank.admin.AdminRemote;
import com.vgsoftware.bank.customer.CustomerHome;
import com.vgsoftware.bank.customer.CustomerRemote;
import com.vgsoftware.vaction.Action;

public class LoginAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String type=request.getParameter("type");

		try
		{
			if(type.equals("admin"))
			{
				AdminHome home=(AdminHome)request.getSession().getServletContext().getAttribute(Globals.ADMIN_HOME);
				AdminRemote remote=home.create();
				if(remote.login(username,password))
				{
					request.getSession().setAttribute("type","admin");
					request.getSession().setAttribute(Globals.ADMIN_REMOTE,remote);
				}
			}
			else
			{
				CustomerHome home=(CustomerHome)request.getSession().getServletContext().getAttribute(Globals.CUSTOMER_HOME);
				CustomerRemote remote=home.create();
				if(remote.login(username,password))
				{
					request.getSession().setAttribute("type","customer");
					request.getSession().setAttribute(Globals.CUSTOMER_REMOTE,remote);
				}
			}
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(CreateException ce)
		{
			ce.printStackTrace(System.err);
		}
		return(Globals.SUCCESS);
	}
}
