package model;

import java.util.ArrayList;

public class Localitzacio {

	String nom;
	String nomFitxerImatge;
	ArrayList<Nivell> nivells;
	int indexNivellActual;
	
	
	// CONSTRUCTOR
	public Localitzacio() {
		this.nom = "Desconegut";
		this.nomFitxerImatge = "default";
		this.nivells = new ArrayList<Nivell>();
		this.indexNivellActual = 0;
	}
	
	public Localitzacio(String nom, ArrayList<Nivell> nivells) {
		this.nom = nom;
		this.nomFitxerImatge = "default";
		this.nivells = nivells;
		this.indexNivellActual = 0;
	}
	
	// GETTERS & SETTERS
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNomFitxerImatge() {
		return this.nomFitxerImatge;
	}
	
	public void setNomFitxerImatge(String nomFitxer) {
		this.nomFitxerImatge = nomFitxer;
	}
	
	public ArrayList<Nivell> getNivells() {
		return this.nivells;
	}

	public void setNivells(ArrayList<Nivell> nivells) {
		this.nivells = nivells;
	}
	
	public void afegirNivell(Nivell nivell) {
		this.nivells.add(nivell);
	}
	
	public int getIndexNivellActual() {
		return this.indexNivellActual;
	}
	
	public Nivell getNivellActual() {
		return this.nivells.get(indexNivellActual);
	}
	
	public boolean seguentNivell() {
		if(this.nivells.size() > this.indexNivellActual + 1) {
			this.indexNivellActual++;
			return true;
		} else {
			System.out.println("Localització completada");
			return false;
		}
	}
	
}
