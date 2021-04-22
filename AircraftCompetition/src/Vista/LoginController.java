package Vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Controlador.MainApp;
import Controlador.UserDAO;
import Modelo.UsuarioModelo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
				login();
				System.out.println("Inicio Sesión");
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Error iniciando sesión");
				alert.showAndWait();
				System.out.println("El usuario no existe");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

	private void login() throws IOException {
		Stage primaryStage = MainApp.getPrimaryStage();
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
        BorderPane rootLayout = (BorderPane) loader.load();
        
        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
		
        loader.setLocation(MainApp.class.getResource("../Vista/ListView.fxml"));
        //AnchorPane listView = (AnchorPane) loader.load();
        
        // Set LoginCreate into the center of root layout.
        //rootLayout.setCenter(listView);
	}
}
