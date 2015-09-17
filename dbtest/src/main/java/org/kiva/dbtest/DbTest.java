package org.kiva.dbtest;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

public class DbTest {
	
	private static final Logger LOG = Logger.getLogger(DbTest.class);

	private static Environment env;

	public static void main(String[] args) {
		Utils.confLogger();
		LOG.info("start");
		DbType dbType = DbType.parse(args[0]);
		env = new Environment();
		env.init(dbType);

		test1();

	}

	private static void test1() {
		String username = "babulja";

		UserDAO userDAO = env.getUserDAO();

		userDAO.init();
		try {

			User user = createUser(username);

			userDAO.create(user);
			
			LOG.info("created: " + user);

			assert user != null : "created";

			User user1 = userDAO.read(username);
			
			LOG.info("read created: " + user1);

			assert user1 != null : "read";
			assert user1.equals(user);

			modify(user1);

			userDAO.update(user1);
			
			LOG.info("updated: " + user1);

			assert !user.equals(user1);

			User user2 = userDAO.read(username);
			
			LOG.info("read updated: " + user);

			assert user1.equals(user2) : "modified";

			userDAO.delete(user2);

			User user3 = userDAO.read(username);

			LOG.info("deleted: " + user3);
			
			assert user3 == null;
			
		} finally {
			userDAO.destroy();
		}
	}

	public static User createUser(String username) {
		User user = new User();
		user.setUserName(username);
		user.setFirstName("Aus");
		user.setLastName("Vitali");
		user.setBirthDate(new Date());
		user.setAge(41);
		user.setCreated(new Date());
		user.setSex('M');
		user.setSmart(true);
		return user;
	}

	private static void modify(User user) {
		user.setFirstName("kapp");
		user.setLastName("volodja");
		user.setBirthDate(new Date());
		user.setAge(33);
		user.setCreated(new Date());
		user.setSex('F');
		user.setSmart(false);
	}
}
