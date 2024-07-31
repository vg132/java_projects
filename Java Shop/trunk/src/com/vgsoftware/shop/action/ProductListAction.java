package com.vgsoftware.shop.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.CategoryModel;
import com.vgsoftware.shop.model.ItemModel;
import com.vgsoftware.vaction.Action;

public class ProductListAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setAttribute("categorys",CategoryModel.getCategorys(getDataSource(request)));
			if((isParamSet(request,"cid")==false)&&(isParamSet(request,"pgid")==true))
			{
				request.setAttribute("pgid",getParam(request,"pgid"));
				request.setAttribute("products",ItemModel.getProductGroupItems(getDataSource(request),getIntParam(request,"pgid")));
				return(Globals.SUCCESS);
			}
			else if((isParamSet(request,"cid")==true)&&(isParamSet(request,"pgid")==true))
			{
				request.setAttribute("pgid",getParam(request,"pgid"));
				request.setAttribute("products",ItemModel.getCategoryItems(getDataSource(request),getIntParam(request,"pgid"),getIntParam(request,"cid")));
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
