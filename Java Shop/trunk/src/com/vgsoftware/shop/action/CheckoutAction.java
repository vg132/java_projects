package com.vgsoftware.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.vaction.Action;

public class CheckoutAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		if(request.getSession().getAttribute("customer")!=null)
			return(Globals.SUCCESS);
		return(Globals.FAILURE);
	}
}
