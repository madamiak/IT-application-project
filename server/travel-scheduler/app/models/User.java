package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.user.AuthenticationOKResponseData;
import models.user.FailedResponseData;
import models.user.RegistrationOKResponseData;
import models.user.Response;
import models.user.ResponseCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class User extends Model {
	private static final long serialVersionUID = 1L;
	private static final String USER_AUTHENTICATED = "User authenticated";
	private static final String AUTHORIZING_FAILED = "Authorizing failed";
	private static final String USER_HAS_BEEN_CREATED = "User has been created";
	private static final String USER_ALREADY_EXISTS = "User already exists";

	public static final Finder<Long, User> find = new Finder<Long, User>(
			Long.class, User.class);

	@Id
	@Column(name = "id_user")
	public long id;
	@JsonIgnore
	@Column(name = "user_password")
	public String password;
	@Column(name = "user_name")
	public String name;
	@Column(name = "user_surname")
	public String surname;
	@Column(name = "user_email")
	public String email;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_group")
	public Groups group;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	public List<FavouriteRoute> favouriteRoutes;

	public static Response register(User user) {
		if (find.where().eq("email", user.email).findUnique() == null) {
			user.save();
			return registrationOk(user);
		}
		return registrationFailed(user);
	}

	public static Response authenticate(String login, String passwordHash) {
		final User user = find.where().eq("email", login)
				.eq("password", passwordHash).findUnique();
		return user == null ? unauthorized() : authenticationOk(user);
	}

	public static User getByLogin(String login) {
		return find.where().eq("email", login).findUnique();
	}

	public static void delete(User user) {
		final User found = find.where().eq("email", user.email).findUnique();
		if (found != null) {
			found.delete();
		}
	}

	@Override
	public String toString() {
		return String.format("[id: %s, login: %s, password: %s]", id, email,
				password);
	}

	public JsonNode toJson() {
		return Json.toJson(this);
	}

	private static Response registrationOk(User user) {
		final Response response = new Response();
		response.code = ResponseCode.OK;
		response.message = USER_HAS_BEEN_CREATED;
		response.data = new RegistrationOKResponseData();
		((RegistrationOKResponseData) response.data).userId = user.id;
		return response;
	}

	private static Response registrationFailed(User user) {
		final Response response = new Response();
		response.code = ResponseCode.FAILED;
		response.message = USER_ALREADY_EXISTS;
		response.data = new FailedResponseData();
		((FailedResponseData) response.data).trialsLeft = 3;
		return response;
	}

	private static Response authenticationOk(User user) {
		final Response response = new Response();
		response.code = ResponseCode.OK;
		response.message = USER_AUTHENTICATED;
		response.data = new AuthenticationOKResponseData();
		((AuthenticationOKResponseData) response.data).userId = user.id;
		((AuthenticationOKResponseData) response.data).login = user.email;
		return response;
	}

	private static Response unauthorized() {
		final Response response = new Response();
		response.code = ResponseCode.UNAUTHORIZED;
		response.message = AUTHORIZING_FAILED;
		response.data = new FailedResponseData();
		((FailedResponseData) response.data).trialsLeft = 3;
		return response;
	}
}
