package com.vgsoftware.VMap;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.vgsoftware.VMap.View.MapCanvas;

public class VMap extends MIDlet
{
	private Display display;

	public VMap()
	{
		display=Display.getDisplay(this);
	}
	
	protected void destroyApp(boolean unconditional)
	throws MIDletStateChangeException
	{	
	}

	protected void pauseApp()
	{
	}

	protected void startApp()
	throws MIDletStateChangeException
	{
		//SplashCanvas splash=new SplashCanvas();
		//display.setCurrent(splash);
		MapCanvas map=new MapCanvas();
		display.setCurrent(map);
	}
}
