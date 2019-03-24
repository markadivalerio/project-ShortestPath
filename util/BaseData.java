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

    private int TOTAL_AIRPORTS;
    private int TOTAL_UNIQUE_FLIGHTS;

    private BaseData() {
        airport_file = "project-ShortestPath/data/airports.dat";
        flights_file = "project-ShortestPath/data/flights.dat";
        //airport_file = "src/data/airports.dat";
        //airport_file = "src/data/flights.dat";
        airports = new HashMap<String, Vertex>();
        flights = new HashSet<Flight>();
        flightEdges = new HashMap<String, Edge>();
        TOTAL_AIRPORTS = 0;
        TOTAL_UNIQUE_FLIGHTS = 0;
    }
    public static BaseData getInstance(){
        if (single_instance == null)
            single_instance = new BaseData();
        return single_instance;
    }

    public boolean initialize(){
        boolean res = (this.loadAirports() && this.loadFlights());
//        if(res)
//        	res &= 
        return res;
    }
    
    public int getAirportsCount() {
        return TOTAL_AIRPORTS;
    }

    public int getFlightsCounts() {
        return TOTAL_UNIQUE_FLIGHTS;
    }

    public Map<String, Vertex> getAirports() {
        return airports;
    }

    public Set<Flight> getFlights() {
        return flights;
    }
    
    public Map<String, Edge> getFlightEdges()
    {
        return flightEdges;
    }
    
    private boolean loadAirports(){
        boolean bRetVal = true;
        try{
            File file = new File(airport_file);
            Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter("\n");
            int count =0;
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
                        count++;

                    }catch(NumberFormatException e){
                        e.printStackTrace();
                        continue;
                    }
                }

            }
            this.TOTAL_AIRPORTS = count;
            //System.out.println("total airports = " + count);
            inputStream.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            bRetVal = false;
        }catch (Exception e){
            e.printStackTrace();
            bRetVal = false;
        }

        return bRetVal;
    }

    private boolean loadFlights(){
        boolean bRetVal = true;
        try{
            File file = new File(flights_file);
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
            TOTAL_UNIQUE_FLIGHTS = uniqueCount;
            System.out.println("Total flights = " + total + "  Unique flights = " + uniqueCount);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            bRetVal = false;
        }catch(Exception e){
            e.printStackTrace();
            bRetVal = false;
        }

        return bRetVal;
    }
    

}
