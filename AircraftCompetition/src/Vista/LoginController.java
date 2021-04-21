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

public class LoginController {
	
	private UserDAO dao = new UserDAO();
	
    @FXML
    private TextField nlicenciaLabel;
    @FXML
    private TextField contrasenaLabel;
	
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
    public void loginUser(ActionEvent event) throws IOException{
    	UsuarioModelo usuario = new UsuarioModelo();
    	usuario.setNombre("");
    	usuario.setApellidos("");
    	usuario.setNlicencia(Integer.parseInt(nlicenciaLabel.getText()));
    	usuario.setContrasena(contrasenaLabel.getText());
    	
    	try {
    		Boolean encontrado=false;
			ArrayList<UsuarioModelo> listaUsuarios = dao.getAllUsuarios();
			for(int i=0; i<listaUsuarios.size(); i++) {
				System.out.println(listaUsuarios.get(i).getNlicencia()+" = "+usuario.getNlicencia()+"||"+listaUsuarios.get(i).getContrasena()+" = "+usuario.getContrasena());
				if(listaUsuarios.get(i).getNlicencia()==usuario.getNlicencia()&&listaUsuarios.get(i).getContrasena().equals(usuario.getContrasena())) {
					encontrado=true;
				}
			}
			if(encontrado) {
				System.out.println("Inicio Sesión");
			}
			else {
				System.out.println("El usuario no existe");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
