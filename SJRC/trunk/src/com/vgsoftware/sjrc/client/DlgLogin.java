/*
 * Created on 2003-sep-09
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-09 Created by Viktor.
 */

package com.vgsoftware.sjrc.client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This is the login dialog box that is shown when the user wants to connect to
 * a server. It returns a {@link com.vgsoftware.sjrc.client.Connection} with all the
 * data set.<br/>
 * Connection data can also be saved to a XML file using the {@link com.vgsoftware.sjrc.client.AddressBook}.
 */
public class DlgLogin extends JDialog implements ActionListener
{
	private JTextField txtNick=new JTextField();
	private JPasswordField txtPassword=new JPasswordField();
	private JTextField txtServer=new JTextField();
	private JTextField txtRemotePort=new JTextField();
	private JComboBox cboServers=new JComboBox();
	private JButton btnConnect=new JButton("Connect");
	private JButton btnCancel=new JButton("Cancel");
	private JButton btnSave=new JButton("Save");
	private JButton btnDel=new JButton("Del");
	private Connection conn=null;
	private AddressBook ab=new AddressBook();

	/**
	 * Creates a new login dialog. Sets the window layout, loads the saved servers if there
	 * are any.
	 *
	 * @param parent
	 */
	public DlgLogin(JFrame parent)
	{
		super(parent,"JRC - Login",true);
		setSize(290,210);

		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width/2-145,screenSize.height/4);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				((DlgLogin)e.getSource()).hide();
			}
		});

		try
		{
			ab.loadAddresses("servers.xml");
			cboServers=new JComboBox(ab.getAddresses());
			if(cboServers.getItemCount()>0)
			{
				cboServers.setSelectedIndex(0);
				AddressData ad=ab.getAddress(cboServers.getSelectedItem().toString());
				txtServer.setText(ad.getAddress());
				txtRemotePort.setText(Integer.toString(ad.getPort()));
				txtNick.setText(ad.getNick());
				txtPassword.setText(ad.getPassword());
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		cboServers.setEditable(true);
		cboServers.addActionListener(this);

		setTitle("SJRC - Connect");
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(7,2));
		p1.add(new JLabel("Server Name:"));
		p1.add(cboServers);
		p1.add(new JLabel("Address:"));
		p1.add(txtServer);
		p1.add(new JLabel("Remote Port:"));
		p1.add(txtRemotePort);
		p1.add(new JLabel("Nick:"));
		p1.add(txtNick);
		p1.add(new JLabel("Passoword:"));
		p1.add(txtPassword);
		btnDel.addActionListener(this);
		p1.add(btnDel);
		btnSave.addActionListener(this);
		p1.add(btnSave);
		btnCancel.addActionListener(this);
		p1.add(btnCancel);
		btnConnect.addActionListener(this);
		p1.add(btnConnect);
		getContentPane().add(p1);
	}

	/**
	 * Shows the dialog and when the user is ready, has pressed Connect, a
	 * {@link Connection} object is returend.
	 *
	 *@return a {@link Connection}, or null if Cancel was pressed.
	 */
	public Connection showDialog()
	{
		show();
		return(conn);
	}

	/**
	 * Listen after user input and act on is.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource()==btnCancel)
		{
			conn=null;
			this.hide();
		}
		else if(arg.getSource()==btnSave)
		{
			AddressData ad=new AddressData();
			ad.setName((String)cboServers.getSelectedItem());
			ad.setAddress(txtServer.getText());
			ad.setPort(Integer.parseInt(txtRemotePort.getText()));
			ad.setNick(txtNick.getText());
			ad.setPassword(txtPassword.getText());
			ab.addAddress(ad);
			cboServers.addItem(ad);
			try
			{
				ab.saveAddresses("servers.xml");
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
		else if(arg.getSource()==btnDel)
		{
			try
			{
				AddressData ad=(AddressData)cboServers.getSelectedItem();
				ab.removeAddress(ad);
				ab.saveAddresses("servers.xml");
				cboServers.removeItem(ad);
			}
			catch(IOException io)
			{
			}
		}
		else if(arg.getSource()==cboServers)
		{
			AddressData ad=(AddressData)cboServers.getSelectedItem();
			txtServer.setText(ad.getAddress());
			txtRemotePort.setText(""+ad.getPort());
			txtNick.setText(ad.getNick());
			txtPassword.setText(ad.getPassword());
		}
		else if(arg.getSource()==btnConnect)
		{
			if(txtServer.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"You have to enter a server address.");
			}
			else if(txtRemotePort.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"You have to enter a server port.");
			}
			else if(txtNick.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"You have to enter a nick.");
			}
			else if(txtNick.getText().trim().indexOf(" ")!=-1)
			{
				JOptionPane.showMessageDialog(this,"You cant have space characters in your nick.");
			}
			else
			{
				int port=0;
				try
				{
					port=Integer.parseInt(txtRemotePort.getText());
					conn=new Connection(txtServer.getText(),Integer.parseInt(txtRemotePort.getText()),txtNick.getText(),txtPassword.getText());
					this.hide();
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"A port must be a numeric value.");
				}
			}
		}
	}
}
