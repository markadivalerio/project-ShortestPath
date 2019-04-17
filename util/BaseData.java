package util;

import objs.Edge;
import objs.Flight;
import objs.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class BaseData {

    private static BaseData single_instance = null;
    private String airport_file;
    private String flights_file;
    private Map<String, Vertex> airports;
    private Set<Flight> flights;
    private Map<String, Edge> flightEdges;


    private BaseData() {
        airport_file = "project-ShortestPath/data/airports.dat";
        flights_file = "project-ShortestPath/data/flights.dat";
        //airport_file = "src/data/airports.dat";
        //airport_file = "src/data/flights.dat";
        airports = new HashMap<String, Vertex>();
        flights = new HashSet<Flight>();
        flightEdges = new HashMap<String, Edge>();
        
//        try
//        {
//            File file = new File(airport_file);
//            loadVertices(file);
//            file = new File(flights_file);
//            loadEdges(file);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
    }
    
    public static BaseData getInstance(){
        if (single_instance == null)
            single_instance = new BaseData();
        return single_instance;
    }

    public Map<String, Vertex> getVertices() {
        return airports;
    }

    public Set<Flight> getEdges() {
        return flights;
    }
    
    public Map<String, Edge> getFlightEdges()
    {
        return flightEdges;
    }
    
//    private String[] getVertexSyntax()
//    {
//    	String[] syntax = new i[]{1};
//    	return syntax;
//    }
//    	
//    private String[] getEdgeSyntax()
//    {
//		String[] syntax = new String{};
//		return syntax;
//    }

    
    private boolean loadAirports(File file)
    {
        boolean bRetVal = true;
        try
        {
            Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter("\n");
            while(inputStream.hasNext()) {
                //read single line, put in string
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
                        this.airports.putIfAbsent(airport_code, airport);
                        //System.out.println("code="+airport_code);

                    }catch(NumberFormatException e){
                        e.printStackTrace();
                        continue;
                    }
                }

            }
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
            bRetVal = false;
        }

        return bRetVal;
    }

    private boolean loadEdges(File file){
    	if(file == null)
    	{
    		return true;
    	}
    	
        boolean bRetVal = true;
        try
        {
            Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter("\n");
            int uniqueCount = 0;
            int total=0;
            while(inputStream.hasNext()) {
                //read single line, put in string
                String line = inputStream.next();
                List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
                 try {
                     int nstop = Integer.parseInt(items.get(7));
                     if(nstop == 0) {
//                         Flight flight = new Flight(airports.get(items.get(2)), airports.get(items.get(4)), nstop);
                    	 Vertex origin = this.airports.get(items.get(2));
                    	 Vertex destination = this.airports.get(items.get(4));
                    	 if(origin != null && destination != null)
                    	 {
                    		 Edge edge = new Edge(origin, destination, nstop);
                    		 flightEdges.put(edge.getIndex(), edge);
                    	 }
                    	 Flight flight = new Flight(items.get(2), items.get(4), nstop);
                         if (flights.add(flight)) {
                             uniqueCount++;
                         }
                     }
                     total++;

                 }catch(NumberFormatException e){
                     e.printStackTrace();
                    continue;
                 }
            }
            inputStream.close();
        } catch(Exception e){
            e.printStackTrace();
            bRetVal = false;
        }

        return bRetVal;
    }
    
    
    
}
