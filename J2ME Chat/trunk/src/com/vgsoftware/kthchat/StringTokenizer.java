package com.vgsoftware.kthchat;

/**
 * StringTokenizer is a striped down version of the 
 * StringTokenizer that is included with Java Standard Edition.
 * 
 * @author Viktor
 * @version 1.0
 */
public class StringTokenizer
{
	private int currentPosition;
	private int newPosition;
	private String str;
	private char delimiter;

	/**
	 * Class constructor specifying the string to work on and
	 * the delimiter.
	 * 
	 * @param str the string
	 * @param delimiter the delimiter
	 */
	public StringTokenizer(String str, char delimiter)
	{
		currentPosition=0;
		newPosition=-1;
		this.str=str;
		this.delimiter=delimiter;
	}

	/**
	 * Checks if there are any tokens left in the string.
	 * 
	 * @return true if there are more tokens, otherwise false
	 */
	public boolean hasMoreTokens()
	{
		if(currentPosition==-1)
			return(false);
		else
			return(true);
	}

	/**
	 * Gets the next token or null if there are no more tokens.
	 * 
	 * @return the next token or null
	 */
	public String nextToken()
	{
		try
		{
			if(currentPosition==-1)
				return(null);
			newPosition=str.indexOf(delimiter,currentPosition);
			String newStr=null;
			if(newPosition==-1)
			{
				newStr=str.substring(currentPosition);
				currentPosition=-1;
			}
			else
			{
				newStr=str.substring(currentPosition,newPosition);
				currentPosition=newPosition+1;
			}
			return(newStr.trim());
		}
		catch(StringIndexOutOfBoundsException sioobe)
		{
			sioobe.printStackTrace();
		}
		return(null);
	}

	/**
	 * Gets the number of tokens left.
	 * 
	 * @return the number of tokens left
	 */
	public int countTokens()
	{
		int count=0;
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(1)==delimiter)
				count++;
		}
		return(count);
	}
}