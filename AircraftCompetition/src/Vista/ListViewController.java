package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Controlador.CompetitionDAO;
import Controlador.DBConfigDAO;
import Controlador.MainApp;
import Controlador.UserCompetitionDAO;
import Modelo.CompeticionModelo;
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
public class ListViewController {
	
	@FXML
    private TableView<CompeticionModelo> competitionTable;
    @FXML
    private TableColumn<CompeticionModelo, String> nombre;
    @FXML
    private TableColumn<CompeticionModelo, String> fecha;
    @FXML
    private TableColumn<CompeticionModelo, String> nParticipantes;
    
    @FXML
    private Label loginUsuario;
    @FXML
    private Label competicionNombre;
    @FXML
    private Label competicionFecha;
    @FXML
    private Label competicionNParticipantes;
    
    @FXML
    private Button inscribirse;
    @FXML
    private Button desinscribirse;
    @FXML
    private Button nuevaCompeticion;
    @FXML
    private Button anadirPuntuacion;
    @FXML
    private Button verClasificacion;
    
    private int nLicencia;
    private CompeticionModelo competicion;
    
    public ListViewController() {
    }
    
    @FXML
    private void initialize() {
    	setLoginUsuario();
    	setTableData();
    	competitionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCompetitionDetails(newValue));
    	competicionNombre.setText("");
        competicionFecha.setText("");
        competicionNParticipantes.setText("");
        inscribirse.setVisible(false);
        desinscribirse.setVisible(false);
        nuevaCompeticion.setVisible(false);
        anadirPuntuacion.setVisible(false);
        verClasificacion.setVisible(false);
        
        if(nLicencia==0) {
        	nuevaCompeticion.setVisible(true);
        }
    }
    

	/**
     * Establecemos los datos de la tabla
     */
	public void setTableData() {
    	System.out.println("Set table data");
    	CompetitionDAO dao = new CompetitionDAO();
    	ObservableList<CompeticionModelo> listaCompeticiones;
    	
    	
    	try {
    		dao.connectDB();
    		System.out.println("Establecemos los datos de la tabla");
    		
			listaCompeticiones = dao.getAllCompeticionesOL();
			for(int i=0; i<listaCompeticiones.size(); i++) {
				listaCompeticiones.get(i).setNParticipantes();
			}
			nombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNombre()));
    		fecha.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFecha().toString()));
    		nParticipantes.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNParticipantesSTR()));
			competitionTable.setItems(listaCompeticiones);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private void showCompetitionDetails(CompeticionModelo competicion) {
        if (competicion != null) {
        	this.competicion=competicion;
        	Date today = new Date();
        	
            competicionNombre.setText("Competicion "+competicion.getNombre());
            competicionFecha.setText("Fecha "+competicion.getFecha().toString());
            competicionNParticipantes.setText(competicion.getNParticipantesSTR()+" participantes");
            verClasificacion.setVisible(true);
            if(!checkCompeticionUsuario(competicion)) {
            	inscribirse.setVisible(true);
            	desinscribirse.setVisible(false);
            	anadirPuntuacion.setVisible(false);
            	}
            else {
            	inscribirse.setVisible(false);
            	desinscribirse.setVisible(true);
            	
            	if(nLicencia==0) {
            		anadirPuntuacion.setVisible(false);
                }else {
                	anadirPuntuacion.setVisible(true);
                }
            }
            
            //Chequea si se esta en el plazo de 20 dias para inscribirse
            
            if((competicion.getFecha().getTime() - 1728000000) > today.getTime() || today.getTime() > competicion.getFecha().getTime()) {
            	inscribirse.setVisible(false);
                desinscribirse.setVisible(false);
                anadirPuntuacion.setVisible(false);
            }
        } else {
        	competicionNombre.setText("");
            competicionFecha.setText("");
            competicionNParticipantes.setText("");
            inscribirse.setVisible(false);
        }
    }
	
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
				System.out.println("Id de competiciones "+competiciones.get(i).getId()+" = "+competicion.getId());
				if(competiciones.get(i).getId()==competicion.getId()) {
					System.out.println("Encontrado coincidencia de competicion");
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
	public void inscribirse(ActionEvent event) throws IOException {
		UserCompetitionDAO dao = new UserCompetitionDAO();
		
		try {
			dao.connectDB();
			dao.addUsuarioCompeticion(nLicencia, competicion.getId());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	public void desinscribirse(ActionEvent event) throws IOException {
		UserCompetitionDAO dao = new UserCompetitionDAO();
		
		try {
			dao.connectDB();
			dao.delUsuarioCompeticion(nLicencia, competicion.getId());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void gotoNewCompetition(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/NewCompetition.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nueva Competicion");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(MainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NewCompetitionController controller = loader.getController();

			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gotoVerClasificacion(ActionEvent event) throws IOException {
		try {
			Stage primaryStage = MainApp.getPrimaryStage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
			loader.setLocation(MainApp.class.getResource("../Vista/ListParticipants.fxml"));
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
	
	public void gotoAnadirPuntuacion(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/AddPuntuacion.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Añadir puntuacion");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(MainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddPuntuacionController controller = loader.getController();

			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
