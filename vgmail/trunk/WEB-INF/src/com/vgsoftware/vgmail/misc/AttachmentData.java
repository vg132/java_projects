/*
 * Created on: 2003-nov-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-13 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

/**
 * Attachment information class.
 */
public class AttachmentData
{
	private String name=null;
	private String tmpName=null;

	public AttachmentData()
	{
	}

	public AttachmentData(String name)
	{
		this.name=name;
	}

	public AttachmentData(String name, String tmpName)
	{
		this.name=name;
		this.tmpName=tmpName;
	}

	public String getName()
	{
		return(name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getTmpName()
	{
		return(tmpName);
	}

	public void setTmpName(String tmpName)
	{
		this.tmpName=tmpName;
	}
}
