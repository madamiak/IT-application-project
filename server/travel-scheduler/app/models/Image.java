package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Image extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idimage")
	public long id;
	@Column(name = "url")
	public String url;
	@JsonIgnore
	@ManyToMany(mappedBy = "images")
	public Set<Point> points = new HashSet<Point>();

	public static final Finder<Long, Image> find = new Finder<Long, Image>(
			Long.class, Image.class);
	
	public static JsonNode getById(long id) {
		return Json.toJson(find.where().eq("idimage", id).findUnique());
	}

}
