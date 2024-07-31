package u222;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class PrimeHandler extends Thread
{
	private DataOutputStream dos=null;
	private RandomAccessFile raf=null;
	private long lastPrime=2;
	private long lastFind=0;
	private File logFile=new File("prime.txt");

	public PrimeHandler()
	{
		try
		{
			if(logFile.exists())
			{
				DataInputStream dis=new DataInputStream(new FileInputStream(logFile));
				lastFind=dis.readLong();
				lastPrime=dis.readLong();
				dis.close();
			}
			this.start();
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * Check for new prime numbers once every second.
	 */
	public void run()
	{
		long currentNumber=lastPrime;
		while(currentNumber<Long.MAX_VALUE)
		{
			try
			{
				if(isPrime(++currentNumber))
				{
					lastPrime=currentNumber;
					lastFind=System.currentTimeMillis();
					savePrime();
				}
				Thread.sleep(1000);
			}
			catch(InterruptedException ie)
			{
			}
		}
	}
	
	/**
	 * Return the current prime number and the date of when it was found if
	 * the password is correct, otherwise return a error message.
	 */
	public String getPrime(String password)
	{
		if(password.equals("2isAnOddPrime"))
			return(lastPrime+" found at "+new Date(lastFind));
		return("Wrong password!");
	}

	/**
	 * Save the latest prime number found and the time when it was found.
	 */
	private void savePrime()
	{
		try
		{
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(logFile));
			dos.writeLong(lastFind);
			dos.writeLong(lastPrime);
			dos.close();
		}
		catch(IOException io)
		{
		}
	}

	/**
	 * Check if a number is a prime number or not.
	 */
	private boolean isPrime(long nr)
	{
		boolean prime=true;
		long start=2;
		while(start<((nr/2)+1))
		{
			if(nr%start==0)
			{
				prime=false;
				break;
			}
			start++;
		}
		return(prime);
	}
}
