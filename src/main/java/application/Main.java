package application;
 
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
 
public class Main extends Application {
 
	public static Stage finestra;
	
    @Override
    public void start(Stage stage) {
 
    	finestra = stage;
    	
        try {
        	
            // Crear loader i seleccionar la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/vista/Principal.fxml"));
             
            // Carregar la finestra
            Pane ventana = (Pane) loader.load();
             
            // Carregar escena
            Scene scene = new Scene(ventana);
            
            // Posar CSS
            //scene.getStylesheets().add("application/application.css");
            
            // Configurar finestra
            stage.setTitle("Words of Wonders");
            stage.setResizable(false);
            
            // Seleccionar escena i mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public static void main(String[] args) {
        launch(args);
    }
 
}