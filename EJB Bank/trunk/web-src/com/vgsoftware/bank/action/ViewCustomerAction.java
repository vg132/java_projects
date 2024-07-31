package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.admin.AdminRemote;
import com.vgsoftware.vaction.Action;
 
public class ViewCustomerAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String customer=getParam(request,"customer");
			AdminRemote remote=(AdminRemote)request.getSession().getAttribute(Globals.ADMIN_REMOTE);
			request.setAttribute("accounts",remote.getCustomerAccounts(customer));
			request.setAttribute("customer",customer);
			return(Globals.SUCCESS);
		}
		catch(RemoteException re)
		{
		}
		return(Globals.FAILURE);
	}
}
