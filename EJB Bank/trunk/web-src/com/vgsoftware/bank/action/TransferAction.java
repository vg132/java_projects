package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.customer.CustomerRemote;
import com.vgsoftware.vaction.Action;

public class TransferAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Integer from=getIntParam(request,"from");
			Integer to=getIntParam(request,"to");
			Double amount=Double.parseDouble(getParam(request,"amount"));
			CustomerRemote remote=(CustomerRemote)request.getSession().getAttribute(Globals.CUSTOMER_REMOTE);
			if(remote.transfer(from,to,amount))
				return(Globals.SUCCESS);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		request.setAttribute("error","Unable to perform the transfer");
		return(Globals.SUCCESS);
	}
}
