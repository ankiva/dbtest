package org.kiva.dbtest.dao.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.Environment;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

public class DAOTest {

	private static Environment env;
	
	private UserDAO userDAO;
	private StatHolder statHolder = new StatHolder();
	private static int numOfIters = 1000;
	
	private static final Logger LOG = Logger.getLogger(DAOTest.class);
	
	public static void main(String[] args) {
		Utils.confLogger();
		
		DbType dbType = DbType.parse(args[0]);
		DbType dbSubType = null;
		if(args.length > 1){
			dbSubType = DbType.parse(args[1]);
		}
		
		env = new Environment();
		env.init(dbType, dbSubType);
		
		DAOTest test = new DAOTest();
		
		test.clean();
		
		test.test1();
		test.clean();
		
		test.test2();
		test.clean();
		
		test.test3();
		test.clean();
		
		test.test4();
		test.clean();
		
		env.getUserDAO().destroy();
	}
	
	public void test1() {

		LOG.info("test1");
		
		userDAO = env.getUserDAO();

		testIter("user", 1);
		
		statHolder.logStats();
	}
	
	public void test2(){
		
		LOG.info("test2");
		
		userDAO = env.getUserDAO();

		testIter("user", 2);
		
		statHolder.logStats();
	}
	
	public void test3(){
		
		LOG.info("test3");
		
		userDAO = env.getUserDAO();

		testIter("user", numOfIters);
		
		statHolder.logStats();
	}
	
	public void test4(){
		
		LOG.info("test1");
		
		List<User> users = new ArrayList<User>( 2 * numOfIters );
		String prefix = "Iteruser_";
		for(int i = 0; i < numOfIters; i++){
			users.add(createUser(prefix + i));
		}
		testCreateIter(users);
		testReadIter(users);
		for(User u : users){
			u.setFirstName("ubunt");
		}
		testUpdateIter(users);
		testDeleteIter(users);
		
		statHolder.logStats();
	}
	
	private void testIter(String prefix, int iterations){
		for(int i = 0; i < iterations; i++){
			long t = System.currentTimeMillis();
			String username = prefix + "_ " + i;
			User u = createUser(username);
			create(u);
			u = read(username);
			u.setLastName("Foo");
			update(u);
			delete(u);
			statHolder.addIter(new Stat(System.currentTimeMillis() - t));
		}
	}

	public User createUser(String username)
	{
		User u = new User();
			u.setUserName(username);
			u.setFirstName("Joe");
			u.setLastName("Doe");
			u.setAge(40);
			u.setSex('M');
			u.setBirthDate(new Date());
			u.setCreated(new Date());
			u.setSmart(true);
		
		return u;
	}
	
	public void create(User user){
		long t = System.currentTimeMillis();
		userDAO.create(user);
		statHolder.addCreate(new Stat(System.currentTimeMillis() - t));
	}
	
	public User read(String username)
	{
		long start = System.currentTimeMillis();
		
		User u = userDAO.read(username);
		
		statHolder.addRead(new Stat(System.currentTimeMillis() - start));
		return u;
	}
	
	public void update(User u)
	{
		long start = System.currentTimeMillis();
		
		userDAO.update(u);
		
		statHolder.addUpdate(new Stat(System.currentTimeMillis()-start));
	}
	
	public void delete(User u)
	{
		long start = System.currentTimeMillis();
		
		userDAO.delete(u);
		
		statHolder.addDelete(new Stat(System.currentTimeMillis()-start));
	}
	
	private void testCreateIter(List<User> users){
		long t = System.currentTimeMillis();
		for(User u : users){
			create(u);
		}
		statHolder.addCreate(new Stat(System.currentTimeMillis() - t, users.size()));
	}
	
	private void testReadIter(List<User> users){
		long t = System.currentTimeMillis();
		for(User u : users){
			read(u.getUserName());
		}
		statHolder.addRead(new Stat(System.currentTimeMillis() - t, users.size()));
	}
	
	private void testUpdateIter(List<User> users){
		long t = System.currentTimeMillis();
		for(User u : users){
			update(u);
		}
		statHolder.addUpdate(new Stat(System.currentTimeMillis() - t, users.size()));
	}
	
	private void testDeleteIter(List<User> users){
		long t = System.currentTimeMillis();
		for(User u : users){
			delete(u);
		}
		statHolder.addDelete(new Stat(System.currentTimeMillis() - t, users.size()));
	}
	
	public void clean(){
		UserDAO uDao = env.getUserDAO();
		uDao.deleteAll();
		statHolder.reset();
	}
}
