package com.vgsoftware.bank.admin;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AdminHome extends EJBHome
{
	public AdminRemote create()
	throws CreateException, RemoteException;
}
