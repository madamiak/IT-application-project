package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class FavouriteRoute extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idfacourite_route")
	public long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_ID")
	public User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="router_ID")
	public Route route;
	@Column(name="rating")
	public int rating;
}
