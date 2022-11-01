package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private Graph<User,DefaultWeightedEdge> grafo;
	private Map<String, User> userIdMap;
	private YelpDao dao;
	private List<Giornalista> result;
	private int nGiorni;
	
	public Model() {
		dao = new YelpDao();
		userIdMap = dao.getAllUsers();
	}
	
	public void creaGrafo(int n, int anno) {
		
		List <User> vertici = dao.getUsersByRecensioni(n, this.userIdMap);
		this.grafo = new SimpleWeightedGraph<User,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(User u1 : vertici) {
			for(User u2 : vertici) {
				if(!u2.equals(u1)) {
					int peso = dao.getPesoArco(u1, u2, anno);
					if(peso>0) {
						Graphs.addEdgeWithVertices(this.grafo, u1, u2, peso);
					}
				}
			}
		}
	}
	
	double pesoMax;
	
	public List<User> getUtenteSimile(User u) {
		List <User> utentiSimili = new ArrayList<User>();
		pesoMax = 0;
		
		for(DefaultWeightedEdge e : this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e) > pesoMax) {
				pesoMax = this.grafo.getEdgeWeight(e);
			}
		}
		
		for(DefaultWeightedEdge e : this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e) == pesoMax) {
				
				if(this.grafo.getEdgeTarget(e).equals(u)) {
					utentiSimili.add(this.grafo.getEdgeSource(e));
				}else {
					utentiSimili.add(this.grafo.getEdgeTarget(e));
				}
			} 
		}
		
		return utentiSimili;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<User> getVertici(){
		
		List<User> vertexList = new ArrayList <User>();
		for(User u : this.grafo.vertexSet()) {
			vertexList.add(u);
		}
		Collections.sort(vertexList);
		
		return vertexList;
	}
	
	public double getPeso() {
		return this.pesoMax;
	}
	
	
	public void Simula(int X1, int X2) {
		Simulatore sim = new Simulatore(this.grafo);
		sim.init(X1, X2);
		sim.run();
		
		result = sim.getGiornalisti();
		nGiorni = sim.getNumeroGiorni();
		
	}
	
	public List<Giornalista> getGiornalisti(){
		return this.result;
	}
	
	public int getNGiorni() {
		return this.nGiorni;
	}
}
