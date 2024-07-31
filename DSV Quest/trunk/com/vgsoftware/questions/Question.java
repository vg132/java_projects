/**
 * Copyright (C) VG Software 2003-mar-26
 *  
 * Document History
 * 
 * Created: 2003-mar-26 15:17:24 by Viktor
 * 
 */
package com.vgsoftware.questions;

import com.vgsoftware.questions.exception.WrongAwnserFormatException;

/**
 * Abstract base class for questions.
 * 
 * @author Viktor
 * 
 */
public abstract class Question
{
	protected String question=null;
	protected String[] alt=null;
	
	/**
	 * Basic constructor, sets both question and alt to null.
	 */
	public Question()
	{	
		question=null;
		this.alt=null;
	}
	
	public Question(String question)
	{
		this.question=question;
		this.alt=null;
	}

	public Question(String question, String[] alt)
	{
		this.question=question;
		this.alt=alt;
	}

	/**
	 * This function is used when loading a question from a file.
	 * 
	 * @param data The string data read from a question pool file
	 * 
	 * @return boolean True if the data was succesfully parsed, false if not.
	 */
	public abstract boolean fromFile(String data);

	/**
	 * Checks if a given awnser is correct or not. If the awnser is in the wrong format it throws a WrongAwnserFormatException.
	 * 
	 * @param awnser The awnser.
	 * 
	 * @return boolean True if the awnser is correct, false if not.
	 * 
	 * @throws WrongAwnserFormatException If the awnser is given in the wrong format this exception will be thrown.
	 * 
	 */
	public abstract boolean checkAwnser(String awnser)
	throws WrongAwnserFormatException;

	/**
	 * Returns a string represantation of the question.
	 * 
	 * @return String String representation of question
	 */
	public abstract String toString();

	public String[] getAlt()
	{
		return(alt);
	}
	
	/**
	 * Gets the question.
	 * 
	 * @return String Question.
	 */
	public String getQuestion()
	{
		return(question);
	}
}
