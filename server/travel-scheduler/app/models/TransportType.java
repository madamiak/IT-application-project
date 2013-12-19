package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;

@Entity
public class TransportType extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtransport_type")
	public long id;
	@Column(name = "transport_type_name")
	public String name;
	@JsonIgnore
	@OneToMany(mappedBy = "transportType")
	public List<Route> routes;

	public static final Finder<Long, TransportType> find = new Finder<Long, TransportType>(
			Long.class, TransportType.class);

	public static TransportType getById(long id) {
		return find.byId(id);
	}

	public static TransportType getByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
}
