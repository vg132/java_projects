package com.vgsoftware.jdownloader;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.vgsoftware.jdownloader.data.Status;
import com.vgsoftware.jdownloader.engine.JDEngine;
import com.vgsoftware.jdownloader.gui.IGui;

public class Test extends JFrame implements IGui
{
	private static final Object[] resumeOptions={"Resume","Restart","Cancel"};
	private JDEngine engine=null;
	private JLabel lblSpeed=new JLabel();
	private JLabel lblDownloaded=new JLabel();
	private boolean update=false;
	
	public static void main(String args[])
	{
		System.out.println("Kalle Anka");
		new Test();
	}
	
	public Test()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200,100);
		JPanel pnlMain=new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
		pnlMain.add(lblDownloaded);
		pnlMain.add(lblSpeed);
		this.setContentPane(pnlMain);
		startDownload();
		this.setVisible(true);
	}

	public void startDownload()
	{
		try
		{
			StatusUpdater su=new StatusUpdater();
			su.start();
			update=true;
			engine=new JDEngine();
			engine.setGui(this);
			File file=new File("c:\\temp\\java\\gcc-g++-3.0.3-JDown.tar.gz");
			URL url=new URL("http://ftp.sunet.se/pub/gnu/gcc/releases/gcc-3.0.3/gcc-g++-3.0.3.tar.gz");
			engine.addDownload(file,url);
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	public void eventDownloadList()
	{
	}

	public void eventFatalError(String text)
	{
	}

	public void eventFinished(int id)
	{
		update=false;
		System.out.println("Download finished");
	}

	public int eventResumeRestartCancel(File file, URL url)
	{
		return(JOptionPane.showOptionDialog(null,"File already exist, what do you want to do?",
				"File Exists",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,
				resumeOptions,resumeOptions[0]));
	}

	public void eventWarning(String text)
	{
	}
	
	class StatusUpdater extends Thread
	{
		public void run()
		{
			while(update)
			{
				try
				{
					Thread.sleep(1000);
					List<Status> status=engine.getDownloades();
					for(Status s : status)
					{
						lblDownloaded.setText("Downloaded (bytes): "+s.getDownloaded());
						lblSpeed.setText("Speed (Kb/s): "+((float)(s.getSpeed()/1000)));
					}
				}
				catch(InterruptedException ie)
				{
					
				}
			}
		}
	}
}
