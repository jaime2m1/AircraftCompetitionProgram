package Vista;

import java.io.IOException;
import java.sql.SQLException;

import Controlador.MainApp;
import Controlador.UserDAO;
import Modelo.CompeticionModelo;
import Modelo.UsuarioModelo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
			nombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((cellData.getValue().getNombre()) + (cellData.getValue().getApellidos())));
    		licencia.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getNlicencia())));
    		puntuacion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getPuntuacionCompe())));
			participantsTable.setItems(listaParticipantes);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private void showParticipantDetails(UsuarioModelo participante) {
		setTableData();
        if (participante != null) {
        	participantePuesto.setText("Puesto " + participante.getPuesto());
        	participanteNombre.setText("Nombre " + participante.getNombre() + participante.getApellidos());
        	participanteLicencia.setText("No Licencia " + participante.getNlicencia());
        	participantePuntuacion.setText("Puntuacion " + participante.getPuntuacionCompe());
        	
        	
        } else {
        	participantePuesto.setText("");
        	participanteNombre.setText("");
        	participanteLicencia.setText("");
        	participantePuntuacion.setText("");
        	
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
	
	
}
