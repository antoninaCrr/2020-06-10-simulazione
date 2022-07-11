package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.imdb.db.ImdbDAO;


public class Model {
	
	private ImdbDAO dao;
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private Map<Integer,Actor> actMap;
	private Simulator sim;
	
	public Model() {
		super();
		this.dao = new ImdbDAO();
	}
	
	
	public void creaGrafo(String genre) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.actMap = new HashMap<>();
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, dao.getAllVertices(genre,actMap));
		
		// aggiungo gli archi
		for(Arco ai : dao.getAllEdges(genre, actMap)) {
			Graphs.addEdgeWithVertices(this.grafo, ai.getA1(), ai.getA2(), ai.getPeso());
		}
			
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getAllGenres(){
		return dao.listAllGenres();	
	}
	
	public List<Actor> getAllActors(){
		List<Actor> vertices = new ArrayList<Actor>(grafo.vertexSet());
		Collections.sort(vertices);
		return vertices;
	}
	
	public List<Actor> getSimili(Actor a){
		List<Actor> attoriSimili;
		Set<Actor> attori;
		
		ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector(this.grafo);
		attori = ci.connectedSetOf(a);
		attoriSimili = new ArrayList<>(attori);
		Collections.sort(attoriSimili);
		
		return attoriSimili;
	}
	
	public void simula(int giorni) {
		sim = new Simulator(this.grafo,this.getAllActors());
		sim.init(giorni);
		sim.run();
	}
	
	public List<Actor> getAttoriIntervistati(){
		if(sim == null){
			return null;
		}
		return sim.getIntervistati();
	}
	
	public Integer getPause(){
		if(sim == null){
			return null;
		}
		return sim.getPause();
	}
	

}
