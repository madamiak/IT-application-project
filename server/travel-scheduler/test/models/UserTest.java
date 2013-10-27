package models;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import models.Response;
import models.ResponseCode;
import models.User;

import org.junit.Test;

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

}
