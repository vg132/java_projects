package com.vgsoftware.vgutil.misc;

public class WebHelp
{
	public static String toSafeHTML(String str)
	{
		return(str.trim().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br/>"));
	}

	public static String fromSafeHTML(String str)
	{
		return(str.trim().replaceAll("<br/>","\n").replaceAll("&lt;","<").replaceAll("&gt;",">"));
	}
}
