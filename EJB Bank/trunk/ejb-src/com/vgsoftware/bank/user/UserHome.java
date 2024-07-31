package com.vgsoftware.bank.user;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface UserHome extends EJBHome
{
	public static final Integer CUSTOMER_TYPE=0;
	public static final Integer ADMIN_TYPE=1;

	public UserRemote create(String name, String password)
	throws CreateException, RemoteException;

	public UserRemote create(String name, String password, Integer type)
	throws CreateException, RemoteException;

	public UserRemote findByPrimaryKey(String pk)
	throws FinderException, RemoteException;

	public Collection<UserRemote> findAllByType(Integer type)
	throws FinderException, RemoteException;
}
