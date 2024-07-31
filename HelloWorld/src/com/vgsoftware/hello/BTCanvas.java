package com.vgsoftware.hello;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

public class BTCanvas extends Canvas implements Runnable
{

	private MIDlet midlet;
	private BTConnector btConnect; // class used to get the bluetooth devices.

	private boolean active = true; // active while thread is running.

	private Vector devices; // A vector with the devices received form the
													// BTConnector.
	private String[] deviceNames; // An array with the device names

	private int index = -1; // Keep track of the bluetooth device that is
													// selected.
	private int midIndex;

	private final int WIDTH, HEIGHT; // Height and width of the canvas.

	// used for the connection animation
	private boolean connecting = false;
	private int connectX = 10;

	// Used for the device searching animation
	private int searchAnimDeg = 0;
	private float[] sinTable = new float[640];

	public BTCanvas(MIDlet m)
	{
		midlet = m;
		setFullScreenMode(true);

		// Create the sinus table that are used for the searching animation.
		for (int i = 0; i < 640; i++)
		{
			sinTable[i] = (float) Math.sin(Math.toRadians((double) i)) * 5;
		}

		WIDTH = getWidth();
		HEIGHT = getHeight();
		connectX = WIDTH / 2 - 40;

		midIndex = ((HEIGHT - 20) / 20) / 2;
		System.out.println("midIndex=" + midIndex);

		btConnect = new BTConnector();
		devices = btConnect.getDevices();

		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	public void paint(Graphics g)
	{
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(0x000000);

		deviceNames = btConnect.getDeviceNames();

		// Display all the devices that are found by the BTConnector.
		if (!connecting)
		{
			for (int i = 0; i < btConnect.getSize(); i++)
			{
				int offset = 0;
				if (btConnect.getSize() * 20 > HEIGHT - 40)
				{
					if (index > midIndex)
					{
						offset = (index - midIndex) * 20;
					}
				}
				if (i == index)
				{
					g.setColor(0x00FF00);
					g.drawString(deviceNames[i], 0, 20 + i * 20 - offset, 0);
					g.setColor(0x000000);
				} else
				{
					g.drawString(deviceNames[i], 0, 20 + i * 20 - offset, 0);
				}
			}

			g.setColor(0xFFFFFF);
			g.fillRect(0, 0, WIDTH, 20);
			g.setColor(0x000000);
			g.drawString("Bluetooth Devices found...", 0, 0, Graphics.LEFT
					| Graphics.TOP);

			// Paint a little animation while searching for devices.
			if (!btConnect.doneSearchingDevices())
			{
				searchAnimDeg = searchAnimDeg >= 360 ? searchAnimDeg - 360
						: searchAnimDeg + 5;
				int centerX = WIDTH - 15;
				int centerY = 7;
				int x1 = (int) sinTable[searchAnimDeg] + centerX;
				int y1 = (int) sinTable[searchAnimDeg + 90] + centerY;
				int x2 = (int) sinTable[searchAnimDeg + 180] + centerX;
				int y2 = (int) sinTable[searchAnimDeg + 180 + 90] + centerY;
				g.drawLine(x1, y1, x2, y2);
			}

			g.fillRect(0, 15, WIDTH, 2);

			g.setColor(0xFFFFFF);
			g.fillRect(0, HEIGHT - 20, WIDTH, 20);
			g.setColor(0x000000);

			g.fillRect(0, HEIGHT - 18, WIDTH, 2);
			g.drawString("Exit", 0, HEIGHT, Graphics.LEFT | Graphics.BOTTOM);
			g.drawString("Connect...", WIDTH, HEIGHT, Graphics.RIGHT
					| Graphics.BOTTOM);

			// Paint a connecting animation when searching for a bluetooth service.
		} else
		{
			g.drawString("Connecting...", WIDTH / 2 - 40, HEIGHT / 2 - 12,
					Graphics.LEFT | Graphics.BOTTOM);
			g.drawRect(WIDTH / 2 - 40, HEIGHT / 2 - 10, 80, 20);
			g.fillRect(connectX, HEIGHT / 2 - 10, 5, 20);
			connectX = connectX >= WIDTH / 2 + 40 ? WIDTH / 2 - 45 : connectX + 1;

			g.setColor(0xFFFFFF);
			g.fillRect(WIDTH / 2 - 45, HEIGHT / 2 - 10, 5, 21);
			g.fillRect(WIDTH / 2 + 41, HEIGHT / 2 - 10, 5, 21);
			g.setColor(0x000000);

			g.drawString("Exit", 0, HEIGHT, Graphics.LEFT | Graphics.BOTTOM);
			g.drawString("Back...", WIDTH, HEIGHT, Graphics.RIGHT | Graphics.BOTTOM);
		}
	}

	public void keyPressed(int key)
	{
		switch (key)
		{
		case -1:
			index = index > 0 ? index - 1 : index;
			break;
		case -2:
			index = index < btConnect.getSize() - 1 ? index + 1 : index;
			break;
		case -3:
			break;
		case -4:
			break;
		case -6: // Left soft key, used to exit the midlet
			midlet.notifyDestroyed();
			break;
		case -7: // Right soft key is used to connect to a device or to start a new
							// search.
			if (!connecting && index != -1)
			{
				btConnect.connect(index);
				connecting = true;
			} else if (connecting)
			{
				index = -1;
				connecting = false;
				btConnect.startSearch();
			}
			break;
		}
	}

	public void run()
	{

		while (active)
		{
			repaint();

			// If a connection do a device is completed and a bt service is found
			if (btConnect.doneSearchingServices()
					&& btConnect.getUrl().compareTo("") != 0)
			{
				active = false;
			}

			try
			{
				Thread.sleep(10);
			} catch (Exception e)
			{
			}
		}

		// Open a new canvas and start the gps reading.
		GPS gps = (GPS) midlet;
		gps.startGPS();
	}

}
