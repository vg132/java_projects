/*
 * Created on 2003-sep-17
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-17 Created by Viktor.
 */
package com.vgsoftware.sjrc.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import com.vgsoftware.sjrc.client.panel.JRCPanel;

/**
 * Main client window, to this window a {@link com.vgsoftware.sjrc.client.panel.Server} and zero or more {@link com.vgsoftware.sjrc.client.Client}.
 */
public class FrmClient extends JFrame implements ActionListener, IWindow
{
	private JTabbedPane tabb=new JTabbedPane();
	private DlgLogin dlgLogin=new DlgLogin(this);
	private ClientMessageCenter cmc=null;
	private Connection conn=null;
	private JPopupMenu popupMenu=null;
	private JRCPanel server=null;

	/**
	 * Construct the window, setup layout.
	 * @param cmc
	 */
	public FrmClient()
	{
		super();
		cmc=new ClientMessageCenter(this);
		//setup window properties
		setSize(400,400);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width/2-145,screenSize.height/4);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				FrmClient.this.cmc.quitAll();
				System.exit(0);
			}
		});

		setTitle("JRC - Java Relay Chat");

		//setup menus
		setJMenuBar(createMenu());
		popupMenu=createPopupMenu();
		tabb.addMouseListener(new MenuMouseListenener());

		//setup layout
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(tabb,BorderLayout.CENTER);
		getContentPane().add(p1);
	}

	/**
	 * Shows this window on the screen.
	 */
	public void showWindow()
	{
		show();
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
	 * Creates the default menubar for this window.
	 * @return JMenuBar the created menubar.
	 */
	private JMenuBar createMenu()
	{
		JMenuBar bar=new JMenuBar();
		JMenu menu=new JMenu("File");
		menu.add(createMenuItem("Connect..."));
		menu.add(createMenuItem("Close connection"));
		menu.addSeparator();
		menu.add(createMenuItem("Exit"));
		bar.add(menu);
		return(bar);
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
	 * Catch all events that happen in this window.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource() instanceof JMenuItem)
		{
			String label=((JMenuItem)arg.getSource()).getLabel();
			if((label.equals("Connect..."))&&(!cmc.isConnected()))
			{
				//this.enable(false);
				Connection conn=dlgLogin.showDialog();
				//this.enable(true);
				if(conn!=null)
				{
					if(!cmc.connect(conn))
						JOptionPane.showMessageDialog(this,"Unabel to connect to server.");
				}
			}
			else if((label.equals("Close connection"))&&(cmc.isConnected()))
			{
				cmc.quitAll();
			}
			else if(label.equals("Exit"))
			{
				cmc.quitAll();
				System.exit(0);
			}
			else if(label.equals("Close"))
			{
				cmc.quitChannel(tabb.getTitleAt(tabb.getSelectedIndex()));
			}
		}
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
}
