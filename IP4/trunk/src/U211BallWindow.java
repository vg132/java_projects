


import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * @author vikto-ga
 */
public class U211BallWindow extends JFrame
{
	private U211BallPanel pnlBalls=new U211BallPanel();
	private BufferedImage bi=null;

	public U211BallWindow()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
