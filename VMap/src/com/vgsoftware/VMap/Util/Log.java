package com.vgsoftware.VMap.Util;

import java.io.OutputStreamWriter;
import java.util.Date;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class Log
{
	private static FileConnection logFileConnection = null;
	private static OutputStreamWriter logOutputStream = null;
	
	public synchronized static final void log(String message)
	{
		try
		{
			if (logFileConnection == null || logOutputStream == null)
			{
				logFileConnection = (FileConnection) Connector.open("file:///Ms/Other/Test/log.txt");
				if (!logFileConnection.exists())
				{
					logFileConnection.create();
				}
				logOutputStream = new OutputStreamWriter(logFileConnection.openOutputStream());
			}
			logOutputStream.write(new Date().toString() + " - " + message);
			logOutputStream.write("\r\n");
		}
		catch (Exception e)
		{
		}
	}
}
