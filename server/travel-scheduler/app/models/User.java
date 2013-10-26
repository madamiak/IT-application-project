package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class User extends Model
{
	private static final long serialVersionUID = 1L;
	private static final String USER_AUTHENTICATED = "User authenticated";
	public static final Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);
	
	@Id
	public String login;
	public String password;

	public static Response authenticate(String login, String passwordHash)
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
