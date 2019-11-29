package model;

import java.io.File;

public class Fitxers {


	public String llegirFitxer() {
		return "";
	}
	
	
	//JSON
	public Object llegirJSON(String fitxer) {
		
		File f = new File(fitxer);
		
		if(!f.exists()) {
			System.err.println();
			return null;
		}
		
		
		return null;
	}
	
}
