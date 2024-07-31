package com.vgsoftware.bank.customer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CustomerHome extends EJBHome
{
	public CustomerRemote create()
	throws CreateException, RemoteException;
}
