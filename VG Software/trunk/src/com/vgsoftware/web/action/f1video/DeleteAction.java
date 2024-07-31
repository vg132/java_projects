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

import com.vgsoftware.web.data.member.ExtraData;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.f1video.VideoModel;
import com.vgsoftware.web.model.member.MemberExtraModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Updated to use new data classes.
 * 2005-apr-30 - Document created.
 */
public class DeleteAction extends Action
{
	private static Log log=LogFactory.getLog(DeleteAction.class);	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		try
		{
			MemberData md=null;
			if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
				md=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
			if((md!=null)&&(request.getParameter("videoId")!=null))
			{
				if(VideoModel.removeVideo(md.getId(),Integer.parseInt(request.getParameter("videoId")),getDataSource(request)))
				{
					ExtraData ed=md.getExtra("f1video_count");
					int videos=Integer.parseInt(ed.getData());
					videos--;
					ed.setData(""+videos);
					MemberExtraModel.updateExtra(ed,getDataSource(request));
					return(mapping.findForward(Constants.SUCCESS));
				}
				ae.add("unknown_error",new ActionMessage("error.video.notremove"));
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to remove a video.",sql);
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}
}
