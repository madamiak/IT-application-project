package services;

import java.util.ArrayList;
import java.util.List;

import models.domain.PointData;

public class PointOrderManipulator {
	
	public static void sortByStraightLine(List<PointData> pointList){
		if(pointList.isEmpty())return;
		List<PointData> currentPoints = new ArrayList<PointData>();
		List<PointData> bestPoints= new ArrayList<PointData>();;
		PointData lastPoint=pointList.get(pointList.size()-1);
		currentPoints.add(pointList.get(0));
		pointList.remove(0);
		pointList.remove(pointList.size()-1);
		searchForRoute(new ArrayList<PointData>(pointList), new ArrayList<PointData>(currentPoints), lastPoint, bestPoints);
		pointList.clear();
		pointList.addAll(bestPoints);
	}
	private static void searchForRoute(List<PointData> pointList,
			List<PointData> currentPoints, PointData lastPoint,
			List<PointData> bestPoints) {
		if (!pointList.isEmpty()) {
			for (int i = 0; i < pointList.size(); i++) {
				List<PointData> currentTmp = new ArrayList<PointData>(
						currentPoints);
				List<PointData> pointTmp = new ArrayList<PointData>(pointList);
				currentTmp.add(pointList.get(i));
				pointTmp.remove(i);
				searchForRoute(pointTmp, currentTmp, lastPoint, bestPoints);
			}
		} else {
			currentPoints.add(lastPoint);
			float distance = calculateRouteDistance(currentPoints);
			float bestDistance = calculateRouteDistance(bestPoints);
			if (distance < bestDistance) {
				bestPoints.clear();
				bestPoints.addAll(currentPoints);
			}
		}
	}

	private static float calculateRouteDistance(List<PointData> points) {
		if (points.size() == 0)
			return Float.MAX_VALUE;
		float distance = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			distance = distance
					+ calculateDistance(points.get(i), points.get(i + 1));
		}
		return distance;
	}

	private static float calculateDistance(PointData point1, PointData point2) {
		float x = point1.longitude - point2.longitude;
		float y = point1.latitudel - point2.latitudel;
		return (float) Math.sqrt((x * x) + (y * y));
	}

}
