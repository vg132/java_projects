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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * The server program, here is where the real calculations happen.
 */
public class FibonacciImpl implements Fibonacci
{
	public FibonacciImpl()
	throws RemoteException
	{
		UnicastRemoteObject.exportObject(this);
	}
	
	public BigInteger getFibonacci(int n) throws RemoteException
	{
		return(getFibonacci(new BigInteger(Integer.toString(n))));
	}

	public BigInteger getFibonacci(BigInteger n) throws RemoteException
	{
		System.out.println("Calculating the "+n+"th Fibonacci number.");
		BigInteger zero=new BigInteger("0");
		BigInteger one=new BigInteger("1");
		
		if(n.equals(zero))
			return(zero);
		else if(n.equals(one))
			return(one);
			
		BigInteger i=one;
		BigInteger a=zero;
		BigInteger b=one;
		
		while(i.compareTo(n)==-1)
		{
			BigInteger temp=b;
			b=b.add(a);
			a=temp;
			i=i.add(one);
		};
		return(b);
	}
}
