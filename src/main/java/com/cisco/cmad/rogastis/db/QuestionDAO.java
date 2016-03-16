package com.cisco.cmad.rogastis.db;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.cisco.cmad.rogastis.api.Question;
import com.cisco.cmad.rogastis.api.QuestionNotFoundException;

public class QuestionDAO {

	private static SessionFactory factory;
	private static QuestionDAO instance;

	private QuestionDAO() {
		Configuration conf = new Configuration();
		conf.configure();
		ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(
				conf.getProperties()).buildServiceRegistry();
		factory = conf.buildSessionFactory(registry);
		System.out.println("Creating factory");
	}

	public synchronized static QuestionDAO getInstance() {
		if (instance == null)
			instance = new QuestionDAO();
		return instance;
	}

	public void create(Question question) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(question);
		session.getTransaction().commit();
		session.close();
	}

	public Question read(BigInteger questionId) {
		Session session = factory.openSession();
		Question question = (Question) session.get(Question.class, questionId);
		session.close();
		if (question == null)
			throw new QuestionNotFoundException();
		return question;
	}

	@SuppressWarnings("unchecked")
	public List<Question> readAll() {
		Session session = factory.openSession();
		List<Question> results = session.createQuery("from Question").list();
		session.close();
		return results;
	}

	public boolean isPresent(BigInteger questionId) {
		Session session = factory.openSession();
		Question question = (Question) session.get(Question.class, questionId);
		session.close();
		if (question == null)
			return false;
		return true;
	}

	public List<Question> readUserAll(String loginId) {
		Session session = factory.openSession();
		@SuppressWarnings("unchecked")
		List<Question> results = session
				.createQuery("from Question where loginId = :loginId")
				.setString("loginId", loginId).list();
		session.close();
		return results;
	}
}
