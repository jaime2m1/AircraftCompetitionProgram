package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Controlador.MainApp;
import Controlador.UserDAO;
import Modelo.CompeticionModelo;
import Modelo.PuntuacionModelo;
import Modelo.UsuarioModelo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * @author Jaime,Pablo,Juan
 * 
 * Clase controlador de la ListView
 */
public class ListParticipantsController {
	
	@FXML
    private TableView<UsuarioModelo> participantsTable;
    @FXML
    private TableColumn<UsuarioModelo, String> puesto;
    @FXML
    private TableColumn<UsuarioModelo, String> nombre;
    @FXML
    private TableColumn<UsuarioModelo, String> licencia;
    @FXML
    private TableColumn<UsuarioModelo, String> puntuacion;
    
    @FXML
    private Label loginUsuario;
    @FXML
    private Label participantePuesto;
    @FXML
    private Label participanteNombre;
    @FXML
    private Label participanteLicencia;
    @FXML
    private Label participantePuntuacion;
    
    @FXML
    private ScrollPane mangasScroll;
    
    @FXML
    private Label segundosManga1;
    @FXML
    private Label distanciaManga1;
    @FXML
    private Label alturaManga1;
    
    @FXML
    private Label segundosManga2;
    @FXML
    private Label distanciaManga2;
    @FXML
    private Label alturaManga2;
    
    @FXML
    private Label segundosManga3;
    @FXML
    private Label distanciaManga3;
    @FXML
    private Label alturaManga3;
    
    @FXML
    private Label segundosManga4;
    @FXML
    private Label distanciaManga4;
    @FXML
    private Label alturaManga4;
    
    @FXML
    private Label segundosManga5;
    @FXML
    private Label distanciaManga5;
    @FXML
    private Label alturaManga5;
    
    @FXML
    private Label segundosManga6;
    @FXML
    private Label distanciaManga6;
    @FXML
    private Label alturaManga6;
    
    
    @FXML
    private Button anadirPuntuacion;
    
    private CompeticionModelo competicion = new CompeticionModelo();
    
    public ListParticipantsController() {
    }
    
    @FXML
    private void initialize() {
    	setTableData();
    	participantsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showParticipantDetails(newValue));
    	participantePuesto.setText("");
    	participanteNombre.setText("");
    	participanteLicencia.setText("");
    	participantePuntuacion.setText("");
    	
