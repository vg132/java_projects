package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.admin.AdminRemote;
import com.vgsoftware.vaction.Action;

public class AddCustomerAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String name=getParam(request,"name");
			String password=getParam(request,"password");
			AdminRemote remote=(AdminRemote)request.getSession().getAttribute(Globals.ADMIN_REMOTE);
			remote.addCustomer(name,password);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		return(Globals.SUCCESS);
	}
}
