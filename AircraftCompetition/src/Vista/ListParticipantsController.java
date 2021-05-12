package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import Controlador.CompetitionDAO;
import Controlador.DBConfigDAO;
import Controlador.MainApp;
import Controlador.UserCompetitionDAO;
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
import javafx.stage.Modality;
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
    private Label participantePuntuacionTiempo;
    @FXML
    private Label participantePuntuacionDistancia;
    @FXML
    private Label participantePuntuacionAltura;
    
    @FXML
    private Button anadirPuntuacion;
    
    private int nLicencia;
    private UsuarioModelo participante;
    private CompeticionModelo competicion = new CompeticionModelo();
    
    public ListParticipantsController() {
    }
    
    @FXML
    private void initialize() {
    	setLoginUsuario();
    	setTableData();
    	participantsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showParticipantDetails(newValue));
    	participantePuesto.setText("");
    	participanteNombre.setText("");
    	participanteLicencia.setText("");
    	participantePuntuacion.setText("");
    	participantePuntuacionTiempo.setText("");
    	participantePuntuacionDistancia.setText("");
    	participantePuntuacionAltura.setText("");
        
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
			
    		puesto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getPuesto())));
			nombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((cellData.getValue().getNombre()) + (cellData.getValue().getApellidos())));
    		licencia.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getNlicencia())));
    		puntuacion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getPuntuacion())));
			participantsTable.setItems(listaParticipantes);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private void showParticipantDetails(UsuarioModelo participante) {
        if (participante != null) {
        	this.participante=participante;
        	participantePuesto.setText("Puesto " + participante.getPuesto());
        	participanteNombre.setText("Nombre " + participante.getNombre() + participante.getApellidos());
        	participanteLicencia.setText("No Licencia " + participante.getNlicencia());
        	participantePuntuacion.setText("Puntuacion " + participante.getPuntuacion());
        	participantePuntuacionTiempo.setText("");
        	participantePuntuacionDistancia.setText("");
        	participantePuntuacionAltura.setText("");
        	
        } else {
        	participantePuesto.setText("");
        	participanteNombre.setText("");
        	participanteLicencia.setText("");
        	participantePuntuacion.setText("");
        	participantePuntuacionTiempo.setText("");
        	participantePuntuacionDistancia.setText("");
        	participantePuntuacionAltura.setText("");
        }
    }
	
	//Checkpoint
	
	private void setLoginUsuario() {
		DBConfigDAO dao = new DBConfigDAO();
		try {
			nLicencia = Integer.parseInt(dao.readDBConfig()[4]);
		} catch (NumberFormatException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean checkCompeticionUsuario(CompeticionModelo competicion) {
		UserCompetitionDAO dao = new UserCompetitionDAO();
		ArrayList<CompeticionModelo> competiciones;
		Boolean encontrado = false;
		
		try {
			dao.connectDB();
			competiciones = dao.getCompeticionesDeUsuario(this.nLicencia);
			System.out.println(competiciones.size());
			for(int i=0; i<competiciones.size(); i++) {
				//System.out.println("Id de competiciones "+competiciones.get(i).getId()+" = "+competicion.getId());
				if(competiciones.get(i).getId()==competicion.getId()) {
					//System.out.println("Encontrado coincidencia de competici�n");
					encontrado=true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encontrado;
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
	}
	
	
}
