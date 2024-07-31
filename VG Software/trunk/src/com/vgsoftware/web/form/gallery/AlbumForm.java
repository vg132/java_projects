package com.vgsoftware.web.form.gallery;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class AlbumForm extends ActionForm
{
	private String name=null;
	private String description=null;
	
	public AlbumForm()
	{
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description=description.trim();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name.trim();
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((name==null)||(!(name.length()>0)))
			errors.add("no_name",new ActionMessage("error.gallery.album.noname"));
		request.setAttribute("description",description);
		request.setAttribute("name",name);
		return(errors);
	}
}
