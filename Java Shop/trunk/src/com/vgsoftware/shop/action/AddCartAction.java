package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CartData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.data.ItemData;
import com.vgsoftware.shop.model.ItemModel;
import com.vgsoftware.vaction.Action;

public class AddCartAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		if(getIntParam(request,"id")!=-1)
		{
			if(request.getSession().getAttribute("cart")==null)
				request.getSession().setAttribute("cart",new CartData());
			CartData cd=(CartData)request.getSession().getAttribute("cart");
			try
			{
				ItemData id=ItemModel.getItem(getDataSource(request),getIntParam(request,"id"));
				cd.addItem(id.getId(),id.getPrice(),id.getName());
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
