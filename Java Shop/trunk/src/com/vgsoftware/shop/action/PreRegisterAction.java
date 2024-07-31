package com.vgsoftware.shop.action;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.CountryModel;
import com.vgsoftware.vaction.Action;

public class PreRegisterAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Map<String,String> config=(Map<String,String>)request.getAttribute(Globals.CONFIG);
			request.setAttribute("selectedCurrency",config.get("currency"));
			request.setAttribute("countrys",CountryModel.getCountrys(getDataSource(request)));
			return(Globals.SUCCESS);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(Globals.FAILURE);
	}
}
