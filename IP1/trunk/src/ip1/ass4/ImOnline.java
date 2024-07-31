/*
 * Created on 2003-sep-02
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-02 Created by Viktor.
 */
package ip1.ass4;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This program connects to a multicast group and sends a message to the group every second.
 * It also displays all messages sent to the group in a text area every 5 seconds.
 */
public class ImOnline extends JFrame
{
	private JTextArea textArea=new JTextArea();
	private int port=2000;
	private InetAddress ia=null;
	private OTTPParser oParser=null;
	private MulticastSocket mSocket=null;
	private ShowText sText=null;
	
	public static void main(String args[])
	{
		new ImOnline(args);
	}

	/**
	 * Setup window with a textarea. Also change tital to show the current group and port.
	 * 
	 * @param args The commandline arguments sent to this program.
	 */
	public ImOnline(String args[])
	{
		if(!init(args))
			System.exit(1);
		setSize(400,400);
		setTitle("Group: "+ia.getHostAddress()+" On Port: "+port);
		addWindowListener(new Exit());
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JScrollPane(textArea));
		getContentPane().add(p1);
		start();
		setVisible(true);
	}

	/**
	 * Creat a InetAddress object and a parser with the provided data on the command line.
	 * @param args The command line data.
	 * @return True if a InetAddress object could be creatd, otherwise false.
	 */
	private boolean init(String args[])
	{
		try
		{
			ia=InetAddress.getByName("234.235.236.237");
			oParser=new OTTPParser(args[0],InetAddress.getLocalHost().getHostName().toString(),args[1]);
		}
		catch(Exception e)
		{
			System.out.println("Usage: java ImOnline name message");
			return(false);
		}
		return(true);
	}
	
	public void start()
	{
		try
		{
			byte[] data=oParser.toString().getBytes();
			DatagramPacket dp=new DatagramPacket(data,data.length,ia,port);
			mSocket=new MulticastSocket(port);
			mSocket.joinGroup(ia);
			sText=new ShowText(textArea);
			sText.start();
			MCRead mRead=new MCRead(mSocket,sText);
			mRead.start();
			MCSend mSend=new MCSend(mSocket,dp);
			mSend.start();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	/**
	 * Close all connections and leave the multicast group when the user closes the program.
	 */
	class Exit extends WindowAdapter
	{
		public void windowClosing(WindowEvent arg)
		{
			super.windowClosing(arg);
			try
			{
				mSocket.leaveGroup(ia);
				mSocket.close();
				sText.stopThread();
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
			finally
			{
				System.exit(0);
			}
		}
	}
}

/**
 * Update the textarea with all new messages every 5 seconds.
 */
class ShowText extends Thread
{
	private JTextArea textArea=null;
	private Map hm=new HashMap();
	private boolean run=true;
	
	public ShowText(JTextArea textArea)
	{
		this.textArea=textArea;
	}
	
	public void add(OTTPParser oParser)
	{
		hm.put(oParser.getName(),oParser);
	}
	
	public void stopThread()
	{
		run=true;
	}
	
	public void run()
	{
		while(run)
		{
			try
			{
				textArea.setText("");
				Iterator iter=hm.keySet().iterator();
				while(iter.hasNext())
				{
					OTTPParser oParser=(OTTPParser)hm.get(iter.next());
					textArea.append(oParser.getName()+"---"+oParser.getHost()+"---"+oParser.getComment()+"\n");
				}
				hm.clear();
				sleep(5000);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace(System.err);
			}
		}
	}
}

/**
 * Send our message to the multicast group every second.
 */
class MCSend extends Thread
{
	private MulticastSocket mSocket=null;
	private DatagramPacket dPacket=null;

	public MCSend(MulticastSocket mSocket, DatagramPacket dPacket)
	{
		this.mSocket=mSocket;
		this.dPacket=dPacket;
	}

	public void run()
	{
		try
		{
			while(true)
			{
				try
				{
					mSocket.send(dPacket);
					Thread.sleep(1000);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace(System.err);
				}
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
}

/**
 * Listen for incoming messages from the multicast group. When a message arive add it
 * to the hashmap and replice any previous messages from the sender.
 */
class MCRead extends Thread
{
	private MulticastSocket mSocket=null;
	private ShowText st=null;
	
	public MCRead(MulticastSocket mSocket, ShowText st)
	{
		this.mSocket=mSocket;
		this.st=st;
	}
	
	public void run()
	{
		byte[] data=null;
		OTTPParser oParser=null;
		DatagramPacket dp=null;
		try
		{
			while(true)
			{
				data=new byte[512];
				dp=new DatagramPacket(data,data.length);
				mSocket.receive(dp);
				try
				{
					st.add(new OTTPParser(new String(dp.getData())));
				}
				catch(OTTPParserException ope)
				{
					ope.printStackTrace(System.err);
				}
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
}

/**
 * A class that converts a OTTP message string to usable data and also converts usable
 * data to a OTTP message string.
 */
class OTTPParser
{
	private String name=null;
	private String host=null;
	private String comment=null;
	
	public OTTPParser()
	{
	}
	
	public OTTPParser(String text)
	throws OTTPParserException
	{
		parse(text);
	}
	
	public OTTPParser(String name, String host, String comment)
	{
		this.name=name;
		this.host=host;
		this.comment=comment;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return(name);
	}
	
	public void setHost(String host)
	{
		this.host=host;
	}
	
	public String getHost()
	{
		return(host);
	}
	
	public void setComment(String comment)
	{
		this.comment=comment;
	}
	
	public String getComment()
	{
		return(comment);
	}
	
	public String toString()
	{
		return("From: "+name+" Host: "+host+" Comment: "+comment);
	}
	
	/**
	 * Takes a OTTP message string and converts it to usable data.
	 * 
	 * @param text The OTTP message string to be converted.
	 * @throws OTTPParserException When the OTTP message string is of invalid format.
	 */
	public void parse(String text)
	throws OTTPParserException
	{
		int iHost=text.indexOf("Host:");
		int iComment=text.indexOf("Comment:");
		if((iHost==-1)||(iComment==-1)||(text.indexOf("From:")==-1))
			throw(new OTTPParserException());
		name=text.substring(5,text.indexOf("Host:")).trim();
		host=text.substring(iHost+5,iComment).trim();
		comment=text.substring(iComment+8).trim();
	}
}

/**
 * Exception used by OTTPParser
 */
class OTTPParserException extends Exception
{
}