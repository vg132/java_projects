package com.vgsoftware.VTracker;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.vgsoftware.VTracker.Bluetooth.Client;

public class VTracker extends MIDlet
{
	private Display display;
	private DebugCanvas canvas;

	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException
	{
	}

	protected void pauseApp()
	{
	}

	protected void startApp() throws MIDletStateChangeException
	{
		Client c = new Client();
		display = Display.getDisplay(this);
		canvas = new DebugCanvas(c);
		display.setCurrent(canvas);
	}

	class DebugCanvas extends Canvas implements Runnable
	{
		private int counter = 1;
		private Client client;

		public DebugCanvas(Client c)
		{
			client=c;
			Thread t=new Thread(this);
			t.start();
		}
		
		protected void paint(Graphics g)
		{
			g.setColor(0x0FFFFFF);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(0);
			for(int i=0;i<client.getStatus().size();i++)
			{
				g.drawString(client.getStatus().elementAt(i).toString(),0, i*13, Graphics.TOP | Graphics.LEFT);
			}
		}

		public void run()
		{
			while (true)
			{
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException ie)
				{
				}
				this.repaint();
				counter++;
			}
		}
	}
}