    	mangasScroll.setVisible(false);
        
    }
    

	/**
     * Establecemos los datos de la tabla
     */
	public void setTableData() {
    	//System.out.println("Set table data");
    	UserDAO dao = new UserDAO();
    	ObservableList<UsuarioModelo> listaParticipantes;
    	
    	
    	try {
    		dao.connectDB();
    		//System.out.println("Establecemos los datos de la tabla");
    		
    		listaParticipantes = dao.getUsuariosOLCompeticion(competicion);
    		
    		//Actualizamos la puntuacion
    		for(int i=0; i<listaParticipantes.size(); i++) {
    			listaParticipantes.get(i).updatePuntuacionCompe(competicion.getId());
    		}
    		
    		//Ordenamos la lista de participantes
    		listaParticipantes.sort((a, b) -> Double.compare(b.getPuntuacionCompe(), a.getPuntuacionCompe()));
    		
    		//Establecemos el puesto
    		for(int i=0; i<listaParticipantes.size(); i++) {
    			listaParticipantes.get(i).setPuesto(i+1);
    		}
    		
    	
    		puesto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getPuesto())));
			nombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((cellData.getValue().getNombre()) +" "+ (cellData.getValue().getApellidos())));
    		licencia.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getNlicencia())));
    		puntuacion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getPuntuacionCompe())));
			participantsTable.setItems(listaParticipantes);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private void showParticipantDetails(UsuarioModelo participante) {
		
		
        if (participante != null) {
        	setTableData();
        	participantePuesto.setText("Puesto " + participante.getPuesto());
        	participanteNombre.setText("Nombre " + participante.getNombre() +" "+ participante.getApellidos());
        	participanteLicencia.setText("No Licencia " + participante.getNlicencia());
        	participantePuntuacion.setText("Puntuacion " + participante.getPuntuacionCompe());
        	
        	mangasScroll.setVisible(true);
        	setMangasDetails(participante);
        } else {
        	participantePuesto.setText("");
        	participanteNombre.setText("");
        	participanteLicencia.setText("");
        	participantePuntuacion.setText("");
        	
        	mangasScroll.setVisible(false);
        	
        }
        
    }
	
	
	@FXML
	public void gotoPantallaPrincipal(ActionEvent event) throws IOException {
		try {
			Stage primaryStage = MainApp.getPrimaryStage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
			loader.setLocation(MainApp.class.getResource("../Vista/ListView.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();

			primaryStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCompeticion(CompeticionModelo competicion) {
		System.out.println("Competicion de lista de participantes asignada");
		this.competicion = competicion;
		setTableData();
	}
	
	public void setMangasDetails(UsuarioModelo participante) {
		ArrayList<PuntuacionModelo> puntuaciones = new ArrayList<PuntuacionModelo>();
		UserDAO dao = new UserDAO();
		try {
			dao.connectDB();
			puntuaciones = dao.getPuntuacionesUsuarioCompeticion(participante.getNlicencia(), this.competicion.getId());
			System.out.println("ListParticipants - Hay "+puntuaciones.size()+" puntuaciones");
			
			asignarPuntuaciones(puntuaciones);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void asignarPuntuaciones(ArrayList<PuntuacionModelo> puntuaciones) {
		int tamano = puntuaciones.size();
		
		if(tamano>=1) {
			segundosManga1.setText("Tiempo: "+puntuaciones.get(0).getSegundosVuelo());
			distanciaManga1.setText("Distancia: "+puntuaciones.get(0).getDistanciaVuelo());
			alturaManga1.setText("Altura: "+puntuaciones.get(0).getAlturaVuelo());
		}
		else {
			segundosManga1.setText("Tiempo: ");
			distanciaManga1.setText("Distancia: ");
			alturaManga1.setText("Altura: ");
		}
		
		if(tamano>=2) {
			segundosManga2.setText("Tiempo: "+puntuaciones.get(1).getSegundosVuelo());
			distanciaManga2.setText("Distancia: "+puntuaciones.get(1).getDistanciaVuelo());
			alturaManga2.setText("Altura: "+puntuaciones.get(1).getAlturaVuelo());
		}
		else {
			segundosManga2.setText("Tiempo: ");
			distanciaManga2.setText("Distancia: ");
			alturaManga2.setText("Altura: ");
		}
		
		if(tamano>=3) {
			segundosManga3.setText("Tiempo: "+puntuaciones.get(2).getSegundosVuelo());
			distanciaManga3.setText("Distancia: "+puntuaciones.get(2).getDistanciaVuelo());
			alturaManga3.setText("Altura: "+puntuaciones.get(2).getAlturaVuelo());
		}
		else {
			segundosManga3.setText("Tiempo: ");
			distanciaManga3.setText("Distancia: ");
			alturaManga3.setText("Altura: ");
		}
		
		if(tamano>=4) {
			segundosManga4.setText("Tiempo: "+puntuaciones.get(3).getSegundosVuelo());
			distanciaManga4.setText("Distancia: "+puntuaciones.get(3).getDistanciaVuelo());
			alturaManga4.setText("Altura: "+puntuaciones.get(3).getAlturaVuelo());
		}
		else {
			segundosManga4.setText("Tiempo: ");
			distanciaManga4.setText("Distancia: ");
			alturaManga4.setText("Altura: ");
		}
		
		if(tamano>=5) {
			segundosManga5.setText("Tiempo: "+puntuaciones.get(4).getSegundosVuelo());
			distanciaManga5.setText("Distancia: "+puntuaciones.get(4).getDistanciaVuelo());
			alturaManga5.setText("Altura: "+puntuaciones.get(4).getAlturaVuelo());
		}
		else {
			segundosManga5.setText("Tiempo: ");
			distanciaManga5.setText("Distancia: ");
			alturaManga5.setText("Altura: ");
		}
		
		if(tamano>=6) {
			segundosManga6.setText("Tiempo: "+puntuaciones.get(5).getSegundosVuelo());
			distanciaManga6.setText("Distancia: "+puntuaciones.get(5).getDistanciaVuelo());
			alturaManga6.setText("Altura: "+puntuaciones.get(5).getAlturaVuelo());
		}
		else {
			segundosManga6.setText("Tiempo: ");
			distanciaManga6.setText("Distancia: ");
			alturaManga6.setText("Altura: ");
		}
	}
	
	
}
