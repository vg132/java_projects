/*
 * Created on 2005-mar-29
 */
package com.vgsoftware.vgutil.misc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author viktor
 */
public class DumpFile
{
	public static String getFileContent(String file)
	{
		String content="";
		String line="";
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null)
				content+=line+"\n";
			br.close();
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(content);
	}
}
