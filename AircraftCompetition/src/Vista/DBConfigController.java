package Vista;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import Controlador.DBConfigDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DBConfigController {
	
	private String host, database, user, password;
	
	private Stage dialogStage;
	private boolean okClicked = false;
	
	@FXML
    private TextField hostLabel;
    @FXML
    private TextField databaseLabel;
    @FXML
    private TextField userLabel;
    @FXML
    private TextField passwordLabel;

    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setData() {
    	DBConfigDAO dao = new DBConfigDAO();
    	String[] DBConfig;
		try {
			DBConfig = dao.readDBConfig();
			hostLabel.setText(DBConfig[0]);
	    	databaseLabel.setText(DBConfig[1]);
	    	userLabel.setText(DBConfig[2]);
	    	passwordLabel.setText(DBConfig[3]);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
	public boolean isOkClicked() {
		return okClicked;
	}
	
	public boolean isInputValid() {
		if(hostLabel.getText()==null&&
		   databaseLabel.getText()==null&&
		   userLabel.getText()==null&&
		   passwordLabel.getText()==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@FXML
    private void handleSave() {
		
		DBConfigDAO dao = new DBConfigDAO();
		
        if (isInputValid()) {
            this.host=hostLabel.getText();
            this.database=databaseLabel.getText();
            this.user=userLabel.getText();
            this.password=passwordLabel.getText();
            String[] DBConfig = {host, database, user, password};
            
            
            try {
				dao.updateDBConfig(DBConfig);
			} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            okClicked = true;
            dialogStage.close();
        }
    }

	

}
