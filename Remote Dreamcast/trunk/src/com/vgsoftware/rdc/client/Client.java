package com.vgsoftware.rdc.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


/**
 * Quick and very, very, very dirty make client.
 * 
 * @author Viktor 2005
 */
public class Client extends JFrame implements ActionListener, WindowListener
{
	private TextPanel errorPanel=new TextPanel();
	private TextPanel textPanel=new TextPanel();
	private TextPanel executePanel=new TextPanel();
	private JTabbedPane tabbedPane=new JTabbedPane();
	private JLabel lblCompile=new JLabel("Compile:");
	private JComboBox cboCompile=new JComboBox();
	private JComboBox cboExecute=new JComboBox();
	private JLabel lblExecute=new JLabel("Execute:");
	private JButton btnCompile=new JButton("Compile");
	private JButton btnExecute=new JButton("Execute");
	private JButton btnBoth=new JButton("Both");
	private JSplitPane split1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textPanel,errorPanel);
	private JSplitPane split2=new JSplitPane(JSplitPane.VERTICAL_SPLIT,split1,executePanel);
	private boolean both=false;
	private List<String> compile=new ArrayList<String>();
	private List<String> execute=new ArrayList<String>();

	private BufferedWriter bw=null;
	private BufferedReader br=null;
	
	public static void main(String args[])
	{
		new Client();
	}

	public Client()
	{
		cboExecute.setEditable(true);
		cboCompile.setEditable(true);

		this.addWindowListener(this);
		this.setSize(600,600);
		this.setTitle("Remote Dreamcast Compiler and Executer");

		JPanel pnlMain=new JPanel();
		pnlMain.setLayout(new BorderLayout());
		split1.setResizeWeight(0.5);
		split2.setResizeWeight(0.66);
		pnlMain.add(split2,BorderLayout.CENTER);
		
		JPanel pnlTmp=new JPanel();
		pnlTmp.setLayout(new BoxLayout(pnlTmp,BoxLayout.Y_AXIS));
		
		JPanel pnlTmp2=new JPanel();
		pnlTmp2.setLayout(new BorderLayout());
		pnlTmp2.add(lblCompile,BorderLayout.WEST);
		pnlTmp2.add(cboCompile,BorderLayout.CENTER);
		pnlTmp.add(pnlTmp2);

		pnlTmp2=new JPanel();
		pnlTmp2.setLayout(new BorderLayout());
		pnlTmp2.add(lblExecute,BorderLayout.WEST);
		pnlTmp2.add(cboExecute,BorderLayout.CENTER);
		pnlTmp.add(pnlTmp2);

		pnlTmp2=new JPanel();
		pnlTmp2.setLayout(new BoxLayout(pnlTmp2,BoxLayout.X_AXIS));
		pnlTmp2.add(btnCompile);
		btnCompile.addActionListener(this);
		pnlTmp2.add(btnExecute);
		btnExecute.addActionListener(this);
		pnlTmp2.add(btnBoth);
		btnBoth.addActionListener(this);
		pnlTmp.add(pnlTmp2);
		
		pnlMain.add(pnlTmp,BorderLayout.NORTH);
		this.setContentPane(pnlMain);
		this.setVisible(true);
	}
	
	public void compile()
	{
		try
		{
			Socket s=new Socket("192.168.0.36",3699);
			bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			Listen l=new Listen();
			l.start();
			
			//"make -C /home/viktor/dev/dreamcast/gamepack1/ all"
			send("command|"+cboCompile.getSelectedItem());
			
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
	
	public void send(String line)
	{
		try
		{
			bw.write(line.trim()+"\n");
			bw.flush();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
	
	public void decode(String line)
	{
		StringTokenizer st=new StringTokenizer(line,"|");
		String command=st.nextToken();
		if(command.equals("return"))
		{
			String exit=st.nextToken();
			textPanel.addLine("Exit value: "+exit);
			if((both==true)&&(exit.equals("0")))
			{
				both=false;
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException ie)
				{
				}
				Execute e=new Execute();
				e.start();
			}
			else if(both==true)
			{
				both=false;
			}
		}
		else if(command.equals("error"))
			errorPanel.addLine(st.nextToken());
		else if(command.equals("text"))
			textPanel.addLine(st.nextToken());
	}
	
	class Listen extends Thread
	{
		public void run()
		{
			try
			{
				String line=null;
				while((line=br.readLine())!=null)
					decode(line);
			}
			catch(IOException io)
			{
			}
		}
	}

	public void actionPerformed(ActionEvent event)
	{
		if(!compile.contains(cboCompile.getSelectedItem()))
		{
			compile.add((String)cboCompile.getSelectedItem());
			cboCompile.addItem(cboCompile.getSelectedItem());
		}
		if(!execute.contains(cboExecute.getSelectedItem()))
		{
			execute.add((String)cboExecute.getSelectedItem());
			cboExecute.addItem(cboExecute.getSelectedItem());
		}
		if(event.getSource()==btnCompile)
		{
			errorPanel.setText("");
			textPanel.setText("");
			compile();
		}
		else if(event.getSource()==btnExecute)
		{
			executePanel.setText("");
			Execute e=new Execute();
			e.start();
		}
		else if(event.getSource()==btnBoth)
		{
			errorPanel.setText("");
			textPanel.setText("");
			executePanel.setText("");
			both=true;
			compile();
		}
	}

	class Execute extends Thread
	{
		private Process p=null;
		
		public void cancelExection()
		{
			p.destroy();
		}
		
		public void run()
		{
			try
			{
				//c:\windows\system32\dctool.bat z:\dev\dreamcast\gamepack1\bin\gamepack1.elf
				String line;
				Process p=Runtime.getRuntime().exec("c:\\windows\\system32\\dctool.bat "+cboExecute.getSelectedItem());
				BufferedReader input=new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
				CReader crInput=new CReader(input);
				CReader crError=new CReader(error);
				crInput.start();
				crError.start();
				p.waitFor();
				executePanel.addLine("Exit value: "+p.exitValue());
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace(System.err);
			}
			catch(IOException io)
			{
				io.printStackTrace();
			}
		}
	}
	
	class CReader extends Thread
	{
		private BufferedReader br=null;

		public CReader(BufferedReader br)
		{
			this.br=br;
		}

		public void run()
		{
			String line=null;
			try
			{
				while((line=br.readLine())!=null)
				{
					executePanel.addLine(line);
				}
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
	}

	public void windowActivated(WindowEvent arg0)
	{
	}

	public void windowClosed(WindowEvent arg0)
	{
	}

	public void windowClosing(WindowEvent event)
	{
		int i=0;
		String tmp="";
		Properties p=new Properties();
		for(String str : compile)
			tmp+=str+"|";
		if(tmp.length()>0)
			p.setProperty("compile",tmp.substring(0,tmp.length()-1));
		tmp="";
		for(String str : execute)
			tmp+=str+"|";
		if(tmp.length()>0)
			p.setProperty("execute",tmp.substring(0,tmp.length()-1));
		try
		{
			p.storeToXML(new FileOutputStream(new File("rdc.xml")),"Remote Dreamcast Compiler Data");
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent arg0)
	{
	}

	public void windowDeiconified(WindowEvent arg0)
	{
	}

	public void windowIconified(WindowEvent arg0)
	{
	}

	public void windowOpened(WindowEvent arg0)
	{
		Properties p=new Properties();
		try
		{
			File f=new File("rdc.xml");
			if(f.exists())
			{
				p.loadFromXML(new FileInputStream(f));
				String tmp=p.getProperty("compile");
				if((tmp!=null)&&(!tmp.equals("null")))
				{
					StringTokenizer st=new StringTokenizer(tmp,"|");
					while(st.hasMoreTokens())
					{
						String t=st.nextToken();
						cboCompile.addItem(t);
						compile.add(t);
					}
				}
				tmp="";
				tmp=p.getProperty("execute");
				if((tmp!=null)&&(!tmp.equals("null")))
				{
					StringTokenizer st=new StringTokenizer(tmp,"|");
					while(st.hasMoreTokens())
					{
						String t=st.nextToken();
						cboExecute.addItem(t);
						execute.add(t);
					}
				}
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
}
