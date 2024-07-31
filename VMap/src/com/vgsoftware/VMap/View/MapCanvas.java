package com.vgsoftware.VMap.View;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.vgsoftware.VMap.Model.Map;
import com.vgsoftware.VMap.Model.Position;
import com.vgsoftware.VMap.Model.TileManager;
import com.vgsoftware.VMap.Util.Log;

public class MapCanvas extends Canvas
{
	private Map map;
	private TileManager tileManager;
	private Test t=new Test();

	public MapCanvas()
	{
		this.setFullScreenMode(true);
		map = new Map();
		map.setCurrentPosition(new Position(17, 17.901698, 59.210973));

		tileManager = new TileManager();
		t.start();
	}

	protected void keyPressed(int keyCode)
	{
		int gameAction=getGameAction(keyCode);
		Log.log("Key pressed: '"+gameAction+"' ('"+keyCode+"')");
		switch(gameAction)
		{
			case DOWN:
				Log.log("Move Down");
				map.getCurrentPosition().MoveDown();
				break;
			case UP:
				Log.log("Move Up");
				map.getCurrentPosition().MoveUp();
				break;
			case LEFT:
				Log.log("Move Left");
				map.getCurrentPosition().MoveLeft();
				break;
			case RIGHT:
				Log.log("Move right");
				map.getCurrentPosition().MoveRight();
				break;
			default:
				Log.log("No action code");
				break;
		}
		repaint();
	}
	
	
	class Test extends Thread
	{
		public void run()
		{
			while (!tileManager.isReady())
			{
				try
				{
					repaint();
					Thread.sleep(1000);
				}
				catch (InterruptedException ie)
				{
				}
			}
		}
	}

	private int counter = 0;

	protected void paint(Graphics g)
	{
		counter++;
		g.setColor(255, 255, 255);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(0, 0, 0);
		if (tileManager.isReady())
		{
			g.drawString("Laddar: ready! " + counter, 0, 0, 0);
			drawMaps(g);
		}
		else
		{
			g.drawString("Laddar: " + counter, 0, 0, 0);
		}
	}

	private void drawMaps(Graphics g)
	{
		String[] files = map.getMapFiles();
		try
		{
			int x = (this.getWidth() / 2) - (256 * 3 / 2) + map.getMapPosition().getX();
			int y = (this.getHeight() / 2) - (256 * 3 / 2) + map.getMapPosition().getY();
			int ySize = 256;
			int xSize = 256;
			for (int i = 0; i < files.length; i++)
			{
				String file = files[i];
				Image bitmap = tileManager.getTile(file);
				if(bitmap!=null)
				{
					g.drawImage(bitmap, x, y, 0);
					x += xSize;
					if (((i + 1) % 3) == 0)
					{
						y += ySize;
						x = (this.getWidth() / 2) - (256 * 3 / 2) + map.getMapPosition().getX();
					}
				}
			}
			x = (this.getWidth() / 2);
			y = (this.getHeight() / 2);
			g.setColor(255,0,0);
			g.drawRect(x, y, 2, 2);
		}
		catch (Exception ex)
		{
			Log.log("Exception when drawing maps: " + ex.getMessage());
		}
	}
}
