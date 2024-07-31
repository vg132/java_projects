package com.vgsoftware.bank.action;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.bank.Globals;
import com.vgsoftware.bank.customer.CustomerRemote;
import com.vgsoftware.vaction.Action;

public class DepositAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Integer number=getIntParam(request,"to");
			Double amount=Double.parseDouble(getParam(request,"amount"));
			CustomerRemote remote=(CustomerRemote)request.getSession().getAttribute(Globals.CUSTOMER_REMOTE);
			if(remote.deposit(number,amount))
				return(Globals.SUCCESS);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		request.setAttribute("error","Unable to perform the deposit");
		return(Globals.SUCCESS);
	}
}
