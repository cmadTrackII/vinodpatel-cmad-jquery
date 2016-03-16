package com.cisco.cmad.rogastis.db;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.cisco.cmad.rogastis.api.Answer;

public class AnswerDAO {

	private static SessionFactory factory;
	private static AnswerDAO instance;

	private AnswerDAO() {
		Configuration conf = new Configuration();
		conf.configure();
		ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(
				conf.getProperties()).buildServiceRegistry();
		factory = conf.buildSessionFactory(registry);
		System.out.println("Creating factory");
	}

	public synchronized static AnswerDAO getInstance() {
		if (instance == null)
			instance = new AnswerDAO();
		return instance;
	}

	public void create(Answer answer) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(answer);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Answer> readAnswers(BigInteger questionId) {
		Session session = factory.openSession();
		List<Answer> results = session
				.createQuery("from Answer where questionId= :questionId order by postedOn")
				.setBigInteger("questionId", questionId).list();
		session.close();
		return results;
	}

	public boolean isPresent(BigInteger responseId) {
		Session session = factory.openSession();
		Answer response = (Answer) session.get(Answer.class, responseId);
		session.close();
		if (response == null)
			return false;
		return true;
	}
	
	public void updateLikes(BigInteger answerId) {
		Session session = factory.openSession();
		session.beginTransaction();
		Query qry = session.createSQLQuery("update Answer ans set ans.likes=ans.likes + 1 where ans.answerId= :answerId").setBigInteger("answerId", answerId);
		qry.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateDisLikes(BigInteger answerId) {
		Session session = factory.openSession();
		session.beginTransaction();
		Query qry = session.createSQLQuery("update Answer ans set ans.dislikes=ans.dislikes + 1 where ans.answerId= :answerId").setBigInteger("answerId", answerId);
		qry.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

}
