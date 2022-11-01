package it.polito.tdp.yelp.model;

public class Event implements Comparable <Event>{

	public enum EventType{
		INTERVISTA,
		FERIE;
	}
	
	private int giorno;
	private Giornalista giornalista;
	private User intervistato;
	private EventType type;
	
	public Event(int giorno, Giornalista giornalista, User intervistato, EventType type) {
		super();
		this.giorno = giorno;
		this.giornalista = giornalista;
		this.intervistato = intervistato;
		this.type = type;
	}

	public int getGiorno() {
		return giorno;
	}

	public Giornalista getGiornalista() {
		return giornalista;
	}

	public User getIntervistato() {
		return intervistato;
	}
	
	public EventType getEventType() {
		return this.type;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public void setGiornalista(Giornalista giornalista) {
		this.giornalista = giornalista;
	}

	public void setIntervistato(User intervistato) {
		this.intervistato = intervistato;
	}

	@Override
	public int compareTo(Event o) {
		return this.getGiorno()-o.getGiorno();
	}
}
