package com.vgsoftware.shop.action;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.WishListModel;
import com.vgsoftware.vaction.Action;

public class AddWishListAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,String> config=(Map<String,String>)request.getAttribute(Globals.CONFIG);
		int id=getIntParam(request,"id");
		CustomerData cd=null;
		if(request.getSession().getAttribute("customer")!=null)
			cd=((CustomerData)request.getSession().getAttribute("customer"));
		if((cd!=null)&&(id!=-1))
		{
			try
			{
				if(WishListModel.addItem(getDataSource(request),config.get("seq_wishlist"),cd.getId(),id))
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
