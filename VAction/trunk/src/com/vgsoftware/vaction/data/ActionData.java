package com.vgsoftware.vaction.data;

import java.util.HashMap;
import java.util.Map;

import com.vgsoftware.vaction.Action;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-04 - Document created.
 */
public class ActionData
{
	private Action action=null;
	private String path=null;
	private Map<String,ViewData> views=new HashMap<String,ViewData>();

	private Action loadClass(String actionClass)
	{
		try
		{
			return((Action)Class.forName(actionClass).newInstance());
		}
		catch(IllegalAccessException iae)
		{
			iae.printStackTrace(System.err);
		}
		catch(InstantiationException ie)
		{
			ie.printStackTrace(System.err);
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace(System.err);
		}
		return(null);
	}

	/**
	 * @return Returns the model.
	 */
	public Action getAction()
	{
		return(action);
	}

	/**
	 * @param model The model to set.
	 */
	public void setAction(String action)
	{
		this.action=loadClass(action);
	}

	/**
	 * @return Returns the view.
	 */
	public ViewData getView(String name)
	{
		return(views.get(name));
	}

	/**
	 * @param view The view to set.
	 */
	public void addView(String name, ViewData vd)
	{
		views.put(name,vd);
	}

	public String getPath()
	{
		return(path);
	}

	public void setPath(String path)
	{
		this.path=path;
	}
	
	public boolean hasView(String name)
	{
		return(views.containsKey(name));
	}
}
