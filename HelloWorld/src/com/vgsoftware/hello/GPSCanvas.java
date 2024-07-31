package com.vgsoftware.hello;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDlet;

public class GPSCanvas extends Canvas implements Runnable
{

	private final int WIDTH, HEIGHT;
	private MIDlet midlet;

	private String[] timerTxt = new String[] { "Start", "Stop", "Reset" };
	private int timerIndex = 0;
	private long timer = 0; // number of seconds active.
	private String timerStr = "0:00:00";
	private boolean runTimer = false;

	private double distance = 0; // the distance in meters.
	private double minutesKm = 0;
	private double speed = 0;

	private Image numberImage = null;

	private boolean SAT_OK = false;
	private int index = 0;

	private boolean landscapeMode = false; // The user can shoose to display the
																					// application in portrait/landscape
																					// mode
	private Image landscapeImage = null; // If landscape mode is used the graphics
																				// is painted to this image which is
																				// rotated and painted on the canvas.
	private Graphics landscapeGraphics;

	private Graphics imageGraphics;

	public GPSCanvas(MIDlet m)
	{
		midlet = m;

		try
		{
			numberImage = Image.createImage("/res/numbers.png");
		} catch (Exception e)
		{
			System.out.println("Failed to load number image!");
		}

		setFullScreenMode(true);

		WIDTH = getWidth();
		HEIGHT = getHeight();

		landscapeImage = Image.createImage(HEIGHT, WIDTH);
		landscapeGraphics = landscapeImage.getGraphics();

		GPSReader gpsReader = new GPSReader(BTConnector.url);
		// GPSReader gpsReader = new
		// GPSReader("btspp://000A5600F776:1;authenticate=false;encrypt=false;master=true");

		Thread t = new Thread(this);
		t.start();
	}

	public void paint(Graphics g)
	{
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(0x000000);

		if (!landscapeMode)
		{
			// PORTRAIT MODE
			g.drawString("Timer: ", 0, 0, 0);
			drawNumbers(timerStr, g, 0, 20);
			g.fillRect(0, 45, WIDTH, 2);

			g.drawString("Distance (m): " + String.valueOf(distance), 0, 50, 0);
			drawNumbers((int) distance, g, 0, 70);
			g.fillRect(0, 95, WIDTH, 2);

			g.drawString("Speed (km/h): " + GPSReader.SPEED_KMH_STR, 0, 100, 0);
			drawNumbers(round(speed, 2), g, 0, 120);
			g.fillRect(0, 145, WIDTH, 2);

			g.drawString("minutes/km: " + String.valueOf(minutesKm), 0, 150, 0);
			drawNumbers(round(minutesKm, 2), g, 0, 170);
			g.fillRect(0, 195, WIDTH - 40, 2);
			g.fillRect(WIDTH - 40, 180, 2, 17);
			g.fillRect(WIDTH - 40, 180, 40, 2);

			g.drawString(GPSReader.NUM_SATELITES + " : "
					+ GPSReader.NUM_SATELITES_STR, WIDTH / 2 - 3, HEIGHT, Graphics.LEFT
					| Graphics.BOTTOM);
		} else
		{
			// LANDSCAPE MODE
			landscapeGraphics.setColor(0xFFFFFF);
			landscapeGraphics.fillRect(0, 0, HEIGHT, WIDTH);
			landscapeGraphics.setColor(0x000000);

			landscapeGraphics.drawString("Timer: ", 0, 0, 0);
			drawNumbers(timerStr, landscapeGraphics, 0, 20);
			landscapeGraphics.fillRect(0, 45, HEIGHT, 2);

			landscapeGraphics.drawString("Distance (m): " + String.valueOf(distance),
					0, 50, 0);
			drawNumbers((int) distance, landscapeGraphics, 0, 70);
			landscapeGraphics.fillRect(0, 95, HEIGHT, 2);

			landscapeGraphics.drawString("Speed (km/h):", 0, 100, 0);
			landscapeGraphics.drawString("minutes/km:", HEIGHT / 2, 100, 0);
			drawNumbers(speed, landscapeGraphics, 0, 120);
			drawNumbers(minutesKm, landscapeGraphics, HEIGHT / 2, 120);
			landscapeGraphics.fillRect(0, 145, HEIGHT, 2);

			landscapeGraphics.drawString(GPSReader.NUM_SATELITES + " : "
					+ GPSReader.NUM_SATELITES_STR, HEIGHT / 2 - 3, WIDTH, Graphics.LEFT
					| Graphics.BOTTOM);
		}

		if (!landscapeMode)
		{
			g.drawString("Exit", 0, HEIGHT, Graphics.LEFT | Graphics.BOTTOM);
			g.drawString(timerTxt[timerIndex], WIDTH, HEIGHT - 15, Graphics.RIGHT
					| Graphics.BOTTOM);
			g
					.drawString(timerTxt[2], WIDTH, HEIGHT, Graphics.RIGHT
							| Graphics.BOTTOM);
		} else
		{
			landscapeGraphics.drawString("Exit", 0, WIDTH, Graphics.LEFT
					| Graphics.BOTTOM);
			landscapeGraphics.drawString(timerTxt[timerIndex], HEIGHT, WIDTH - 13,
					Graphics.RIGHT | Graphics.BOTTOM);
			landscapeGraphics.drawString(timerTxt[2], HEIGHT, WIDTH, Graphics.RIGHT
					| Graphics.BOTTOM);

			g.drawRegion(landscapeImage, 0, 0, HEIGHT, WIDTH, Sprite.TRANS_ROT270, 0,
					0, 0);
		}

	}

