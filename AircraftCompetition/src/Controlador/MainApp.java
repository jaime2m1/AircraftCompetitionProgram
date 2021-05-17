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

/**
 * @author Jaime,Pablo,Juan
 * 
 * Esta clase es la encargada de iniciar el programa
 */
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
	 * Iniciamos el layout base
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cargamos la vista de LoginCreate
	 */
	public void showLoginCreate() {
		try {

			loader.setLocation(MainApp.class.getResource("../Vista/LoginCreateView.fxml"));
			AnchorPane loginCreateView = (AnchorPane) loader.load();
			rootLayout.setCenter(loginCreateView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mostramos la configuración de la base de datos
	 * 
	 * @return
	 */
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
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayoutCompetition() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../Vista/RootLayoutCompetition.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mostramos la lista de competiciones
	 */
	public void showListCompetition() {

		try {
			loader.setLocation(MainApp.class.getResource("../Vista/ListView.fxml"));
			AnchorPane listView = (AnchorPane) loader.load();
			rootLayout.setCenter(listView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve el primaryStage
	 * 
	 * @return
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
