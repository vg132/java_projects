/*
 * Created on 2005-mar-30
 */
package com.vgsoftware.web.action.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.form.ServiceForm;
import com.vgsoftware.web.helper.Constants;

/**
 * @author viktor
 */
public class ServiceAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		DataSource dataSource=getDataSource(request);
		Connection conn=null;
		ActionErrors ae=new ActionErrors();
		ServiceForm sf=(ServiceForm)form;
		try
		{
			conn=dataSource.getConnection();
			PreparedStatement ps=conn.prepareStatement("INSERT INTO service(name) VALUES(?)");
			ps.setString(1,sf.getName());
			if(ps.executeUpdate()==1)
				return(mapping.findForward(Constants.SUCCESS));
			else
				ae.add("error_service_exist",new ActionMessage("error.service.exist"));
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(SQLException sql)
			{
			}
		}
		if(ae.size()==0)
		{
			ae.add("error_unknown",new ActionMessage("error.unknown"));
		}
		addErrors(request,ae);
		request.setAttribute("name",sf.getName());
		return(mapping.findForward(Constants.FAILURE));
	}
}
