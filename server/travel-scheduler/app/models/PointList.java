package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class PointList extends Model {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idmiddle_points")
	public long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="route_ID")
	public Route route;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="point_ID")
	public Point point;
	@Column(name="number")
	public int number;
}
