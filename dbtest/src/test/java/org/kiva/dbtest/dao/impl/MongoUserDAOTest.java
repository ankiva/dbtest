package org.kiva.dbtest.dao.impl;

import java.util.Date;

import org.bson.Document;
import org.kiva.dbtest.model.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Test
public class MongoUserDAOTest {

	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document> collection;

	private User user;
	private MongoUserDAO mongoDAO = new MongoUserDAO();

	@BeforeClass
	public void buildUp() {
		client = new MongoClient();
		db = client.getDatabase("MongoTestDb");

		collection = db.getCollection("MongoTestCollection");

		if (collection == null) {
			db.createCollection("MongoTestCollection");
			collection = db.getCollection("MongoTestCollection");
		}

		mongoDAO.setCollection(collection);

		user = createTestUser();
	}

	@AfterClass
	public void tearDown() {
		user = null;
		mongoDAO = null;
		db.drop();
		client.close();
	}

	@Test
	public void testCreate() {
		mongoDAO.create(user);

		Document doc = collection.find(new BasicDBObject("userName", "test"))
				.first();
		Assert.assertNotNull(doc);
	}

	@Test(dependsOnMethods = { "testCreate" })
	public void testRead() {
		User u = mongoDAO.read("test");
		Assert.assertNotNull(u);
	}

	@Test(dependsOnMethods = { "testCreate" })
	public void testUpdate() {
		User u = mongoDAO.read("test");
		u.setLastName("Foo");
		mongoDAO.update(u);

		u = mongoDAO.read("test");

		Assert.assertEquals(u.getLastName(), "Foo");
	}

	@Test(dependsOnMethods = { "testRead" })
	public void testDelete() {
		User u = mongoDAO.read("test");
		mongoDAO.delete(u);

		u = mongoDAO.read("test");
		Assert.assertNull(u);
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
