package application;

public class Edge {
	College adjacent;
	double distance;

	public Edge(College adjacentCollege, double distance) {
		this.adjacent = adjacentCollege;
		this.distance = distance;
	}

	public College getAdjacentCollege() {
		return adjacent;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "Edge [adjacent=" + adjacent + ", distance=" + distance + "]";
	}

}
