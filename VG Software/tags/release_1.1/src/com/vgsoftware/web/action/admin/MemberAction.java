/*
 * Created on 2005-mar-30
 */
package com.vgsoftware.web.action.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.member.ServiceData;
import com.vgsoftware.web.helper.Constants;

/**
 * @author viktor
 */
public class MemberAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		DataSource dataSource=getDataSource(request);
		Connection conn=null;
		PreparedStatement ps=null;
		ActionErrors ae=new ActionErrors();
		try
		{
			conn=dataSource.getConnection();
			if(request.getParameter("whattodo").equals("addservice"))
			{
				ps=conn.prepareStatement("INSERT INTO member_service(member_id, service_id) VALUES(?,?)");
				ps.setInt(1,Integer.parseInt(request.getParameter("member_id")));
				ps.setInt(2,Integer.parseInt(request.getParameter("service")));
				if(ps.executeUpdate()==1)
					ae.add("changes_saved",new ActionMessage("error.changessaved"));
				else
					ae.add("error_unknown",new ActionMessage("error.unknown"));
			}
			else if(request.getParameter("whattodo").equals("removeservice"))
			{
				ps=conn.prepareStatement("DELETE FROM member_service WHERE service_id=?");
				ps.setInt(1,Integer.parseInt(request.getParameter("service")));
				if(ps.executeUpdate()==1)
					ae.add("changes_saved",new ActionMessage("info.changessaved"));
				else
					ae.add("error_unknown",new ActionMessage("error.unknown"));
			}
			else if(request.getParameter("whattodo").equals("save"))
			{
				ps=conn.prepareStatement("UPDATE member SET username=?, email=? WHERE id=?");
				ps.setString(1,request.getParameter("username"));
				ps.setString(2,request.getParameter("email"));
				ps.setInt(3,Integer.parseInt(request.getParameter("member_id")));
				if(ps.executeUpdate()==1)
					ae.add("changes_saved",new ActionMessage("info.changessaved"));
				else
					ae.add("error_unknown",new ActionMessage("error.unknown"));
			}
			
			if(request.getParameter("name")==null)
			{
				ps=conn.prepareStatement("SELECT id, username, email FROM member WHERE id=?");
				ps.setInt(1,Integer.parseInt(request.getParameter("member_id")));
			}
			else
			{
				ps=conn.prepareStatement("SELECT id, username, email FROM member WHERE username=?");
				ps.setString(1,request.getParameter("name"));
			}
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				MemberData user=new MemberData();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				request.setAttribute("member",user);

				ps=conn.prepareStatement("SELECT s.id, s.name FROM service s, member_service ms WHERE ms.service_id=s.id AND ms.member_id=?");
				ps.setInt(1,user.getId());
				rs=ps.executeQuery();
				List services=new ArrayList();
				while(rs.next())
					services.add(new ServiceData(rs.getInt("id"),rs.getString("name")));
				request.setAttribute("member_service",services);

				ps=conn.prepareStatement("SELECT id, name FROM service WHERE id NOT IN (SELECT service_id FROM member_service WHERE member_id=?)");
				ps.setInt(1,user.getId());
				rs=ps.executeQuery();
				services=new ArrayList();
				while(rs.next())
					services.add(new ServiceData(rs.getInt("id"),rs.getString("name")));
				request.setAttribute("service",services);
				addErrors(request,ae);
				return(mapping.findForward("edit"));
			}
			else
			{
				ae.add("error_member_not_found",new ActionMessage("error.member.notfound"));
			}
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
		return(mapping.findForward(Constants.FAILURE));
	}
}
