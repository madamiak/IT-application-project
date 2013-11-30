package models;

import static org.junit.Assert.*;
import models.User;
import models.user.AuthenticationOKResponseData;
import models.user.FailedResponseData;
import models.user.RegistrationOKResponseData;
import models.user.Response;
import models.user.ResponseCode;

import org.junit.Test;

import util.IntegrationBaseTest;

import com.avaje.ebean.Ebean;

public class UserTest extends IntegrationBaseTest
{
	private static final String CORRECT_PASSWORD = "P@SSW0RD";
	private static final String CORRECT_LOGIN = "login";
	private static final String INCORRECT_LOGIN = "incorrect";
	private static final String INCORRECT_PASSWORD = "incorrect";

	@Test
	public void whenCorrectCredentials_ShouldReturnOKResponse()
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(ResponseCode.OK, response.code);
	}

	@Test
	public void whenCorrectCredentials_ShouldReturnUserAuthenticatedMessage()
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals("User authenticated", response.message);
	}

	@Test
	public void whenCorrectCredentials_ShouldReturnUserId() throws Exception
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(1, ((AuthenticationOKResponseData) response.data).userId);
	}

	@Test
	public void whenCorrectCredentials_ShouldReturnLogin() throws Exception
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(CORRECT_LOGIN, ((AuthenticationOKResponseData) response.data).login);
	}

	@Test
	public void whenIncorrectCredentials_ShouldReturnUnauthorizedCode() throws Exception
	{
		Response response = User.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
		assertEquals(ResponseCode.UNAUTHORIZED, response.code);
	}

	@Test
	public void whenIncorrectCredentials_ShouldReturnAuthorizingFailedMessage() throws Exception
	{
		Response response = User.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
		assertEquals("Authorizing failed".toLowerCase(), response.message.toLowerCase());
	}

	@Test
	public void whenIncorrectCredentials_ShouldReturnNumberOfTrialsLeft() throws Exception
	{
		Response response = User.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
		assertEquals(3, ((FailedResponseData) response.data).trialsLeft);
	}

	@Test
	public void shouldBeAbleToDeleteUser() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			user.login = CORRECT_LOGIN;
			user.password = CORRECT_PASSWORD;
			User.delete(user);
			assertEquals(ResponseCode.UNAUTHORIZED, User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD).code);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenTryingToDeleteNotExistingUser_ShouldNotThrowAnyException() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			user.login = INCORRECT_LOGIN;
			user.password = INCORRECT_PASSWORD;
			User.delete(user);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldReturnOKResponse() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			final Response response = User.register(user);
			assertEquals(ResponseCode.OK, response.code);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldReturnProperMessage() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			final Response response = User.register(user);
			assertEquals("User has been created", response.message);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldReturnUserId() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			final Response response = User.register(user);
			assertEquals(2, ((RegistrationOKResponseData) response.data).userId);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldPersistUserInDB() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			User.register(user);
			assertEquals(2, User.find.all().size());
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringExistingUser_ShouldReturnFailedResponseCode() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			user.login = CORRECT_LOGIN;
			user.password = CORRECT_PASSWORD;
			final Response response = User.register(user);
			assertEquals(ResponseCode.FAILED, response.code);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringExistingUser_ShouldReturnProperMessage() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			user.login = CORRECT_LOGIN;
			user.password = CORRECT_PASSWORD;
			final Response response = User.register(user);
			assertEquals("User already exists", response.message);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

	@Test
	public void whenRegisteringExistingUser_ShouldReturnNumberOfTrialsLeft() throws Exception
	{
		Ebean.beginTransaction();
		try
		{
			User user = new User();
			user.login = CORRECT_LOGIN;
			user.password = CORRECT_PASSWORD;
			final Response response = User.register(user);
			assertEquals(3, ((FailedResponseData) response.data).trialsLeft);
		} finally
		{
			Ebean.rollbackTransaction();
		}
	}

}
