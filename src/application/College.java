package application;

import java.util.LinkedList;

import javafx.scene.control.Button;

public class College implements Comparable<College> {
	float x;
	float y;
	String name;
	LinkedList<Edge> edges = new LinkedList<>();
	private double max = Double.MAX_VALUE;
	private College tempPrev;
	College prevousCollege;
	Button test = new Button();

	public College(String name, float x, float y) { // College name
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public College() {

	}

	public void addAdjacentCollege(College college, double distance) {
		edges.add(new Edge(college, distance));
	}

	public void resetTemps() {
		max = Double.MAX_VALUE;
		tempPrev = null;
	}

	public Button getTest() {
		return test;
	}

	public void setTest(Button test) {
		this.test = test;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public LinkedList<Edge> getAdjacentsList() {
		return edges;
	}

	public void setTempCost(double tempCost) {
		this.max = tempCost;
	}

	public double getTempCost() {
		return max;
	}

	public void setTempPrev(College tempPrev) {
		this.tempPrev = tempPrev;
	}

	public String getFullName() {
		return name;
	}

	public College getTempPrev() {
		return tempPrev;
	}

	@Override
	public String toString() {
		return "College [x=" + x + ", y=" + y + ", name=" + name + "]";
	}

	@Override
	public int compareTo(College o) {
		if (this.max > o.max)
			return 1;
		else if (this.max < o.max)
			return -1;
		return 0;
	}

}
