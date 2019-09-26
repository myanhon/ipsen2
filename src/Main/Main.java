package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Deze main class, hier wordt de applicatie gestart
 * Created by murtazaaydogdu on 11-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0 Nov 2016
 */
public class Main extends Application {
    /**
     * Hier wordt een object van Stage gedeclareerd,
     * zodat deze in een methode geopent en gesloten kan worden.
     */
    public static Stage primaryStage;

    /**
     * Deze start methode van de applicatie. Deze is
     * verantwoordelijk voor het starten van de login view
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/loginView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("LoginView");
        Scene scene = new Scene(root);
        root.getStylesheets().add(ThemaEnum.DEFAULT.getThema());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * De main van de applicatie. Hier start de applicatie
     * De main is verantwoordelijk voor het maken van het
     * login view. De main launched JavaFX
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }



    /**
     * Deze methode wordt aangeroepen wanneer een view moet worden afgesloten.
     */
    public static void closeWindow(){
        primaryStage.close();
    }

}
