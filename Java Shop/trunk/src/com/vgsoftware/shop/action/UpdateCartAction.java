package com.vgsoftware.shop.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.CartData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.vaction.Action;

public class UpdateCartAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		if(request.getSession().getAttribute("cart")!=null)
		{
			try
			{
				CartData cart=(CartData)request.getSession().getAttribute("cart");
				String param=null;
				Map params=request.getParameterMap();
				Iterator iter=params.keySet().iterator();
				while(iter.hasNext())
				{
					param=(String)iter.next();
					if(param.startsWith("quantity_"))
						cart.updateQuantity(Integer.parseInt(param.replaceAll("quantity_","")),Integer.parseInt(((String[])params.get(param))[0]));
				}
			}
			catch(NumberFormatException nfe)
			{
				nfe.printStackTrace(System.err);
			}
		}
		return(Globals.SUCCESS);
	}
}
