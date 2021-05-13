package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Controlador.CompetitionDAO;
import Controlador.DBConfigDAO;
import Controlador.MainApp;
import Controlador.MangaGruposDAO;
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
import javafx.scene.image.Image;
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
    private Button borrarCompeticion;
    @FXML
    private Button anadirPuntuacion;
    @FXML
    private Button verClasificacion;
    
    private int nLicencia;
    private CompeticionModelo competicion;
    private LocalDate todayLD = LocalDate.now();
    
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
        borrarCompeticion.setVisible(false);
        anadirPuntuacion.setVisible(false);
        verClasificacion.setVisible(false);
        
        if(nLicencia==0) {
        	nuevaCompeticion.setVisible(true);
        	borrarCompeticion.setVisible(true);
        }
    }
    

	/**
     * Establecemos los datos de la tabla
     */
	public void setTableData() {
    	//System.out.println("Set table data");
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
	/**
	 * Mostramos los datos de la competición seleccionada
	 * @param competicion
	 */
	private void showCompetitionDetails(CompeticionModelo competicion) {
        if (competicion != null) {
        	this.competicion=competicion;
        	
        	
            competicionNombre.setText("Competicion "+competicion.getNombre());
            competicionFecha.setText("Fecha "+competicion.getFecha().toString());
            competicionNParticipantes.setText(competicion.getNParticipantesSTR()+" participantes");
            verClasificacion.setVisible(true);
            //Si no está inscrito y antes de 20 días
            if(!checkCompeticionUsuario(competicion) && checkDateCompetition()) {
            	inscribirse.setVisible(true);
            	desinscribirse.setVisible(false);
            	anadirPuntuacion.setVisible(false);
            	}
            //Si está inscrito y antes de 20 días
            else if(checkCompeticionUsuario(competicion) && checkDateCompetition()){
            	inscribirse.setVisible(false);
            	desinscribirse.setVisible(true);
            	
            	if(nLicencia==0) {
            		anadirPuntuacion.setVisible(false);
                }else {
                	anadirPuntuacion.setVisible(true);
                }
            }
            //Si está inscrito y después de 20 días
            else if(checkCompeticionUsuario(competicion) && !checkDateCompetition()){
            	inscribirse.setVisible(false);
            	desinscribirse.setVisible(false);
            	
            	if(nLicencia==0) {
            		anadirPuntuacion.setVisible(false);
                }else {
                	anadirPuntuacion.setVisible(true);
                }
            }
            //Si no inscrito y después de 20 días
            else if(!checkCompeticionUsuario(competicion) && !checkDateCompetition()){
            	inscribirse.setVisible(false);
            	desinscribirse.setVisible(false);
            	
            	if(nLicencia==0) {
            		anadirPuntuacion.setVisible(false);
                }else {
                	anadirPuntuacion.setVisible(false);
                }
            }
            
            //Chequea si se esta en el plazo de 20 dias para inscribirse
            
//            if((competicion.getFecha().getTime() - 1728000000) > today.getTime() || today.getTime() > competicion.getFecha().getTime()) {
//            	inscribirse.setVisible(false);
//                desinscribirse.setVisible(false);
//                anadirPuntuacion.setVisible(false);
//            }
            
        } else {
        	competicionNombre.setText("");
            competicionFecha.setText("");
            competicionNParticipantes.setText("");
            inscribirse.setVisible(false);
        }
    }
	
	/**
	 * Obtenemos el usuario con el que se ha logeado
	 */
	private void setLoginUsuario() {
		DBConfigDAO dao = new DBConfigDAO();
		try {
			nLicencia = Integer.parseInt(dao.readDBConfig()[4]);
		} catch (NumberFormatException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Chequeamos que el usuario esté inscrito en la competición seleccionada
	 * @param competicion
	 * @return
	 */
	private boolean checkCompeticionUsuario(CompeticionModelo competicion) {
		UserCompetitionDAO dao = new UserCompetitionDAO();
		ArrayList<CompeticionModelo> competiciones;
		Boolean encontrado = false;
		
		try {
			dao.connectDB();
			competiciones = dao.getCompeticionesDeUsuario(this.nLicencia);
			//System.out.println(competiciones.size());
			for(int i=0; i<competiciones.size(); i++) {
				//System.out.println("Id de competiciones "+competiciones.get(i).getId()+" = "+competicion.getId());
				if(competiciones.get(i).getId()==competicion.getId()) {
					//System.out.println("Encontrado coincidencia de competicion");
					encontrado=true;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encontrado;
	}
	
	private boolean checkDateCompetition() {
		if(todayLD.isBefore(competicion.getFechaLD().minusDays(20))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Inscribimos al usuario en la competición
	 * @param event
	 * @throws IOException
	 */
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
	/**
	 * Desinscribimos al usuario
	 * @param event
	 * @throws IOException
	 */
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
	
	/**
	 * Abrimos la ventana para crear una nueva competición
	 * @param event
	 * @throws IOException
	 */
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
	
	/**
	 * Borramos una competición y todas las relaciones con los usuarios
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void deleteCompetition(ActionEvent event) throws IOException {
		CompetitionDAO daoCompe = new CompetitionDAO();
		try {
			daoCompe.connectDB();
			daoCompe.delCompeticion(this.competicion.getId());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserCompetitionDAO daoUserCompe = new UserCompetitionDAO();
		try {
			daoUserCompe.connectDB();
			daoUserCompe.delUsuarioCompeticion(this.competicion.getId());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MangaGruposDAO mangaDAO = new MangaGruposDAO();
		try {
			mangaDAO.connectDB();
			mangaDAO.delMangaCompeticion(this.competicion.getId());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Vamos a la ventana para ver la clasificación
	 * @param event
	 * @throws IOException
	 */
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
			
			ListParticipantsController controller = loader.getController();
			controller.setCompeticion(this.competicion);

			primaryStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Vamos a anadir la puntuación
	 * @param event
	 * @throws IOException
	 */
	public void gotoAnadirPuntuacion(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/AddPuntuacion.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Anadir puntuacion");
			dialogStage.getIcons().add(new Image("file:resources/images/plane.png"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(MainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddPuntuacionController controller = loader.getController();

			controller.setDialogStage(dialogStage);
			controller.setCompeticion(this.competicion);

			dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