	// Draw the numbers image.
	private void drawNumbers(int number, Graphics g, int x, int y)
	{
		String num = String.valueOf(number);
		int tmp;
		for (int i = 0; i < num.length(); i++)
		{
			try
			{
				tmp = Integer.parseInt(num.substring(i, i + 1));
				g.drawRegion(numberImage, tmp * 20, 0, 20, 20, 0, x + (20 * i), y, 0);
			} catch (Exception e)
			{
			}
		}
	}

	private void drawNumbers(double number, Graphics g, int x, int y)
	{
		String num = String.valueOf(number);
		int tmp;
		for (int i = 0; i < num.length(); i++)
		{
			try
			{
				tmp = Integer.parseInt(num.substring(i, i + 1));
				g.drawRegion(numberImage, tmp * 20, 0, 20, 20, 0, x + (20 * i), y, 0);
			} catch (Exception e)
			{
				g.setColor(0x067606);
				g.fillRect(i * 20 + 8 + x, y + 16, 4, 4);
				g.setColor(0x000000);
			}
		}
	}

	private void drawNumbers(String num, Graphics g, int x, int y)
	{
		int tmp;
		for (int i = 0; i < num.length(); i++)
		{
			try
			{
				tmp = Integer.parseInt(num.substring(i, i + 1));
				g.drawRegion(numberImage, tmp * 20, 0, 20, 20, 0, x + (20 * i), y, 0);
			} catch (Exception e)
			{
				g.setColor(0x067606);
				g.fillRect(i * 20 + 8, y + 16, 4, 4);
				g.setColor(0x000000);
			}
		}
	}

	public void keyPressed(int key)
	{
		switch (key)
		{
		case 49:
			break;
		case 50:
			break;
		case -6: // Left soft button
			midlet.notifyDestroyed();
			break;
		case -7: // Right soft button
			switch (timerIndex)
			{
			case 0:
				runTimer = true;
				break;
			case 1:
				runTimer = false;
				break;
			}
			timerIndex = timerIndex == 1 ? 0 : timerIndex + 1;
			break;
		case -8: // 'C' button
			timer = 0;
			timerStr = "0:00:00";
			break;
		case 42: // Reset the calculated distance by pressing the star key
			distance = 0;
			break;
		case 35: // Pound key to change tha appearance.
			landscapeMode ^= true;
			break;
		}
	}

	// Method to calculate the travelled distance from the current speed
	private double calculateDistance(long startTime)
	{
		long stopTime = System.currentTimeMillis();
		long totTime = stopTime - startTime;

		double speed1 = (speed / 3600) * 1000; // m/s
		double dist = speed1 * ((double) totTime / 1000D);
		return dist;
	}

	// Method for removing decimals
	public double round(double d, int decimals)
	{
		String s = String.valueOf(d);
		int pos = s.indexOf('.');
		if (pos == -1)
		{
			return d;
		}
		if (s.length() > decimals + pos)
		{
			s = s.substring(0, pos + decimals);
		}
		return Double.parseDouble(s);
		// --------------------------------------------
		// double first = (int)d;
		// double next = d - first;
		// String s = String.valueOf(next);
		//
		// s = s.substring(2, s.length());
		//
		// if(s.length()>decimals){
		// s = s.substring(0, 2);
		// }
		//
		// s = "0." + s;
		// next = Double.parseDouble(s);
		// return first + next;
	}

	public void run()
	{

		int h = 0, m = 0, s = 0;

		long ptime = System.currentTimeMillis() / 1000;
		long distTime = System.currentTimeMillis();

		long startTime = 0;

		String secStr = "", minStr = "";

		while (true)
		{
			if (runTimer)
			{
				if (ptime != System.currentTimeMillis() / 1000)
				{
					timer++;
					ptime = System.currentTimeMillis() / 1000;

					h = (int) timer / 3600;
					m = (int) (timer % 3600) / 60;
					s = (int) timer % 60;

					secStr = s < 10 ? "0" : "";
					minStr = m < 10 ? "0" : "";

					timerStr = h + ":" + minStr + m + ":" + secStr + s;
				}
			}

			index++;
			SAT_OK = false;
			if (GPSReader.NUM_SATELITES > 2)
			{
				SAT_OK = true;
				speed = GPSReader.SPEED_KMH;

				try
				{
					minutesKm = round(
							(1000 / ((GPSReader.SPEED_KMH * 1000) / 3600)) / 60, 2);
				} catch (Exception e)
				{
				}
			}
			distance += calculateDistance(distTime);
			distTime = System.currentTimeMillis();

			repaint();
			try
			{
				Thread.sleep(100);
			} catch (Exception e)
			{
			}
		}
	}

}
