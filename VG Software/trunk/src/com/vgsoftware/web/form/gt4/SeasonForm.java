package com.vgsoftware.web.form.gt4;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-11 - Document created.
 */
public class SeasonForm extends ActionForm
{
	private String name=null;
	private int pointRounds=-1;
	
	public SeasonForm()
	{
	}
	
	public SeasonForm(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return(name);
	}
	
	public void setName(String name)
	{
		this.name=name;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((name==null)||(name.trim().equals("")))
			errors.add("no_name",new ActionMessage("error.name.required"));
		request.setAttribute("name",name);
		return(errors);
	}

	/**
	 * @return Returns the pointRounds.
	 */
	public int getPointRounds()
	{
		return pointRounds;
	}

	/**
	 * @param pointRounds The pointRounds to set.
	 */
	public void setPointRounds(int pointRounds)
	{
		this.pointRounds=pointRounds;
	}
}
