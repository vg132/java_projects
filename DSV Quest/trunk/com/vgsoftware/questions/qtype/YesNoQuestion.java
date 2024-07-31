/**
 * Copyright (C) VG Software 2003-mar-26
 *  
 * Document History
 * 
 * Created: 2003-mar-26 15:17:56 by Viktor
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
public class YesNoQuestion extends Question
{
	private String awnser="ja";
	
	public YesNoQuestion()
	{
	}
	
	public YesNoQuestion(String question, String awnser)
	{
		super(question);
		alt=new String[]{"Ja","Nej"};
		this.awnser=awnser;
	}

	public boolean checkAwnser(String awnser)
	throws WrongAwnserFormatException
	{
		if((awnser.compareToIgnoreCase("ja")!=0)&&(awnser.compareToIgnoreCase("nej")!=0))
			throw(new WrongAwnserFormatException("Wrong Format. Awnser with \"Ja\" or \"Nej\"."));
		if(this.awnser.compareToIgnoreCase(awnser)==0)
			return(true);
		else
			return(false);
	}
	
	public String toString()
	{
		String tmp="[Question: "+question +", Awnser: "+awnser+"]";
		return(tmp);
	}
	
	public boolean fromFile(String data)
	{
		StringTokenizer st=new StringTokenizer(data,"|");
		if(st.countTokens()!=3)
			return(false); 
		st.nextElement();
		question=st.nextToken();
		awnser=st.nextToken();
		alt=new String[]{"Ja","Nej"};
		return(true);
	}
}
