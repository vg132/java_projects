package com.vgsoftware.shop.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.data.ProductGroupData;
import com.vgsoftware.shop.model.ItemModel;
import com.vgsoftware.shop.model.ProductGroupModel;
import com.vgsoftware.vaction.Action;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class IndexAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			//Load all product groups and 4 random items from every product group
			List<ProductGroupData> productGroups=ProductGroupModel.getProductGropus(getDataSource(request));
			for(ProductGroupData pgd : productGroups)
				pgd.setItems(ItemModel.getFourItems(getDataSource(request),pgd.getId()));
			request.setAttribute("products",productGroups);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(Globals.SUCCESS);
	}
}
