package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.ItemModel;
import com.vgsoftware.vaction.Action;

public class ProductInfoAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			if(request.getParameter("pid")!=null)
			{
				int pid=Integer.parseInt(request.getParameter("pid"));
				request.setAttribute("product",ItemModel.getItem(getDataSource(request),pid));
				return(Globals.SUCCESS);
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(Globals.FAILURE);
	}
}
