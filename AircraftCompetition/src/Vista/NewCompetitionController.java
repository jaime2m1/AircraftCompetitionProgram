package Vista;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import Controlador.CompetitionDAO;
import Modelo.CompeticionModelo;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase controlador de la ventana dialogo de la configuración XML de la
 *         base de datos
 */
public class NewCompetitionController {

	private String name;
	private LocalDate date;

	private Stage dialogStage;
	private boolean okClicked = false;

	@FXML
	private TextField nameLabel;
	@FXML
	private DatePicker dateLabel;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Chequeamos si la entrada del usuario es válida
	 * 
	 * @return
	 */

	public boolean isInputValid() {
		if (nameLabel.getText() == null && dateLabel.getValue() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Guardamos los cambios realizados por el usuario
	 */
	@FXML
	private void newCompetition() {

		CompetitionDAO dao = new CompetitionDAO();

		if (isInputValid()) {
			this.name = nameLabel.getText();
			this.date = dateLabel.getValue();
			ZoneId defaultZoneId = ZoneId.systemDefault();
			
			CompeticionModelo competicion = new CompeticionModelo();
			competicion.setNombre(name);
			competicion.setFecha(Date.from(date.atStartOfDay(defaultZoneId).toInstant()));
			
			try {
				dao.connectDB();
				dao.addCompeticion(competicion);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			okClicked = true;
			dialogStage.close();
		}
	}

}
