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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * A simple WebStart application with 2 running threads the print messages in the text area.
 */
public class WebStartThread extends JFrame implements ActionListener
{

	private JTextArea txtArea=new JTextArea();
	private JButton btnThread1=new JButton("Aktivera utskrift från tråd 1");
	private JButton btnThread2=new JButton("Aktivera utskrift från tråd 2");
	private Thread1 thdT1=null;
	private Thread2 thdT2=null;

	public static void main(String[] arg)
	{
		new WebStartThread();
	}

	/**
	 * Setup window with buttons and a textarea.
	 */
	public WebStartThread()
	{
		setTitle("WebStart Thread");
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());

		JPanel p2=new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
		btnThread1.addActionListener(this);
		btnThread2.addActionListener(this);
		p2.add(btnThread1);
		p2.add(btnThread2);
		
		p1.add(p2,BorderLayout.SOUTH);		
		
		JScrollPane scrollPane = new JScrollPane(txtArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		p1.add(scrollPane,BorderLayout.CENTER);
		setContentPane(p1);
		setVisible(true);
	}
	
	public void addText(String text)
	{
		txtArea.append(text+"\n");
		txtArea.setCaretPosition(txtArea.getText().length()); 
	}

	/**
	 * Start threads and update button text when a button is pressed.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource()==btnThread1)
		{
			if(thdT1==null)
			{
				thdT1=new Thread1(this);
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
				thdT2=new Thread2(this);
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

class Thread1 extends Thread
{
	private boolean run=false;
	private WebStartThread parent=null;
	
	public Thread1(WebStartThread parent)
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

class Thread2 implements Runnable
{
	private boolean run=false;
	private WebStartThread parent=null;
	
	public Thread2(WebStartThread parent)
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
