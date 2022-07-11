package it.polito.tdp.imdb.model;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		INTERVISTA, 
		PAUSA	
	}
	
	private int time; // misurato in giorni
	private EventType type;
	private Actor intervistato;
	
	public Event(int time, EventType type, Actor intervistato) {
		super();
		this.time = time;
		this.type = type;
		this.intervistato = intervistato;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Actor getIntervistato() {
		return intervistato;
	}

	public void setIntervistato(Actor intervistato) {
		this.intervistato = intervistato;
	}
	
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time-o.time;
	}
	
	
	
	
	
	

}
