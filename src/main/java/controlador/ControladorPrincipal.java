package controlador;

import model.Direccio;
import model.Localitzacio;
import model.Nivell;
import model.ParaulaData;
import model.WOW;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ControladorPrincipal {

	// Instancia del joc
	WOW wow = WOW.getInstance();

	// Mapa per generar el tauler0
	HashMap<Point, Character> taulellLletres = new HashMap<Point, Character>();
	
	// PANELLS
	@FXML
	private Pane panellInici;
	@FXML
	private Pane panellMain;
	@FXML
	private Pane panellNivellCompletat;
	@FXML
	private Pane panellFi;

	
	// PANELL => INICI
	@FXML
	private Button btnIniciar;
	@FXML
	private TextArea txtNormes;
	
	// PANELL => PRINCIPAL (JOC)
	
	// - Tauler
	@FXML
	private GridPane gridParaules;
	public static final Point gridParaulesMax = new Point(15, 12); // Tamany del grid/tauler
	
	// - Lletres
	@FXML
	private GridPane gridLletres;
	@FXML
	private TextField txtParaula;
	@FXML
	private Button btnValidar;
	
	// => SUBPANELL => INFORMACIÓ
	@FXML
	private Pane panellInfo;
	@FXML
	private Text lblPunts;

	// PANELL => NIVELL COMPLETAT
	@FXML
	private Text lblNivellCompletat;
	@FXML
	private Button btnSeguentNivell;
	
	
	// PANELL => FI DEL JOC
	@FXML
	private Text lblFiPuntuacio;
	@FXML
	private Button btnFiSortir;
	
	// Llista dels botons de les lletres
	HashMap<Button, Character> btnsLletres = new HashMap<Button, Character>();
	
	// Llista dels botons de les lletres pressionats (i deshabilitats, per tornar a habilitar)
	ArrayList<Button> btnsLletresPressionats = new ArrayList<Button>();

	// Llista amb la posicio de les paraules
	HashMap<String, ParaulaData> paraules = new HashMap<String, ParaulaData>();
	
	// Llista amb les paraules extra
	Map<String, Boolean> paraulesExtra = new HashMap<String, Boolean>();

	// Temporal (s'ha de millorar), utilitzat per saber cuan no queden més paraules al nivell
	int numParaulesNormals = 0;
	
	@FXML
	public void initialize() {

//		// Carrega dades hardcodejades
//		wow.carregaManual();

		// Carregar dades desde JSON
		wow.carregaAutomatica();
		
		// Mostrar pantalla d'inici amb les regles
		panellInici.setVisible(true);
		panellMain.setVisible(false);
		panellFi.setVisible(false);
		panellNivellCompletat.setVisible(false);

	}

	// INICIAR JOC
	@FXML
	public void clickBtnIniciar(ActionEvent event) {
		
		// Carregara el nivell actual (primera localitzacio, primer nivell)
		carregarNivellActual();
		
		// Mostrar pantalla de joc
		panellInici.setVisible(false);
		panellMain.setVisible(true);

	}

	// JOC

	public void btnValidarClick(ActionEvent event) {

		// Comprovar si la paraula esta en la llista
		String paraula = txtParaula.getText().toUpperCase();

		txtParaula.setText("");

		// Comprovar si es una paraula normal o extra
		if (paraules.containsKey(paraula) && !paraules.get(paraula).isResolt()) {

			System.out.println("Paraula normal => " + paraula);
			resoldreParaula(paraules.get(paraula));

		} else if (paraulesExtra.containsKey(paraula) && !paraulesExtra.get(paraula)) {
			resoldreParaulaExtra(paraula);
		}

		// Habilitar els botons de les lletres
		for (Button btn : btnsLletresPressionats)
			btn.setDisable(false);
		
		// Si no queden lletres per resoldre
		if(numParaulesNormals == 0) {
			
			// Avançar al seguent nivell/localitzacio
			if(wow.getPartida().avancar()) {
	
				// Mostrar panell de nivell completat
				lblNivellCompletat.setText("Has completat el nivell " + wow.getPartida().getLocalitzacioActual().getIndexNivellActual() + " i tens un total de " + wow.getPartida().getPuntuacio() + " punts");
				panellNivellCompletat.setVisible(true);
				panellMain.setVisible(false);
				
				System.out.println("\n[NIVELL COMPLETAT]");
				System.out.println("Pasant al seguent nivell");
				
				// Carrega el seguent nivell
				carregarNivellActual();
			
			} else {
				
				// Mostrar FI DEL JOC i puntuacio
				System.out.println("Joc completat");
				panellFi.setVisible(true);
				lblFiPuntuacio.setText("Puntuacio: " + wow.getPartida().getPuntuacio());
	
				panellMain.setVisible(false);
			}
		}
		
	}

	
	//
	// EVENTS
	//

	// MAIN => Click btnLletra
	EventHandler<ActionEvent> eventLletraClick = new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event){
			Button btn = (Button)event.getSource();
			txtParaula.setText(txtParaula.getText() + btn.getText());
			btn.setDisable(true);
			btnsLletresPressionats.add(btn);
		}
	};	

	// txtParaula - KeyPressed
	@FXML
	public void txtParaulaKeyPressed(KeyEvent event) {

		// Si es pulsa una tecla (permet agafar les tecles especials com BACKSPACE i ENTER)
		
		// Segons la tecla pressionada
		switch(event.getCode()) {
			case BACK_SPACE: //Esborrar
				
				// Treure l'ultim caracter i habilitar el seu boto
				if(txtParaula.getText().length() > 0) {
					txtParaula.setText(txtParaula.getText().substring(0, txtParaula.getText().length() - 1));
					btnsLletresPressionats.get(btnsLletresPressionats.size() - 1).setDisable(false);
					btnsLletresPressionats.remove(btnsLletresPressionats.size() - 1);
				}
				break;
			
			case ENTER: //Validar
				btnValidar.fire();
				break;	
		}
	}
	
	// txtParaula - KeyPressed
	@FXML
	public void txtParaulaKeyTyped(KeyEvent event) {
		
		// Si s'escriu un caracter (permet agafar el caracter que es vol escriure)
		Character ch = event.getCharacter().toUpperCase().charAt(0);
		
		// Buscar si n'hi ha un boto amb aquest caracter
		if(btnsLletres.containsValue(ch))
			
			// Per cada boto
			for(Map.Entry<Button, Character> par : btnsLletres.entrySet())

				// Te el caracter i el boto esta habilitat??
				if(par.getValue().equals(ch) && !par.getKey().isDisable()) {
					
					// Deshabilitar, afegir a la llista de botons pressionats i posar caracter al input text
					par.getKey().setDisable(true);
					btnsLletresPressionats.add(par.getKey());
					txtParaula.setText(txtParaula.getText() + ch);
					break;
				}
	}
	
	@FXML
	public void btnPistaClick(ActionEvent event) {
		System.out.println("PISTA");
	}
	
	// NIVELL COMPLETAT
	// => Boto Seguent Nivell
	@FXML
	public void btnSeguentNivellClick(ActionEvent event) {
		panellNivellCompletat.setVisible(false);
		panellMain.setVisible(true);
	}
	
	// FI DEL JOC
	// => Boto Sortir
	@FXML
	public void btnFiSortirClick(ActionEvent event) {
		System.exit(0);
	}
		
		
	//
	// METODES
	//

	public void resoldreParaula(ParaulaData paraulaData) {

		// Donar punts
		wow.getPartida().incrementarPuntuacio(10);
		
		numParaulesNormals--;
		
		// Actualitzar punts
		actualitzaPuntuacio();
		
		// Per cada lletra de paraula, mostrala al tauler (Posar lletra al TextView associat)
		for (Map.Entry<Node, Character> lletra : paraulaData.getLletres().entrySet()) {
			TextField tf = (TextField) lletra.getKey();
			tf.setText(lletra.getValue().toString());
			tf.setStyle("-fx-control-inner-background: lightgreen");
			//System.out.println(" Mostrar lletra => " + lletra.getValue().toString());
		}

		// Marca com a resolta
		paraulaData.setResolt(true);
	}

	public void resoldreParaulaExtra(String paraula) {
		
		// Donar punts
		wow.getPartida().incrementarPuntuacio(5);
		
		// Actualitzar punts
		actualitzaPuntuacio();
		
		System.out.println("Paraula extra => " + paraula);
		
		// Marcar com a resolta (sobre-escriura el registre del mapa amb el nou valor True ja que utilitza la mateixa clau)
		paraulesExtra.put(paraula, true);
	}

	public void actualitzaPuntuacio() {
		lblPunts.setText("Punts: " + wow.getPartida().getPuntuacio());
	}
	
	public void carregarNivellActual() {
		
		Localitzacio loc = wow.getPartida().getLocalitzacioActual();
		if(loc == null) {
			System.err.println("La localitzacio esta buida");
			return;
		}
		
		Nivell nivell = loc.getNivellActual();
		if (nivell == null) {
			System.err.println("El nivell esta buit");
			return;
		}
		
		if(nivell.getLletres().size() == 0 || nivell.getParaules().size() == 0) {
			System.err.println("No es pot jugar aquest nivell, no te lletres i/o paraules suficients");
			return;
		}

		// Canviar titol de la finestra
		Main.finestra.setTitle("WOW - " + loc.getNom() + " - Nivell: " + nivell.getNumNivell()); //Main.finestra.getTitle()

		// Generar joc
		posarLletres(nivell.getLletres());
		posarParaules(nivell.getParaules());
		posarParaulesExtra(nivell.getParaulesExtra());

	}

	// Posar els botons amb les lletres disponibles
	public void posarLletres(ArrayList<Character> lletres) {

		// Netejar lletres
		gridLletres.getChildren().clear();
		btnsLletres.clear();
		btnsLletresPressionats.clear();
		
		System.out.println("\nColocant lletres disponibles:");

		// Carregar lletres
		Button btn;
		boolean flag = true;
		int i = 0;

		// Remenar lletres
		Collections.shuffle(lletres);
		
		System.out.println(lletres);
		
		// Colocar lletres
		for (Character ch : lletres) {

			btn = new Button();
			btn.setText(ch.toString());
			btn.setMaxWidth(Double.MAX_VALUE);
			btn.setMaxHeight(Double.MAX_VALUE);
			btn.setOnAction(eventLletraClick);
			
			gridLletres.add(btn, (flag ? 0 : 1), i);

			if (!flag)
				i++;
			flag = !flag;

			// System.out.println("Boto: Lletra => " + ch + " Posicio => " + i + ", " +
			// (flag ? 0 : 1));
			
			// Afegir al mapa de botons de les lletres
			btnsLletres.put(btn, ch);
			
		}

	}

	// Posar TextFields per les paraules
	private void posarParaules(ArrayList<String> llistaParaules) {

		// Netejar tauler
		gridParaules.getChildren().clear();
		
		// Treure paraules del HashMap
		this.paraules.clear();
		// Treure coordenades de les lletres
		this.taulellLletres.clear();

		// CREAR TAULER
		
		// CABLEJAT
//		posarParaula("LAMPARA", 0, 0, Direccio.HORITZONTAL);
//		posarParaula("ALARMA", 1, 0, Direccio.VERTICAL);
//		posarParaula("PALMA", 0, 5, Direccio.HORITZONTAL);

		// NO CABLEJAT
		generarTauler(llistaParaules);

	}

	// Quan es genera el tauler (colocar cada lletra de la paraula), mirar si la
	// posicio ja te un node assignat
	HashMap<Point, Node> coordsNodesLletres = new HashMap<Point, Node>();

	private LinkedHashMap<Point, Character> posarParaula(String paraula, int x, int y, Direccio dir) {

		//System.out.println(" Colocar paraula => " + paraula);

		// Per retornar les posicions dels caracters colocats
		// - Utilitza LinkedHashMap per preservar l'ordre d'inserció (necessari per
		// despres trobar les colisions de les lletres a la funcio generarTauler())
		LinkedHashMap<Point, Character> colocat = new LinkedHashMap<Point, Character>();

		// Agafa tots els caracters/lletres de la paraula
		char[] chars = paraula.toCharArray();
		int indexChar = 0;

		TextField tf;
		ParaulaData pd = new ParaulaData();

		int i = x;
		int j = y;

		do {

			// DEBUG
			//System.out.println(" [" + chars[indexChar] + "] => i=" + i + " j=" + j);

			// Agafa la posicio/coordenada
			Point p = new Point(i, j);

			// Si existeix un node amb aquesta posicio, recuperal (ja que si creem un altre,
			// es posara a sobre y visualment tapara el existent)
			if (coordsNodesLletres.containsKey(p)) {

				// Es posa com a node de la lletra actual el node ja existent
				pd.afegirLletra(coordsNodesLletres.get(p), chars[indexChar]);

			} else {

				// Es genera el node per aquesta lletra

				// Generar textfield
				tf = new TextField();
				tf.setMaxWidth(Double.MAX_VALUE);
				tf.setMaxHeight(Double.MAX_VALUE);
				// tf.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
				tf.setId("tf_" + i + "_" + j);

				tf.setEditable(false);

				// Guardar la lletra amb el seu node corresponent
				pd.afegirLletra(tf, chars[indexChar]);

				// Afegir node al panell
				gridParaules.add(tf, i, j);

				// Guardar posicio/coordenada a la llista amb el node que conte
				coordsNodesLletres.put(p, tf);
			}

			colocat.put(p, chars[indexChar]);
			
			// Guardar al tauler per generar les altres paraules correctament
			taulellLletres.put(p, chars[indexChar]);
			
			// Ves a la dreta o cap avall segons la direccio
			if (dir == Direccio.HORITZONTAL)
				i++;
			else if (dir == Direccio.VERTICAL)
				j++;
			
			indexChar++;

			// Mentres no acabi la paraula i no es surti del grid
		} while (indexChar < chars.length && i < gridParaulesMax.x && j < gridParaulesMax.y);

		// Afegir paraula i la seva informacio al mapa
		paraules.put(paraula, pd);

		// Incrementa el numero de paraules colocades
		numParaulesNormals++;
		
		return colocat;

	}

	private void posarParaulesExtra(ArrayList<String> llistaParaulesExtra) {

		paraulesExtra.clear();

		// Guardar paraules a la llista (marcar com no trobades)
		for (String paraula : llistaParaulesExtra)
			paraulesExtra.put(paraula, false);
	}

	// Netejar tauler (lletres i paraules)
	public void netejarTauler() {
		gridLletres.getChildren().clear();
		gridParaules.getChildren().clear();
	}

	private void generarTauler(ArrayList<String> llistaParaules) {

		if (llistaParaules.size() == 0) {
			System.err.println("No es pot generar el tauler, no n'hi ha paraules");
			return;
		}

		System.out.println("\nColocant paraules al tauler");
		
		// Ordenar paraules per longitud
		Collections.sort(llistaParaules, new Comparator<String>() {
			public int compare(String str1, String str2) {
				return str2.length() - str1.length();
			}
		});

		// Posar paraula mes llarga al centre (en vertical)
		String paraula = llistaParaules.get(0);
		Point gridSize = ControladorPrincipal.gridParaulesMax;
		int gridMidX = gridSize.x / 2 - 1; // -1 perque son coordenades i comencen a 0
		int gridMidY = gridSize.y / 2 - 1;
		int paraulaMid = (int) (paraula.length() / 2d);

		// Treure coordenades de les lletres ja  existents
		taulellLletres.clear();
		
		// Posar paraula centrada al grid (en vertical)
		
		// Posar lletres i agafar les seves posicions
		LinkedHashMap<Point, Character> posicionsChars;
		posicionsChars = posarParaula(paraula, gridMidX, gridMidY - paraulaMid, Direccio.VERTICAL);

		// Treure paraula de la llista de paraules a colocar
		llistaParaules.remove(paraula); // remove(0);

		// Enviar la paraula colocada
		posicionarParaula(llistaParaules, posicionsChars, Direccio.VERTICAL);

	}

	public void posicionarParaula(ArrayList<String> llistaParaules, LinkedHashMap<Point, Character> paraulaAnterior, Direccio dir) {
		
		if(llistaParaules.size() == 0)
			return;
		
		// Veure la posicio que tindra la nova paraula
		Direccio dirActual = (dir == Direccio.HORITZONTAL ? Direccio.VERTICAL : Direccio.HORITZONTAL);

		boolean potColocar = false;
		
		// Per cada lletra de la paraula anterior, intenta posar una paraula nova utilitzant alguna de les seves lletres
		// - Agafa una paraula
		for (Iterator<String> iterator = llistaParaules.iterator(); iterator.hasNext() && !potColocar;) {
			String paraula = iterator.next();
			
			//System.out.println(paraula); //DEBUG
			
			// Per cada lletra de la paraula anterior
			for(Map.Entry<Point, Character> coord : paraulaAnterior.entrySet()) {
				
				// Mira si alguna la paraula colocada i la nova tenen algun caracter en comu
				// - Agafat de Stackoverflow (https://stackoverflow.com/questions/5034442/indexes-of-all-occurrences-of-character-in-a-string)
				for (int index = paraula.indexOf(coord.getValue()); index >= 0 && !potColocar; index = paraula.indexOf(coord.getValue(), index + 1)) {
					
					// DEBUG
					//System.out.println(" Colisio => " + coord.getValue() + " Index:" + index);
					
					// Mirar si es podria colocar una paraula sense colisionar amb altres ja posades
					
					// - Calcular coordenades on començara i acabara la paraula
					Point coordsParaulaInici = new Point(coord.getKey()); //coord.getKey();
					Point coordsParaulaFinal = new Point(coord.getKey()); //coord.getKey();
					
					// Si es vertical
					if(dirActual == Direccio.HORITZONTAL) {
						// Modifiquem nomes la X
						coordsParaulaInici.x -= index;
						coordsParaulaFinal.x = coordsParaulaInici.x + paraula.length() - 1;
					} else {
						// Modifiquem nomes la Y
						coordsParaulaInici.y -= index;
						coordsParaulaFinal.y = coordsParaulaInici.y + paraula.length() - 1;
					}

					// @ Si les noves coordenades no es surten del tauler
					if(coordsParaulaInici.x >= 0 && coordsParaulaInici.y >= 0 && coordsParaulaFinal.x < gridParaulesMax.x && coordsParaulaFinal.y < gridParaulesMax.y) {
						
						// @ Comprovar si no colisionarien amb altres paraules ja existents al mateix axis (X o Y)
						// Treballa en un axis, en linea recta (ja sigui vertical o horitzontal)
						boolean colisiona = false;
						
						// - Agafar la posicio inicial, s'utilitzara com a punter per a recorrer (s'utilitzara al "For")
						Point coordAct = new Point(coordsParaulaInici);
						
						// Per cada coordenada a l'axis (desde l'inici de la paraula al final)
						// - Recorrer desde l'inici al final, es pot utilitzar com a index de la paraula per saber a quin caracter esta actualment
						int fiAxis = (dirActual == Direccio.HORITZONTAL ? coordsParaulaFinal.x - coordsParaulaInici.x : coordsParaulaFinal.y - coordsParaulaInici.y);
						for(int indexAxis = 0;indexAxis <= fiAxis && !colisiona; indexAxis++) {
							
							//System.out.println("[CHECK] X:" + coordAct.x + " Y:" + coordAct.y); //DEBUG
							
							// Si colisiona (amb una altre lletra ja colocada)
							if(taulellLletres.containsKey(coordAct)) {
							
								// Agafar posicio a continuacio de la colisio
								Point coordSegonaColisio = new Point(coordAct);
								if(dirActual == Direccio.HORITZONTAL) {coordSegonaColisio.x++;} else { coordSegonaColisio.y++;}
								
								// Si també colisiona amb la seguent colisio (a continuacio de la primera colisio)
								if(taulellLletres.containsKey(coordSegonaColisio)) {
									// Marca com invalid
									colisiona = true;
									
								} else {
									
									// NO COLISIONA AMB LA CONTINUACIO DE LA PRIMERA COLISIO
									
									// Si la lletra de la primera colisio es de la paraula colocada anteriorment, ignorar
									if(paraulaAnterior.containsKey(coordAct))
										colisiona = false;
									else
										// Si la lletra de la primera colisio no pertany a la paraula anterior, veure si son la mateixa
										// Si son diferents lletres, marca com a invalid
										colisiona = taulellLletres.get(coordAct) != paraula.charAt(indexAxis);
									
								} // If - No colisiona amb la seguent també
								
								// If - colisiona
							} else
								colisiona = false;
							
							
							//#### Comprovar que al voltant no colisiona amb altres paraules (te un marge de 1 casella a cada costat)
							
							if(!colisiona) {
								
								// - Si es el principi, comprovar una casella avants de l'inici (a l'esquerra o a dalt)
								if(indexAxis == 0) {
									Point cordVoltant = new Point(coordAct);
									if(dirActual == Direccio.HORITZONTAL) { cordVoltant.x--;} else {cordVoltant.y--;}
									colisiona = taulellLletres.containsKey(cordVoltant) && !paraulaAnterior.containsKey(cordVoltant);
									
									// DEBUG
									//System.out.println("[CHECK] Inici: " + cordVoltant.x + ", " + cordVoltant.y + " Colisiona: " + (colisiona ? "SI" : "NO"));
								}
								
								if(!colisiona) {
								
									// - Comprova els costats sempre (dalt i abaix o esquerra i dreta)								
									Point cordVoltant1 = new Point(coordAct);
									Point cordVoltant2 = new Point(coordAct);
									if(dirActual == Direccio.HORITZONTAL) {
										cordVoltant1.y--;
										cordVoltant2.y++;
									} else {
										cordVoltant1.x--;
										cordVoltant2.x++;
									}
	
									// Si colisiona en algun costat, marca-ho com a invalid
									colisiona = (taulellLletres.containsKey(cordVoltant1) || taulellLletres.containsKey(cordVoltant2)) && !paraulaAnterior.containsKey(cordVoltant1) && !paraulaAnterior.containsKey(cordVoltant2);
								
									// DEBUG
									//System.out.println("[CHECK] Costats: " + cordVoltant1.x + ", " + cordVoltant1.y + " // " + cordVoltant2.x + ", " + cordVoltant2.y + " Colisiona: " + (colisiona ? "SI" : "NO"));
									
									// - Si es el final, comprova una casella despres del final
									if(!colisiona && indexAxis == fiAxis) {
										Point cordVoltant = new Point(coordAct);
										if(dirActual == Direccio.HORITZONTAL) { cordVoltant.x++;} else {cordVoltant.y++;}
										colisiona = taulellLletres.containsKey(cordVoltant) && !paraulaAnterior.containsKey(cordVoltant);
										
										// DEBUG
										//System.out.println("[CHECK] Final: " + cordVoltant.x + ", " + cordVoltant.y);
										
									}
								}
								
							}
							
							// Anar movent punter/coordenades actual cap a la dreta o abaix (segons direccio)
							if(dirActual == Direccio.HORITZONTAL) {
								coordAct.x++;
							} else {
								coordAct.y++;
							}
							
						} // For - Colisio amb paraules existens a les mateixes coordenades
						
						// Si no colisiona, marca com a valid
						if(!colisiona)
							potColocar = true;
						
						// DEBUG
						//System.out.println("[COLISIO] Colisiona? " + (colisiona ? "SI" : "NO"));
						
						
						// If - Coords surten del tauler
					} else
						potColocar = false;

					// Si pot colocar la paraula, fes-ho
					if(potColocar) {
						LinkedHashMap<Point, Character> posChars = posarParaula(paraula, coordsParaulaInici.x, coordsParaulaInici.y, dirActual);
						llistaParaules.remove(paraula);
						posicionarParaula(llistaParaules, posChars, dirActual);
					}
					
				} //For - Index de colisions
				
			} //For - LinkedMap - Paraula anterior
			
			// DEBUG
			//System.out.println("[INFO] Es pot colocar?? " + (potColocar ? "SI" : "NO"));
		}

	} // FI - posicionarParaula

}
