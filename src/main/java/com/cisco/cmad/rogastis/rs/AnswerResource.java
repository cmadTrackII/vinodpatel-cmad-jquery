package com.cisco.cmad.rogastis.rs;

import java.math.BigInteger;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.rogastis.api.Answer;
import com.cisco.cmad.rogastis.api.Rogastis;
import com.cisco.cmad.rogastis.service.RogastisService;

@Path("/answer")
public class AnswerResource {

	private Rogastis rogastis;

	public AnswerResource() {
		rogastis = new RogastisService();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response add(Answer answer) {
		rogastis.postAnswer(answer);
		return Response.ok().build();
	}

	@Path("/{questionId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@PathParam("questionId") BigInteger questionId) {
		List<Answer> answers = rogastis.getAnswers(questionId);
		GenericEntity<List<Answer>> entity = new GenericEntity<List<Answer>>(
				answers) {
		};
		return Response.ok().entity(entity).header("Access-Control-Allow-Origin", "*").build();
	}

	@PUT
	@Path("/{answerId}/{option}")
	//@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response update(@PathParam("answerId,option") BigInteger answerId,
			String option) {
		if (option.equals("likes"))
			rogastis.updateAnswerLikes(answerId);
		if (option.equals("dislikes"))
			rogastis.updateAnswerDislikes(answerId);
		return Response.ok().build();
	}
}
