package Vista;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Controlador.DBConfigDAO;
import Controlador.MangaGruposDAO;
import Controlador.ScoreDAO;
import Modelo.CompeticionModelo;
import Modelo.GrupoModelo;
import Modelo.PuntuacionModelo;
import Modelo.UsuarioModelo;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jaime,Pablo,Juan
 * 
 *         Clase controlador de la ventana dialogo de la configuracion XML de la
 *         base de datos
 */
public class AddPuntuacionController {

	private Stage dialogStage;
	private boolean okClicked = false;

	@FXML
	private Label mangaLabel;
	@FXML
	private Label grupoLabel;
	@FXML
	private TextField tiempoLabel;
	@FXML
	private TextField distanciaLabel;
	@FXML
	private TextField alturaLabel;
	@FXML
	private ChoiceBox<String> mangaBoxLabel;
	
	private int nLicencia;
	private CompeticionModelo competicion;
	
	@FXML
    public void initialize() {
		setLoginUsuario();
		for(int i=1; i<=6; i++) {
			mangaBoxLabel.getItems().add(String.valueOf(i));
		}
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

	/**
	 * Anadimos la puntuacion de el usuario en X competicion y x manga
	 */
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
				//Generamos conexion y anadimos puntuacion
				dao.connectDB();
				dao.addPuntuacion(puntuacion);
				//Guardamos el id que se le ha asignado a la puntuacion
				puntuacion.setId(dao.getLastPuntuacionId());
				
				//Obtenemos el id de la manga
				MangaGruposDAO grupoDAO = new MangaGruposDAO();
				grupoDAO.connectDB();
				int nmanga = Integer.parseInt((String) mangaBoxLabel.getValue());
				
				//Creamos un grupo con la relación de Puntuación y Manga
				GrupoModelo grupo = new GrupoModelo();
				grupo.setManga(grupoDAO.getMangaCompeticion(competicion, nmanga));
				grupo.setPuntuacion(puntuacion);
				grupo.setNgrupo(asignarGrupo(nmanga));
				grupoDAO.addGrupo(grupo);
				
				dialogStage.close();
				
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Establecemos el usuario logeado
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
	 * Establecemos la competicion de la ventana
	 * @param competicion
	 */
	public void setCompeticion(CompeticionModelo competicion) {
		this.competicion = competicion;
	}
	
	/**
	 * Asignamos el grupo respecto la manga
	 * 
	 * @param manga
	 * @return
	 */
	private int asignarGrupo(int manga) {
		MangaGruposDAO dao = new MangaGruposDAO();
		int NGrupo1, NGrupo2, GrupoFin = 0;
		try {
			dao.connectDB();
			
			NGrupo1 = dao.getNGruposManga(manga, 1);
			NGrupo2 = dao.getNGruposManga(manga, 2);
			
			if(NGrupo1 >= 10) {
				GrupoFin = 2;
				
			} else if(NGrupo2 >= 10) {
				GrupoFin = 1;
				
			} else {
				if(Math.random()<=0.5) {
					GrupoFin = 1;
					
				} else {
					GrupoFin = 2;
					
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GrupoFin;
		
	}

}
