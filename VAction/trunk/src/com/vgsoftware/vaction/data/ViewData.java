package com.vgsoftware.vaction.data;

public class ViewData
{
	private String url=null;
	private boolean redirect=false;

	public ViewData(String url, boolean redirect)
	{
		this.url=url;
		this.redirect=redirect;
	}

	public boolean isRedirect()
	{
		return (redirect);
	}

	public void setRedirect(boolean redirect)
	{
		this.redirect=redirect;
	}

	public String getUrl()
	{
		return (url);
	}

	public void setUrl(String url)
	{
		this.url=url;
	}
}
