package Vista;

import java.sql.SQLException;

import Controlador.CompetitionDAO;
import Modelo.CompeticionModelo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    
    public void setTableData() {
    	System.out.println("Set table data");
    	CompetitionDAO dao = new CompetitionDAO();
    	ObservableList<CompeticionModelo> listaCompeticiones;
    	try {
			listaCompeticiones = (ObservableList<CompeticionModelo>) dao.getAllCompeticiones();
			competitionTable.setItems(listaCompeticiones);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
}
