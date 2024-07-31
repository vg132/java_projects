/*
 * Created on 2003-aug-27
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-27 Created by Viktor.
 */
package ip1.ass6;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Database based guestbook program.
 */
public class Guestbook extends JApplet implements ActionListener
{
	private JTextField txtName=new JTextField();
	private JTextField txtEmail=new JTextField();
	private JTextField txtHomepage=new JTextField();
	private JTextField txtComment=new JTextField();
	private JTextArea txtMessages=new JTextArea();
	private JButton btnSend=new JButton("Send Comment");
	private Connection dbConnection=null;
	
	public static void main(String[] arg)
	{
		new Guestbook();
	}
	
	/**
	 * Setup database connection and main window.
	 *
	 */
	public void init()
	{
		if(dbConnect())
		{
			JPanel p1=new JPanel();
			p1.setLayout(new BorderLayout());
			JPanel p2=new JPanel();
			p2.setLayout(new GridLayout(5,2));
			p2.add(new JLabel("Name:"));
			p2.add(txtName);
			p2.add(new JLabel("Email:"));
			p2.add(txtEmail);
			p2.add(new JLabel("Homepage"));
			p2.add(txtHomepage);
			p2.add(new JLabel("Comment:"));
			p2.add(txtComment);
			p2.add(new JLabel(""));
			btnSend.addActionListener(this);
			p2.add(btnSend);
			p1.add(p2,BorderLayout.NORTH);
			JScrollPane scrollPane=new JScrollPane(txtMessages);
			p1.add(scrollPane,BorderLayout.CENTER);
			setContentPane(p1);
//			getContentPane().add(p1);
			listMessages();
//			show();
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to the database.");
			System.exit(0);
		}
	}
	
	/**
	 * Retrive all messages from the database and print them to the text area.
	 *
	 */
	private void listMessages()
	{
		try
		{
			Statement stmt=dbConnection.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT name,email,url,comment FROM ip1_guestbook");
			int i=1;
			while(rs.next())
				txtMessages.append("Comment nr: "+(i++)+"\nName: "+censur(rs.getString(1))+"\nEmail: "+censur(rs.getString(2))+"\nHomepage: "+censur(rs.getString(3))+"\nComment: "+censur(rs.getString(4))+"\n\n");
			stmt.close();
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
	}
	
	/**
	 * Take the data from the text fields and send them to the database.
	 *
	 */
	private void sendMessage()
	{
		try
		{
			PreparedStatement ps=dbConnection.prepareStatement("INSERT INTO ip1_guestbook (name, email, url, comment) VALUES(?,?,?,?)");
			ps.setString(1,txtName.getText());
			ps.setString(2,txtEmail.getText());
			ps.setString(3,txtHomepage.getText());
			ps.setString(4,txtComment.getText());
			if(ps.executeUpdate()!=1)
				JOptionPane.showMessageDialog(this,"Error when sending data. Please try again later.");
			ps.close();
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
	}

	/**
	 * Replace all html taggs with the text "CENSUR".
	 * 
	 * @param text The text to censor.
	 * @return Censored text.
	 */
	private String censur(String text)
	{
		Pattern p = Pattern.compile("<.*>"); 
		Matcher m=p.matcher(text);
		return(m.replaceAll("CENSUR"));
	}

	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource()==btnSend)
		{
			if((txtName.getText().equals(""))||(txtComment.getText().equals("")))
			{
				JOptionPane.showMessageDialog(this,"You have to enter a name and a commnet.");
			}
			else
			{
				sendMessage();
				listMessages();
			}
		}
	}

	/**
	 * Open a database connection to the mysql server at atlas.dsv.su.se.
	 * 
	 * @return True if conection opend succesfully, otherwise false.
	 */
	private boolean dbConnect()
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			dbConnection = DriverManager.getConnection("jdbc:mysql://atlas.dsv.su.se/db_03_vikto_ga", "db_03_vikto_ga", "829238");
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace(System.err);
		}
		catch(InstantiationException ie)
		{
			ie.printStackTrace(System.err);
		}
		catch(IllegalAccessException iae)
		{
			iae.printStackTrace(System.err);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		if(dbConnection!=null)
			return(true);
		else
			return(false);
	}
}
