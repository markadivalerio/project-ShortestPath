package objs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.BaseData;

// Adjacency list representation of undirected graph
public class Graph
{

	private Map<String, Vertex> vertices; // all (even unconnected)
	private Map<String, Edge> edges;
	private Map<Vertex, List<Vertex>> adjVertices;
	private Map<String, ArrayList<Vertex>> connectedVertices;
	private Map<String, ArrayList<Edge>> vertexEdges;

	public Graph()
	{
		this(true);
	}

	public Graph(boolean loadBaseData)
	{
		adjVertices = new HashMap<Vertex, List<Vertex>>();
		connectedVertices = new HashMap<String, ArrayList<Vertex>>();
		vertexEdges = new HashMap<String, ArrayList<Edge>>();
		if(loadBaseData)
			buildFromBaseData(true);
	}

	public Set<Vertex> getVertices()
	{
		return adjVertices.keySet();
	}
	
	public Set<String> getRealVertices()
	{
		return vertices.keySet();
	}
	
	public Vertex getVertex(String code)
	{
		return vertices.get(code);
	}

	public int size()
	{
		return adjVertices.size();
	}

	public void connectVertices(Vertex u, Vertex v, boolean undirected)
	{
		addEdge(u, v);
		if (undirected)
			addEdge(v, u);
	}

	public void addConnectedNodes(Vertex u, Vertex v, boolean undirected)
	{
		addVertex(u);
		addVertex(v);
		connectVertices(u, v, undirected);
	}

	public void addVertex(Vertex v)
	{
		if (!adjVertices.containsKey(v))
		{
			adjVertices.putIfAbsent(v, new ArrayList<Vertex>());
		}
	}

	public void addEdge(Vertex v, Vertex w)
	{
		if(!adjVertices.get(v).contains(w))
		{
			adjVertices.get(v).add(w);
		}
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
	
	public Edge getRealEdge(String u, String v)
	{
		String eIndex = Edge.indexOf(u, v);
		return edges.get(eIndex);
	}
	
	public Edge getRealEdge(Vertex u, Vertex v)
	{
		String eIndex = Edge.indexOf(u, v);
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
	
	public Edge getRealEdge(String edgeCode)
	{
		return edges.get(edgeCode);
	}
	public Set<String> getEdges()
	{
		return edges.keySet();
	}

	public List<Vertex> getEdges(Vertex v)
	{
		if (!this.adjVertices.containsKey(v))
		{
			return new ArrayList<Vertex>();
		}
		return adjVertices.get(v);
	}

	public void buildFromBaseData(Boolean undirectedEdges)
	{
		BaseData bdata = BaseData.getInstance();
		bdata.initialize();

		vertices = bdata.getAirports();
		Map<String, Edge> tempEdges = bdata.getFlightEdges();
		edges = new HashMap<String, Edge>();
		for(String edgeIndex : tempEdges.keySet())
		{
			Edge e = tempEdges.get(edgeIndex);
			addEdge(e, undirectedEdges);
		}

	}

	@Override
	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("Graph {\n");
		sb.append("size = " + this.size() + " \n");
		for (Vertex key : adjVertices.keySet())
		{
			sb.append(key.getCode());
			sb.append(" -> ");
			sb.append("[");
			for (Vertex edge : adjVertices.get(key))
			{
				sb.append(edge.getCode() + ",");
			}
			if (sb.lastIndexOf(",") != -1)
			{
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			sb.append("]\n");
		}
		sb.append("}");

		return sb.toString();
	}
}
