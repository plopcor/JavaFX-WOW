package model;

import java.util.ArrayList;

public class Partida {

	ArrayList<Localitzacio> localitzacions;
	int indexLocalitzacioActual;
	int puntuacio;

	// CONSTRUCTOR
	public Partida() {
		localitzacions = new ArrayList<Localitzacio>();
		this.indexLocalitzacioActual = 0;
		this.puntuacio = 0;
	}

	// GETTERS & SETTERS

	public ArrayList<Localitzacio> getLocalitzacions() {
		return this.localitzacions;
	}

	public Localitzacio getLocalitzacioActual() {
		return localitzacions.get(indexLocalitzacioActual);
	}
	
	public void setLocalitzacions(ArrayList<Localitzacio> localitzacions) {
		this.localitzacions = localitzacions;
	}

	public void afegirLocalitzacio(Localitzacio localitzacio) {
		this.localitzacions.add(localitzacio);
	}
	
	public int getIndexLocalitzacioActual() {
		return indexLocalitzacioActual;
	}
	
	public int getPuntuacio() {
		return puntuacio;
	}

	public void setPuntuacio(int puntuacio) {
		this.puntuacio = puntuacio;
	}
	
	public void incrementaPuntuacio(int puntuacio) {
		if(puntuacio > 0)
			this.puntuacio += puntuacio;
	}

	public boolean seguentLocalitzacio() {
		
		if(this.localitzacions.size() > this.indexLocalitzacioActual) {
			this.indexLocalitzacioActual++;
			return true;
		} else {
			System.err.println("No n'hi ha mes localitzacions disponibles");
			System.out.println("FI DEL JOC");
			return false;
		}
	}

	public void avancar() {
		
		// Avança al seguent nivell, si no te més nivells ves a la seguent localitzacio
		if(!this.getLocalitzacioActual().seguentNivell()) {
			this.seguentLocalitzacio();
		}		
	}
	
	// METODES
	
//	// Iniciar nova partida
//	public void comencar() {
//		this.puntuacio = 0;
//		this.indexLocalitzacioActual = 0;
//
//		carregarNivell(0);
//	}

}
