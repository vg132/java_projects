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
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Simple mail recive class. This will get mails from a POP3 store and print them into a JTextArea.
 */
public class Receive extends JFrame implements ActionListener
{
	private JTextField server=new JTextField();
	private JTextField user=new JTextField();
	private JPasswordField password=new JPasswordField();
	private JTextArea txtMessages=new JTextArea();
	private JButton get=new JButton("Get!");

	public static void main(String argv[])
	{
		new Receive();
	}

	public Receive()
	{
		setSize(400,400);
		setTitle("POP3 Mail Receive");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout(3,2));
		p2.add(new JLabel("Server:"));
		p2.add(server);
		p2.add(new JLabel("User:"));
		p2.add(user);
		p2.add(new JLabel("Password:"));
		p2.add(password);
		p1.add(p2,BorderLayout.NORTH);
		p1.add(new JScrollPane(txtMessages),BorderLayout.CENTER);
		get.addActionListener(this);
		p1.add(get,BorderLayout.SOUTH);
		getContentPane().add(p1);
		setVisible(true);
	}

	/**
	 * Get mail messages if Get button is pressed. Use data from textFields to get messages.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource()==get)
		{
			try
			{
				Session session=Session.getDefaultInstance(new Properties(),null);
				Store store=session.getStore("pop3");
				store.connect(server.getText(),user.getText(),password.getPassword().toString());
				Folder inbox=store.getFolder("INBOX");
				if(inbox==null)
				{
					JOptionPane.showMessageDialog(this,"No INBOX!");
					return;
				}
				inbox.open(Folder.READ_ONLY);
				Message[] messages=inbox.getMessages();
				for(int i=0;i<messages.length;i++)
				{
					txtMessages.append("\n----------Message "+i+"----------\n\nSubject: "+messages[i].getSubject()+"\n"+messages[i].getContent());
				}
				inbox.close(false);
				store.close();
			}
			catch(MessagingException me)
			{
				me.printStackTrace(System.err);
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
	}
}
