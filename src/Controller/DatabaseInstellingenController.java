package Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Deze class wordt gebruikt om de nieuwe database instellingen te setten.
 * Created by murtazaaydogdu on 11-10-16.
 * @author Murtaza Aydogdu
 * @Version 1.0, Nov 2016
 */
public class DatabaseInstellingenController {

    /**
     * Hier wordt de JFXTextField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXTextField txtDbIp;

    /**
     * Hier wordt de JFXTextField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXTextField txtPoortnummer;

    /**
     * Hier wordt de JFXTextField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXTextField txtDbGebruikersnaam;

    /**
     * Hier wordt de JFXTextField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXPasswordField txtDBWachtwoord;

    /**
     * Hier wordt de JFXTextField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXTextField txtDbNaam;

    /**
     * Hier wordt de FormValidator object aangemaakt, zodat er op een correcte manier de databaseinstellingen
     * ingevuld worden.
     */
    private FormValidator formValidator = new FormValidator();

    /**
     *  Hier wordt de Melding object aangemaakt, zodra de gebruiker op opslaan klikt en de actie succesvol is
     *  afgehandeld dat de gebruiker dan een melding krijgt.
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt de stage gedacaleeerd zodat de stage vanuit de logincontroller meekomt en terug kan worden gegaan.
     */
    private Stage stage;

    /**
     * Deze methode zorgt ervoor dat er een validatie komt op de verplichte velden.
     * @return formValidator.getValid();
     */
    public boolean validator(){
        formValidator.setHasError(true);
        formValidator.hasFormValidator(txtDbIp);
        formValidator.hasFormValidator(txtPoortnummer);
        formValidator.hasFormValidator(txtDbGebruikersnaam);
        formValidator.passwordValidator(txtDBWachtwoord);
        formValidator.hasFormValidator(txtDbNaam);
        return formValidator.getValid();
    }

    /**
     * Deze methode zorgt ervoor dat als de gebruiker op opslaan klikt, dat er dan als eerst wordt gekeken of alle
     * velden zijn ingevuld. Mocht dit het geval zijn dan wordt dit weggeschreven naar de config.properties.
     * @param actionEvent
     */
    public void opslaanDatabaseInstellingen(ActionEvent actionEvent) {

        formValidator.hasFormValidator(txtDbIp);

        Properties props = new Properties();
        OutputStream outputStream = null;

        try {

            outputStream = new FileOutputStream("src/resources/config/config.properties");

            if (validator() == true) {
                props.setProperty("Database ip", txtDbIp.getText().trim());
                props.setProperty("Poortnummer", txtPoortnummer.getText().trim());
                props.setProperty("Gebruikersnaam", txtDbGebruikersnaam.getText().trim());
                props.setProperty("Wachtwoord", txtDBWachtwoord.getText().trim());
                props.setProperty("Database naam", txtDbNaam.getText().trim());
                melding.showNormalMessage("Succes","Gegevens opgeslagen","De nieuwe database instellingen zijn " +
                        "opgeslagen.");
                if(melding.isVoorActie()){
                    this.stage.close();
                }
            }
            else{
                melding.showWarningMessage("Warning!","Action failed","Opslaan is mislukt!");
            }

            props.store(outputStream,null);

            if(outputStream != null){
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat zodra je op de cancel knop klikt dat de view wordt afgesloten
     * @param actionEvent
     */
    public void cancelDatabaseInstellingen(ActionEvent actionEvent) {
        stage.close();
    }

    /**
     * Deze methode zorgt ervoor dat de stage vanuit de loginController wordt geset. Dit heeft als doel zodra de
     * gebruiker op cancel klikt de database instellingen view kan worden afgesloten.
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
