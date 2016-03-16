package com.cisco.cmad.rogastis.rs;

import java.math.BigInteger;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.rogastis.api.Question;
import com.cisco.cmad.rogastis.api.Rogastis;
import com.cisco.cmad.rogastis.service.RogastisService;

@Path("/question")
public class QuestionResource {

	private Rogastis rogastis;

	public QuestionResource() {
		rogastis = new RogastisService();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response add(Question question) {
		rogastis.postQuestion(question);
		return Response.ok().build();
	}

	@Path("/{questionId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@PathParam("questionId") BigInteger questionId) {
		Question question = rogastis.getQuestion(questionId);
		Response.ok().header("Access-Control-Allow-Origin", "*");
		return Response.ok().entity(question).build();
	}
	
//	@Path("/{loginId}")
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	public Response get(@PathParam("loginId") String loginId) {
//		List<Question> questions = rogastis.getUserQuestions(loginId);
//		GenericEntity<List<Question>> entity = new GenericEntity<List<Question>>(
//				questions) {
//		};
//		return Response.ok().entity(entity).build();
//	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get() {
		List<Question> questions = rogastis.getAllQuestions();
		GenericEntity<List<Question>> entity = new GenericEntity<List<Question>>(
				questions) {
		};
		return Response.ok().entity(entity).header("Access-Control-Allow-Origin", "*").build();
	}

}
