package impl;

import objs.Graph;
import objs.Route;


public class Main {
	public static void main(String[] args)
	{
		// Run the program with four arguments as below
		// 1 5 ALL /raj/UT-Masters/Spring2019/Algorithms/Practice/src/com/ut/practice/datastructure/USA-road-d.NY-small.gr
//		if(args.length < 4)
//		{
//			System.out.println("Please pass source, destination, runmode and datafile \n");
//			System.out.println("java -jar project-ShortestPath.jar 1 5 Dijkstra /raj/UT-Masters/Spring2019/Algorithms/Project/Data/USA-road-d.NY.gr");
//			return;
//		}
		System.out.println("--------- START ------------");

//		Graph g = new Graph("NY-small");
		Graph g = new Graph("NY"); // load data into graph object - load airports/flights data. re-used.
		System.out.println("Source node = " + args[0]);
		System.out.println("Destination node = " + args[1]);
		System.out.println("Run mode = " + args[2]);
		System.out.println("Datafile = " + args[3]);
		String start_test = args[0];
		String end_test = args[1];
		String runMode = args[2];
		g.setDatafile(args[3]);
//		String start_test = "173";
//		String end_test = "463";
//		String runMode = "ALL";
//		g.setDatafile("");
		g.loadFiles();

		Route shortestRoute;
		if (runMode.equalsIgnoreCase("ALL") || runMode.equalsIgnoreCase("Dijkstra")) {


			//System.out.println(g.toString());
			System.out.println("\n--- Dijkstra using Binary Heap ---");
			Dijkstra dj = new Dijkstra(start_test, end_test, g);
			dj.startTimer();
			shortestRoute = dj.findShortestPath();
			dj.stopTimer();
			System.out.println(dj.asString(shortestRoute, true));

			System.out.println("\n--- Dijkstra using Fibonacci Heap ---");
			dj.startTimer();
			shortestRoute = dj.findShortestPathUsingFibonacciHeap(start_test, end_test);
			dj.stopTimer();
			System.out.println(dj.asString(shortestRoute, true));
		}

		if (runMode.equalsIgnoreCase("ALL") || runMode.equalsIgnoreCase("Bellman")) {
			System.out.println("\n--- Bellman-Ford using Fibonacci Heap ---");
			BellmanFord bf = new BellmanFord(start_test, end_test, g);
			bf.startTimer();
			shortestRoute = bf.findShortestPath();
			bf.stopTimer();
			System.out.println(bf.asString(shortestRoute));
		}

		if (runMode.equalsIgnoreCase("ALL") || runMode.equalsIgnoreCase("AStar")) {

			AStar as = new AStar(start_test, end_test, g);
			as.startTimer();
			shortestRoute = as.findShortestPath();
			as.stopTimer();
			System.out.println(as.asString(shortestRoute));
		}

		System.out.println("--------- END ------------");
	}
}
