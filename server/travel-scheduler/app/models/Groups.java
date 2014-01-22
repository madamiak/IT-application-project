package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Groups extends Model {

	private static final long serialVersionUID = 1L;
	public static final Finder<Long, Groups> find = new Finder<Long, Groups>(
			Long.class, Groups.class);

	@Id
	@Column(name="id_groups")
	public long id;
	@Column(name="group_name")
	public String name;
	
	public String toString1() {
		return String.format("['id'->%d, 'name'->%s]", this.id, this.name);
	}
	
}
