package com.vgsoftware.jbb.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.jbb.Globals;
import com.vgsoftware.jbb.data.CategoryData;
import com.vgsoftware.jbb.model.CategoryModel;
import com.vgsoftware.jbb.model.ForumModel;

public class BoardIndexAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			List<CategoryData> categorys=CategoryModel.getCategorys(getDataSource(request));
			for(CategoryData cd : categorys)
				cd.setForums(ForumModel.getForums(getDataSource(request),cd.getId()));
			request.setAttribute("categorys",categorys);
			return(mapping.findForward(Globals.SUCCESS));
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		ActionErrors ae=new ActionErrors();
		ae.add("unknown_error",new ActionMessage("errors.unknown"));
		addErrors(request,ae);
		return(mapping.findForward(Globals.FAILURE));
	}
}
