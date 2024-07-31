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

import com.vgsoftware.web.data.f1video.VideoData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.f1video.VideoModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-30 - Document created.
 */
public class ViewVideoAction extends Action
{
	private static Log log=LogFactory.getLog(ViewVideoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		try
		{
			if(request.getParameter("videoId")!=null)
			{
				int videoId=Integer.parseInt(request.getParameter("videoId"));
				VideoData vd=VideoModel.getVideo(videoId,getDataSource(request));
				if(vd!=null)
				{
					request.setAttribute("video",vd);
					return(mapping.findForward(Constants.SUCCESS));
				}
			}
		}
		catch(NumberFormatException nfe)
		{
			log.error("NumberFormatExceptoin when trying to view a video.",nfe);
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to view a video",sql);
		}
		ae.add("notfound",new ActionMessage("error.video.novideo"));
		addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}
}
