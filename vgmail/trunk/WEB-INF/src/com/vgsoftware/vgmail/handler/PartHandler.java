/*
 * Created on: 2003-nov-09
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-09 Created by Viktor
 */
package com.vgsoftware.vgmail.handler;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import com.vgsoftware.vgmail.misc.PartData;

/**
 * A <code>Multipart</code> handler class.
 * 
 * @author Viktor
 * @version 1.0
 */
public class PartHandler
{
	/**
	 * Returns a <code>List</code> with all parts
	 * in this multipart message.
	 * 
	 * @param mp Multipart message to process.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	public List getParts(Multipart mp)
	throws MessagingException, IOException
	{
		List messageData=new MailDataList();
		processMultipart(mp,messageData,"");
		return(messageData);
	}

	/**
	 * Gets the <code>Part</code> from the <code>Multipart</code> based on the ID provided.
	 * 
	 * @param mp The <code>Multipart</code> where this <code>Part</code> is located.
	 * @param id The ID value for this <code>Part</code>
	 * 
	 * @return The <code>Part</code> if its found, otherwise null.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Part getPart(Multipart mp, String id)
	throws MessagingException, IOException
	{
		Part part=null;
		Multipart mpTemp=mp;
		StringTokenizer st=new StringTokenizer(id,".");
		while(st.hasMoreTokens())
		{
			part=mpTemp.getBodyPart(Integer.parseInt(st.nextToken()));
			if(part.getContent() instanceof Multipart)
				mpTemp=(Multipart)part.getContent();
		}
		return(part);
	}

	/**
	 * Iterate the <code>Multipart</code> and generate a list with all parts.
	 * If we find a <code>Multipart</code> as a part, call this function again.
	 * 
	 * @param mp The <code>Multipart</code> to iterate.
	 * @param list The <code>List</code> where the <code>MailData</code> is added.
	 * @param level The current ID level.
	 * 
	 * @throws MessagingException
	 * @throws IOException 
	 */
	private void processMultipart(Multipart mp, List list, String level)
	throws MessagingException, IOException
	{
		PartData md=null;
		String tmpLevel=level;
		for(int i=0;i<mp.getCount();i++)
		{
			tmpLevel=level+i;
			if(mp.getBodyPart(i).getContent() instanceof Multipart)
			{
				tmpLevel+=".";
				processMultipart((Multipart)mp.getBodyPart(i).getContent(),list,tmpLevel);
			}
			else
			{
				list.add(processPart(mp.getBodyPart(i),tmpLevel));
			}
		}
	}

	/**
	 * Determens type of part and saves information about this part into a
	 * <code>MailData</code> object that is the returend.
	 * 
	 * @param p The current part.
	 * @param id This parts ID value.
	 * 
	 * @return The <code>MailData</code> representation of this <code>Part</code>.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private PartData processPart(Part p, String id)
	throws MessagingException, IOException
	{
		PartData md=new PartData();
		md.setContentType(p.getContentType());
		if((p.getFileName()==null)||(p.getFileName().equals("")))
			md.setFileName("Part ["+id+"]");
		else
			md.setFileName(p.getFileName());
		md.setSize(p.getSize());
		md.setId(id);
		if(((p.getDisposition()!=null)&&(p.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)))||!(p.getContent() instanceof String))
		{
			md.setInline(false);
			md.setData(null);
		}
		else
		{
			md.setInline(true);
			md.setData((String)p.getContent());
		}
		return(md);
	}
}