



/**
 * @author vikto-ga
 */
public class U211Client
{
	public static void main(String arg[])
	{	
		U211BallWindow bw=new U211BallWindow();
		bw.updateBalls(null);
		U211RMIClient client=new U211RMIClient(bw);
		client.start();
	}
}