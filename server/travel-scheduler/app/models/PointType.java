package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class PointType extends Model {

	private static final long serialVersionUID = 1L;
	public static final Finder<Long, PointType> find = new Finder<Long, PointType>(Long.class, PointType.class);
	
	@Id
	@Column(name="idpoint_type")
	public long id;
	@Column(name="type_category")
	public String name;
	
	public static PointType getByName(String name){
		PointType pt = find.where().eq("type_category", name).findUnique();
		return pt;
	}
}
