package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Controlador.UserDAO;
import Modelo.UsuarioModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
	
	private UserDAO dao = new UserDAO();
	
	@FXML
    private TextField nombreLabel;
    @FXML
    private TextField apellidosLabel;
    @FXML
    private TextField nlicenciaLabel;
    @FXML
    private TextField contrasenaLabel;
    @FXML
    private TextField repcontrasenaLabel;
	
	@FXML
	private void initialize() {
		try {
			dao.connectDB();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @FXML
    public void gotoLoginCreate(ActionEvent event) throws IOException {
    	Parent loginCreateView = FXMLLoader.load(getClass().getResource("../Vista/LoginCreateView.fxml"));
    	Scene loginCreateScene = new Scene(loginCreateView);
    	
    	//Get stage info
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(loginCreateScene);
    	window.show();
    }
    
    @FXML
    public void registerUser(ActionEvent event) throws IOException{
    	UsuarioModelo usuario = new UsuarioModelo();
    	usuario.setNombre(nombreLabel.getText());
    	usuario.setApellidos(apellidosLabel.getText());
    	usuario.setNlicencia(Integer.parseInt(nlicenciaLabel.getText()));
    	if(contrasenaLabel.getText().equals(repcontrasenaLabel.getText())) {
    		usuario.setContrasena(contrasenaLabel.getText());
    	}
    	try {
    		Boolean encontrado=false;
			ArrayList<UsuarioModelo> listaUsuarios = dao.getAllUsuarios();
			for(int i=0; i<listaUsuarios.size(); i++) {
				if(listaUsuarios.get(i).getNlicencia()==usuario.getNlicencia()) {
					encontrado=true;
				}
			}
			if(!encontrado) {
				dao.addUsuario(usuario);
			}
			else {
				System.out.println("El usuario ya está creado");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
