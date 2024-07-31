/**
 * Copyright (C) VG Software 2003-mar-26
 *  
 * Document History
 * 
 * Created: 2003-mar-26 15:20:51 by Viktor
 * 
 */
package com.vgsoftware.questions.exception;

/**
 * 
 * 
 * @author Viktor
 * 
 */
public class WrongAwnserFormatException extends Exception
{
  private String message;

  public WrongAwnserFormatException()
  {
    super();
    message = "unknown";
  }

  public WrongAwnserFormatException(String message)
  {
    super(message);
    this.message = message;
  }
  
  public String getMessage()
  {
		return(message);
  }
  
  public String toString()
  {
		return("WrongAwnserFormatException: "+message);
  }
}
