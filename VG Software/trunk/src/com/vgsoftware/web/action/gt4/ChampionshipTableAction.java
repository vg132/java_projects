package com.vgsoftware.web.action.gt4;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vgsoftware.web.data.gt4.SeasonData;
import com.vgsoftware.web.data.gt4.SeasonResultData;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gt4.SeasonModel;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-16 - Document created.
 */
public class ChampionshipTableAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		try
		{
			int seasonId=-1;
			if(request.getParameter("season")!=null)
				seasonId=Integer.parseInt(request.getParameter("season"));
			else if(request.getAttribute("season")!=null)
				seasonId=Integer.parseInt(request.getAttribute("season").toString());
			if(seasonId!=-1)
			{
				Map<String,Driver> drivers=new HashMap<String,Driver>();
				SeasonData sd=SeasonModel.getSeason(seasonId,getDataSource(request));
				List results=SeasonModel.getSeasonResults(seasonId,getDataSource(request));
				int eventId=-1;
				int pos=-1;
				Iterator iter=results.iterator();
				while(iter.hasNext())
				{
					SeasonResultData srd=(SeasonResultData)iter.next();
					if(srd.getEventId()!=eventId)
					{
						eventId=srd.getEventId();
						pos=1;
					}
					else
					{
						pos++;
					}
					if(!drivers.containsKey(srd.getUsername()))
						drivers.put(srd.getUsername(),new Driver(srd.getUsername(),sd.getPointRounds()));
					drivers.get(srd.getUsername()).addResult(eventId,pos,srd.getBestTime());
				}
				List table=new ArrayList(drivers.values());
				Collections.sort(table);
				request.setAttribute("drivers",table);
				request.setAttribute("season",SeasonModel.getSeason(seasonId,getDataSource(request)));
				return(mapping.findForward(Constants.SUCCESS));
			}
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(mapping.findForward(Constants.FAILURE));
	}
}