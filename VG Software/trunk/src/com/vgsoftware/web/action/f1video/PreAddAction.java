package com.vgsoftware.web.action.f1video;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.f1video.ChannelModel;
import com.vgsoftware.web.model.f1video.CountryModel;
import com.vgsoftware.web.model.f1video.LanguageModel;
import com.vgsoftware.web.model.f1video.MediaModel;
import com.vgsoftware.web.model.f1video.QualityModel;
import com.vgsoftware.web.model.f1video.RaceTypeModel;
import com.vgsoftware.web.model.f1video.TrackModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Updated to use new data classes.
 * 2005-apr-30 - Document created.
 */
public class PreAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=null;
		try
		{
			MemberData md=null;
			if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
				md=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
			if(md!=null)
			{
				request.setAttribute("channels",ChannelModel.getChannels(getDataSource(request)));
				request.setAttribute("countrys",CountryModel.getCountrys(getDataSource(request)));
				request.setAttribute("medias",MediaModel.getMedias(getDataSource(request)));
				request.setAttribute("racetypes",RaceTypeModel.getRaceTypes(getDataSource(request)));
				request.setAttribute("tracks",TrackModel.getTracks(getDataSource(request)));
				request.setAttribute("languages",LanguageModel.getLanguages(getDataSource(request)));
				request.setAttribute("qualities",QualityModel.getQualities(getDataSource(request)));
				return(mapping.findForward(Constants.SUCCESS));
			}
			return(mapping.findForward(Constants.LOGIN));
		}
		catch(SQLException sql)
		{
			ae=new ActionErrors();
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
			sql.printStackTrace(System.err);
		}
		if(ae!=null)
			addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}
}
