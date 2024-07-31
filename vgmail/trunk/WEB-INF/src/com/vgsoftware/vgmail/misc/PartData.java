/*
 * Created on: 2003-nov-09
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-09 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

public class PartData
{
	private String fileName=null;
	private String id=null;
	private int size=0;
	private String contentType=null;
	private boolean inline=false;
	private String data=null;	

	public boolean isInline()
	{
		return(inline);
	}

	public void setInline(boolean inline)
	{
		this.inline=inline;
	}

	public String getData()
	{
		return(data);
	}
	
	public void setData(String data)
	{
		this.data=data;
	}

	public String getContentType()
	{
		return(contentType);
	}

	public String getFormattedContentType()
	{
		try
		{
			int index=contentType.indexOf(';');
			if(index!=-1)
				return(MimeUtility.decodeText(contentType.substring(0,index)));
			else
				return(MimeUtility.decodeText(contentType));
		}
		catch(UnsupportedEncodingException uee)
		{
			return(contentType);
		}
	}

	public String getFileName()
	{
		return(fileName);
	}
	
	public String getFormattedFileName()
	{
		try
		{
			return(MimeUtility.decodeText(fileName));
		}
		catch(UnsupportedEncodingException uee)
		{
			return(fileName);
		}
	}

	public String getId()
	{
		return(id);
	}

	public int getSize()
	{
		return(size);
	}

	public void setContentType(String contentType)
	{
		this.contentType=contentType;
	}

	public void setFileName(String fileName)
	{
		this.fileName=fileName;
	}

	public void setId(String id)
	{
		this.id=id;
	}

	public void setSize(int size)
	{
		this.size=size;
	}
}
