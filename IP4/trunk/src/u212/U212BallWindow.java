package u212;



import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * @author vikto-ga
 */
public class U212BallWindow extends JFrame
{
	private U212BallPanel pnlBalls=new U212BallPanel();
	private BufferedImage bi=null;

	public U212BallWindow()
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
