/*
 * Created on 2005-mar-30
 */
package com.vgsoftware.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author viktor
 */
public class ServiceForm extends ActionForm
{
	private String name=null;

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
}
