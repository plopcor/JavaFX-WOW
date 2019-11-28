package model;

import java.awt.Point;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridPaneWrapper {

	private GridPane grid;
	
	private int x;
	private int y;
	
	HashMap<Point, Node> coordsNodes = new HashMap<Point, Node>();
	
	HashMap<Point, Node> coordsNodesLletres;
	
	//
	// CONSTRUCTORS
	//
	public GridPaneWrapper() {
		
	}
	
	public GridPaneWrapper(GridPane grid, int x, int y) {
		this.x = x;
		this.y = y;
		this.grid = grid;
	}
	
	//
	// GETTERS & SETTERS
	//
	
	public GridPane getGridPane() {
		return this.grid;
	}
	public void setGridPane(GridPane grid) {
		this.grid = grid;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Point getDimensions() {
		return new Point(x, y);
	}
	
	public void setDimensions(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	//
	// METODES
	//
	
	public void add(Node node, int posX, int posY) {
		this.grid.add(node, posX, posY);
		coordsNodes.put(new Point(posX,posY), node); 
	}

	public Node get(int posX, int posY) {
		return coordsNodes.get(new Point(posX, posY));
	}
	
	public void netejar() {
		this.grid.getChildren().clear();
	}
	
}
