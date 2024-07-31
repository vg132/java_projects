/**
 * Copyright (C) VG Software 2003-apr-05
 *  
 * Document History
 * 
 * Created: 2003-apr-05 20:55:28 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vgsoftware.questions.Question;
import com.vgsoftware.questions.exception.WrongAwnserFormatException;
import com.vgsoftware.questions.qtype.AltQuestion;

/**
 * @author Viktor
 * 
 */
public class QuestionForm extends JDialog implements ActionListener
{
	private Question q=null;
	private JTextField txtAwnser=null;
	private JButton btnOk=null;
	private boolean correctAwnser=false;
	
	public boolean showDialog(Question q)
	{
		this.q=q;

		setSize(350,200);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JLabel(q.getQuestion()),BorderLayout.NORTH);


    JPanel p1=new JPanel();
    p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));

		String[] alt=q.getAlt();
		if(alt!=null)
		{
			for(int i=0;i<alt.length;i++)
			{
				if(q instanceof AltQuestion)
					p1.add(new JLabel((char)('a'+i) +": "+ alt[i]));
				else
					p1.add(new JLabel(alt[i]));
			}
		}
    getContentPane().add(p1,BorderLayout.CENTER);

		p1=new JPanel();
		p1.setLayout(new BorderLayout());
    txtAwnser=new JTextField();
    btnOk=new JButton("OK");
    btnOk.addActionListener(this);
    p1.add(txtAwnser,BorderLayout.NORTH);
    p1.add(btnOk,BorderLayout.SOUTH);
    getContentPane().add(p1,BorderLayout.SOUTH);

		show();
		return(correctAwnser);
	}
	
	public QuestionForm(JFrame parent)
	{
		super(parent,null,true);
		setTitle("DSV Quest - Question");
		setSize(350,320);
    addWindowListener(new WindowAdapter()
    {
    	public void windowClosing(WindowEvent e)
    	{
				((QuestionForm)e.getSource()).hide(); 
    	}
    	
    });
		this.setResizable(false);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(q.checkAwnser(txtAwnser.getText()))
			{
				JOptionPane.showMessageDialog(this,"Correct Awnser!");
				correctAwnser=true;
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Wrong Awnser! You just wasted one of your lives!");
				correctAwnser=false;
			}
			this.hide(); 
		}
		catch(WrongAwnserFormatException wafe)
		{
			JOptionPane.showMessageDialog(this, wafe.getMessage());
		}
	}
}