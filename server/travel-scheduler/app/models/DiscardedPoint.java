package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class DiscardedPoint extends Model {

	private static final long serialVersionUID = 1L;
	public static final Finder<Long, DiscardedPoint> find = new Finder<Long, DiscardedPoint>(Long.class, DiscardedPoint.class);

	@Id
	@Column(name="iddiscarded_point")
	public long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_ID")
	public User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="point_ID")
	public Point point;
	
}
