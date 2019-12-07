package model;

import java.util.ArrayList;

public class Nivell {
	
	int numNivell;
	boolean completat;
	ArrayList<Character> lletres;
	ArrayList<String> paraules;
	ArrayList<String> paraulesExtra;

	public Nivell() {
		numNivell = 1;
		completat = false;
		lletres = new ArrayList<Character>();
		paraules = new ArrayList<String>();
		paraulesExtra = new ArrayList<String>();
	}
	
	public Nivell(int numNivell, ArrayList<Character> lletres, ArrayList<String> paraules, ArrayList<String> paraulesExtra) {
		this();
		this.numNivell = numNivell;
		this.lletres = lletres;
		this.paraules = paraules;
		this.paraulesExtra = paraulesExtra;
	}

	// SETTERS & GETTERS
	public ArrayList<Character> getLletres() {
		return lletres;
	}

	public void setLletres(ArrayList<Character> lletres) {
		this.lletres = lletres;
	}

	public ArrayList<String> getParaules() {
		return paraules;
	}

	public void setParaules(ArrayList<String> paraules) {
		this.paraules = paraules;
	}

	public ArrayList<String> getParaulesExtra() {
		return paraulesExtra;
	}

	public void setParaulesExtra(ArrayList<String> paraulesExtra) {
		this.paraulesExtra = paraulesExtra;
	}

	public int getNumNivell() {
		return numNivell;
	}
	
	public void setNumNivell(int numNivell) {
		this.numNivell = numNivell;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += "Nivell: " + numNivell + "\n";
		str += "Lletres: " + lletres + "\n";
		
		return str;
	}
}
