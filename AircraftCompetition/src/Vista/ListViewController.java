package Vista;

import java.sql.SQLException;

import Controlador.CompetitionDAO;
import Modelo.CompeticionModelo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    
    public ListViewController() {
    }
    
    @FXML
    private void initialize() {
    	setTableData();
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
    		nombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNombre()));
    		fecha.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFecha().toString()));
			listaCompeticiones = dao.getAllCompeticionesOL();
			competitionTable.setItems(listaCompeticiones);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
}
