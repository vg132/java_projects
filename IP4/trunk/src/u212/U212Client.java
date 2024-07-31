package u212;

/**
 * @author vikto-ga
 */
public class U212Client
{
	public static void main(String arg[])
	{	
		U212BallWindow bw=new U212BallWindow();
		bw.updateBalls(null);
		U212RMIClient client=new U212RMIClient(bw);
		client.start();
	}
}