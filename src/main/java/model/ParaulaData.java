package model;

import java.util.HashMap;

import javafx.scene.Node;

public class ParaulaData {

	//Point coordsInici;
	//Point coordsFinal;
	
	//ArrayList<Node> nodes = new ArrayList<Node>();
	HashMap<Node, Character> lletresNodes; // Guarda les lletres i els seus nodes corresponents
	boolean resolt;
	
	public ParaulaData() {
		lletresNodes = new HashMap<Node, Character>();
		this.resolt = false;
	}

	public boolean isResolt() {
		return resolt;
	}

	public void setResolt(boolean resolt) {
		this.resolt = resolt;
	}

	public void resoldre() {
		this.resolt = true;
	}
	
	public void afegirLletra(Node node, Character ch) {
		this.lletresNodes.put(node, ch);
	}
	
	public HashMap<Node, Character> getLletres() {
		return this.lletresNodes;
	}
	
}
