package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 *Deze classe wordt gebruikt om de meldingen te kunnen weergeven binnen de applicatie.
 * Created by murtazaaydogdu on 22-10-16.
 * @author Murtaza Aydogdu
 * @author Mike Yan
 * @version 1.0, Nov 2016
 */
public class Melding {

    /**
     * Hier wordt een object van de Alert gemaakt.
     */
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    /**
     * Hier wordt een object van de buttonTypeCancel gemaakt.
     */
    private ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    /**
     * Hier wordt een object van de buttonTypeOk gemaakt.
     */
    private ButtonType buttonTypeOk  = new ButtonType("OK");

    /**
     * Hier wordt een voorActie gemaakt met als type een boolean.
     */
    private boolean voorActie;

    /**
     * Dit is een universele melding die zelf ingevuld kan worden door de programmeur
     * kant.
     * @param title
     * @param header
     * @param content
     * @return voorActie
     */
    public boolean showWarningMessage(String title,String header, String content){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            voorActie = true;
        } else {
            voorActie = false;
        }
        return voorActie;
    }

    /**
     * Dit is een universele melding die zelf ingevuld kan worden door de programmeur
     * kant.
     * @param title
     * @param header
     * @param content
     */
    public void showNormalMessage(String title,String header, String content){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(buttonTypeOk);
        alert.showAndWait();
    }

    /**
     * Deze methode zorgt ervoor dat als deze wordt aangeroepen die de voorActie returnd.
     * @return voorActie
     */
    public boolean isVoorActie() {
        return voorActie;
    }
}
