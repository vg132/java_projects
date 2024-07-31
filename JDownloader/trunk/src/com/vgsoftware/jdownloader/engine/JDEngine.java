package com.vgsoftware.jdownloader.engine;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vgsoftware.jdownloader.data.Status;
import com.vgsoftware.jdownloader.gui.IGui;

public class JDEngine implements IJDEngine
{
	private IGui gui=null;
	private Map<Integer,Downloader> downloaders=new HashMap<Integer,Downloader>();

	public boolean addDownload(File file, URL url)
	{
		if(!file.exists())
			FileSystem.addExtension(file,".jde");
		if(file.exists())
		{
			int retVal=gui.eventResumeRestartCancel(file,url);
			if(retVal==1)
				file.delete();
			else if(retVal==2)
				return(false);
			if(!file.getAbsolutePath().endsWith(".jde"))
				file.renameTo(new File(file.getAbsolutePath()+".jde"));
		}
		Downloader d=new Downloader(this,url,file);
		d.start();
		downloaders.put(d.getDownloadId(),d);
		return(true);
	}

	public void downloadFinished(int id)
	{
		gui.eventFinished(id);
	}

	public boolean cancelDownload(int id, boolean deleteFile)
	{
		if(downloaders.containsKey(id))
		{
			Downloader d=downloaders.remove(id);
			d.stopDownload();
			if(deleteFile)
				d.getFile().delete();
		}
		return(false);
	}

	public boolean cancelDownload(int id)
	{
		return(cancelDownload(id,false));
	}

	public Status getDownload(int id)
	{
		if(downloaders.containsKey(id))
			return(downloaders.get(id).getStatus());
		else
			return(null);
	}

	public List<Status> getDownloades()
	{
		List<Status> status=new ArrayList<Status>();
		Iterator<Downloader> iter=downloaders.values().iterator();
		while(iter.hasNext())
		{
			status.add(iter.next().getStatus());
		}
		return(status);
	}

	public void pause(int id)
	{
		if(downloaders.containsKey(id))
		{
			downloaders.get(id).pauseDownload();
		}
	}

	public void quit()
	{
		Iterator<Downloader> iter=downloaders.values().iterator();
		while(iter.hasNext())
		{
			iter.next().stopDownload();
		}
	}

	public void setGui(IGui gui)
	{
		this.gui=gui;
	}
}
