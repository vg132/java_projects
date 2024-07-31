/**
 * Copyright (C) VG Software 2003-mar-26
 *  
 * Document History
 * 
 * Created: 2003-mar-26 15:18:05 by Viktor
 * 
 */
package com.vgsoftware.questions.qtype;

import java.util.StringTokenizer;
import com.vgsoftware.questions.*;
import com.vgsoftware.questions.exception.WrongAwnserFormatException;

/**
 * @author Viktor
 * 
 */
public class AltQuestion extends Question
{
	private char awnser='a';

	public AltQuestion()
	{
	}

	public AltQuestion(String question, String[] alt, char awnser)
	{
		super(question,alt);
		this.awnser=Character.toLowerCase(awnser);
	}
	
	public boolean checkAwnser(String awnser)
	throws WrongAwnserFormatException
	{
		if((awnser==null)||((awnser.charAt(0)-'a')>alt.length)||((awnser.charAt(0)-'a')<0))
			throw(new WrongAwnserFormatException("Wrong format. Please enter a letter between a and "+(char)(('a'+(alt.length-1)))+"."));
		else if(Character.toLowerCase(awnser.charAt(0))==this.awnser)
			return(true);
		else
			return(false);
	}
	
	public String toString()
	{
		String tmp="[Question: "+question+" [ ";
		for(int i=0;i<alt.length;i++)
			tmp+=(char)('a'+i)+": "+alt[i]+", ";
		tmp=tmp.substring(0,tmp.length()-2);
		tmp+=" ] Awnser: "+awnser+"]";
		return(tmp);
	}
	
	public boolean fromFile(String data)
	{
		StringTokenizer st=new StringTokenizer(data,"|");
		int tokens=st.countTokens(); 
		if(tokens<4)
			return(false); 
		alt=new String[tokens-3];
		st.nextToken();
		question=st.nextToken();
		for(int i=0;i<tokens-3;i++) 
			alt[i]=st.nextToken();
		awnser=st.nextToken().charAt(0);
		return(true);
	}
}
