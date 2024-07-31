package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.WishListModel;
import com.vgsoftware.vaction.Action;

public class ViewWishListAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		if(request.getSession().getAttribute("customer")!=null)
		{
			try
			{
				CustomerData cd=(CustomerData)request.getSession().getAttribute("customer");
				request.setAttribute("products",WishListModel.getWishList(getDataSource(request),cd.getId()));
				request.setAttribute("wishlist","true");
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
