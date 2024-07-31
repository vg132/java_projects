package prime.server;

import java.io.Serializable;
import java.util.Random;

/**
 * Class for holding and sending information to a client
 * about his interval to search prime numbers in.
 */
public class PrimeData implements Serializable
{
	private static Random rnd=new Random();
	private long id=0;
	private long sent=0;
	private long startNr=0;
	private long size=0;

	public PrimeData()
	{
		this.sent=System.currentTimeMillis();
		this.id=rnd.nextLong();
	}
	
	public PrimeData(long startNr, long size)
	{
		this.startNr=startNr;
		this.size=size;
		this.sent=System.currentTimeMillis();
		this.id=rnd.nextLong();
	}
	
	/**
	 * @return Returns the endNr.
	 */
	public long getSize()
	{
		return(size);
	}

	/**
	 * @param size The size to set.
	 */
	public void setSize(long size)
	{
		this.size=size;
	}

	/**
	 * @return Returns the startNr.
	 */
	public long getStartNr()
	{
		return(startNr);
	}

	/**
	 * @param startNr The startNr to set.
	 */
	public void setStartNr(long startNr)
	{
		this.startNr=startNr;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return(id);
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		this.id=id;
	}

	/**
	 * @return Returns the sent.
	 */
	public long getSent()
	{
		return(sent);
	}

	/**
	 * @param sent The sent to set.
	 */
	public void setSent(long sent)
	{
		this.sent=sent;
	}
}
