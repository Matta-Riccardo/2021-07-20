package it.polito.tdp.yelp.model;

public class Giornalista {

	private int idGiornalista;
	private int nIntervistati;
	
	public Giornalista(int idGiornalista) {
		super();
		this.idGiornalista = idGiornalista;
		this.nIntervistati = 0;
	}

	public int getIdGiornalista() {
		return idGiornalista;
	}

	public int getnIntervistati() {
		return nIntervistati;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGiornalista;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giornalista other = (Giornalista) obj;
		if (idGiornalista != other.idGiornalista)
			return false;
		return true;
	}
	
	public void addIntervistato() {
		this.idGiornalista++;
	}

	@Override
	public String toString() {
		return "Giornalista " + idGiornalista + ", interviste eseguite= " + nIntervistati;
	}
	
	
}
