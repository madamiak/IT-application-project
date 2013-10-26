package services.authentication;

import static org.junit.Assert.*;

import models.authentication.Response;
import models.authentication.ResponseCode;

import org.junit.Before;
import org.junit.Test;

import services.authentication.AuthenticationService;

public class AuthenticationServiceTest
{
	private static final String CORRECT_PASSWORD = "P@SSW0RD";
	private static final String CORRECT_LOGIN = "login";
	
	private AuthenticationService testObject;

	@Before
	public void setUp() throws Exception
	{
		testObject = new AuthenticationService();
	}

	@Test
	public void whenCorrectCredentialsShouldReturnOKResponse()
	{
		Response response = testObject.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(ResponseCode.OK, response.code);
	}
	
	@Test
	public void whenCorrectCredentialsShouldReturnUserAuthenticatedMessage() {
		Response response = testObject.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals("User authenticated", response.message);
	}
	
	@Test
	public void whenCorrectCredentialsShouldReturnUserId() throws Exception
	{
		Response response = testObject.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(1, response.data.userId);
	}
	
	@Test
	public void whenCorrectCredentialsShouldReturnLogin() throws Exception
	{
		Response response = testObject.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		assertEquals(CORRECT_LOGIN, response.data.login);
	}

}
