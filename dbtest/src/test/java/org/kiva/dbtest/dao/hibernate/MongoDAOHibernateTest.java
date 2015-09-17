package org.kiva.dbtest.dao.hibernate;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kiva.dbtest.Utils;
import org.kiva.dbtest.model.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MongoDAOHibernateTest {

	private EntityManager entityManager;
	private User user;

	static{
		Utils.confLogger();
	}
	
	
	
	
	@Test
	public void testCreate()
	{
	    entityManager.persist(user);
	    User loadedUser = entityManager.find(User.class, user.getUserName());
	    
		Assert.assertEquals(user, loadedUser);
	}
	
	@Test(dependsOnMethods = {"testCreate"})
	public void testRead()
	{
	    User loadedUser = entityManager.find(User.class, user.getUserName());
	    
		Assert.assertEquals(user, loadedUser);
	}
	
	@Test(dependsOnMethods = {"testRead"})
	public void testUpdate()
	{
		user.setLastName("Changed name");
	    entityManager.merge(user);
	    User loadedUser = entityManager.find(User.class, user.getUserName());
	    
		Assert.assertEquals(loadedUser.getLastName(), "Changed name");
	}
	
	@Test(dependsOnMethods = {"testUpdate"})
	public void testDelete()
	{
	    entityManager.remove(user);
		Assert.assertFalse(entityManager.contains(user));
	}

	
	@BeforeClass
	public void buildUp() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mongol");
		
		entityManager = entityManagerFactory.createEntityManager();
		Utils.confLogger();
		
		user = createTestUser();
	}
	
	@AfterClass
	public void tearDown() {
		entityManager.close();
	}
	
	@BeforeMethod
	public void beforeTest()
	{
		entityManager.getTransaction().begin();
	}
	
	@AfterMethod
	public void afterTest()
	{
		entityManager.getTransaction().commit();
	}
	
	private User createTestUser() {
		User user = new User();
		user.setUserName("test");
		user.setFirstName("test");
		user.setLastName("test");
		user.setBirthDate(new Date());
		user.setAge(41);
		user.setCreated(new Date());
		user.setSex('M');
		user.setSmart(true);

		return user;
	}

}
