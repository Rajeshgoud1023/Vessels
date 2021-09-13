package com.vessels;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class VesselsController {

	@GetMapping("/generateReport")
	public ResponseEntity<TestData> generateReport() throws JsonParseException, JsonMappingException, IOException {

		TestData testData = new ObjectMapper().readValue(new File("./TestData.json"), TestData.class);
		
		testData.getVessels().forEach(vessel -> {
			double totalDistanceTraveled = 0; 
			long totalTimeElapsed = 0;
			int index = 0;
			List<Position> positions = vessel.getPositions();
			for (Position position : positions) {
					int x1,y1,x2,y2;
					if (index == 0){
						x1 = 0;
						y1 = 0;
					} else {
						x1 = positions.get(index-1).x;
						y1 = positions.get(index-1).y;
					}
				x2 = positions.get(index).x;
				y2 = positions.get(index).y;
				double distanceTraveled = calDistance(x1,y1,x2,y2);
				if (index > 0) {
					long timeElapsed = position.getTimestamp().getTime() - positions.get(index-1).getTimestamp().getTime(); // hours
					totalDistanceTraveled = totalDistanceTraveled + distanceTraveled;
					totalTimeElapsed = totalTimeElapsed+ timeElapsed;	
				}
				index= index+1;
			}
			vessel.setAvgSpeed(totalDistanceTraveled/totalTimeElapsed);
			vessel.setTotalDistanceTravelled(totalDistanceTraveled);
		});
		
		return new ResponseEntity<>(testData, HttpStatus.OK);
	}
	
	private double calDistance(int x1, int y1, int x2, int y2) {
	    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) * 1.0);
	}

	static class TestData {
		private List<Vessel> vessels;

		public List<Vessel> getVessels() {
			return vessels;
		}

		public void setVessels(List<Vessel> vessels) {
			this.vessels = vessels;
		}
	}

	static class Vessel {
		private String name;
		private List<Position> positions;
		private double avgSpeed;
		private double totalDistanceTravelled;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Position> getPositions() {
			return positions;
		}

		public void setPositions(List<Position> positions) {
			this.positions = positions;
		}

		public double getAvgSpeed() {
			return avgSpeed;
		}

		public void setAvgSpeed(double avgSpeed) {
			this.avgSpeed = avgSpeed;
		}

		public double getTotalDistanceTravelled() {
			return totalDistanceTravelled;
		}

		public void setTotalDistanceTravelled(double totalDistanceTravelled) {
			this.totalDistanceTravelled = totalDistanceTravelled;
		}

	}

	static class Position {
		private int x;
		private int y;
		private Date timestamp;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}

	}
}
