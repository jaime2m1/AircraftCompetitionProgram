package Vista;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterController {
	@FXML
	private void initialize() {
		
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
}
