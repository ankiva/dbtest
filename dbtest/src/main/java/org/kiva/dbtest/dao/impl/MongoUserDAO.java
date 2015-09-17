package org.kiva.dbtest.dao.impl;

import java.util.Date;

import org.bson.Document;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoUserDAO implements UserDAO{
	
	private MongoClient mongo;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private final String dbName = "MongoDB";
	private final String collectionName = "User";
	
	@Override
	public User create(User user) {
		Document doc = collection.find(new BasicDBObject("_id", user.getUserName())).first();
		
		if(doc == null)
		{
			doc = new Document();
			setDocValues(doc, user);
			collection.insertOne(doc);
		}
		
		return read(user.getUserName());
	}

	@Override
	public User read(String userName) {
		Document doc = collection.find(new BasicDBObject("_id", userName)).first();
		User u = null;
		if(doc != null)
		{
			u = new User();
			setUserValues(doc, u);
		}
		
		return u;
	}

	@Override
	public void update(User user) {
		Document doc = collection.find(new BasicDBObject("_id", user.getUserName())).first();
		if(doc != null)
		{
			Document newDoc = new Document();
			setDocValues(newDoc, user);
			//collection.updateOne(new BasicDBObject("_id", user.getUserName()), doc);
			collection.findOneAndReplace(doc, newDoc);
		}
		//TODO: throw UserNotFoundException()
	}

	@Override
	public void delete(User user) {
		collection.deleteOne(new BasicDBObject("_id", user.getUserName()));
	}

	@Override
	public void init() {
		mongo = new MongoClient();
		db = mongo.getDatabase(dbName);
		collection = db.getCollection(collectionName);
		if(collection == null)
		{
			db.createCollection(collectionName);
			setCollection(db.getCollection(collectionName));
		}
	}

	@Override
	public void destroy() {
		mongo.close();
	}
	
	private void setUserValues(Document doc, User u)
	{
		u.setUserName((String) doc.get("_id"));
		u.setFirstName((String) doc.get("firstName"));
		u.setLastName((String) doc.get("lastName"));
		u.setAge((Integer) doc.get("age"));
		u.setSex(((String)doc.get("sex")).charAt(0));
		u.setBirthDate((Date) doc.get("birthDate"));
		u.setCreated((Date) doc.get("created"));
		u.setSmart((Boolean) doc.get("smart"));
	}
	
	private void setDocValues(Document doc, User user)
	{
		doc.put("_id", user.getUserName());
		doc.put("firstName", user.getFirstName());
		doc.put("lastName", user.getLastName());
		doc.put("age", user.getAge());
		doc.put("sex", user.getSex());
		doc.put("birthDate", user.getBirthDate());
		doc.put("created", user.getCreated());
		doc.put("smart", user.getSmart());
	}
	
	public void setCollection(MongoCollection<Document> coll)
	{
		collection = coll;
	}

	@Override
	public void deleteAll() {
		collection.drop();
	}

}
