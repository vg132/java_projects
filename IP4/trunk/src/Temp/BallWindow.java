package Temp;
/*
 * Created on 2005-maj-04
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */

import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * @author vikto-ga
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BallWindow extends JFrame
{
	private BallPanel pnlBalls=new BallPanel();
	private BufferedImage bi=null;

	public BallWindow()
	{
		this.setSize(500,500);
		this.setContentPane(pnlBalls);
		this.setVisible(true);
	}
/*
	public void update(Graphics g)
	{
		System.out.print("Draw!!!");
	}*/

	public void updateBalls(Vector balls)
	{
		pnlBalls.updateBalls(balls);
	}
}
