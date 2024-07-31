/*
 * Created on 2003-aug-26
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-26 Created by Viktor.
 */
package ip1.ass1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * A Applet that runs 2 threads, one extending thread and the other implementing 
 * runnable. Both threads, when activated, prints a message to a text area in the 
 * main applet.
 */
public class AppletThread extends JApplet implements ActionListener
{
	private JTextArea txtArea=new JTextArea();
	private JButton btnThread1=new JButton("Aktivera utskrift från tråd 1");
	private JButton btnThread2=new JButton("Aktivera utskrift från tråd 2");
	private AThread1 thdT1=null;
	private AThread2 thdT2=null;

	/**
	 * Setup the applet window.
	 */
	public void init()
	{
		JPanel main=new JPanel();
		main.setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(txtArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		
		main.add(scrollPane,BorderLayout.CENTER);
		JPanel p2=new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
		btnThread1.addActionListener(this);
		btnThread2.addActionListener(this);
		p2.add(btnThread1);
		p2.add(btnThread2);
		main.add(p2,BorderLayout.SOUTH);
		setContentPane(main);
	}
	
	public void addText(String text)
	{
		txtArea.append(text+"\n");
		txtArea.setCaretPosition(txtArea.getText().length()); 
	}

	/**
	 * Handel when a user press a button
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource()==btnThread1)
		{
			if(thdT1==null)
			{
				thdT1=new AThread1(this);
				thdT1.start();
				btnThread1.setText("Deaktivera utskrift från tråd 1");
			}
			else
			{
				thdT1.stopThread();
				thdT1=null;
				btnThread1.setText("Aktivera utskrift från tråd 1");
			}
		}
		else if(arg.getSource()==btnThread2)
		{
			if(thdT2==null)
			{
				thdT2=new AThread2(this);
				Thread thd=new Thread(thdT2);
				thd.start();
				btnThread2.setText("Deaktivera utskrift från tråd 2");
			}
			else
			{
				thdT2.stopThread();
				thdT2=null;
				btnThread2.setText("Aktivera utskrift från tråd 2");
			}
		}
	}
}

class AThread1 extends Thread
{
	private boolean run=false;
	private AppletThread parent=null;
	
	public AThread1(AppletThread parent)
	{
		this.parent=parent;
	}
	
	/**
	 * Main function that prints a message and then sleeps for 1 second
	 */
	public void run()
	{
		run=true;
		try
		{
			while(run)
			{
				parent.addText("Utskrift från tråd 1");
				sleep(1000);
			};
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace(System.err);
		}
	}
	
	public void stopThread()
	{
		run=false;
	}
}

class AThread2 implements Runnable
{
	private boolean run=false;
	private AppletThread parent=null;
	
	public AThread2(AppletThread parent)
	{
		this.parent=parent;
	}
	
	/**
	 * Main function that prints a message and then sleeps for 1 second
	 */
	public void run()
	{
		run=true;
		try
		{
			while(run)
			{
				parent.addText("Utskrift från tråd 2");
				Thread.sleep(1000);
			};
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace(System.err);
		}
	}
	
	public void stopThread()
	{
		run=false;
	}
}
