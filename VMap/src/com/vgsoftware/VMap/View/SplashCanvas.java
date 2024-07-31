package com.vgsoftware.VMap.View;

//import java.io.IOException;
//import java.io.InputStream;

//import javax.microedition.io.Connector;
//import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class SplashCanvas extends Canvas
{
	private String text="Kalle Anka";
	private Image bgImage=null;

	public SplashCanvas()
	{
		this.setFullScreenMode(true);
/*		String url = "file:///Ms/Other/Test/image.png";
		FileConnection conn = null;
		try
		{
			conn = (FileConnection) Connector.open(url);
			if (conn.exists())
			{
				//String tmpString = "";
				InputStream is = conn.openInputStream();
				bgImage=Image.createImage(is);
				is.close();
				byte buffer[] = new byte[1024];
				int length;
				while ((length = is.read(buffer, 0, 1024)) > 0)
				{
					tmpString += new String(buffer, 0, length);
				}
				is.close();
				text=tmpString;
			}
		} catch (IOException ex)
		{
		}*/
	}

	protected void paint(Graphics g)
	{
		//g.drawString("VMap by Viktor Gars", 50, 50, 0);
		if(bgImage!=null)
		{
			g.drawImage(bgImage,0,0,0);
		}
		g.drawString(text,50,50,0);
	}
}
