package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.InvoiceModel;
import com.vgsoftware.vaction.Action;

public class OrdersAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		CustomerData cd=null;
		if(request.getSession().getAttribute("customer")!=null)
			cd=((CustomerData)request.getSession().getAttribute("customer"));
		if(cd!=null)
		{
			try
			{
				request.setAttribute("orders",InvoiceModel.getInvoices(getDataSource(request),cd.getId()));
				return(Globals.SUCCESS);	
			}
			catch(SQLException sql)
			{
				sql.printStackTrace(System.err);
			}
		}
		return(Globals.FAILURE);
	}
}
