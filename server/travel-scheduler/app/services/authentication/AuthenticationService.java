package services.authentication;

import models.authentication.Response;
import models.authentication.ResponseCode;
import models.authentication.ResponseData;

public class AuthenticationService
{
	private static final String USER_AUTHENTICATED = "User authenticated";

	public Response authenticate(String login, String passwordHash)
	{
		final Response response = new Response();
		response.code = ResponseCode.OK;
		response.message = USER_AUTHENTICATED;
		response.data = new ResponseData();
		response.data.userId = 1;
		response.data.login = login;
		return response;
	}

}
