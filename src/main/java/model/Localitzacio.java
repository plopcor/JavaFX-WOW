package model;

import java.io.File;
import java.util.ArrayList;

public class Localitzacio {

	String nom;
	File fitxerImatge;
	ArrayList<Nivell> nivells;
	int indexNivellActual = 0;
	
	
	// CONSTRUCTOR
	public Localitzacio() {
		this.nom = "Desconegut";
		this.fitxerImatge = null;
		this.nivells = new ArrayList<Nivell>();
		this.indexNivellActual = 0;
	}
	
	public Localitzacio(String nom, ArrayList<Nivell> nivells) {
		this();
		this.nom = nom;
		this.nivells = nivells;
		this.indexNivellActual = 0;
	}
	
	public Localitzacio(String nom, String nomFitxerImatge, ArrayList<Nivell> nivells) {
		this(nom, nivells);
		this.fitxerImatge = new File("src/imatges/" + nomFitxerImatge);
	}
	
	// GETTERS & SETTERS
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public boolean hasFitxerImatge() {
		return this.fitxerImatge != null && this.fitxerImatge.exists();
	}
	
	public File getFitxerImatge() {
		return this.fitxerImatge;
	}
	
	public void setFitxerImatge(File fitxerImatge) {
		this.fitxerImatge = fitxerImatge;
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
