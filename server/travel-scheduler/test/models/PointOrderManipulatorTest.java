package models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import models.domain.PointData;

import org.junit.Test;

public class PointOrderManipulatorTest {

	@Test
	public void sortByStraightLineTest() {
		List<PointData> pointList = new ArrayList<PointData>();
		List<Integer> answerList = new ArrayList<Integer>();
		
		pointList.add(new PointData(1, 17+(2/60), 51+(7/60)));//Wrocław
		pointList.add(new PointData(2, 21+(2/60), 52+(12/60)));//Warszawa
		pointList.add(new PointData(3, 19+(0/60), 50+(15/60)));//Katowice
		pointList.add(new PointData(4, 19+(41/60), 51+(24/60)));//Piotrków Trybunalski
		pointList.add(new PointData(5, 18+(34/60), 51+(13/60)));//Wieluń
		pointList.add(new PointData(6, 22+(34/60), 51+(14/60)));//Lublin
		
		Point.sortByStraightLine(pointList);

		answerList.add(1);
		answerList.add(5);
		answerList.add(3);
		answerList.add(4);
		answerList.add(2);
		answerList.add(6);
		
		for(int i=0; i<pointList.size();i++){
			assertTrue(answerList.get(i)==pointList.get(i).id);
		}
	}

}
