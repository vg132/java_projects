package com.vgsoftware.VTracker.Bluetooth;

import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class Client implements DiscoveryListener
{
	private DiscoveryAgent discoveryAgent;
	private Vector remoteDevices=new Vector();
	private Vector status=new Vector();
  private int[] attrSet = {0x4321};
  private UUID[] uuidSet = {new UUID(0x1101)};

	public Client()
	{
		try
		{
			LocalDevice localDevice=LocalDevice.getLocalDevice();
			discoveryAgent=localDevice.getDiscoveryAgent();
			discoveryAgent.startInquiry(DiscoveryAgent.GIAC,this);
			status.addElement("Bluetooth inquiry started");
		}
		catch(BluetoothStateException btse)
		{
			status.addElement("Bluetooth state exception: "+btse.getMessage());
			System.out.println("Bluetooth state exception: "+btse.getMessage());
			System.out.flush();
		}
	}
	
	public Vector getStatus()
	{
		return status;
	}
	
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod)
	{
		remoteDevices.addElement(btDevice);
		status.addElement("Device discovered");
	}

	public void inquiryCompleted(int discType)
	{
		status.addElement("Inquiry Completed");
		try
		{
			status.addElement("Start service search");
			String name=((RemoteDevice)remoteDevices.elementAt(1)).getFriendlyName(true);
			if(name.startsWith("DROP"))
			{
				status.addElement(((RemoteDevice)remoteDevices.elementAt(0)).getFriendlyName(true));
				discoveryAgent.searchServices(attrSet,uuidSet,(RemoteDevice)remoteDevices.elementAt(0),this);
			}
			else
			{
				status.addElement(((RemoteDevice)remoteDevices.elementAt(1)).getFriendlyName(true));
				discoveryAgent.searchServices(attrSet,uuidSet,(RemoteDevice)remoteDevices.elementAt(1),this);
			}			
		}
		catch(BluetoothStateException bse)
		{
			status.addElement("BSE: "+bse.getMessage());
		}
		catch(IOException io)
		{
			status.addElement("IO: "+io.getMessage());
		}
	}

	public void serviceSearchCompleted(int transID, int respCode)
	{
		status.addElement("Service Search Completed");
	}

	public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
	{
		status.addElement("Service Discovered: "+servRecord.length);
		if(servRecord.length>0)
		{
			status.addElement("Service url:");
			status.addElement(servRecord[0].getConnectionURL(0, false));
		}
	}
}
