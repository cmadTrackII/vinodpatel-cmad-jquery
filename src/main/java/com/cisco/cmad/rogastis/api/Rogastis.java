package com.cisco.cmad.rogastis.api;

import java.math.BigInteger;
import java.util.List;

public interface Rogastis {

	public void registerUser(User user);
	public User getUser(String userId);
	
	public void postQuestion(Question question);
	public List<Question> getAllQuestions();
	public Question getQuestion(BigInteger questionId);
	public List<Question> getUserQuestions(String loginId);

	public void postAnswer(Answer response);
	public List<Answer> getAnswers(BigInteger questionId);
	public void updateAnswerLikes(BigInteger answerId);
	public void updateAnswerDislikes(BigInteger answerId);
}
