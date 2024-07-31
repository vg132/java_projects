/**
 * Copyright (C) VG Software 2003-mar-26
 *  
 * Document History
 * 
 * Created: 2003-mar-26 15:17:38 by Viktor
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
public class MathQuestion extends Question
{
	private float awnser=0.0f;
	
	public MathQuestion()
	{
	}
	
	public MathQuestion(String question, float awnser)
	{
		super(question);
		this.awnser=awnser;
	}
	
	public boolean checkAwnser(String awnser)
	throws WrongAwnserFormatException
	{
		try
		{
			awnser=awnser.replace(',','.');
			if(Float.compare(this.awnser,Float.parseFloat(awnser))==0)
				return(true);
			else
				return(false);
		}
		catch(NumberFormatException nfe)
		{
			throw(new WrongAwnserFormatException("Wrong format. Only enter digits."));
		}
	}
	
	public String toString()
	{
		return("[Question: "+question+", Awnser: "+awnser+"]");
	}
	
	public boolean fromFile(String data)
	{
		StringTokenizer st=new StringTokenizer(data,"|");
		if(st.countTokens()!=3)
			return(false); 
		st.nextElement();
		question=st.nextToken();
		awnser=Float.parseFloat(st.nextToken());
		return(true);
	}
}
