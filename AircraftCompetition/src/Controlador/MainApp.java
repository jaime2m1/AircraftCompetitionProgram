package Controlador;

import java.io.IOException;

import Vista.DBConfigController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;
    private BorderPane rootLayout;
    private FXMLLoader loader = new FXMLLoader();

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("AircraftCompetition");
        MainApp.primaryStage.getIcons().add(new Image("file:resources/images/plane.png"));

        initRootLayout();
        showLoginCreate();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../Vista/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showLoginCreate() {
        try {
            // Load LoginCreate.
            
            loader.setLocation(MainApp.class.getResource("../Vista/LoginCreateView.fxml"));
            AnchorPane loginCreateView = (AnchorPane) loader.load();
            
            // Set LoginCreate into the center of root layout.
            rootLayout.setCenter(loginCreateView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showDBConfig() {
    	
    	try {
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Configura DB");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        DBConfigController controller = loader.getController();
	        
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayoutCompetition() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showListCompetition() {
    	
    	try {
            // Load LoginCreate.
            
            loader.setLocation(MainApp.class.getResource("../Vista/ListView.fxml"));
            AnchorPane listView = (AnchorPane) loader.load();
            
            // Set LoginCreate into the center of root layout.
            rootLayout.setCenter(listView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
