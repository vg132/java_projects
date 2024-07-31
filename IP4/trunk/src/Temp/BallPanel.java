package Temp;
/*
 * Created on 2005-maj-04
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.awt.Graphics;

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
/**
 * @author vikto-ga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BallPanel extends JPanel
{
	private BufferedImage bi=null;
	private Vector balls=null;

	public void updateBalls(Vector balls)
	{
		this.balls=balls;
		this.repaint();
//		this.invalidate();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;

		if(balls!=null)
		{
			bi=(BufferedImage)this.createImage(this.getWidth(),this.getHeight());
			Graphics2D gc=bi.createGraphics();
			for(int i=0;i<balls.size();i+=3)
				gc.fillOval(((Integer)balls.get(i)).intValue(),((Integer)balls.get(i+1)).intValue()-30,((Integer)balls.get(i+2)).intValue(),((Integer)balls.get(i+2)).intValue());
			g2.drawImage(bi,null,0,0);
		}
		/*
		
		if(bi==null)
		{
			int w = this.getWidth();
      int h = this.getHeight();
			
			
			for(int x=0; x<w; x+=10)
			{
				gc.drawLine(x, 0, x, h);
			}
      for(int y=0; y<h; y+=10)
      {
      	gc.drawLine(0, y, w, y);
      }
		}
		g2.drawImage(bi, null, 0, 0);*/
	}
}
