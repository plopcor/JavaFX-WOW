package model;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Dades {

	public static String carpeta = "dades/";

	// JSON
	public static class JSON {

		private static String fitxer = "Data.json";
		private static JSONParser parser = new JSONParser();

		// Agafar localitzacions
		public static ArrayList<Localitzacio> getLocalitzacions() {

			ArrayList<Localitzacio> localitzacions = new ArrayList<Localitzacio>();
			Localitzacio loc;
			JSONObject jsonObj;
			
			try {

				// Llegir fitxer
				JSONObject jsonFitxer = (JSONObject) parser.parse(new FileReader(Dades.carpeta + JSON.fitxer));

				// Agafar array de "localitzacions"
				JSONArray jsonArrayLocs = (JSONArray) jsonFitxer.get("localizaciones");

				// Per cada localitzacio, busca els nivells
				for (Object jsonLoc : jsonArrayLocs) {
					
					// Convertir a json object
					jsonObj = (JSONObject) jsonLoc;
					
					// Crear localitzacio amb les dades
					String nom = (String) jsonObj.get("nombre");
					String imatge = (String) jsonObj.get("imagen");
					ArrayList<Nivell> nivells = getNivells( (JSONArray) jsonObj.get("niveles"));
					
					// Si n'hi ha dades (nivells)
					if(nivells != null) {
					
						// Crear localitzacio
						loc = new Localitzacio(nom, imatge, nivells);

						// Afegir a la llista
						localitzacions.add(loc);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return localitzacions;
		}

		
		// Agafar nivells
		private static ArrayList<Nivell> getNivells(JSONArray jsonNivells) {
			
			if(jsonNivells.size() == 0)
				return null;
			
			ArrayList<Nivell> nivells = new ArrayList<Nivell>();
			JSONObject jsonObj;
			Nivell nivell;
			
			// Per cada nivell a l'array
			int nivellNum = 1;
			for(Object jsonNivell : jsonNivells) {
				
				// Pasar a JSON
				jsonObj = (JSONObject) jsonNivell;
				
				// Agafar dades del nivell
				ArrayList<Character> lletres = new ArrayList<Character>();
				ArrayList<String> paraules = new ArrayList<String>();
				ArrayList<String> paraulesExtra = new ArrayList<String>();

				// Lletres
				for(char c : ((String)jsonObj.get("letras")).toCharArray()) {
					lletres.add(c);
				}
				
				// Paraules normals
				for(Object jsonPar : (JSONArray)jsonObj.get("palabras")) {
					paraules.add((String) jsonPar);
				}
				
				// Paraules extra
				for(Object jsonParExt : (JSONArray)jsonObj.get("extras")) {
					paraulesExtra.add((String) jsonParExt);
				}
				
				// Crear nivell nomes si n'hi ha dades suficients (les extra son opcionals)
				if(lletres.size() > 0 && paraules.size() > 0) {

					// Crear nivell amb les dades
					nivell = new Nivell(nivellNum, lletres, paraules, paraulesExtra);
					
					// Afegir a la llista
					nivells.add(nivell);
					nivellNum++;
				}
			}

			return nivells;
		}
		
	} // Classe JSON

}
