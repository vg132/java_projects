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
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the interface the client taks to, the commands are then relayed back to the server
 * and the servers response is relayed back to the client.
 */
public interface Fibonacci extends Remote
{
	public BigInteger getFibonacci(int n)
	throws RemoteException;
	public BigInteger getFibonacci(BigInteger n)
	throws RemoteException;
}
