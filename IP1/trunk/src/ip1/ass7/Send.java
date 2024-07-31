/*
 * Created on 2003-sep-03
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-03 Created by Viktor.
 */
package ip1.ass7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Simple stand alone java application to send email messages through a SMTP server.
 */
public class Send extends JFrame implements ActionListener
{
	private JTextArea message=new JTextArea();
	private JTextField server=new JTextField();
	private JTextField from=new JTextField();
	private JTextField to=new JTextField();
	private JTextField subject=new JTextField();
	private JButton send=new JButton("Send");
	
	public static void main(String args[])
	{
		new Send();
	}
	
	/**
	 * Setup main application window
	 */
	public Send()
	{
		setSize(400,400);
		setTitle("Email Sender");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout(4,2));
		p2.add(new JLabel("SMTP Server:"));
		p2.add(server);
		p2.add(new JLabel("From:"));
		p2.add(from);
		p2.add(new JLabel("To:"));
		p2.add(to);
		p2.add(new JLabel("Subject:"));
		p2.add(subject);
		p1.add(p2,BorderLayout.NORTH);
		p1.add(message,BorderLayout.CENTER);
		send.addActionListener(this);
		p1.add(send,BorderLayout.SOUTH);
		getContentPane().add(p1);
		setVisible(true);
	}

	/**
	 * Check if send button was pressed and send mail based on the information enterd into the application window.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource()==send)
		{
			try
			{
				Properties props=new Properties();
				props.put("mail.host",server.getText());
			
				Session mailConnection=Session.getInstance(props);
				Message msg=new MimeMessage(mailConnection);
				Address bill=new InternetAddress(from.getText());
				msg.setFrom(bill);
				Address viktor=new InternetAddress(to.getText());
				msg.setRecipient(Message.RecipientType.TO, viktor);
				msg.setSubject(subject.getText());				
				msg.setContent(message.getText(),"text/plain");

				Transport.send(msg);
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
			}			
		}
	}
}
