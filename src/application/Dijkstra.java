package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
	static ArrayList<College> Colleges = new ArrayList<College>();
	static HashMap<String, College> allNodes = new HashMap<>();
	private College source;
	private College destination;
	private PriorityQueue<College> heap;

	public Dijkstra() {

	}

	public Dijkstra(ArrayList<College> Colleges, College source, College destination) {
		heap = new PriorityQueue<>(Colleges.size());
		this.destination = destination;
		this.Colleges = Colleges;
		for (College college : Colleges) {
			college.resetTemps();
			if (college == source) {
				college.setTempCost(0);
			}
			heap.add(college);
		}
	}

	public void generateDijkstra() {
		while (!heap.isEmpty()) {
			College minUnknown = heap.poll();
			LinkedList<Edge> adjacentsList = minUnknown.getAdjacentsList();
			for (Edge edge : adjacentsList) {
				College adjacentCollege = edge.getAdjacentCollege();
				if ((minUnknown.getTempCost() + edge.getDistance()) < adjacentCollege.getTempCost()) {
					heap.remove(adjacentCollege);
					adjacentCollege.setTempCost(minUnknown.getTempCost() + edge.getDistance());
					adjacentCollege.setTempPrev(minUnknown);
					heap.add(adjacentCollege);
				}

			}
		}
	}

	private String pathString;
	String distanceString;

	public College[] pathTo(College destination) {
		LinkedList<College> colleges = new LinkedList<>();
		College iterator = destination;
		distanceString = String.format("%.2f", destination.getTempCost());
		while (iterator != source) {
			colleges.addFirst(iterator);
			pathString = iterator.getFullName() + " : " + String.format("%.2f", iterator.getTempCost()) + " M" + "\n"
					+ "  ->  " + pathString;
			iterator = iterator.getTempPrev();
		}

		return colleges.toArray(new College[0]);
	}

	public String getPathString() {
		if (countOccurrences(pathString, '\n') <= 1) {
			pathString = "No Path";
			distanceString = "\t\t\t------------------";
			return pathString;
		}

		pathString = "\t" + pathString;

		int truncateIndex = pathString.lastIndexOf('\n');
		pathString = pathString.substring(0, truncateIndex);

		return pathString;
	}

	private static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	public static ArrayList<College> readFile() throws FileNotFoundException {
		String line = null;
		int numberOfCollegees, numberOfEdges;
		File file = new File("data.txt");
		Scanner scan = new Scanner(file);
		line = scan.nextLine();
		String[] str = line.split(" ");
		numberOfCollegees = Integer.parseInt(str[0]);
		numberOfEdges = Integer.parseInt(str[1]);

		for (int i = 0; i < numberOfCollegees; i++) {
			float x, y;
			line = scan.nextLine();
			String[] strN = line.split(" ");
			x = (float) Double.parseDouble(strN[1]);
			y = (float) Double.parseDouble(strN[2]);
			College newCollege = new College(strN[0], x, y);
			Colleges.add(newCollege);
			allNodes.putIfAbsent(strN[0], newCollege);// hash map

		}
		for (int i = 0; i < numberOfEdges; i++) {
			line = scan.nextLine();
			String[] strN = line.split(" ");
			String fromCollegeName = strN[0], toCollegeName = strN[1];
			College fromCollege = allNodes.get(fromCollegeName), toCollege = allNodes.get(toCollegeName);
			double distance = Double.parseDouble(strN[2]);
			fromCollege.addAdjacentCollege(toCollege, distance);
			toCollege.addAdjacentCollege(fromCollege, distance);
		}

		return Colleges;

	}

}
