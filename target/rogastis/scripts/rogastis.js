// The root URL for the RESTful services
var rootURL = "http://localhost:8080/rogastis/service";

$('#loginIdLink').click(function() {
	showUserInfo();
	return false;
});

function validateUser() {
	console.log('Validating User');
	var tempusername = $('#loginusername').val();
	$.ajax({
		type : 'GET',
		url : rootURL + '/user/' + tempusername,
		dataType : "jsonp", // data type of response
		success : function(json) {
			if ((json.loginId == tempusername)
					&& (json.password == $('#loginpassword').val()))
				alert('User Login Successful !!');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 403)
				alert('Username does not exist !!');
			else if (jqXHR.status == 200) {
				$("#register-dialog-link").hide();
				$("#login-dialog-link").hide();
				$('displayUsername').html("");
				$('#displayUsername').append(
						'<h2 class="demoHeaders">Welcome ' + tempusername + '!</h2>');
				$("#logout-dialog-link").show();
			} else
				alert('userLogin error: ' + textStatus);
		}

	});
}

function retrieveAllQuestions() {
	console.log('retrieveAllQuestions');
	$.ajax({
		type : 'GET',
		url : rootURL + '/question',
		dataType : "jsonp", // data type of response
		success : function(json) {
			alert('Inside Success');
			questions = json;
			console.log('Question List:' + questions);
			renderAnswerList(questions);
		},
		error : function(json, textStatus, jqXHR) {
			alert('Inside Failure: ' + jqXHR.status + "Status: " + textStatus);
			alert('Date: ' + json.question);
			renderQuestionList(json);
			if (jqXHR.status == 200) {
				alert('Inside 200');
				console.log("List of questions: " + json);
				renderQuestionList(json);
			}
		}
	});
}

function retrieveAllAnswers(qstnNum) {
	console.log('retrieveAllAnswers');
	$.ajax({
		type : 'GET',
		url : rootURL + '/answer/' + qstnNum,
		dataType : "jsonp", // data type of response
		success : function(json) {
			answers = json;
			console.log('Question List:' + answers);
			renderAnswerList(answers);
		}
	});
}

function addUser() {
	console.log('addUser');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL + "/user",
		dataType : "json",
		data : registerFormToJSON(),
		success : function(data, textStatus, jqXHR) {
			$("#register-dialog").dialog("close");
			$("#userAddSuccess").show();
			// alert('User created successfully');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status == 402)
				alert('Username already exists !!');
			else if (jqXHR.status == 200) {
				$("#register-dialog").dialog("close");
				$("#userAddSuccess").show();
			} else
				alert('addUser error: ' + textStatus);
		}
	});
}

function postQuestion(loginId) {
	console.log('postQuestion');
	alert('Login Id received: ' + loginId);
	// $.ajax({
	// type : 'POST',
	// contentType : 'application/json',
	// accept : 'application/json',
	// url : rootURL + "/question",
	// dataType : "json",
	// data : constructQuestionJSON(loginId),
	// success : function(data, textStatus, jqXHR) {
	// },
	// error : function(jqXHR, textStatus, errorThrown) {
	// }
	// });
}

function renderQuestionList(questiondata) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an
	// object (not an 'array of one')
	var list = questiondata == null ? []
			: (questiondata instanceof Array ? questiondata : [ questiondata ]);

	$('questionList').html("");
	$.each(list, function(index, question) {
		console.log('Print question: ' + question.question);
		$('#questionList').append('<h3>' + question.question + '</h3>');
		// retrieveAllAnswers(question.questionId);
	});
}

function renderAnswerList(answerdata) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an
	// object (not an 'array of one')
	var list = answerdata == null ? []
			: (answerdata instanceof Array ? answerdata : [ answerdata ]);
	$
			.each(
					list,
					function(index, answer) {
						$('#questionList')
								.append(
										'<div><style type="text/css"> .tg  {border-collapse:collapse;border-spacing:0;} .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;} .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;} .tg .tg-0ord{text-align:right} </style> <table class="tg"> <tr> <th class="tg-031e">Like</th> <th class="tg-031e" rowspan="2">'
												+ answer.answer
												+ '</th> <th class="tg-0ord">'
												+ answer.loginId
												+ '</th> </tr><tr><td class="tg-031e">Dislike</td> <td class="tg-0ord">'
												+ answer.postedOn
												+ '</td> </tr> </table></div>');
					});
}

// Helper function to serialize all the form fields into a JSON string
function registerFormToJSON() {
	return JSON.stringify({
		"loginId" : $('#username').val(),
		"password" : $('#password').val(),
		"firstName" : $('#firstname').val(),
		"lastName" : $('#lastname').val(),
	});
}

// Helper function to serialize all the form fields into a JSON string
function constructQuestionJSON(loginId) {
	// var jq = jQuery.noConflict();
	return JSON.stringify({
		"questionId" : 123,
		"question" : $('#question').val(),
		"loginId" : loginId,
	});
}

// Helper function to serialize all the form fields into a JSON string
function constructAnswerJSON(loginId) {
	// var jq = jQuery.noConflict();
	return JSON.stringify({
		"answerId" : 123,
		"answer" : $('#description').val(),
		"loginId" : loginId,
	// How do i get question ID here ?? - Vinod
	});
}
