package impl;

import objs.Graph;
import objs.Route;

public class Main {
	public static void main(String[] args)
	{
//		Dijkstra dj = new Dijkstra();
//		Route shortestRoute = dj.findShortestPath("SFO", "CDG");
//		System.out.println(dj.asString(shortestRoute));
//		
//		BellmanFord bf = new BellmanFord();
//		shortestRoute = bf.findShortestPath("SFO", "CDG");
//		System.out.println(bf.asString(shortestRoute));
		
		Graph g = new Graph(true);
		String start_test = "SFO";
		String end_test = "CDG";
//		start_test = "HOU";
//		end_test = "CDG";
		Route shortestRoute;
		
		Dijkstra dj = new Dijkstra(start_test, end_test, g);
		shortestRoute = dj.findShortestPath();
		System.out.println(dj.asString(shortestRoute));
		
		Dijkstra dj2 = new Dijkstra(start_test, end_test, null);
		shortestRoute = dj2.findShortestPath2(start_test, end_test);
		System.out.println(dj2.asString(shortestRoute));
		
		BellmanFord bf = new BellmanFord(start_test, end_test, g);
		shortestRoute = bf.findShortestPath();
		System.out.println(bf.asString(shortestRoute));
		
		BellmanFord bf2 = new BellmanFord(start_test, end_test, null);
		shortestRoute = bf2.findShortestPath2(start_test, end_test);
		System.out.println(bf2.asString(shortestRoute));
	}
}
