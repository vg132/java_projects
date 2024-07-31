package com.vgsoftware.hibernate;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main
{
	public static void main(String args[])
	{
		Session session=null;
		try
		{
			SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
			session=sessionFactory.openSession();
			Track t=new Track();
			t.setId(1);
			t.setAdded(new Date());
			t.setPlayTime(new Date());
			t.setTitle("Test Title");
			t.setVolume((short)20);
			t.setFilePath("Test Path");
			session.save(t);
		}
		catch(HibernateException he)
		{
			he.printStackTrace(System.err);
		}
		finally
		{
			session.flush();
			session.close();
		}
	}
}
