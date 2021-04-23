package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import Controlador.DBConfigDAO;
import Controlador.MainApp;
import Controlador.UserDAO;
import Modelo.UsuarioModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase controlador de la LoginView
 * 
 * @author Jaime,Pablo,Juan 
 */
public class LoginController {

	private UserDAO dao = new UserDAO();
	
	private UsuarioModelo usuario = new UsuarioModelo();

	@FXML
	private TextField nlicenciaLabel;
	@FXML
	private TextField contrasenaLabel;

	/**
	 * Establecemos conexión con la base de datos
	 */
	@FXML
	private void initialize() {
		try {
			dao.connectDB();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Volvemos atrás al LoginCreateView
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void gotoLoginCreate(ActionEvent event) throws IOException {
		Parent loginCreateView = FXMLLoader.load(getClass().getResource("../Vista/LoginCreateView.fxml"));
		Scene loginCreateScene = new Scene(loginCreateView);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(loginCreateScene);
		window.show();
	}

	/**
	 * Comprobamos el inicio de sesión
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void loginUser(ActionEvent event) throws IOException {
		usuario.setNombre("");
		usuario.setApellidos("");
		usuario.setNlicencia(Integer.parseInt(nlicenciaLabel.getText()));
		usuario.setContrasena(contrasenaLabel.getText());

		try {
			Boolean encontrado = false;
			ArrayList<UsuarioModelo> listaUsuarios = dao.getAllUsuarios();
			for (int i = 0; i < listaUsuarios.size(); i++) {
				System.out.println(listaUsuarios.get(i).getNlicencia() + " = " + usuario.getNlicencia() + "||"
						+ listaUsuarios.get(i).getContrasena() + " = " + usuario.getContrasena());
				if (listaUsuarios.get(i).getNlicencia() == usuario.getNlicencia()
						&& listaUsuarios.get(i).getContrasena().equals(usuario.getContrasena())) {
					encontrado = true;
				}
			}
			if (encontrado) {
				login();
				System.out.println("Inicio Sesión");
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Error iniciando sesión");
				alert.showAndWait();
				System.out.println("El usuario no existe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Nos dirigimos a la ListView
	 * 
	 * @throws IOException
	 */
	private void login() throws IOException {
		
		DBConfigDAO dao = new DBConfigDAO();
		try {
			String[] DBConfig = dao.readDBConfig();
			DBConfig[4] = String.valueOf(usuario.getNlicencia());
			dao.updateDBConfig(DBConfig);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Stage primaryStage = MainApp.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
		loader.setLocation(MainApp.class.getResource("../Vista/ListView.fxml"));
		AnchorPane rootLayout = (AnchorPane) loader.load();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();

		primaryStage.show();
	}
}
