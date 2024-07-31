/**
 * Copyright (C) VG Software 2003-mar-29
 *  
 * Document History
 * 
 * Created: 2003-mar-29 14:09:53 by Viktor
 * 
 */
package com.vgsoftware.questions.qtype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import com.vgsoftware.questions.*;


/**
 * @author Viktor
 * 
 */
public class QuestionPool
{
	private static Random rand=new Random();
	private List unusedQuestions=new LinkedList();
	private List usedQuestions=new ArrayList();
	
	public static void main(String[] arga)
	{
		new QuestionPool();
	}
	
	public void reset()
	{
		for(int i=0;i<usedQuestions.size();i++)
			unusedQuestions.add(usedQuestions.get(i));
	}
	
	public Question getQuestion()
	{
		if(unusedQuestions.size()==0)
			reset();
		if(unusedQuestions.size()==0)
			return(null);
		int qNr=rand.nextInt(unusedQuestions.size());
		usedQuestions.add(unusedQuestions.get(qNr));
		return((Question)unusedQuestions.remove(qNr));
	}
	
	public QuestionPool()
	{
	}
	
	public QuestionPool(String filePath)
	{
		readFile(new File(filePath));
	}
	
	private void readFile(File file)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=br.readLine().trim();
			Question qObject=null;
			StringTokenizer st=null;
			while(line!=null)
			{
				st=new StringTokenizer(line,"|");
				if((st.countTokens()>0)&&(line.charAt(0)!=';')&&(!line.equals("")))
				{
					qObject=(Question)Class.forName(st.nextToken()).newInstance();
					qObject.fromFile(line);
					unusedQuestions.add(qObject);
				}
				line=br.readLine(); 
			}
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace(System.err);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(System.err);
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace(System.err);
		}
		catch(InstantiationException ie)
		{
			ie.printStackTrace(System.err);
		}
		catch(IllegalAccessException iae)
		{
			iae.printStackTrace(System.err);
		}
	}
}
