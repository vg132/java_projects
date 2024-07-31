package com.vgsoftware.VMap.Model;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Image;

import com.vgsoftware.VMap.Util.Log;

public class TileLoader extends Thread
{
	private Hashtable imageCache=new Hashtable();
	
	private Hashtable mapLookupTable=new Hashtable();
	private String mapLookupTableFilePath="file:///Ms/Other/Test/map.map";
	private boolean initialized=false;
	
	private String mapFilePath="file:///Ms/Other/Test/map.dat";
	private FileConnection mapFileConnection;
	private DataInputStream mapInputStream;
	private byte[] mapData;

	public TileLoader()
	{
		this.start();
	}
	
	public Image getTile(String name)
	{
		Log.log("getTile: '"+name+"'");
		if(imageCache.containsKey(name))
		{
			Log.log("Found in cache");
			return (Image)imageCache.get(name);
		}
		if(mapLookupTable.containsKey(name))
		{
			Log.log("Tile is in lookup table");
			MapPart mapPart=(MapPart)mapLookupTable.get(name);
			try
			{
				Log.log("Start: '"+mapPart.start+"'");
				Log.log("Length: '"+mapPart.length+"'");
				Log.log("Convert data to image");
				Image tmp=Image.createImage(mapData,mapPart.start,mapPart.length);
				if(!imageCache.containsKey(name))
				{
					Log.log("Adding file to cache: '"+name+"'");
					imageCache.put(name,tmp);
				}
				return tmp; 
			}
			catch(Exception io)
			{
				Log.log("IOException in TileLoader.getTile: "+io.getMessage());
			}
		}
		Log.log("tile was not found retuning null");
		return null;
	}
	
	public void run()
	{
		Log.log("read lookup table");
		ReadLookupTable();
		Log.log("Open map file");
		OpenMapFile();
		Log.log("Tell everyone we are finished");
		initialized=true;
	}

	public boolean isReady()
	{
		return this.initialized;
	}

	private void OpenMapFile()
	{
		try
		{
			mapFileConnection=(FileConnection)Connector.open(mapFilePath);
			if(mapFileConnection.exists())
			{
				mapInputStream = mapFileConnection.openDataInputStream();
				mapData=new byte[(int)mapFileConnection.fileSize()];
				Log.log("Start reading file: '"+mapFileConnection.fileSize()+"'");
				mapInputStream.read(mapData);
				Log.log("Finished reading file");
				mapInputStream.close();
			}
		}
		catch(IOException io)
		{
			Log.log("IOException when opening map file: "+io.getMessage());
		}
	}
	
	private void ReadLookupTable()
	{
		FileConnection lookupTableFileConnection;
		try
		{
			lookupTableFileConnection=(FileConnection)Connector.open(mapLookupTableFilePath);
			if(lookupTableFileConnection.exists())
			{
				DataInputStream lookupTableInputStream=lookupTableFileConnection.openDataInputStream();
				
				byte[] nameBuffer = new byte[20];
				byte[] lengthBuffer = new byte[10];
				byte[] posBuffer=new byte[10];
				while (lookupTableInputStream.available() > 0)
				{
					lookupTableInputStream.read(nameBuffer);
					String name = new String(nameBuffer).trim();
					lookupTableInputStream.read(posBuffer);
					lookupTableInputStream.read(lengthBuffer);
					int length = Integer.parseInt(new String(lengthBuffer).trim());
					int pos=Integer.parseInt(new String(posBuffer).trim());
					mapLookupTable.put(name, new MapPart(pos,length));
				}
			}
		}
		catch(IOException io)
		{
			Log.log("IOException when reading lookup table: "+io.getMessage());
		}
	}

	class MapPart
	{
		public int start;
		public int length;
		
		public MapPart(int start, int length)
		{
			this.start=start;
			this.length=length;
		}
	}
}
