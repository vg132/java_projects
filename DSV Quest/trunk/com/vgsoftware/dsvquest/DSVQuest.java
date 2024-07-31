/**
 * Copyright (C) VG Software 2003-mar-30
 *  
 * Document History
 * 
 * Created: 2003-mar-30 15:15:28 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Viktor
 * 
 */
public class DSVQuest extends JFrame implements ActionListener
{
	private GameField gf=null;
	private JLabel lblLives=new JLabel();
	private JLabel lblLevel=new JLabel();
	private String[] data=new String[]{"     No Keys    "};
	private JList lstKeys=new JList(data);
	private Start start=null;

	public static void main(String[] args)
	{
		DSVQuest quest=new DSVQuest();
		quest.show();
	}

	public DSVQuest()
	{
		super("QuestionPoolEditor");
		setTitle("DSV Quest");
		setSize(700,535);
		this.setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width-getWidth())/2,(screenSize.height-getHeight())/2,getWidth(),getHeight());
 		setDefaultCloseOperation(EXIT_ON_CLOSE);
    setJMenuBar(createMenu());

		gf=new GameField(this,1);
		getContentPane().add(gf, BorderLayout.CENTER);

		JPanel p1=new JPanel();
		p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
		p1.add(lblLives);
		p1.add(lblLevel);
		lstKeys.setEnabled(false);
		p1.add(new JScrollPane(lstKeys));
		getContentPane().add(p1,BorderLayout.EAST);
		gf.setStatus(GameField.STATUS_GAME_OVER);		
		repaint();
	}

	public void repaint()
	{
		super.repaint();
		if((gf.getStatus()==GameField.STATUS_RUN)&&(gf.getPlayer().getLives()>0))
		{
			List tmp=gf.getPlayer().getKeys();
			if((tmp==null)||(tmp.isEmpty()))
				lstKeys.setListData(data);
			else
			{
				lstKeys.setListData(tmp.toArray());
			}
			lblLives.setText("Lives: "+Integer.toString(gf.getPlayer().getLives()));
			lblLevel.setText("Level: "+gf.getLevel());
		}
		else
		{
			start=new Start(this);
			if(!start.showDialog())
			{
				System.exit(0);
			}
			else
			{
				gf.restart();
			}
		}
	}

	/**
	 * Creates the main menu bar and returns it.
	 * 
	 * @return menu bar.
	 */	
	private JMenuBar createMenu()
	{
		JMenuBar jmb=new JMenuBar();
		// File menu
		JMenu menu=new JMenu("File");
		menu.add(menuItemCreater("New Game..."));
		menu.addSeparator(); 
		menu.add(menuItemCreater("Exit")); 
		jmb.add(menu);
		return(jmb);
	}
	
	private JMenuItem menuItemCreater(String text)
	{
		JMenuItem item=new JMenuItem(text);
		item.addActionListener(this);
		return(item);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		String arg=evt.getActionCommand(); 
		if(evt.getSource() instanceof JMenuItem)
		{
			if(arg.equals("Exit"))
				System.exit(0);
			else if(arg.equals("New Game..."))
			{
				gf.setStatus(GameField.STATUS_GAME_OVER);
				repaint();
			}
		}
	}
}