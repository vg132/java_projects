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

//import com.vgsoftware.web.data.MemberData;
import com.vgsoftware.web.data.f1video.VideoData;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.f1video.ChannelModel;
import com.vgsoftware.web.model.f1video.CountryModel;
import com.vgsoftware.web.model.f1video.LanguageModel;
import com.vgsoftware.web.model.f1video.MediaModel;
import com.vgsoftware.web.model.f1video.QualityModel;
import com.vgsoftware.web.model.f1video.RaceTypeModel;
import com.vgsoftware.web.model.f1video.TrackModel;
import com.vgsoftware.web.model.f1video.VideoModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Updated to new data classes.
 * 2005-apr-30 - Document created.
 */
public class PreEditAction extends Action
{
	private static Log log=LogFactory.getLog(PreEditAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
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

				int videoId=-1;
				if(request.getParameter("videoId")!=null)
				{
					videoId=Integer.parseInt(request.getParameter("videoId"));
					VideoData vd=VideoModel.getVideo(videoId,getDataSource(request));
					request.setAttribute("videoId",""+vd.getId());
					request.setAttribute("title",vd.getTitle());
					request.setAttribute("channel_id",""+vd.getChannelId());
					request.setAttribute("quality_id",""+vd.getQualityId());
					request.setAttribute("language_id",""+vd.getLanguageId());
					request.setAttribute("track_id",""+vd.getTrackId());
					request.setAttribute("racetype_id",""+vd.getRaceTypeId());
					request.setAttribute("length",""+vd.getLength());
					request.setAttribute("year",""+vd.getYear());
					request.setAttribute("media_id",""+vd.getMediaId());
					request.setAttribute("comment",vd.getComment());
					request.setAttribute("videoid",vd.getVideoid());
					request.setAttribute("filesize",""+vd.getFilesize());
					if(vd.getMemberId()==md.getId())
						return(mapping.findForward(Constants.SUCCESS));
					ae.add("notown",new ActionMessage("error.video.notown"));
				}
				else if(request.getAttribute("videoId")!=null)
				{
					return(mapping.findForward(Constants.SUCCESS));
				}
			}
			else
			{
				return(mapping.findForward(Constants.LOGIN));
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to pre-edit a video.",sql);
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}
}
