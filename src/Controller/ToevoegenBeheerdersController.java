package Controller;

import DAO.BeheerderDAO;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Deze class wordt gebruikt als de controller van de toevoegenBeheerderView.
 * Hierin staat alle logica die nodig zijn voor de toevoegenBeheerderView.
 * Created by murtazaaydogdu on 05-10-16.
 * @author Murtaza Aydogdu, Mohamed EL Baze
 * @version 1.0, Nov 2016
 */
public class ToevoegenBeheerdersController {

    /**
     * Hier wordt de txtVoornaam gedacaleerd.
     */
    @FXML
    private JFXTextField txtVoornaam;

    /**
     * Hier wordt de txtAchternaam gedacaleerd.
     */
    @FXML
    private JFXTextField txtAchternaam;

    /**
     * Hier wordt de txtAdresHuisnr gedacaleerd.
     */
    @FXML
    private JFXTextField txtAdresHuisnr;

    /**
     * Hier wordt de txtPostcode gedacaleerd.
     */
    @FXML
    private JFXTextField txtPostcode;

    /**
     * Hier wordt de txtWoonplaats gedacaleerd.
     */
    @FXML
    private JFXTextField txtWoonplaats;

    /**
     * Hier wordt de txtTelefoon gedacaleerd.
     */
    @FXML
    private JFXTextField txtTelefoon;

    /**
     * Hier wordt de txtEmail gedacaleerd.
     */
    @FXML
    private JFXTextField txtEmail;

    /**
     * Hier wordt de txtWachtwoord gedacaleerd.
     */
    @FXML
    private JFXPasswordField txtWachtwoord;

    /**
     * Hier wordt de rbAdmin gedacaleerd.
     */
    @FXML
    private JFXRadioButton rbAdmin;

    /**
     * Hier wordt de rbBeheerder gedacaleerd.
     */
    @FXML
    private JFXRadioButton rbBeheerder;

    /**
     * Hier wordt de btnOpslaan gedacaleerd.
     */
    @FXML
    private JFXButton btnOpslaan;

    /**
     * Hier wordt een object van de beheerderDAO aangemaakt.
     */
    private BeheerderDAO beheerderDAO = new BeheerderDAO();

    /**
     * Hier wordt de stage gedacaleerd.
     */
    private Stage stage;

    /**
     * Hier wordt een object van de PasswordEncryption aangemaakt.
     */
    private PasswordEncryption passwordEncryption = new PasswordEncryption();

    /**
     * Hier wordt een object van de FormValidator aangemaakt.
     */
    private FormValidator formValidator = new FormValidator();

    /**
     * Hier wordt een object van de Beheerder aangemaakt.
     */
    private Beheerder beheerder = new Beheerder();

    /**
     *Hier wordt een object van de Melding aangemaakt
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt de BeheerderController gedacaleerd.
     */
    private BeheerderController controller;

    /**
     * Deze methode zorgt ervoor dat als eerst de textfield worden gevalideerd en daarna wordt opgeslagen in de
     * database.
     * @param actionEvent
     */
    public void opslaanBeheerders(ActionEvent actionEvent) {
        if(validator() == true){
            beheerder.setVoornaam(txtVoornaam.getText());
            beheerder.setAchternaam(txtAchternaam.getText());
            beheerder.setAdres(txtAdresHuisnr.getText());
            beheerder.setPostcode(txtPostcode.getText());
            beheerder.setWoonplaats(txtWoonplaats.getText());
            beheerder.setTelefoon(txtTelefoon.getText());
            beheerder.setEmail(txtEmail.getText());
            beheerder.setWachtwoord(passwordEncryption.GenerateHash(txtWachtwoord.getText()));
            if(rbAdmin.isSelected()){
                beheerder.setRechten_id(1);
            }else{
                beheerder.setRechten_id(2);
            }
            beheerderDAO.stage(stage);
            beheerderDAO.setBeheerder(beheerder);
            beheerderDAO.insert();
            if(beheerderDAO.getRows() > 0){
                melding.showNormalMessage("Succes","Toevoegen beheerders","Het toevoegen van beheerders is gelukt");
                stage.close();
                controller.refreshTable();
            }else{
                melding.showWarningMessage("Warning","Toevoegen beheerders mislukt", beheerderDAO.getErrorBeschrijving());
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het validaton van alle textfield binnen deze class.
     * @return formValidator.getValid();
     */
    private boolean validator(){
        formValidator.setHasError(true);
        formValidator.hasFormValidator(txtVoornaam);
        formValidator.hasFormValidator(txtAchternaam);
        formValidator.hasFormValidator(txtAdresHuisnr);
        formValidator.hasFormValidator(txtPostcode);
        formValidator.hasFormValidator(txtWoonplaats);
        formValidator.hasFormValidator(txtTelefoon);
        formValidator.emailValidator(txtEmail);
        formValidator.passwordValidator(txtWachtwoord);
        return formValidator.getValid();
    }

    /**
     * Deze methode zorgt ervoor dat de stage wordt geset.
     * @param stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Deze methode zorgt ervoor dat de stage wordt afgesloten.
     * @param actionEvent
     */
    public void cancelToevoegenBeheerders(ActionEvent actionEvent) {
        stage.close();
    }

    /**
     * Deze methode zorgt ervoor dat de beheerdercontroller wordt geset.
     * @param controller
     */
    public void setController(BeheerderController controller) {
        this.controller = controller;
    }
}
