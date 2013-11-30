package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class TransportType extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idtransport_type")
	public long id;
	@Column(name="transport_type_name")
	public String name;
}
