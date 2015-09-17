package org.kiva.dbtest.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

import redis.clients.jedis.Jedis;

public class RedisUserDAO implements UserDAO {

	private static Logger LOG = Logger.getLogger(RedisUserDAO.class);

	private Jedis jedis;

	@Override
	public void init() {
		LOG.info("init");
		jedis = new Jedis("192.168.59.103");
	}

	@Override
	public void destroy() {
		LOG.info("close");
		// jedis.flushAll();
		jedis.close();
	}

	@Override
	public User create(User user) {
		jedis.hset(user.getUserName(), "firstname", user.getFirstName());
		jedis.hset(user.getUserName(), "lastname", user.getLastName());
		jedis.hset(user.getUserName(), "age", String.valueOf(user.getAge()));
		jedis.hset(user.getUserName(), "birthdate",
				Utils.format(user.getBirthDate()));
		jedis.hset(user.getUserName(), "created",
				Utils.format(user.getCreated()));
		jedis.hset(user.getUserName(), "sex", String.valueOf(user.getSex()));
		jedis.hset(user.getUserName(), "smart", String.valueOf(user.getSmart()));
		jedis.set("username", user.getUserName());
		return user;
	}

	@Override
	public User read(String userName) {
		try {
			if (jedis.exists(userName)) {
				User user = new User();
				user.setUserName(userName);
				user.setFirstName(jedis.hget(userName, "firstname"));
				user.setLastName(jedis.hget(userName, "lastname"));
				user.setAge(Integer.valueOf(jedis.hget(userName, "age")));
				user.setBirthDate(Utils.parse(jedis.hget(userName, "birthdate")));
				user.setCreated(Utils.parse(jedis.hget(userName, "created")));
				user.setSex(jedis.hget(userName, "sex").charAt(0));
				user.setSmart(Boolean.valueOf(jedis.hget(userName, "smart")));
				return user;
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve user", e);
		}
		return null;
	}

	@Override
	public void update(User user) {
		if (user != null && jedis.exists(user.getUserName())) {
			jedis.hset(user.getUserName(), "firstname", user.getFirstName());
			jedis.hset(user.getUserName(), "lastname", user.getLastName());
			jedis.hset(user.getUserName(), "age", String.valueOf(user.getAge()));
			jedis.hset(user.getUserName(), "birthdate",
					Utils.format(user.getBirthDate()));
			jedis.hset(user.getUserName(), "created",
					Utils.format(user.getCreated()));
			jedis.hset(user.getUserName(), "sex", String.valueOf(user.getSex()));
			jedis.hset(user.getUserName(), "smart",
					String.valueOf(user.getSmart()));
			jedis.set("username", user.getUserName());
		}
	}

	@Override
	public void delete(User user) {
		if (user != null) {
			jedis.del(user.getUserName());
		}
	}

	@Override
	public void deleteAll() {
		jedis.del("*");

	}

}
