package com.vgsoftware.web.action.f1video;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.member.MemberModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Updated to use new data classes.
 * 2005-apr-30 - Document created.
 */
public class UserListAction extends Action
{
	private static Log log=LogFactory.getLog(UserListAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		try
		{
			List<MemberData> members=MemberModel.getMembersWithExtra("f1video_count",getDataSource(request));
			Collections.sort(members,new F1VideoSorter());
			request.setAttribute("members",members);
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to get f1 video member list",sql);
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}

	class F1VideoSorter implements Comparator<MemberData>
	{
		public int compare(MemberData md1, MemberData md2)
		{
			try
			{
				int videos1=Integer.parseInt(md1.getExtraData("f1video_count"));
				int videos2=Integer.parseInt(md2.getExtraData("f1video_count"));
				return(videos2-videos1);
			}
			catch(NumberFormatException nfe)
			{
				log.error("Extra data not a int, "+md1.getName()+" and "+md2.getName()+".",nfe);
			}
			return(0);
		}
	}
}
