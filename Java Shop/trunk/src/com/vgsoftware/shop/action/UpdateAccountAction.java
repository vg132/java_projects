package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.CustomerModel;
import com.vgsoftware.vaction.Action;

public class UpdateAccountAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		CustomerData customer=null;
		if(request.getSession().getAttribute("customer")!=null)
			customer=((CustomerData)request.getSession().getAttribute("customer"));
		if(customer!=null)
		{
			try
			{	
				CustomerData cd=new CustomerData();
				cd.setId(customer.getId());
				cd.setName(getParam(request,"name"));
				cd.setAddress(getParam(request,"address"));
				cd.setCity(getParam(request,"city"));
				cd.setState(getParam(request,"state"));
				cd.setPostCode(getParam(request,"post_code"));
				cd.setCountryId(getIntParam(request,"country"));
				cd.setCurrency(getParam(request,"currency"));
				if(CustomerModel.updateCustomer(getDataSource(request),cd))
				{
					request.getSession().setAttribute("customer",CustomerModel.getCustomer(getDataSource(request),customer.getId()));
					return(Globals.SUCCESS);
				}
			}
			catch(SQLException sql)
			{
				sql.printStackTrace(System.err);
			}
		}
		return(Globals.FAILURE);
	}
}
