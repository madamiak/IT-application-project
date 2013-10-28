package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import models.Response;
import models.ResponseCode;
import models.User;

import org.junit.Test;

import com.avaje.ebean.Ebean;

public class UserTest
{
	private static final String CORRECT_PASSWORD = "P@SSW0RD";
	private static final String CORRECT_LOGIN = "login";
	private static final String INCORRECT_LOGIN = "incorrect";
	private static final String INCORRECT_PASSWORD = "incorrect";

	@Test
	public void whenCorrectCredentials_ShouldReturnOKResponse()
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
				assertEquals(ResponseCode.OK, response.code);
			}
		});
	}

	@Test
	public void whenCorrectCredentials_ShouldReturnUserAuthenticatedMessage()
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
				assertEquals("User authenticated", response.message);
			}
		});
	}

	@Test
	public void whenCorrectCredentials_ShouldReturnUserId() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
				assertEquals(1, response.data.userId);
			}
		});
	}

	@Test
	public void whenCorrectCredentials_ShouldReturnLogin() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
				assertEquals(CORRECT_LOGIN, response.data.login);
			}
		});
	}

	@Test
	public void whenIncorrectCredentials_ShouldReturnUnauthorizedCode() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
				assertEquals(ResponseCode.UNAUTHORIZED, response.code);
			}
		});
	}

	@Test
	public void whenIncorrectCredentials_ShouldReturnAuthorizingFailedMessage() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
				assertEquals("Authorizing failed".toLowerCase(), response.message.toLowerCase());
			}
		});
	}

	@Test
	public void whenIncorrectCredentials_ShouldReturnNumberOfTrialsLeft() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Response response = User.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
				assertEquals(3, response.data.trialsLeft);
			}
		});
	}

	@Test
	public void shouldBeAbleToDeleteUser() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
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
		});
	}

	@Test
	public void whenTryingToDeleteNotExistingUser_ShouldNotThrowAnyException() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
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
		});
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldReturnOKResponse() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
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
		});
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldReturnProperMessage() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
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
		});
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldReturnUserId() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
			{
				Ebean.beginTransaction();
				try
				{
					User user = new User();
					final Response response = User.register(user);
					assertEquals(2, response.data.userId);
				} finally
				{
					Ebean.rollbackTransaction();
				}
			}
		});
	}

	@Test
	public void whenRegisteringWithCorrectData_ShouldPersistUserInDB() throws Exception
	{
		running(fakeApplication(), new Runnable()
		{
			public void run()
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
		});
	}

}
