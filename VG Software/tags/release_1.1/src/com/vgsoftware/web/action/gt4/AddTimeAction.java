package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

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

import com.vgsoftware.web.data.gt4.EventData;
import com.vgsoftware.web.data.gt4.TimeData;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.form.gt4.TimeForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;
import com.vgsoftware.web.model.gt4.TimeModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-11 - Document created.
 */
public class AddTimeAction extends Action
{
	private static Log log=LogFactory.getLog(AddTimeAction.class);
	private static final int MILLI = 1000;
	private static final int SEC = 60;
	private static final String SEPERATOR = ".:;,";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		TimeForm tf=(TimeForm)form;
		MemberData user=null;
		if(request.getSession().getAttribute(Constants.USER_KEY)!=null)
			user=(MemberData)request.getSession().getAttribute(Constants.USER_KEY);
		try
		{
			if((user!=null)&&(user.isMemberOf("gt4")))
			{
				List events=EventModel.getActiveEvents(getDataSource(request));
				Iterator iter=events.iterator();
				while(iter.hasNext())
				{
					if(((EventData)iter.next()).getId()==tf.getEventId())
					{
						TimeData td=new TimeData();
						td.setMemberId(user.getId());
						td.setControlTypeId(tf.getControlType());
						td.setAdded(new Timestamp(System.currentTimeMillis()));
						td.setEventId(tf.getEventId());
						td.setTime(timeConvert(tf.getTime()));
						TimeModel.addTime(td,getDataSource(request));
						return(mapping.findForward(Constants.SUCCESS));
					}
				}
				ae.add("invalidevent",new ActionMessage("error.gt4.invalidevent"));
			}
		}
		catch(SQLException sql)
		{
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
			log.error("SQLException when adding a time.",sql);
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.FAILURE));
	}

	private int timeConvert(String time)
	{
		time=time.trim();
		StringTokenizer token = new StringTokenizer(time, SEPERATOR);
		int minuter = Integer.parseInt(token.nextToken());
		int sekunder = Integer.parseInt(token.nextToken());
		int milliSekunder = Integer.parseInt(token.nextToken());
		return(milliSekunder + (sekunder * MILLI)+(minuter*SEC*MILLI));
	}
}
