package com.vgsoftware.hello;

import java.util.Vector;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class BTConnector implements Runnable, DiscoveryListener
{

	private DiscoveryAgent discoveryAgent;
	private LocalDevice localDevice;

	private Vector devices = new Vector(); /* RemoteDevice */
	private String[] deviceNames = new String[20];
	private int index = 0;

	private int[] attrSet = { 0x4321 };
	private UUID[] uuidSet = { new UUID(0x1101) };

	private int transactionID = 0;

	RemoteDevice remoteDevice;
	public static String url = "";

	private boolean doneSearching = false;
	private boolean isSearching = false;
	private boolean isServiceSearching = false;
	private boolean doneServiceSearching = false;

	public BTConnector()
	{
		Thread t = new Thread(this);
		t.start();
	}

	// reset the settings if a new device search is started.
	public void startSearch()
	{
		discoveryAgent.cancelServiceSearch(transactionID);

		Thread t = new Thread(this);
		t.start();
	}

	public Vector getDevices()
	{
		return devices;
	}

	public String[] getDeviceNames()
	{
		return deviceNames;
	}

	public int getSize()
	{
		return index;
	}

	public boolean doneSearchingDevices()
	{
		return doneSearching;
	}

	public boolean doneSearchingServices()
	{
		return doneServiceSearching;
	}

	public String getUrl()
	{
		return url;
	}

	// Connect to a specifyed index.
	public void connect(int i)
	{
		discoveryAgent.cancelInquiry(this);
		remoteDevice = (RemoteDevice) devices.elementAt(i);

		synchronized (this)
		{ // resume the thread.
			this.notify();
		}
	}

	public void run()
	{

		while (isSearching || isServiceSearching)
		{
			Thread.yield();
		}

		devices = null;
		devices = new Vector();

		deviceNames = null;
		deviceNames = new String[20];

		index = 0;
		remoteDevice = null;
		doneServiceSearching = false;
		doneSearching = false;
		url = "";

		try
		{
			// create/get a local device and discovery agent
			LocalDevice localDevice = LocalDevice.getLocalDevice();
			discoveryAgent = localDevice.getDiscoveryAgent();

			// start searching for remote devices. If a device is found the
			// deviceDiscovered method will be called.
			isSearching = true;
			discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);

			// Pause the thread until the user selects another bt device to connect
			// to.
			synchronized (this)
			{
				try
				{
					wait();
				} catch (Exception e)
				{
				}
			}

			// Searsh for services on the remote bt device.
			isServiceSearching = true;
			transactionID = discoveryAgent.searchServices(null, uuidSet,
					remoteDevice, this);
		} catch (Exception e)
		{
			System.err.println("Can't initialize bluetooth: " + e);
		}
	}

	// Called if a remote btDevice is found
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod)
	{

		try
		{
			deviceNames[index] = btDevice.getFriendlyName(false); // Store the name of
																														// the device
			System.out.println(index + " : " + deviceNames[index]);
			index++; // keep track on how many devices are found.
			devices.addElement(btDevice); // store the device
		} catch (Exception e)
		{
			// If the btDevice is unknown, don't add it to the list.
			System.out.println("Failed to get FriendlyName");
		}
	}

	// When a connection is made and a service is found, get the connection string
	// to that service.
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
	{
		url = servRecord[0].getConnectionURL(
				ServiceRecord.NOAUTHENTICATE_NOENCRYPT, true);
		System.out.println("url: " + url);
	}

	public void serviceSearchCompleted(int transID, int respCode)
	{
		System.out.println("serviceSearchCompleted");
		isServiceSearching = false;
		doneServiceSearching = true;
	}

	public void inquiryCompleted(int discType)
	{
		System.out.println("inquiryCompleted");
		isSearching = false;
		doneSearching = true;
	}
}