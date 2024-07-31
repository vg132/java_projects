package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.WishListModel;
import com.vgsoftware.vaction.Action;

public class RemoveWishListAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		int id=getIntParam(request,"id");
		CustomerData cd=null;
		if(request.getSession().getAttribute("customer")!=null)
			cd=((CustomerData)request.getSession().getAttribute("customer"));
		if((cd!=null)&&(id!=-1))
		{
			try
			{
				if(WishListModel.removeItem(getDataSource(request),id))
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
