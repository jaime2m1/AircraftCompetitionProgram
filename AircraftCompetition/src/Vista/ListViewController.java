package Vista;

import java.sql.SQLException;

import Controlador.CompetitionDAO;
import Modelo.CompeticionModelo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


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
    private Label competicionNombre;
    @FXML
    private Label competicionFecha;
    @FXML
    private Label competicionNParticipantes;
    
    @FXML
    private Button inscribirse;
    
    public ListViewController() {
    }
    
    @FXML
    private void initialize() {
    	setTableData();
    	competitionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCompetitionDetails(newValue));
    	competicionNombre.setText("");
        competicionFecha.setText("");
        competicionNParticipantes.setText("");
        inscribirse.setVisible(false);
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
            competicionNombre.setText("Competición "+competicion.getNombre());
            competicionFecha.setText("Fecha "+competicion.getFecha().toString());
            competicionNParticipantes.setText(competicion.getNParticipantesSTR()+" participantes");
            inscribirse.setVisible(true);
        } else {
        	competicionNombre.setText("");
            competicionFecha.setText("");
            competicionNParticipantes.setText("");
            inscribirse.setVisible(false);
        }
    }
}
