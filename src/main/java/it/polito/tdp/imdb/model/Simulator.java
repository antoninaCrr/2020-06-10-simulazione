package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.imdb.model.Event.EventType;

public class Simulator {
	
	// dati in input
	private int N; // numero giorni da simulare
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private List<Actor> attori;
	
	// dati in output
	private List<Actor> intervistati;
	private int pause;
	
	// modello del mondo
	private List<Actor> daIntervistare;
	private int tempoSimulazione;
	
	// coda degli eventi
	private PriorityQueue<Event> queue;
	Random rand = new Random();

	// costruttore
	public Simulator(Graph<Actor, DefaultWeightedEdge> grafo, List<Actor> attori) {
		super();
		this.grafo = grafo;
		this.attori = attori;
	}
	
	public void init(int giorni) {
		this.N = giorni;
		
		this.intervistati = new ArrayList<>();
		this.pause = 0;
		
		this.daIntervistare = new ArrayList<>(this.attori);
		this.tempoSimulazione = 0;
		
		this.queue = new PriorityQueue<>();
		
		// primo evento
		Actor a = null;
		a = daIntervistare.get(rand.nextInt(daIntervistare.size()));
		queue.add(new Event(1, EventType.INTERVISTA,a));
		this.daIntervistare.remove(a);
		this.intervistati.add(a);
	}
	
	public void run() {
		
		while(!queue.isEmpty() && tempoSimulazione < N) { // SE LA CODA NON E' VUOTA ALLORA ESTRAI IL PROX EVENTO E GESTISCILO
			Event e = this.queue.poll(); // estrai un evento
			processEvent(e);
		}
		
	}
	
	private void processEvent(Event e) {
		int time = e.getTime();
		tempoSimulazione = time;
		EventType type = e.getType();
		Actor a = e.getIntervistato();
		Actor estratto = null;
		
		switch(type) {
		case INTERVISTA:
			if(this.intervistati.get(intervistati.size()-2).getGender().equals(this.intervistati.get(intervistati.size()-1).getGender()) && Math.random() <= 0.90) {
				queue.add(new Event(time+1, EventType.PAUSA,null));
			}else {
				if(Math.random() <= 0.60) {
					estratto  = daIntervistare.get(rand.nextInt(daIntervistare.size()));
					queue.add(new Event(time+1, EventType.INTERVISTA,estratto));
					this.daIntervistare.remove(estratto);
					this.intervistati.add(estratto);
				}else {
					Actor consigliato = consigliami(this.intervistati.get(intervistati.size()-1));
					if(consigliato != null) {
					queue.add(new Event(time+1, EventType.INTERVISTA,consigliato));
					this.daIntervistare.remove(consigliato);
					this.intervistati.add(consigliato);
				}else {
					estratto  = daIntervistare.get(rand.nextInt(daIntervistare.size()));
					queue.add(new Event(time+1, EventType.INTERVISTA,estratto));
					this.daIntervistare.remove(estratto);
					this.intervistati.add(estratto);
				}
			 }
			}
			break;
		case PAUSA:
			pause++;
			estratto = daIntervistare.get(rand.nextInt(daIntervistare.size()));
			queue.add(new Event(time+1, EventType.INTERVISTA,estratto));
			this.daIntervistare.remove(estratto);
			break;
		}
		
	}

	private Actor consigliami(Actor actor) {
		// TODO Auto-generated method stub
		Actor vicino = null;
		int peso = 0;
		
		for(Actor ai : Graphs.neighborListOf(this.grafo, actor)) {
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(actor, vicino)) > peso) {
				peso = (int)this.grafo.getEdgeWeight(this.grafo.getEdge(actor, vicino));
				vicino = ai;
			}
		}
		
		return vicino;
	}

	public List<Actor> getIntervistati() {
		return intervistati;
	}

	public int getPause() {
		return pause;
	}

	
	
	

	

}
