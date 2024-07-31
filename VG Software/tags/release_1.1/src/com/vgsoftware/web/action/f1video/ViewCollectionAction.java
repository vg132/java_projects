package com.vgsoftware.web.action.f1video;

import java.io.IOException;
import java.sql.SQLException;

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
import com.vgsoftware.web.model.f1video.VideoModel;
import com.vgsoftware.web.model.member.MemberModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-30 - Document created.
 */
public class ViewCollectionAction extends Action
{
	private static Log log=LogFactory.getLog(ViewCollectionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		try
		{
			MemberData md=null;
			int memberId=-1;
			if(request.getParameter("memberId")!=null)
				memberId=Integer.parseInt(request.getParameter("memberId"));
			if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			{
				md=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
				if(memberId==-1)
					memberId=md.getId();
				if(md.getId()==memberId)
					request.setAttribute("own","true");
			}
			md=MemberModel.getMember(memberId,getDataSource(request));
			if(md!=null)
			{
				request.setAttribute("title",md.getName());
				request.setAttribute("email",md.getEmail());
				request.setAttribute("race_videos",VideoModel.getVideos(md.getId(),VideoModel.RACE_VIDEOS,getDataSource(request)));
				request.setAttribute("other_videos",VideoModel.getVideos(md.getId(),VideoModel.OTHER_VIDEOS,getDataSource(request)));
				return(mapping.findForward(Constants.SUCCESS));
			}
			else
			{
				ae.add("notfound",new ActionMessage("error.video.nomember"));
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when loading race and other videos.",sql);
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}
}
