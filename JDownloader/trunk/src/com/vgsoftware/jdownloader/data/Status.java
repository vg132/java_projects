package com.vgsoftware.jdownloader.data;

import java.io.File;
import java.net.URL;

public class Status
{
	private int id=-1;
	private int resumes=-1;
	private long startTime=-1;
	private long size=-1;
	private long downloaded=-1;
	private long speed=0;
	private URL url=null;
	private File file=null;
	private boolean running=false;

	public long getSpeed()
	{
		return(speed);
	}

	public void setSpeed(long speed)
	{
		this.speed=speed;
	}

	public long getDownloaded()
	{
		return(downloaded);
	}

	public void setDownloaded(long downloaded)
	{
		this.downloaded=downloaded;
	}

	public File getFile()
	{
		return(file);
	}

	public void setFile(File file)
	{
		this.file=file;
	}

	public int getId()
	{
		return(id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public int getResumes()
	{
		return(resumes);
	}

	public void setResumes(int resumes)
	{
		this.resumes=resumes;
	}

	public boolean isRunning()
	{
		return(running);
	}

	public void setRunning(boolean running)
	{
		this.running=running;
	}

	public long getSize()
	{
		return(size);
	}

	public void setSize(long size)
	{
		this.size=size;
	}

	public long getStartTime()
	{
		return(startTime);
	}

	public void setStartTime(long startTime)
	{
		this.startTime=startTime;
	}

	public URL getUrl()
	{
		return(url);
	}

	public void setUrl(URL url)
	{
		this.url=url;
	}
}
