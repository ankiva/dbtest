package org.kiva.dbtest.dao.tests;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.Environment;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

public class DAOTest {

	private static int numOfRecords = 1000;
	private static Environment env;
	private static UserDAO userDAO;
	
	private static final Logger LOG = Logger.getLogger(DAOTest.class);
	
	public static void main(String[] args) {
		DbType dbType = DbType.parse(args[0]);
		env = new Environment();
		env.init(dbType);
		
		userDAO = env.getUserDAO();
		
		Utils.confLogger();
		
		DAOTest dTest = new DAOTest();
		
		long c = dTest.create();
		long r = dTest.read();
		long u = dTest.update();
		long d = dTest.delete();
		
		LOG.info("Creation took "+c+" milliSec");
		LOG.info("Read took "+r+" milliSec");
		LOG.info("Update took "+u+" milliSec");
		LOG.info("Delete took "+d+" milliSec");
		LOG.info("Total time "+(c+r+u+d)+" milliSec");
		
		userDAO.destroy();
	}

	public long create()
	{
		User u = new User();
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<numOfRecords; i++)
		{
			u.setUserName(String.valueOf(i));
			u.setFirstName("Joe");
			u.setLastName("Doe");
			u.setAge(40);
			u.setSex('M');
			u.setBirthDate(new Date());
			u.setCreated(new Date());
			u.setSmart(true);
			
			userDAO.create(u);
		}
		
		return (System.currentTimeMillis()-start);
	}
	
	public long read()
	{
		long start = System.currentTimeMillis();
		
		for(int i=0; i<numOfRecords; i++)
		{
			userDAO.read(String.valueOf(i));
		}
		
		return (System.currentTimeMillis()-start);
	}
	
	public long update()
	{
		long start = System.currentTimeMillis();
		
		for(int i=0; i<numOfRecords; i++)
		{
			User u = userDAO.read(String.valueOf(i));
			u.setLastName("Foo");
			userDAO.update(u);
			//LOG.info(u.getUserName());
		}
		
		return (System.currentTimeMillis()-start);
	}
	
	public long delete()
	{
		long start = System.currentTimeMillis();
		
		for(int i=0; i<numOfRecords; i++)
		{
			User u = userDAO.read(String.valueOf(i));
			userDAO.delete(u);
			//LOG.info(u.getUserName());
		}
		
		return (System.currentTimeMillis()-start);
	}
}
