package objs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.BaseData;

// Adjacency list representation of undirected graph
public class Graph
{

	private Map<String, Vertex> vertices; // all (even unconnected)
	private Map<String, Edge> edges;
	private Map<String, ArrayList<Vertex>> connectedVertices;
	private Map<String, ArrayList<Edge>> vertexEdges;
	private final String DATA_FILE_PATH = "project-ShortestPath/data/";
	private final String FLIGHTS_FILENAME = "flights.dat";
	private final String AIRPORTS_FILENAME = "airports.dat";
	private String abbreviation;

	public void setDatafile(String datafile) {
		this.datafile = datafile;
	}

	private String datafile;



	public Graph()
	{
		this("NY");
	}

	public Graph(String area)
	{
		vertices = new HashMap<String, Vertex>();
		edges = new HashMap<String, Edge>();
//		adjVertices = new HashMap<Vertex, List<Vertex>>();
		connectedVertices = new HashMap<String, ArrayList<Vertex>>();
		vertexEdges = new HashMap<String, ArrayList<Edge>>();
		if(area != null && area.length() > 0)
		{
			if(area == "AIRPORTS_FLIGHTS")
			{
				// TODO
			}
			else
			{
//			buildFromBaseData(true);
			//loadFile("project-ShortestPath/data/USA-road-d.NY.gr");
				abbreviation = area;
			}
		}
	}
	
	public Set<String> getRealVertices()
	{
		return vertices.keySet();
	}
	
	public Vertex getVertex(String code)
	{
		return vertices.get(code);
	}

	public void addEdge(Edge e, boolean undirected)
	{
		if (!edges.containsKey(e.getIndex()))
			edges.put(e.getIndex(), e);

		Vertex origin = e.getOrigin();
		Vertex destination = e.getDestination();
		vertices.put(origin.getCode(), origin);
		vertices.put(destination.getCode(), destination);

		if(!connectedVertices.containsKey(origin.getCode()))
			connectedVertices.putIfAbsent(origin.getCode(), new ArrayList<Vertex>());
		if(!connectedVertices.get(origin.getCode()).contains(destination))
			connectedVertices.get(origin.getCode()).add(destination);
		
		if(!vertexEdges.containsKey(origin.getCode()))
			vertexEdges.put(origin.getCode(), new ArrayList<Edge>());
		if(!vertexEdges.get(origin.getCode()).contains(e))
			vertexEdges.get(origin.getCode()).add(e);
		
		if(undirected)
		{
			Vertex dest = destination;
			Vertex src = origin;
			Edge reversed = new Edge(dest, src, e.getWeight());
			if (!edges.containsKey(reversed.getIndex()))
				edges.put(reversed.getIndex(), reversed);
			
			if(!connectedVertices.containsKey(destination.getCode()))
				connectedVertices.put(destination.getCode(), new ArrayList<Vertex>());
			if(!connectedVertices.get(destination.getCode()).contains(origin))
				connectedVertices.get(destination.getCode()).add(origin);

			if(!vertexEdges.containsKey(destination.getCode()))
				vertexEdges.put(destination.getCode(), new ArrayList<Edge>());
			if(!vertexEdges.get(destination.getCode()).contains(reversed))
				vertexEdges.get(destination.getCode()).add(reversed);
		}
	}
	
	public Edge getEdgeFromECode(String edgeCode)
	{
		return edges.get(edgeCode);
	}
	
	public Edge getRealEdge(String u, String v)
	{
		String eIndex = Edge.generateEIndex(u, v);
		return edges.get(eIndex);
	}
	
	public Edge findEdge(Vertex u, Vertex v)
	{
		String eIndex = Edge.generateEIndex(u, v);
		return edges.get(eIndex);
	}
	
	public ArrayList<Vertex> getConnectedVertices(Vertex u)
	{
		return connectedVertices.getOrDefault(u.getCode(), new ArrayList<Vertex>());
	}
	
	public ArrayList<Edge> getConnectedEdges(String u)
	{
		return vertexEdges.getOrDefault(u, new ArrayList<Edge>());
	}

	public ArrayList<Edge> getOutboundConnectedEdges(Vertex u)
	{
		String uCode = u.getCode();
		ArrayList<Edge> connectedEdges = getConnectedEdges(uCode);
		ArrayList<Edge> outbound = new ArrayList<Edge>();
		for(Edge e: connectedEdges)
		{
			if(e.getOrigin().getCode().equals(uCode))
			{
				outbound.add(e);
			}
		}
		return outbound;
	}
	
	
	public Set<String> getEdges()
	{
		return edges.keySet();
	}

	public void buildFromBaseData(Boolean undirectedEdges)
	{
		BaseData bdata = BaseData.getInstance();

		vertices = bdata.getVertices();
		Map<String, Edge> tempEdges = bdata.getFlightEdges();
		edges = new HashMap<String, Edge>();
		for(String edgeIndex : tempEdges.keySet())
		{
			Edge e = tempEdges.get(edgeIndex);
			addEdge(e, undirectedEdges);
		}

	}
	
