package org.kiva.dbtest.dao.impl;

import java.util.Formatter;

import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraUserDaoImpl implements UserDAO {

	Cluster cluster;
	Session session;
	private String dbName = "dbTest";
	
	public void init() {
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect(dbName);
	}
	
	@Override
	public User create(User user) {
		String sql = "INSERT INTO user (userName, firstName, lastName, age, sex, birthDate, created, smart) VALUES (%1$, %2$, %3$, %4$, %5$, %6$, %7$, %8$)";
		String formattedSql = String.format(sql, user.getUserName(), user.getFirstName(), user.getLastName(), user.getAge(), user.getSex(), user.getBirthDate(), user.getCreated(), user.getSmart());
		System.out.println("formattedSql:"+formattedSql);
		session.execute(formattedSql);
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
	public void destroy() {
		cluster.close();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
