package org.kiva.dbtest.dao.impl;

import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUserDAO implements UserDAO{
	
	private MongoClient mongo;
	private MongoDatabase db;
	private final String dbName = "mongo_db";
	
	@Override
	public User create(User user) {
		return null;
	}

	@Override
	public User read(String userName) {
		// TODO Auto-generated method stub
		return null;
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
	}

	@Override
	public void destroy() {
		mongo.close();
	}

}
