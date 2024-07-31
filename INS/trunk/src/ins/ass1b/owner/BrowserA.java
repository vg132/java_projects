/*
 * Created on 2003-aug-26
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-26 Created by Viktor.
 */
package ins.ass1b.owner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Applet browser that works in the same way as the other browser but can only browse 
 * files located on the local server due to the Applet security restrictions.
 */
public class BrowserA extends JApplet implements ActionListener, KeyListener
{
	private JEditorPane edtBrowser=new JEditorPane();
	private JTextField txtAddress=new JTextField();
	private JButton btnGo=new JButton("Go");

	/**
	 * Setup Applet
	 */
	public void init()
	{
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

		edtBrowser.setEditable(false);
		edtBrowser.addHyperlinkListener(new LinkAdapter(edtBrowser));
		JScrollPane editorScrollPane = new JScrollPane(edtBrowser);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		p1.add(editorScrollPane,BorderLayout.CENTER);
		getContentPane().add(p1);
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
	 * Tell the JEditorPane what page to display, catch securityexceptions when the user
	 * try to browse outside the local server.
	 *
	 */
	private void browse()
	{
		try
		{
			edtBrowser.setPage(txtAddress.getText());
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
		catch(SecurityException se)
		{
			se.printStackTrace(System.err);
			JOptionPane.showMessageDialog(this,"You can only browse webpages located on the same server as this Applet ("+this.getCodeBase().getHost()+").");
		}
	}
}

/**
 * Listen for clicks in the JEditorPane and redirect the JEditorPane to the clicked url.
 */
class LinkAdapter implements HyperlinkListener
{
	private JEditorPane jep=null;
	
	public LinkAdapter(JEditorPane jep)
	{
		this.jep=jep;
	}
	
	public void hyperlinkUpdate(HyperlinkEvent evt)
	{
		if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
		{
			try
			{
				jep.setPage(evt.getURL());
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
			}
		}
	}
}