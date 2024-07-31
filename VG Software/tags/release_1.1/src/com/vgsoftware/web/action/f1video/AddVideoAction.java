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
import com.vgsoftware.web.data.member.ExtraData;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.form.f1video.AddVideoForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.f1video.TrackInfoModel;
import com.vgsoftware.web.model.f1video.VideoModel;
import com.vgsoftware.web.model.member.MemberExtraModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Updated to use new data classes.
 * 2005-maj-01 - Document created.
 */
public class AddVideoAction extends Action
{
	private static Log log=LogFactory.getLog(AddVideoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		AddVideoForm avf=(AddVideoForm)form;
		try
		{
			MemberData md=null;
			if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
				md=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
			if(md!=null)
			{
				if((TrackInfoModel.getTrackInfo(avf.getTrack(),avf.getYear(),getDataSource(request))!=null)||(avf.getTitle()!=null))
				{
					VideoData vd=new VideoData();
					vd.setMemberId(md.getId());
					vd.setRaceTypeId(avf.getRacetypeId());
					vd.setTitle(avf.getTitle());
					if(avf.getTitle()==null)
						vd.setTrackId(avf.getTrack());
					else
						vd.setTrackId(96);
					vd.setYear(avf.getYear());
					vd.setLength(avf.getLength());
					vd.setChannelId(avf.getChannelId());
					vd.setLanguageId(avf.getLanguage());
					vd.setMediaId(avf.getMediaId());
					vd.setFilesize(avf.getFilesize());
					vd.setQualityId(avf.getQuality());
					vd.setVideoid(avf.getVideoid());
					vd.setComment(avf.getComment());
					if(VideoModel.addVideo(vd,getDataSource(request)))
					{
						ExtraData ed=md.getExtra("f1video_count");
						int videos=Integer.parseInt(ed.getData());
						videos++;
						ed.setData(""+videos);
						MemberExtraModel.updateExtra(ed,getDataSource(request));

						ae.add("info",new ActionMessage("info.video.added"));
						addErrors(request,ae);
						return(mapping.findForward(Constants.SUCCESS));
					}
				}
				else
				{
					ae.add("norace",new ActionMessage("error.video.norace"));
				}
			}
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to add a new video.",sql);
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		}
		addErrors(request,ae);
		request.setAttribute("title",avf.getTitle());
		request.setAttribute("channel_id",""+avf.getChannelId());
		request.setAttribute("quality_id",""+avf.getQuality());
		request.setAttribute("language_id",""+avf.getLanguage());
		request.setAttribute("track_id",""+avf.getTrack());
		request.setAttribute("racetype_id",""+avf.getRacetypeId());
		request.setAttribute("length",""+avf.getLength());
		request.setAttribute("year",""+avf.getYear());
		request.setAttribute("media_id",""+avf.getMediaId());
		request.setAttribute("comment",avf.getComment());
		request.setAttribute("videoid",avf.getVideoid());
		request.setAttribute("filesize",""+avf.getFilesize());
		return(mapping.findForward(Constants.SUCCESS));
	}
}