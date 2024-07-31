package com.vgsoftware.web.action.admin.gt4;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vgsoftware.web.data.gt4.EventData;
import com.vgsoftware.web.form.gt4.EventForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.EventModel;

/**
 * Adds a new event to a season.
 * 
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class AddEventAction extends Action
{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=null;
		EventForm ef=(EventForm)form;
		try
		{
			EventData ed=new EventData();
			ed.setTrack(ef.getTrack());
			ed.setType(ef.getType());
			ed.setSeasonId(ef.getSeason());

			Calendar c=Calendar.getInstance();
			c.setTime(sdf.parse(ef.getStart()));
			c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),0,0,0);
			ed.setStart(new Timestamp(c.getTimeInMillis()));

			c.setTime(sdf.parse(ef.getEnd()));
			c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),23,59,59);
			ed.setEnd(new Timestamp(c.getTimeInMillis()));

			EventModel.addEvent(ed,getDataSource(request));
			request.setAttribute("season",""+ef.getSeason());
			return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			ae=new ActionErrors();
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
			addErrors(request,ae);
			sql.printStackTrace(System.err);
		}
		catch(ParseException pe)
		{
			ae=new ActionErrors();
			ae.add("unknown_error",new ActionMessage("error.unknown"));
			addErrors(request,ae);
			pe.printStackTrace(System.err);
		}
		return(mapping.findForward(Constants.ERROR));
	}
}
