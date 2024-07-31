/**
 * Copyright (C) VG Software 2003-apr-07
 *  
 * Document History
 * 
 * Created: 2003-apr-07 23:37:25 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.vgsoftware.dsvquest.components.JFixedButton;

/**
 * @author Viktor
 * 
 */
public class Start extends JDialog implements ActionListener
{
	private JFixedButton btnStart=new JFixedButton("Start DSVQuest",150,26);
	private JFixedButton btnQuit=new JFixedButton("Quit DSVQuest",150,26);
	private boolean toDo=false;
	
	public boolean showDialog()
	{
		show();
		return(toDo);
	}
	
	public Start(JFrame parent)
	{
		super(parent,null,true);
		setTitle("DSV Quest - Start Game");
		setSize(300,200);
    addWindowListener(new WindowAdapter()
    {
    	public void windowClosing(WindowEvent e)
    	{
				((Start)e.getSource()).hide(); 
    	}
    });
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		JLabel tmp=new JLabel("DSV Quest");
		tmp.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		tmp.setFont(new Font("SansSerif",Font.PLAIN,30));
		getContentPane().add(tmp,BorderLayout.CENTER);
		
		JPanel p1=new JPanel();
		p1.setLayout(new BoxLayout(p1,BoxLayout.X_AXIS));
		p1.add(btnStart/*,BorderLayout.WEST*/);
		p1.add(btnQuit/*,BorderLayout.EAST*/);
    btnStart.addActionListener(this);
    btnQuit.addActionListener(this);
		getContentPane().add(p1,BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==btnQuit)
			toDo=false;
		else
			toDo=true;
		this.hide();
	}
}
