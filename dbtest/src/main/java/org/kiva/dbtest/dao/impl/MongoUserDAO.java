package org.kiva.dbtest.dao.impl;

import java.util.Date;

import org.bson.Document;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoUserDAO implements UserDAO{
	
	private MongoClient mongo;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private final String dbName = "mongo_db";
	
	@Override
	public User create(User user) {
		Document doc = new Document();
		doc.put("userName", user.getUserName());
		doc.put("firstName", user.getFirstName());
		doc.put("lastName", user.getLastName());
		doc.put("age", user.getAge());
		doc.put("sex", user.getSex());
		doc.put("birthDate", user.getBirthDate());
		doc.put("created", user.getCreated());
		doc.put("smart", user.getSmart());
		
		collection.insertOne(doc);
		
		return user;
	}

	@Override
	public User read(String userName) {
		Document doc = collection.find(new BasicDBObject("userName", userName)).first();
		User u = new User();
		u.setUserName((String) doc.get("userName"));
		u.setFirstName((String) doc.get("firstName"));
		u.setLastName((String) doc.get("lastName"));
		u.setAge((Integer) doc.get("age"));
		u.setSex((Character) doc.get("sex"));
		u.setBirthDate((Date) doc.get("birthDate"));
		u.setCreated((Date) doc.get("created"));
		u.setSmart((Boolean) doc.get("smart"));
		return u;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		mongo = new MongoClient();
		db = mongo.getDatabase(dbName);
		//create collection aka table
		collection = db.getCollection("mongo_coll");
		if(collection == null)
		{
			db.createCollection("mongo_coll");
			collection = db.getCollection("mongo_coll");
		}
	}

	@Override
	public void destroy() {
		mongo.close();
	}

}
