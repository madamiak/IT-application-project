package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class HotelParameters extends Model {

	private static final long serialVersionUID = 1L;
	public static final Finder<Long, HotelParameters> find = new Finder<Long, HotelParameters>(
			Long.class, HotelParameters.class);
	
	public HotelParameters() {
	}

	@Id
	@Column(name="id_hotel")
	public long id;
	@Column(name="price_min")
	public Integer priceMin;
	@Column(name="price_max")
	public Integer priceMax;
	@Column(name="ratings")
	public Integer ratings;
	@Column(name="stars")
	public Integer stars;
	@Column(name="phone")
	public String phone;
	@Column(name="fax")
	public String fax;
	@Column(name="contact")
	public String contact;
	@Column(name="description")
	public String description;
	@Column(name="kind")
	public String kind;
	@Column(name="rooms")
	public Integer rooms;
	@Column(name="check_in")
	public String checkIn;
	@Column(name="check_out")
	public String checkOut;
	@Column(name="payment")
	public String payment;
	@Column(name="target")
	public String target;
	@Column(name="comforts")
	public String comforts;
	@Column(name="equipment")
	public String equipment;
	@Column(name="sports")
	public String sports;
	@Column(name="offerURL")
	public String offerURL;
	@Column(name="infoURL")
	public String infoURL;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idpoints")
	public Point point;
}
