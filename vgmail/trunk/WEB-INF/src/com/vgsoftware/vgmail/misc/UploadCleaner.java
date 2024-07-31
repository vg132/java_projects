/*
 * Created on: 2003-nov-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-13 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

import java.io.File;

/**
 * This class will check all files in a specified directory
 * and if it finds a file that is older then max age the file will be deleted.
 */
public class UploadCleaner extends Thread
{
	private String directory=null;
	private int interval=0;
	private int maxage=0;
	private boolean stopLoop=false;

	/**
	 * Default constructor.
	 * 
	 * @param directory The directorys where the files are located.
	 * @param interval The interval between the checks, time in minutes.
	 * @param maxage The maximum age of a file in this directory, time in minutes. 
	 */
	public UploadCleaner(String directory, int interval, int maxage)
	{
		this.directory=directory;
		this.interval=interval*60*1000;
		this.maxage=maxage*60*1000;
	}

	/**
	 * Main loop, run until stopLoop is true.
	 * Delete all files older then maxpage.
	 */
	public void run()
	{
		while(!stopLoop)
		{
			try
			{
				Thread.sleep(interval);
				File[] fileList=new File(directory).listFiles();
				for(int i=0;i<fileList.length;i++)
				{
					if(fileList[i].lastModified()<(System.currentTimeMillis()-maxage))
						fileList[i].delete();
				}
			}
			catch(InterruptedException ie)
			{
			}
		}
	}

	/**
	 * Stop the main loop.
	 */
	public void stopThread()
	{
		stopLoop=true;
	}
}
