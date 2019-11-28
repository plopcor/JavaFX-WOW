package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import controlador.ControladorPrincipal;
import javafx.scene.layout.GridPane;

public class Generador {

	
	
	public static void generarTauler(GridPane grid, ArrayList<String> paraules) {
		
		if(paraules.size() == 0) {
			System.err.println("No es pot generar el tauler, no n'hi ha paraules");
			return;
		}
		
		// Ordenar paraules per longitud
		//Collections.sort(paraules, new Comparadors.Longitud());
		
		Collections.sort(paraules, new Comparator<String>() {
			public int compare(String str1, String str2) {
				return str1.length() - str2.length();
			}
		});
		
		
		// Posar paraula mes llarga al centre
		Point gridSize = ControladorPrincipal.gridParaulesMax;
		int gridMid = gridSize.x / 2;
		int paraulaMid = paraules.get(0).length() / 2;

		
			
		
	}
	
}
