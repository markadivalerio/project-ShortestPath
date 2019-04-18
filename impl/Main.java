package impl;

import objs.Graph;
import objs.Route;

public class Main {
	public static void main(String[] args)
	{
		Graph g = new Graph(true); // load data into graph object - load airports/flights data. re-used.
//		String start_test = "SFO";
//		String end_test = "CDG";
		String start_test = "2";
//		String end_test = "48";
		String end_test = "188238";
//		start_test = "HOU"; // not an international airport - guaranteed at least 3 airports.
		Route shortestRoute;
		
		Dijkstra dj = new Dijkstra(start_test, end_test, g);
		shortestRoute = dj.findShortestPath();
		System.out.println(dj.asString(shortestRoute));
		
		
		BellmanFord bf = new BellmanFord(start_test, end_test, g);
		shortestRoute = bf.findShortestPath();
		System.out.println(bf.asString(shortestRoute));
		
		AStar as = new AStar(start_test, end_test, g);
		shortestRoute = as.findShortestPath();
		System.out.println(as.asString(shortestRoute));
	}
}
