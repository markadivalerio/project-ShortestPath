package impl;

import objs.Graph;
import objs.Route;


public class Main {
	public static void main(String[] args)
	{

		Graph g = new Graph("NY-small"); // load data into graph object - load airports/flights data. re-used.
//		String start_test = "SFO";
//		String end_test = "CDG";
		//String start_test = "2";
		//String end_test = "48";

		String start_test = "197";
		String end_test = "405";

		Route shortestRoute;
		//System.out.println(g.toString());
		
		/*
		System.out.println("--- Dijkstra using Binary Heap --- \n");
		Dijkstra dj = new Dijkstra(start_test, end_test, g);
		dj.startTimer();
		shortestRoute = dj.findShortestPath();
		dj.stopTimer();
		System.out.println(dj.asString(shortestRoute));

		System.out.println("--- Dijkstra using Fibonacci Heap --- \n");
		dj.startTimer();
		shortestRoute = dj.findShortestPathUsingFibonacciHeap(start_test, end_test);
		dj.stopTimer();
		System.out.println(dj.asString(shortestRoute));
		*/


		System.out.println("--- Bellman Ford --- \n");
		BellmanFord bf = new BellmanFord(start_test, end_test, g);
		bf.startTimer();
		shortestRoute = bf.findShortestPath();
		bf.stopTimer();
		System.out.println(bf.asString(shortestRoute));
/*
		AStar as = new AStar(start_test, end_test, g);
		as.startTimer();
		shortestRoute = as.findShortestPath();
		as.stopTimer();
		System.out.println(as.asString(shortestRoute));
*/

	}
}
