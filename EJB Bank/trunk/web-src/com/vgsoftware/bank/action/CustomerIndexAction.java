package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.customer.CustomerRemote;
import com.vgsoftware.vaction.Action;

public class CustomerIndexAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			CustomerRemote remote=(CustomerRemote)request.getSession().getAttribute(Globals.CUSTOMER_REMOTE);
			request.setAttribute("accounts",remote.getAccounts());
			request.setAttribute("customer",remote.getName());
			return(Globals.SUCCESS);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		return(Globals.FAILURE);
	}
}
