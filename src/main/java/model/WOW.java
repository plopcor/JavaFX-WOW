package model;

public class WOW {

	private static WOW wow;
	Partida partida;
	
	int indexLocalitzacio;
	
	public WOW() {
		partida = new Partida();
	}
	
	// METODES
	
	public static WOW getInstance() {
		if (wow == null)
			wow = new WOW();
		return wow;
	}
	
//	public void comencarPartida() {
//		partida.getLocalitzacioActual();		
//	}
	
	public Partida getPartida() {
		return this.partida;
	}
	
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	
	// METODES

	
//	public Localitzacio getLocalitzacio(int index) {
//		if(index >= localitzacions.size()) {
//			//System.err.println("Error al agafar una localitzacio per index, l'index especificat no existeix");
//			return null;
//		}
//		return localitzacions.get(index);
//	}
	
	public void carregaManual() {
		
		Localitzacio loc = new Localitzacio();
		
		// Generar nivell
		Nivell nivell = new Nivell();

		// Afegir lletres
		nivell.lletres.add('A');
		nivell.lletres.add('R');
		nivell.lletres.add('A');
		nivell.lletres.add('L');
		nivell.lletres.add('M');
		nivell.lletres.add('A');
		nivell.lletres.add('P');
		
		// Afegir paraules
		nivell.paraules.add("LAMPARA");
		nivell.paraules.add("ALARMA");
		nivell.paraules.add("PALMA");
		nivell.paraules.add("RAMPA");
		nivell.paraules.add("RAMA");
		nivell.paraules.add("AMAR");
		nivell.paraules.add("PARA");
		nivell.paraules.add("MALA");
		nivell.paraules.add("MAPA");
		nivell.paraules.add("ARMA");
		nivell.paraules.add("PALA");
		
		// Afegir paraules extra
		nivell.paraulesExtra.add("MAR");
		nivell.paraulesExtra.add("MAL");
		nivell.paraulesExtra.add("ARPA");
		nivell.paraulesExtra.add("LAMA");
		nivell.paraulesExtra.add("PAR");
		nivell.paraulesExtra.add("ALMA");
		nivell.paraulesExtra.add("AMA");
		nivell.paraulesExtra.add("ALA");
		nivell.paraulesExtra.add("PALMAR");
		nivell.paraulesExtra.add("ARA");
		nivell.paraulesExtra.add("LAR");
		nivell.paraulesExtra.add("RAMAL");
		nivell.paraulesExtra.add("ALAR");
		nivell.paraulesExtra.add("LAPA");
		nivell.paraulesExtra.add("PAL");
		nivell.paraulesExtra.add("PARLA");
		nivell.paraulesExtra.add("LAMPA");
		nivell.paraulesExtra.add("MARA");
		nivell.paraulesExtra.add("APA");
		nivell.paraulesExtra.add("PARAL");
		nivell.paraulesExtra.add("RAPA");
		nivell.paraulesExtra.add("ALAMAR");
		nivell.paraulesExtra.add("AMPLA");
		nivell.paraulesExtra.add("ALAMA");
		nivell.paraulesExtra.add("AMALA");
		nivell.paraulesExtra.add("MALAR");
		nivell.paraulesExtra.add("LAMPAR");
		
		// Afegir nivell a la localitzacio
		loc.setNom("Cablejat");
		loc.afegirNivell(nivell);
		
		// Afegir localitzacio a la partida
		this.partida.afegirLocalitzacio(loc);
		
	}
		
}
