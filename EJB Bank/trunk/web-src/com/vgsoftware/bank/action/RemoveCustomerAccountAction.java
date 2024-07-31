package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.admin.AdminRemote;
import com.vgsoftware.vaction.Action;

public class RemoveCustomerAccountAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Integer number=getIntParam(request,"number");
			AdminRemote remote=(AdminRemote)request.getSession().getAttribute(Globals.ADMIN_REMOTE);
			if(remote.removeCustomerAccount(number))
				return(Globals.SUCCESS);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		return(Globals.SUCCESS);
	}
}
