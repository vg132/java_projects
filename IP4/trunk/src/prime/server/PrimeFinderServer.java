package prime.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrimeFinderServer extends UnicastRemoteObject implements PrimeFinderServerInterface, Runnable
{
	private List<PrimeData> unCalculatedNumbers=new ArrayList<PrimeData>();
	private Map<Long,PrimeData> sentNumbers=new HashMap<Long,PrimeData>();
	private List<Long> foundPrimes=new ArrayList<Long>();
	private long nextStart=0;
	private Thread cleaner=null;
	private ServerConfig sc=null;
	private long maxPrimeNumber=-1;
	private File stateFile=null;
	private RandomAccessFile rafLog=null;

	/**
	 * Default constructor, setup the log file, load the state from last session.
	 */
	public PrimeFinderServer(ServerConfig sc)
	throws RemoteException
	{
		this.sc=sc;
		loadState();
		try
		{
			rafLog=new RandomAccessFile(sc.getPrimeLogFile(),"rw");
			rafLog.seek(rafLog.length());
		}
		catch(IOException io)
		{
		}
		cleaner=new Thread(this);
		cleaner.start();
	}

	/**
	 * Load the satate from the last session.
	 */
	private void loadState()
	{
		try
		{
			stateFile=new File(sc.getStateFile());
			if(stateFile.exists())
			{
				DataInputStream dis=new DataInputStream(new FileInputStream(stateFile));
				maxPrimeNumber=dis.readLong();
				nextStart=dis.readLong();
				//read until EOF exception is thrown.
				while(true)
				{
					Long start=dis.readLong();
					Long size=dis.readLong();
					unCalculatedNumbers.add(new PrimeData(start,size));
				}
			}
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * Save the current state, max prime number found, the next start number and all
	 * numbers that have not been calculated. If we have a crash we dont want to lose
	 * anything.
	 */
	private synchronized void saveState()
	{
		try
		{
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(stateFile));
			dos.writeLong(maxPrimeNumber);
			dos.writeLong(nextStart);
			for(PrimeData pd : sentNumbers.values())
			{
				dos.writeLong(pd.getStartNr());
				dos.writeLong(pd.getSize());
			}
			for(PrimeData pd : unCalculatedNumbers)
			{
				dos.writeLong(pd.getStartNr());
				dos.writeLong(pd.getSize());
			}
			dos.close();
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * Save the prime numbers found to a log file.
	 */
	private synchronized void saveToLog(List<Long> list)
	{
		try
		{
			for(Long l : list)
				rafLog.writeLong(l);
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * If no result from a sent number interval has been given within 
	 * 15 minutes resend it to a new client.
	 */
	public void run()
	{
		Iterator<PrimeData> iter=null;
		PrimeData pd=null;
		while(true)
		{
			try
			{
				Thread.sleep(60000);
				iter=sentNumbers.values().iterator();
				while(iter.hasNext())
				{
					pd=iter.next();
					if(pd.getSent()<System.currentTimeMillis()-(sc.getTimeout()*60000))
					{
						unCalculatedNumbers.add(pd);
						iter.remove();
					}
				}
			}
			catch(InterruptedException ie)
			{
			}
		}
	}

	/**
	 * @see prime.server.PrimeFinderServerInterface#getMaxPrime()
	 */
	public Long getMaxPrime()
	throws RemoteException
	{
		return(maxPrimeNumber);
	}

	/**
	 * @see prime.server.PrimeFinderServerInterface#getNextInterval(int)
	 */
	public synchronized PrimeData getNextInterval()
	throws RemoteException
	{
		if((nextStart>=Long.MAX_VALUE)||(nextStart<0))
			return(null);
		PrimeData pd=null;
		if(unCalculatedNumbers.size()==0)
		{
			pd=new PrimeData();
			pd.setStartNr(nextStart);
			pd.setSize(sc.getIntervalSize());
			nextStart+=sc.getIntervalSize();
		}
		else
		{
			pd=unCalculatedNumbers.remove(0);
		}
		sentNumbers.put(pd.getId(),pd);
		saveState();
		return(pd);
	}

	/**
	 * @see prime.server.PrimeFinderServerInterface#getPrimes()
	 */
	public Integer getPrimes()
	throws RemoteException
	{
		return(foundPrimes.size());
	}

	/**
	 * @see prime.server.PrimeFinderServerInterface#results(java.lang.Long, java.util.List)
	 */
	public synchronized void results(Long id, List<Long> primes)
	throws RemoteException
	{
		if(sentNumbers.remove(id)!=null)
		{
			foundPrimes.addAll(primes);
			saveToLog(primes);
			if(primes.get(primes.size()-1)>maxPrimeNumber)
			{
				maxPrimeNumber=primes.get(primes.size()-1);
				saveState();
			}
		}
	}
}
