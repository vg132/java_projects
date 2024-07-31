package com.vgsoftware.jdownloader.engine;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.vgsoftware.jdownloader.data.Status;

public class Downloader extends Thread
{
	public static int idCounter=0;

	private long totalSize=0;
	private long downloaded=0;
	private URL url=null;
	private File file=null;
	private JDEngine engine=null;
	private boolean runDownload=true;
	private boolean paused=false;
	private Status status=null;
	private int resumes=0;
	private long startTime=0;
	private int downloadId=0;
	private long speed=0;

	public Downloader(JDEngine engine, URL url, File file)
	{
		downloadId=idCounter++;
		this.engine=engine;
		this.url=url;
		this.file=file;
		downloaded=file.length();
	}

	public void run()
	{
		long lastCheck=0;
		long nextStop=0;
		int c=-1;
		DataInputStream dis=null;
		RandomAccessFile raf=null;
		HttpURLConnection httpURL=null;
		startTime=System.currentTimeMillis();
		while(runDownload)
		{
			if(paused==false)
			{
				try
				{
					//Open connection and set range
					resumes++;
					httpURL=(HttpURLConnection)url.openConnection();
					httpURL.addRequestProperty("Range","bytes="+downloaded+"-");
					try
					{
						totalSize=Integer.parseInt(httpURL.getHeaderField("Content-Length"));
					}
					catch(NumberFormatException nfe)
					{
						totalSize=-1;
					}
					lastCheck=downloaded;
					nextStop=System.currentTimeMillis()+1000;
					raf=new RandomAccessFile(file,"rw");
					if(raf.length()>0)
						raf.seek(raf.length());
					dis=new DataInputStream(httpURL.getInputStream());
					while(((c=dis.read())!=-1)&&(paused==false))
					{
						raf.write(c);
						downloaded++;
						if(System.currentTimeMillis()>nextStop)
						{
							speed=downloaded-lastCheck;
							lastCheck=downloaded;
							nextStop=System.currentTimeMillis()+1000;
						}
					}
					raf.close();
					dis.close();
					httpURL.disconnect();
					if(totalSize==downloaded)
					{
						engine.downloadFinished(downloadId);
						runDownload=false;
						FileSystem.removeExtension(file,".jde");
					}
				}
				catch(IOException io)
				{
					try
					{
						if(httpURL.getResponseCode()==416)
						{
							engine.downloadFinished(downloadId);
							runDownload=false;
						}
						else
						{
							io.printStackTrace(System.err);
						}
					}
					catch(Exception e)
					{
					}
				}
			}
			else
			{
				try
				{
					Thread.sleep(60000);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace(System.err);
				}
			}
			speed=0;
		}
	}

	public int getDownloadId()
	{
		return(downloadId);
	}

	public Status getStatus()
	{
		status=new Status();
		status.setDownloaded(downloaded);
		status.setFile(file);
		status.setId(downloadId);
		status.setResumes(resumes);
		status.setRunning(!paused);
		status.setSize(totalSize);
		status.setStartTime(startTime);
		status.setUrl(url);
		status.setSpeed(speed);
		return(status);
	}

	public void stopDownload()
	{
		runDownload=false;
		this.interrupt();
	}

	public void pauseDownload()
	{
		paused=!paused;
		this.interrupt();
	}

	public File getFile()
	{
		return(file);
	}

	public URL getUrl()
	{
		return(url);
	}
}
