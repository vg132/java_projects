package u3211;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class U3211Client extends MIDlet implements CommandListener
{
	private RecordStore rs=null; //new
	private Network network=null;

	private Form frmNetwork=new Form("Network Setup");
	private Form frmChat=new Form("Chat");
	private TextField txtPort=new TextField("Port:","",4,TextField.NUMERIC);
	private TextField txtAddress=new TextField("Server:","",64,TextField.ANY);
	private TextField txtMessage=new TextField("Msg:","",150,TextField.ANY);
	private TextField txtMessages=new TextField("Messages","",999,TextField.ANY|TextField.UNEDITABLE);

	private Command btnExit=new Command("Exit",Command.EXIT,1);
	private Command btnConnect=new Command("Connect",Command.OK,1);
	private Command btnSend=new Command("Send",Command.OK,1);

	private Display display=null;
	
	/**
	 * Build the two screens we need in the application.
	 * Check if we have any saved data in a record store, if we have load it.
	 */
	public U3211Client()
	{
		//start new RMS code
		try
		{
			rs=RecordStore.openRecordStore("u312_db",true);
			if(rs.getNumRecords()>0)
			{
				txtAddress.setString(new String(rs.getRecord(1)));
				txtPort.setString(new String(rs.getRecord(2)));
			}
		}
		catch(RecordStoreException rse)
		{
			rse.printStackTrace();
		}
		//end new RMS code

		display=Display.getDisplay(this);
		
		//build network form
		frmNetwork.addCommand(btnExit);
		frmNetwork.addCommand(btnConnect);
		frmNetwork.append(txtAddress);
		frmNetwork.append(txtPort);
		frmNetwork.setCommandListener(this);
		
		//build chat form
		frmChat.addCommand(btnExit);
		frmChat.addCommand(btnSend);
		frmChat.append(txtMessages);
		frmChat.append(txtMessage);
		frmChat.setCommandListener(this);
	}
	
	protected void destroyApp(boolean unconditional)
	throws MIDletStateChangeException
	{
	}

	protected void pauseApp()
	{
	}

	protected void startApp()
	throws MIDletStateChangeException
	{
		display.setCurrent(frmNetwork);
	}
	
	/**
	 * Add message to the message display item.
	 */
	public void addMessage(String message)
	{
		if(message.equals(""))
			return;
		if(txtMessages.getString().equals(""))
			txtMessages.setString(txtMessages.getString()+message);
		else
			txtMessages.setString(txtMessages.getString()+"\n"+message);
	}

	/**
	 * Act on users input.
	 * Save address and port to a record store. 
	 */
	public void commandAction(Command c, Displayable d)
	{
		if(c==btnConnect)
		{
			//new rms code start
			try
			{
				if(rs!=null)
				{
					if(rs.getNumRecords()==2)
					{
						rs.setRecord(1,txtAddress.getString().getBytes(),0,txtAddress.getString().length());
						rs.setRecord(2,txtPort.getString().getBytes(),0,txtPort.getString().length());
					}
					else
					{
						rs.addRecord(txtAddress.getString().getBytes(),0,txtAddress.getString().length());
						rs.addRecord(txtPort.getString().getBytes(),0,txtPort.getString().length());						
					}
				}
			}
			catch(RecordStoreException rse)
			{
				rse.printStackTrace();
			}
			//new rms code end

			network=new Network(this,txtAddress.getString(),txtPort.getString());
			network.start();

			display.setCurrent(frmChat);
		}
		else if(c==btnSend)
		{
			network.sendMessage(txtMessage.getString());
			addMessage(txtMessage.getString());
			txtMessage.setString("");
		}
		else if(c==btnExit)
		{
			try
			{
				this.destroyApp(false);
			}
			catch(MIDletStateChangeException mse)
			{
				mse.printStackTrace();
			}
		}
	}
}
