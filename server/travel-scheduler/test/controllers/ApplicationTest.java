package controllers;

import static org.junit.Assert.*;

import models.User;

import org.junit.Test;

import play.mvc.Result;
import play.test.Helpers;
import util.IntegrationBaseTest;

public class ApplicationTest extends IntegrationBaseTest
{
	private static final String NEW_LOGIN = "asd";
	private static final String CORRECT_PASSWORD = "P@SSW0RD";
	private static final String CORRECT_LOGIN = "login";
	private static final String NEW_PASSWORD = "asdsgf";

	@Test
	public void whenUserAuthenticates_ShouldReturnOKCode()
	{
		Result result = Helpers.callAction(controllers.routes.ref.Application.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
		assertTrue(Helpers.contentAsString(result).contains("\"code\":\"OK\""));
	}

	@Test
	public void whenUserRegisters_ShouldReturnOKCode() throws Exception
	{
		User user = new User();
		user.login = NEW_LOGIN;
		user.password = NEW_PASSWORD;
		final Result result = Helpers.callAction(controllers.routes.ref.Application.register(),
				Helpers.fakeRequest(Helpers.PUT, "/user/register").withJsonBody(user.toJson()));
		assertTrue(Helpers.contentAsString(result).contains("\"code\":\"OK\""));
	}

}
