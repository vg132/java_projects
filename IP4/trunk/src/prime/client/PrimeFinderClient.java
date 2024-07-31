package prime.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import prime.server.PrimeData;
import prime.server.PrimeFinderServerInterface;

public class PrimeFinderClient extends JFrame implements ActionListener, Runnable
{
	private Thread calc=null;
	private boolean calculate=true;
	private ClientConfig cc=null;
	private long primesFoundTotal=0;
	private long primesFoundSession=0;
	private JLabel lblPrimesTotal=new JLabel("0");
	private JLabel lblPrimesSession=new JLabel("0");
	private JLabel lblPrimesServerTotal=new JLabel("0");
	private JLabel lblMaxPrimeFound=new JLabel("0");
	private JButton btnQuit=new JButton("Quit");
	private JButton btnPause=new JButton("Pause");
	private File statFile=null;

	public static void main(String args[])
	{
		new PrimeFinderClient();
	}
	
	/**
	 * Default constructor, setup the GUI and calculations.
	 */
	public PrimeFinderClient()
	{
		//Setup the gui
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(250,250);
		this.setTitle("Prime Calculator");

		JPanel pnlMain=new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
		pnlMain.add(lblPrimesTotal);
		pnlMain.add(lblPrimesSession);
		pnlMain.add(lblPrimesServerTotal);
		pnlMain.add(lblMaxPrimeFound);
		
		btnPause.addActionListener(this);
		pnlMain.add(btnPause);		
		btnQuit.addActionListener(this);
		pnlMain.add(btnQuit);
		this.setContentPane(pnlMain);
		this.setVisible(true);

		//Setup the calculations
		cc=new ClientConfig();
		loadPrimeData();
		calc=new Thread(this);
		calc.setPriority(1);
		calculate=true;
		calc.start();
	}

	/**
	 * Load old primes found data from the stat file if there is one.
	 */
	private void loadPrimeData()
	{
		try
		{
			statFile=new File(cc.getStatFile());
			if(statFile.exists())
			{
				DataInputStream dis=new DataInputStream(new FileInputStream(statFile));
				primesFoundTotal=dis.readLong();
				dis.close();
			}
			
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * Save the number of primes found to a file.
	 */
	private void savePrimeData()
	{
		try
		{
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(statFile));
			dos.writeLong(primesFoundTotal);
			dos.close();
		}
		catch(IOException io)
		{
		}
	}
	
	/**
	 * Calculate prime numbers and show stat to the user.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			PrimeFinderServerInterface pfsi=null;
			pfsi=(PrimeFinderServerInterface)Naming.lookup(cc.getServerUrl());
			
			PrimeData pd=null;
			List<Long> results=new ArrayList<Long>();
			while(((pd=pfsi.getNextInterval())!=null)&&(calculate==true))
			{
				for(long start=pd.getStartNr();start<(pd.getStartNr()+pd.getSize());start++)
				{
					if(isPrime(start))
					{
						results.add(start);
						primesFoundTotal++;
						primesFoundSession++;
						lblPrimesTotal.setText("Total Primes found: "+primesFoundTotal);
						lblPrimesSession.setText("Primes found in this session: "+primesFoundSession);
					}
				}
				pfsi.results(pd.getId(),results);
				results.clear();
				savePrimeData();
				lblMaxPrimeFound.setText("Highest prime number found: "+pfsi.getMaxPrime());
				lblPrimesServerTotal.setText("Prime numbers found by all clients: "+pfsi.getPrimes());
			}
		}
		catch(NotBoundException nbe)
		{
			nbe.printStackTrace(System.err);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btnQuit)
		{
			calculate=false;
			System.exit(0);
		}
		else if(e.getSource()==btnPause)
		{
			if(calculate==false)
			{
				btnPause.setText("Pause");
				calculate=true;
				calc=new Thread(this);
				calc.start();
			}
			else
			{
				btnPause.setText("Restart");
				calculate=false;
			}
		}
	}

	/**
	 * Check if a number is a prime number.
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
