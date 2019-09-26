package Controller;

import Main.Main;
import javafx.stage.Stage;

import static Main.Main.primaryStage;

/**
 * Deze class wordt gebruikt als de controller van dae LogOutView.
 * Hierin staat alle logica die nodig zijn voor de LogOutView.
 * Created by murtazaaydogdu on 11-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class LogoutController {

    /**
     * Hier wordt de stage van de hoofdview en van de logout gedeclareerd
     */
    private Stage hoofdViewStage, logOutStage;

    /**
     * Hier wordt een nieuwe object van de main aangemaakt.
     */
    private Main main = new Main();

    /**
     * Deze methode zorgt ervoor dat de applicatie wordt afgesloten.
     */
    public void uitschakelenApplicatie() {
        System.exit(0);
    }

    /**
     * Deze methode zorgt ervoor dat de gebruiker wordt uitgelogd.
     */
    public void uitloggenUser(){
        hoofdViewStage.close();
        logOutStage.close();
        try {
            main.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Deze methode zorgt ervoor dat de stage van de hoofdView wordt geset.
     * @param hoofdViewStage
     */
    public void setHoofdViewStage(Stage hoofdViewStage) {
        this.hoofdViewStage = hoofdViewStage;
    }

    /**
     * Deze methode zorgt ervoor dat de stage van de logOut wordt geset.
     * @param logOutStage
     */
    public void setLogOutStage(Stage logOutStage) {
        this.logOutStage = logOutStage;
    }
}
