package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.ItemModel;
import com.vgsoftware.vaction.Action;

public class SearchAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		if((isParamSet(request,"search_term"))&&(isParamSet(request,"product")))
		{
			int product=getIntParam(request,"product");
			try
			{
				request.setAttribute("products",ItemModel.findItems(getDataSource(request),getParam(request,"search_term"),product));
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
