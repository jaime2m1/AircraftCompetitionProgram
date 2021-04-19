package Vista;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoginCreateController {
	
	@FXML
	private void initialize() {
		
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
}
