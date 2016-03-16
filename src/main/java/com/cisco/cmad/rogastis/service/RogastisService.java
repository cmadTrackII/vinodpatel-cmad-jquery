package com.cisco.cmad.rogastis.service;

import java.math.BigInteger;
import java.util.List;

import com.cisco.cmad.rogastis.api.DuplicateUserException;
import com.cisco.cmad.rogastis.api.InvalidQuestionException;
import com.cisco.cmad.rogastis.api.InvalidAnswerException;
import com.cisco.cmad.rogastis.api.InvalidUserException;
import com.cisco.cmad.rogastis.api.Question;
import com.cisco.cmad.rogastis.api.QuestionNotFoundException;
import com.cisco.cmad.rogastis.api.Answer;
import com.cisco.cmad.rogastis.api.AnswerNotFoundException;
import com.cisco.cmad.rogastis.api.Rogastis;
import com.cisco.cmad.rogastis.api.User;
import com.cisco.cmad.rogastis.api.UserNotFoundException;
import com.cisco.cmad.rogastis.db.QuestionDAO;
import com.cisco.cmad.rogastis.db.AnswerDAO;
import com.cisco.cmad.rogastis.db.UserDAO;

public class RogastisService implements Rogastis {

	private UserDAO udao;
	private QuestionDAO qdao;
	private AnswerDAO adao;

	public RogastisService() {
		udao = UserDAO.getInstance();
		qdao = QuestionDAO.getInstance();
		adao = AnswerDAO.getInstance();
	}

	public void registerUser(User user) {
		if (user == null || user.getFirstName() == null
				|| user.getLastName() == null || user.getLoginId() == null
				|| user.getPassword() == null)
			throw new InvalidUserException();
		if (udao.isPresent(user.getLoginId()))
			throw new DuplicateUserException();
		udao.create(user);
	}

	public User getUser(String loginId) {
		if (udao.isPresent(loginId))
			return udao.read(loginId);
		throw new UserNotFoundException();
	}

	public void postQuestion(Question question) {
		if (question == null || question.getQuestion() == null)
			throw new InvalidQuestionException();
		qdao.create(question);
	}

	public void postAnswer(Answer answer) {
		if (answer == null || answer.getAnswer() == null)
			throw new InvalidAnswerException();
		adao.create(answer);
	}

	public Question getQuestion(BigInteger questionId) {
		if (qdao.isPresent(questionId))
			return qdao.read(questionId);
		throw new QuestionNotFoundException();
	}

	public List<Answer> getAnswers(BigInteger questionId) {
		if (qdao.isPresent(questionId))
			return adao.readAnswers(questionId);
		throw new QuestionNotFoundException();

	}

	public List<Question> getAllQuestions() {
		if (qdao.readAll() != null)
			return qdao.readAll();
		throw new QuestionNotFoundException();
	}

	public void updateAnswerLikes(BigInteger answerId) {
		if (adao.isPresent(answerId))
			adao.updateLikes(answerId);
		else
			throw new AnswerNotFoundException();
	}

	public void updateAnswerDislikes(BigInteger answerId) {
		if (adao.isPresent(answerId))
			adao.updateDisLikes(answerId);
		else
			throw new AnswerNotFoundException();
	}

	public List<Question> getUserQuestions(String loginId) {
		if (qdao.readUserAll(loginId) != null)
			return qdao.readUserAll(loginId);
		throw new QuestionNotFoundException();
	}
}
