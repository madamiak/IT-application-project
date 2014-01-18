package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.dto.FavouriteRouteDTO;
import models.dto.PrefferencesDTO;
import models.dto.RouteDTO;
import models.dto.FavouriteRoutesDTO;
import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class FavouriteRoute extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idfavourite_route")
	public long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_ID")
	public User user;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "router_ID")
	public Route route;
	@Column(name = "rating")
	public int rating;

	public static final Finder<Long, FavouriteRoute> find = new Finder<Long, FavouriteRoute>(
			Long.class, FavouriteRoute.class);

	public static FavouriteRoute getById(long id) {
		return find.byId(id);
	}

	public JsonNode asJson() {
		return Json.toJson(this);
	}

	public static List<FavouriteRoute> getAllByUserId(long userId) {
		return find.where().eq("user_ID", userId).findList();
	}

	public static FavouriteRoutesDTO toDTO(List<FavouriteRoute> trips) {
		FavouriteRoutesDTO dto = new FavouriteRoutesDTO();
		dto.routes = new ArrayList<>();
		for (FavouriteRoute favouriteRoute : trips) {
			dto.routes.add(favouriteRoute.toDTO());
		}
		return dto;

	}

	public FavouriteRouteDTO toDTO() {
		FavouriteRouteDTO dto = new FavouriteRouteDTO();
		dto.prefferences = createPreferences();
		RouteDTO trip = calculateRoute(dto.prefferences);
		dto.points = trip.points;
		dto.routes = trip.routes;
		dto.summary = trip.summary;
		return dto;

	}

	private RouteDTO calculateRoute(PrefferencesDTO prefferences) {
		return Route.schedule(Arrays.asList(createSortedArray()), prefferences);
	}

	private Point[] createSortedArray() {
		Point[] pointArray = new Point[this.route.pointList.size()];
		try {
			for (PointList pt : this.route.pointList) {
				pointArray[pt.number] = pt.point;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			for (PointList pt : this.route.pointList) {
				pointArray[pt.number - 1] = pt.point;
			}
		}
		return pointArray;
	}

	private PrefferencesDTO createPreferences() {
		PrefferencesDTO preferences = new PrefferencesDTO();
		preferences.budget = this.route.budget;
		preferences.startDate = new SimpleDateFormat("YYYY-MM-dd HH:mm")
				.format(this.route.startingTime);
		preferences.kmPerDay = Integer.MAX_VALUE;
		return preferences;
	}

	@Override
	public String toString() {
		return String.format("['id'->%d, 'user'->%s, 'route->%s', 'rating'->%d]", this.id, this.user.toString1(), this.route.toString1(), this.rating);
	}
}
