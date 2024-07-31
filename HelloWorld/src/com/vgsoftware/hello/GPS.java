package com.vgsoftware.hello;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;

public class GPS extends javax.microedition.midlet.MIDlet
{

	private Display display;
	private Canvas canvas;

	public GPS()
	{
		display = Display.getDisplay(this);
		canvas = new BTCanvas(this);
	}

	public void startGPS()
	{
		GPSCanvas gpsCanvas = new GPSCanvas(this);
		canvas = null;
		canvas = gpsCanvas;
		display.setCurrent(canvas);
	}

	public void startApp()
	{
		display.setCurrent(canvas);
	}

	public void pauseApp()
	{
	}

	public void destroyApp(boolean unconditional)
	{
	}
}
