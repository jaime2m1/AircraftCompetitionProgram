package Vista;

import java.io.IOException;

import Controlador.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginCreateController {
	
	@FXML
	private void initialize() {
		
	}
    
    @FXML
    public void gotoLogin(ActionEvent event) throws IOException {
    	Parent loginView = FXMLLoader.load(getClass().getResource("../Vista/LoginView.fxml"));
    	Scene loginScene = new Scene(loginView);
    	
    	//Get stage info
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(loginScene);
    	window.show();
    }
    
    @FXML
    public void gotoRegister(ActionEvent event) throws IOException {
    	Parent registerView = FXMLLoader.load(getClass().getResource("../Vista/RegisterView.fxml"));
    	Scene registerScene = new Scene(registerView);
    	
    	//Get stage info
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(registerScene);
    	window.show();
    }
    
    @FXML
    public void gotoConfigDatabase(ActionEvent event) throws IOException {
    	try {
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(MainApp.class.getResource("../Vista/DBConfigDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Configura DB");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(MainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        DBConfigController controller = loader.getController();
	        
	        controller.setDialogStage(dialogStage);
	        controller.setData();
	        
	        dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
