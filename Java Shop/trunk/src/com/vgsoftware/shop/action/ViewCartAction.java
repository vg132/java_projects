package com.vgsoftware.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.vaction.Action;

public class ViewCartAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		return(Globals.SUCCESS);
	}
}