	//public void loadFiles(String abbreviation)
	public void loadFiles()
    {
    	// uncomment this
		String distFile;

		if(datafile == null || "".equals(datafile)){
			distFile = DATA_FILE_PATH + "USA-road-d." + this.abbreviation + ".gr";
		}
		else if(datafile.toLowerCase().contains("airport") || datafile.toLowerCase().contains("flight"))
		{
			loadAirportsAndFlights();
			return;
		}
		else {
			distFile = datafile;
		}
//		@SuppressWarnings("unused")
//		String timeFile = DATA_FILE_PATH + "t." + abbreviation + ".gr";
//
//		@SuppressWarnings("unused")
//		String coordFile = DATA_FILE_PATH + "d." + abbreviation + ".co";

		// comment this when needed
		//String distFile = "/raj/UT-Masters/Spring2019/Algorithms/Project/Data/USA-road-d.E.gr";
//		String distFile = "/raj/UT-Masters/Spring2019/Algorithms/Project/project-ShortestPath-refactor-OLD/data/USA-road-d.NY.gr";
		//String distFile = "/raj/UT-Masters/Spring2019/Algorithms/Project/project-ShortestPath-refactor-OLD/data/USA-road-d.NY.gr";
		//String distFile = "/raj/UT-Masters/Spring2019/Algorithms/Practice/src/com/ut/practice/datastructure/USA-road-d.NY-small.gr";

    	try
    	{
    		File file = new File(distFile);
    		Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter("\n");
            while(inputStream.hasNext())
            {
            	String[] line = inputStream.next().split(" ");
//            	System.out.println(Arrays.toString(line));
            	if(!line[0].equals("a"))
            		continue;
            	Vertex src = new Vertex(line[1]);
            	Vertex dest = new Vertex(line[2]);
            	Edge e = new Edge(src, dest, Integer.parseInt(line[3]));
            	addEdge(e, false);
            }
            
            inputStream.close();
			System.out.println("Vertices = " + vertices.size() + ".   Edges = "+edges.size());
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	
	public void loadAirportsAndFlights()
	{
		HashMap<String, Vertex> airports = new HashMap<String, Vertex>();
		try
    	{
			System.out.println("Loading airports");
    		File file = new File(DATA_FILE_PATH+AIRPORTS_FILENAME);
    		Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter("\n");
            while(inputStream.hasNext())
            {
				String line = inputStream.next();
		        List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
		        //System.out.println(items.toString());
		        String airport_code = items.get(4);
		        if (!airport_code.contains("\\N")){
		            try {
		                airport_code = airport_code.replace("\"", "");
		                Vertex airport = new Vertex(airport_code, // code
		                        items.get(1), // name
		                        items.get(3), // Country
		                        Double.parseDouble(items.get(6)), // latitude
		                        Double.parseDouble(items.get(7))); // longitude
		                airports.putIfAbsent(airport_code, airport);
		            }
		            catch(NumberFormatException e){
	                    e.printStackTrace();
	                   continue;
	                }
		        }
            }
            inputStream.close();
            
            System.out.println("Loading flights");
            
            file = new File(DATA_FILE_PATH+FLIGHTS_FILENAME);
            inputStream = new Scanner(file);
            inputStream.useDelimiter("\n");
            while(inputStream.hasNext())
            {
                //read single line, put in string
                String line = inputStream.next();
                String[] items = line.split("\\s*,\\s*");
                try {
                     int nstop = Integer.parseInt(items[7]);
                     if(nstop == 0) {
//                             Flight flight = new Flight(airports.get(items.get(2)), airports.get(items.get(4)), nstop);
                    	 Vertex origin = airports.get(items[2]);
                    	 Vertex destination = airports.get(items[4]);
                    	 if(origin != null && destination != null)
                    	 {
                    		 Edge edge = new Edge(origin, destination, (int)Edge.calculateDistance(origin, destination));
                    		 this.addEdge(edge, true);
                    	 }

                     }
                 }
                catch(NumberFormatException e){
                    e.printStackTrace();
                   continue;
                }
            }
            inputStream.close();
            
    	}
		catch(Exception e){
            e.printStackTrace();
        }
		
		
	}

//	@Override
//	public String toString()
//	{
//		StringBuilder sb = new StringBuilder();
//		sb.append("Graph {\n");
//		sb.append("size = " + vertices.size() + " \n");
//		for (String key : edges.keySet())
//		{
//			sb.append(key);
//			sb.append(" -> ");
//			sb.append("[");
//			for (Edge edge : edges.get(key))
//			{
//				sb.append(edge.getCode() + ",");
//			}
//			if (sb.lastIndexOf(",") != -1)
//			{
//				sb.deleteCharAt(sb.lastIndexOf(","));
//			}
//			sb.append("]\n");
//		}
//		sb.append("}");
//
//		return sb.toString();
//
//		private Map<String, Vertex> vertices; // all (even unconnected)
//		private Map<String, Edge> edges;
//	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("Graph{Size: ");
		sb.append(vertices.size());
		sb.append("\nVertices \n");

		for (String v : vertices.keySet()) {
			sb.append(vertices.get(v).toString() + "\n");
		}

		sb.append("\nEdges \n");

		for (String e : edges.keySet()) {
			sb.append(edges.get(e).toString() + "\n");
		}
		return sb.toString();
	}

}
