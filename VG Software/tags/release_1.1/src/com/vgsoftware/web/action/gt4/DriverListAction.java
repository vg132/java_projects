package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-17 - Document created.
 */
public class DriverListAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			request.setAttribute("drivers",MemberModel.getMembersWithService("gt4",getDataSource(request)));
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(mapping.findForward(Constants.FAILURE));
	}
}
