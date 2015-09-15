package org.kiva.dbtest.dao.impl;

import java.sql.Date;
import java.util.Formatter;

import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraUserDaoImpl implements UserDAO {

	Cluster cluster;
	Session session;
	private String dbName = "dbTest";
	private String host = "127.0.0.1";
	
	public void init() {
		cluster = Cluster.builder().addContactPoint(host).build();
		session = cluster.connect(dbName);
	}
	
	@Override
	public User create(User user) {
		String sql = "INSERT INTO users (userName, firstName, lastName, age, sex, birthDate, created, smart) VALUES ('%1$s', '%2$s', '%3$s', %4$s, '%5$s', '%6$s', '%7$s', %8$s)";
		String formattedSql = String.format(sql, user.getUserName(), user.getFirstName(), user.getLastName(), user.getAge(), user.getSex(), new Date(user.getBirthDate().getTime()), new Date(user.getCreated().getTime()), user.getSmart());
		System.out.println("formattedSql:"+formattedSql);
		session.execute(formattedSql);
		return user;
	}

	@Override
	public User read(String userName) {
		String sql = "SELECT * FROM users WHERE username = '"+ userName+"'";
		ResultSet rs = session.execute(sql);
		for (Row row : rs) {
			User user = new User();
			user.setUserName(row.getString("userName"));
			user.setFirstName(row.getString("firstName"));
			user.setLastName(row.getString("lastName"));
			user.setAge(row.getInt("age"));
			user.setSex(row.getString("sex").charAt(0));
			user.setBirthDate(new java.util.Date(row.getTimestamp("birthDate").getTime()));
			user.setCreated(new java.util.Date(row.getTimestamp("created").getTime()));
			user.setSmart(row.getBool("smart"));
			return user;
		}
		return null;
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE users SET firstName='%2$s', lastName='%3$s', age=%4$s, sex='%5$s', birthDate='%6$s', created='%7$s', smart=%8$s WHERE username='"+user.getUserName()+"'";
		String formattedSql = String.format(sql, user.getUserName(), user.getFirstName(), user.getLastName(), user.getAge(), user.getSex(), new Date(user.getBirthDate().getTime()), new Date(user.getCreated().getTime()), user.getSmart());
		System.out.println("formattedSql:"+formattedSql);
		session.execute(formattedSql);
	}

	@Override
	public void delete(User user) {
		String sql = "DELETE FROM users WHERE username='"+user.getUserName()+"'";
		session.execute(sql);
	}

	@Override
	public void destroy() {
		session.close();
		cluster.close();
	}

}
