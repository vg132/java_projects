package u133;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements ActionListener
{
	private Socket socket=null;
	private PrintWriter pw=null;
	
	private JTextField txtEmail=new JTextField("");
	private JTextField txtMessage=new JTextField("");
	private JTextField txtHomepage=new JTextField("");
	private JTextField txtName=new JTextField("");
	private JButton btnSend=new JButton("Send");
	private JTextArea txtMessages=new JTextArea();
	
	public static void main(String args[])
	{
		new ChatClient();
	}

	/**
	 * Default constructor, opens the connection and creates the chat windows.
	 */
	public ChatClient()
	{
		if(!connect("atlas.dsv.su.se",9494))
		{
			System.out.println("Could not open a connection to the server.");
			System.exit(0);
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		JPanel pnlMain=new JPanel();
		pnlMain.setLayout(new BorderLayout());
		
		txtMessages.setEnabled(false);
		pnlMain.add(new JScrollPane(txtMessages),BorderLayout.CENTER);
		
		JPanel pnlTmp=new JPanel();
		pnlTmp.setLayout(new BoxLayout(pnlTmp,BoxLayout.Y_AXIS));
		pnlTmp.add(getPnl("Name",txtName));
		pnlTmp.add(getPnl("Email",txtEmail));
		pnlTmp.add(getPnl("Homepage",txtHomepage));
		pnlTmp.add(getPnl("Message",txtMessage));
		btnSend.addActionListener(this);
		pnlTmp.add(getPnl("",btnSend));
		pnlMain.add(pnlTmp,BorderLayout.SOUTH);

		this.setContentPane(pnlMain);
		this.setVisible(true);
	}

	/**
	 * Connects to the server and opens all writers and listeners
	 */
	private boolean connect(String server, int port)
	{
		try
		{
			socket=new Socket(server,port);
			pw=new PrintWriter(socket.getOutputStream());
			Listen l=new Listen();
			l.start();
			return(true);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(false);
	}
	
	private JPanel getPnl(String text, JComponent component)
	{
		JPanel pnlTmp=new JPanel();
		pnlTmp.setLayout(new BoxLayout(pnlTmp,BoxLayout.X_AXIS));
		pnlTmp.add(new JLabel(text));
		pnlTmp.add(component);
		return(pnlTmp);
	}
	
	/**
	 * If send button was pressed create a new message and send it to the server.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btnSend)
		{
			Message msg=new Message();
			msg.setBody(txtMessage.getText());
			msg.setEmail(txtEmail.getText());
			msg.setHomepage(txtHomepage.getText());
			msg.setName(txtName.getText());
			msg.setHost("Unknown");
			pw.write(MessageBuilder.getXMLMessage(msg).replaceAll("\r","").replaceAll("\n","")+"\n");
			pw.flush();
		}
	}
	
	/**
	 * Listen for incoming messages, when one has been recived it tries
	 * to create a Message from it.
	 */
	class Listen extends Thread
	{
		public void run()
		{
			Message msg=null;
			try
			{
				String line=null;
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while((line=br.readLine())!=null)
				{
					msg=MessageParser.getMessage(line);
					if(msg==null)
						txtMessages.append("KUNDE INTE PARSA ETT MEDDELANDE: "+line);
					else
						txtMessages.append(msg.getName()+" ("+msg.getEmail()+"): "+msg.getBody());
					txtMessages.append("\n");
				}
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
	}
}
