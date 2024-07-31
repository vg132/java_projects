/*
 * Created on 2003-sep-01
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-01 Created by Viktor.
 */

package ip1.ass8.a;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The client side.
 * Get the remote object from the server, and call the calculation function on it.
 * The calculation happens on the server and the result is then sent back to the client.
 */
public class FibonacciClient
{
	public static void main(String args[])
	{
		if((args.length==0)||(!args[0].startsWith("rmi:")))
		{
			System.out.println("Usage: java FibonacciClient rmi://host.domain.port/fibonaci number");
			return;
		}
		try
		{
			Object o=Naming.lookup(args[0]);
			Fibonacci calculator=(Fibonacci)o;
			for(int i=1;i<args.length;i++)
			{
				BigInteger index=new BigInteger(args[i]);
				BigInteger f=calculator.getFibonacci(index);
				System.out.println("The "+args[i]+"th Fibonacci number is "+f);
			}
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(NotBoundException nbe)
		{
			nbe.printStackTrace(System.err);
		}
	}
}
