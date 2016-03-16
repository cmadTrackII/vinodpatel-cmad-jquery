package com.cisco.cmad.rogastis.db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.cisco.cmad.rogastis.api.User;
import com.cisco.cmad.rogastis.api.UserNotFoundException;

public class UserDAO {

	private static SessionFactory factory;
	private static UserDAO instance;

	private UserDAO() {
		Configuration conf = new Configuration();
		conf.configure();
		ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(
				conf.getProperties()).buildServiceRegistry();
		factory = conf.buildSessionFactory(registry);
		System.out.println("Creating factory");
	}

	public synchronized static UserDAO getInstance() {
		if (instance == null)
			instance = new UserDAO();
		return instance;
	}

	public void create(User user) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(user);
		Query qry = session
				.createSQLQuery("insert into user_roles(loginId,roleName) values('"
						+ user.getLoginId() + "','user')");
		qry.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	public User read(String loginId) {
		Session session = factory.openSession();
		User user = (User) session.get(User.class, loginId);
		session.close();
		if (user == null)
			throw new UserNotFoundException();
		return user;
	}

	public boolean isPresent(String loginId) {
		Session session = factory.openSession();
		User user = (User) session.get(User.class, loginId);
		session.close();
		if (user == null)
			return false;
		return true;
	}

}
