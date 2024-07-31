/*
 * Created on 2004-jun-04
 * Created by Viktor
 *
 * Document History
 *
 * 2004-jun-04 Created by Viktor
 */

package com.vgsoftware.sjrc.web;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Security;

import javax.swing.JApplet;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.vgsoftware.sjrc.client.ClientMessageCenter;
import com.vgsoftware.sjrc.client.Connection;
import com.vgsoftware.sjrc.client.IWindow;
import com.vgsoftware.sjrc.client.panel.JRCPanel;


public class WebClient extends JApplet implements ActionListener, IWindow
{
	private JTabbedPane tabb=new JTabbedPane();
	private ClientMessageCenter cmc=null;
	private Connection conn=null;
	private JPopupMenu popupMenu=null;
	private JRCPanel server=null;
	
	public void init()
	{
		Security.addProvider(new BouncyCastleProvider());
		cmc=new ClientMessageCenter(this);
		tabb.addMouseListener(new MenuMouseListenener());
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(tabb,BorderLayout.CENTER);
		getContentPane().add(p1);

		conn=new Connection("127.0.0.1",2000,"Viktor_Applet",null);
		if(conn!=null)
		{
			if(!cmc.connect(conn))
				JOptionPane.showMessageDialog(this,"Unabel to connect to server.");
		}
	}
	
	/**
	 * Null function only here to complie with the <code>IWindow</code> interface.
	 */	
	public void showWindow()
	{
	}

	/**
	 * Add a JRCPanel object to this window, it will be placed as the last item on the tabbed pane.
	 * Depending on the selected parameter it will be shown in the forground.
	 *
	 * @param panel The JRCPanel that will be added to this window.
	 * @param label The label of this tabb item.
	 * @param selected If true the added panel will be in the forground of the window.
	 */
	public void addPanel(JRCPanel panel, String label, boolean selected)
	{
		tabb.addTab(label,panel);
		if(selected)
			tabb.setSelectedComponent(panel);
	}
	
	/**
	 * Removes the specified JRCPanel from the tabbed pane
	 * @param panel
	 */
	public void removePanel(JRCPanel panel)
	{
		tabb.remove(panel);
	}

	/**
	 * Creates the default popup menu used by this window.
	 * @return JPopupMenu the created popup menu.
	 */
	private JPopupMenu createPopupMenu()
	{
		JPopupMenu popup=new JPopupMenu();
		popup.add(createMenuItem("Close"));
		return(popup);
	}

	/**
	 * Creates a menuitem with action listener and a label.
	 *
	 * @param label The label of this menuitem.
	 * @return JMenuItem the created menuitem.
	 */
	private JMenuItem createMenuItem(String label)
	{
		JMenuItem item=new JMenuItem(label);
		item.addActionListener(this);
		return(item);
	}
	
	/**
	 * Helper class to check if user has pressed a mouse button.
	 * If popup button is pressed in the right place, show popup menu.
	 */
	class MenuMouseListenener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			if((e.isPopupTrigger())&&(tabb.getSelectedIndex()>0))
			{
				popupMenu.show(e.getComponent(),e.getX(),e.getY());
			}
		}
	}
	
	/**
	 * Catch all events that happen in this window.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource() instanceof JMenuItem)
		{
			String label=((JMenuItem)arg.getSource()).getLabel();
			if(label.equals("Close"))
			{
				cmc.quitChannel(tabb.getTitleAt(tabb.getSelectedIndex()));
			}
		}
	}
}
