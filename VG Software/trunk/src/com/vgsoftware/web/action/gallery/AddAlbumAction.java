package com.vgsoftware.web.action.gallery;

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

import com.vgsoftware.web.data.gallery.AlbumData;
import com.vgsoftware.web.form.gallery.AlbumForm;
import com.vgsoftware.web.helper.Constants;
import com.vgsoftware.web.model.gallery.AlbumModel;

public class AddAlbumAction extends Action
{
	private static Log log=LogFactory.getLog(AddAlbumAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		ActionErrors ae=new ActionErrors();
		try
		{
			AlbumData ad=new AlbumData();
			AlbumForm af=(AlbumForm)form;
			ad.setName(af.getName());
			ad.setDescription(af.getDescription());
			if(AlbumModel.addAlbum(ad,getDataSource(request)))
				return(mapping.findForward(Constants.SUCCESS));
		}
		catch(SQLException sql)
		{
			log.error("SQLException when trying to add a album",sql);
			ae.add("unknown_error",new ActionMessage("error.unknown.database"));
		}
		addErrors(request,ae);
		return(mapping.findForward(Constants.INFO));
	}
}
