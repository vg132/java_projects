package prime.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PrimeFinderServerInterface extends Remote
{
	/**
	 * Gets a <code>PrimeData</code> object with information about the next
	 * calculation.
	 * 
	 * @throws RemoteException
	 */
	public PrimeData getNextInterval()
	throws RemoteException;
	/**
	 * Called to give back the server a list with all primes found in the
	 * interval given to the client.
	 * 
	 * @param id the id for this calculation.
	 * @param primes list with all primes found.
	 * 
	 * @throws RemoteException
	 */
	public void results(Long id, List<Long> primes)
	throws RemoteException;
	/**
	 * Gets the highest prime number found.
	 * 
	 * @return the highest prime number found.
	 * 
	 * @throws RemoteException
	 */
	public Long getMaxPrime()
	throws RemoteException;
	/**
	 * Gets the nr of prime numbers found.
	 * 
	 * @return nr of prime numbers found.
	 * 
	 * @throws RemoteException
	 */
	public Integer getPrimes()
	throws RemoteException;
}
