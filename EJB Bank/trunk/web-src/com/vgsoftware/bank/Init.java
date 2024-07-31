package com.vgsoftware.bank;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.vgsoftware.bank.admin.AdminHome;
import com.vgsoftware.bank.customer.CustomerHome;

public class Init implements ServletContextListener
{
	public void contextDestroyed(ServletContextEvent event)
	{
	}

	public void contextInitialized(ServletContextEvent event)
	{
		try
		{
			Context jndiContext=new InitialContext();
			Object ref=jndiContext.lookup("java:comp/env/Admin");
			event.getServletContext().setAttribute(Globals.ADMIN_HOME,PortableRemoteObject.narrow(ref,AdminHome.class));
			ref=jndiContext.lookup("java:comp/env/Customer");
			event.getServletContext().setAttribute(Globals.CUSTOMER_HOME,PortableRemoteObject.narrow(ref,CustomerHome.class));
		}
		catch(NamingException ne)
		{
			ne.printStackTrace(System.err);
		}
	}
}
