package models.core;

import static org.junit.Assert.*;

import models.Response;
import models.ResponseCode;
import models.User;

import org.junit.Test;

public class UserTest
{
	private static final String CORRECT_PASSWORD = "P@SSW0RD";
	private static final String CORRECT_LOGIN = "login";
	
	@Test
	public void whenCorrectCredentialsShouldReturnOKResponse()
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(ResponseCode.OK, response.code);
	}
	
	@Test
	public void whenCorrectCredentialsShouldReturnUserAuthenticatedMessage() {
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals("User authenticated", response.message);
	}
	
	@Test
	public void whenCorrectCredentialsShouldReturnUserId() throws Exception
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(1, response.data.userId);
	}
	
	@Test
	public void whenCorrectCredentialsShouldReturnLogin() throws Exception
	{
		Response response = User.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(CORRECT_LOGIN, response.data.login);
	}

}
