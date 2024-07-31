/*
 * Created on 2003-aug-26
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-26 Created by Viktor.
 */
package ip1.ass2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A source webbrowser. Downloadeds pages and show the source code for them in the main window.
 * Browsing starts when the user presses the Go button or press return/enter in the address field.
 */
public class BrowserA extends JFrame implements ActionListener, KeyListener
{
	private JTextArea txtSource=new JTextArea();
	private JTextField txtAddress=new JTextField("http://www.dsv.su.se/");
	private JButton btnGo=new JButton("Go");

	public static void main(String[] arg)
	{
		new BrowserA();
	}

	/**
	 * Setup browser window.
	 */
	public BrowserA()
	{
		super("Source Browser");
		setTitle("Source Browser");
		setSize(700,535);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p2=new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
		p2.add(new JLabel("Address:"));
		txtAddress.addKeyListener(this);
		p2.add(txtAddress);
		btnGo.addActionListener(this);
		p2.add(btnGo);
		p1.add(p2,BorderLayout.NORTH);
		JScrollPane scrPane = new JScrollPane(txtSource);
		p1.add(scrPane,BorderLayout.CENTER);
		getContentPane().add(p1);
		browse();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource()==btnGo)
			browse();
	}

	public void keyPressed(KeyEvent arg0)
	{
		if(arg0.getKeyChar()==KeyEvent.VK_ENTER)
			browse();
	}

	public void keyReleased(KeyEvent arg0)
	{
	}

	public void keyTyped(KeyEvent arg0)
	{
	}
	
	/**
	 * Get the page form a webserver and present the page (text) in the textarea.
	 *
	 */
	private void browse()
	{
		try
		{
			URL url=new URL(txtAddress.getText());
			BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
			String line=null;
			StringBuffer document=new StringBuffer();
			while((line=br.readLine())!=null)
			{
				document.append(line+"\n");
			};
			txtSource.setText(document.toString());
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
			JOptionPane.showMessageDialog(this,"Invalid address format.");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(System.err);
			JOptionPane.showMessageDialog(this,"IOException. Check your Internet connection.");
		}
	}
}