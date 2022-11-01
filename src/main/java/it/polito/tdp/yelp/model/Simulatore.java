package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Event.EventType;

public class Simulatore {

	
	//dati in ingresso
	private int X1;
	private int X2;
	
	//dati in uscita
	private List<Giornalista> giornalisti;
	private int numeroGiorni;
	
	//Modello del mondo
	private Set<User> intervistati;
	private Graph<User,DefaultWeightedEdge> grafo;
	
	
	//Coda degli eventi
	private PriorityQueue <Event> queue;
	
	public Simulatore(Graph<User,DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	
	public void init(int X1, int X2) {

		this.queue = new PriorityQueue<Event>();
		
		this.X1 = X1;
		this.X2 = X2;
		
		this.intervistati = new HashSet<User>();
		
		this.numeroGiorni = 0;
		
		this.giornalisti = new ArrayList<Giornalista>();
		
		for(int i = 0; i < this.X1; i++) {
			this.giornalisti.add(new Giornalista(i));
		}
		
		
		//pre-carico la coda
		for(Giornalista g : this.giornalisti) {
			User intervistato = selezionaIntervistato(this.grafo.vertexSet());
			
			this.intervistati.add(intervistato);
			g.addIntervistato();
			this.queue.add(new Event(1, g, intervistato, EventType.INTERVISTA));
		}
		
		
	}
	
	public void run() {
		
		while(!this.queue.isEmpty() && this.intervistati.size() < X2) {
			Event e = this.queue.poll();
			this.numeroGiorni = e.getGiorno();
			
			processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		switch(e.getEventType()) {
			case INTERVISTA:
				
				double caso = Math.random();
				
				if(caso < 0.6) {
					
					User vicino = selezionaAdiacente(e.getIntervistato());
					if(vicino == null) {
						vicino = selezionaIntervistato(this.grafo.vertexSet());
					}
					
					this.queue.add(new Event(e.getGiorno()+1, e.getGiornalista(), vicino, EventType.INTERVISTA));
					this.intervistati.add(vicino);
					e.getGiornalista().addIntervistato();
					
				}else if(caso < 0.8){
					
					this.queue.add(new Event(e.getGiorno()+1, e.getGiornalista(), e.getIntervistato(), EventType.FERIE));
					
				}else {
					this.queue.add(new Event(e.getGiorno()+1, e.getGiornalista(), e.getIntervistato(), EventType.INTERVISTA));
				}
				
				break;
			case FERIE:
				
				this.queue.add(new Event(e.getGiorno()+1, e.getGiornalista(), e.getIntervistato(), EventType.INTERVISTA));
				
				break;
			
		}
			
		
	}

	public int getX1() {
		return X1;
	}

	public int getX2() {
		return X2;
	}

	public List<Giornalista> getGiornalisti() {
		return this.giornalisti;
	}

	public int getNumeroGiorni() {
		return this.numeroGiorni;
	}

	public void setX1(int x1) {
		this.X1 = x1;
	}

	public void setX2(int x2) {
		this.X2 = x2;
	}
	
	private User selezionaIntervistato(Set<User> lista) {
		List<User> candidati = new ArrayList<User>(lista);
		candidati.removeAll(this.intervistati);
		
		int scelto = (int)(Math.random()*candidati.size());
		
		return candidati.get(scelto);
		
	}
	
	private User selezionaAdiacente(User u) {
		List<User> vicini = Graphs.neighborListOf(this.grafo, u);
		vicini.removeAll(this.intervistati);
		
		if(vicini.size() == 0) {
			return null;
		}
		
		double max = 0;
		for(User v: vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(u, v));
			if(peso > max) {
				max = peso;
			}
		}
		
		List<User> migliori = new ArrayList<>();
		for(User v: vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(u, v));
			if(peso == max) {
				migliori.add(v);
			}
		}
		
		int scelto = (int)(Math.random()*migliori.size());
		return migliori.get(scelto);
		
	}
	
}
