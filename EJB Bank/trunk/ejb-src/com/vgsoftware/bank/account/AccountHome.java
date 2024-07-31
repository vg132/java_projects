package com.vgsoftware.bank.account;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface AccountHome extends EJBHome
{
	public AccountRemote create(String owner, Integer number)
	throws RemoteException,CreateException;
	
	public AccountRemote findByPrimaryKey(Integer number)
	throws RemoteException, FinderException;
	
	public Collection<AccountRemote> findByOwner(String owner)
	throws RemoteException, FinderException;
	
	public Collection<AccountRemote> findAll()
	throws RemoteException, FinderException;
}
