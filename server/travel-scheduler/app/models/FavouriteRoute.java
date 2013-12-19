package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	public static FavouriteRoutesDTO getAllByUserId(int userId) {
		FavouriteRoutesDTO routesDTO = new FavouriteRoutesDTO();
		routesDTO.routes = new ArrayList<FavouriteRouteDTO>();
		List<FavouriteRoute> routes = find.where().eq("user_ID", userId).findList();
		for (FavouriteRoute fr : routes) {
			FavouriteRouteDTO favouriteRouteDTO = new FavouriteRouteDTO();
			favouriteRouteDTO.prefferences = new PrefferencesDTO();
			favouriteRouteDTO.prefferences.budget = fr.route.budget;
			favouriteRouteDTO.prefferences.startDate = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(fr.route.startingTime);
			favouriteRouteDTO.prefferences.kmPerDay = Integer.MAX_VALUE;
			if(fr.route.pointList.size() == 0) {
				continue;
			}
			long[] pointIds = new long[fr.route.pointList.size()];
			for(PointList pointList : fr.route.pointList) {
				pointIds[pointList.number] = pointList.point.id;
			}
			RouteDTO routeDTO = Route.schedule(pointIds, favouriteRouteDTO.prefferences);
			favouriteRouteDTO.points = routeDTO.points;
			favouriteRouteDTO.routes = routeDTO.routes;
			favouriteRouteDTO.summary = routeDTO.summary;
			favouriteRouteDTO.prefferences = new PrefferencesDTO();
			favouriteRouteDTO.prefferences.budget = fr.route.budget;
			favouriteRouteDTO.prefferences.startDate = new SimpleDateFormat("YYYY-MM-dd HH:mm").format(fr.route.startingTime);
			
			routesDTO.routes.add(favouriteRouteDTO);
		}
		return routesDTO;
		
	}
}
