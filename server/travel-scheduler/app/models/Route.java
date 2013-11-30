package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Route extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_route")
	public long id;
	@Column(name="route_name")
	public String name;
	@Column(name="route_starting_time")
	public Date startingTime;
	@Column(name="route_budget")
	public int budget;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="route_transport_type")
	public TransportType transportType;
}
