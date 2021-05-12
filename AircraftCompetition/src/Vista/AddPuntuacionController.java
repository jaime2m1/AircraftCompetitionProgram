package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Controlador.CompetitionDAO;
import Controlador.DBConfigDAO;
import Controlador.ScoreDAO;
import Modelo.CompeticionModelo;
import Modelo.PuntuacionModelo;
import Modelo.UsuarioModelo;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase controlador de la ventana dialogo de la configuracion XML de la
 *         base de datos
 */
public class AddPuntuacionController {

	private double tiempo;
	private double distancia;
	private double altura;

	private Stage dialogStage;
	private boolean okClicked = false;

	@FXML
	private TextField mangaLabel;
	@FXML
	private TextField grupoLabel;
	@FXML
	private TextField tiempoLabel;
	@FXML
	private TextField distanciaLabel;
	@FXML
	private TextField alturaLabel;
	
	private int nLicencia;
	
	@FXML
    public void initialize() {
		setLoginUsuario();
    }

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Chequeamos si la entrada del usuario es valida
	 * 
	 * @return
	 */

	public boolean isInputValid() {
		if (tiempoLabel.getText() == null && distanciaLabel.getText() == null && alturaLabel.getText() == null) {
			return false;
		} else {
			return true;
		}
	}

	@FXML
	public void addPuntuacion() {
		if (isInputValid()) {
			PuntuacionModelo puntuacion = new PuntuacionModelo();
			UsuarioModelo usuario = new UsuarioModelo();
			usuario.setNlicencia(nLicencia);
			puntuacion.setUsuario(usuario);
			puntuacion.setSegundosVuelo(Integer.parseInt(tiempoLabel.getText()));
			puntuacion.setDistanciaVuelo(Integer.parseInt(distanciaLabel.getText()));
			puntuacion.setAlturaVuelo(Integer.parseInt(alturaLabel.getText()));
			puntuacion.setPenalizacion(0);
			ScoreDAO dao = new ScoreDAO();
			try {
				dao.connectDB();
				dao.addPuntuacion(puntuacion);
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				dao.connectDB();
				dao.addPuntuacion(puntuacion);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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

}
