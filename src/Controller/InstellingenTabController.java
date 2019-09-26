package Controller;

import DAO.BeheerderDAO;
import Main.ThemaEnum;
import Model.Beheerder;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * Deze class wordt gebruikt om de instellingen van zowel de beheerder als de instellingen van de applicatie te
 * kunnen wijzigen.
 * Created by murtazaaydogdu on 11-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class InstellingenTabController {

    /**
     * Hier wordt de rbDefault gedacaleerd.
     */
    @FXML
    private RadioButton rbDefault;

    /**
     * Hier wordt de rbRood gedacaleerd.
     */
    @FXML
    private RadioButton rbRood;

    /**
     * Hier wordt de rbHazelbruin gedacaleerd.
     */
    @FXML
    private RadioButton rbHazelbruin;

    /**
     * Hier wordt de rbOranje gedacaleerd.
     */
    @FXML
    private RadioButton rbOranje;

    /**
     * Hier wordt de rbBlauw gedacaleerd.
     */
    @FXML
    private RadioButton rbBlauw;

    /**
     * Hier wordt de txtEmail gedacaleerd.
     */
    @FXML
    private JFXTextField txtEmail;

    /**
     * Hier wordt de txtOudeWachtwoord gedacaleerd.
     */
    @FXML
    private JFXPasswordField txtOudeWachtwoord;

    /**
     * Hier wordt de txtNieuweWachtwoord gedacaleerd.
     */
    @FXML
    private JFXPasswordField txtNieuweWachtwoord;

    /**
     * Hier wordt de txtTelefoon gedacaleerd.
     */
    @FXML
    private JFXTextField txtTelefoon;

    /**
     * Hier wordt de beheerder gedacaleerd.
     */
    private Beheerder beheerder;

    /**
     * Hier wordt een nieuwe object van de PasswordEncryption aangmaakt.
     */
    private PasswordEncryption passwordEncryption = new PasswordEncryption();

    /**
     * Hier wordt een nieuwe object van de beheerderDAO aangemaakt.
     */
    private BeheerderDAO beheerderDao = new BeheerderDAO();

    /**
     * Hier wordt een nieuwe object van de melding gemaakt.
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt een nieuwe object van de formValidator gemaakt.
     */
    private FormValidator formValidator = new FormValidator();

    /**
     * Hier wordt een string van css aangemaakt..
     */
    private String css;

    /**
     * Hier wordt loginController gedacaleerd.
     */
    private LoginController loginController;

    /**
     * Hier wordt de stage gedacaleerd.
     */
    private Stage stage;

    /**
     * Deze methode is verantwoordelijk voor het opslaan van de gekozen thema.
     */
    public void opslaanInstellingen() {
        if(rbDefault.isSelected()){
           css = ThemaEnum.DEFAULT.getThema();
        }
        else if(rbRood.isSelected()) {
            css = ThemaEnum.ROOD.getThema();
        }
        else if(rbHazelbruin.isSelected()) {
            css = ThemaEnum.HAZELBRUIN.getThema();
        }
        else if(rbOranje.isSelected()) {
           css = ThemaEnum.ORANJE.getThema();
        }
        else if(rbBlauw.isSelected()) {
           css = ThemaEnum.BLAUW.getThema();
        }
        loginController.setCss(css);
        validateForm();
    }

    /**
     * Deze methode is verantwoordelijk voor het opslaan van de gewijzigde gegevens.
     * @return formValidator.getValid();
     */
    public void validateForm() {
        if(validator() == true){
            beheerder.setTelefoon(txtTelefoon.getText().trim());
            beheerder.setEmail(txtEmail.getText().trim());
            if(!txtOudeWachtwoord.getText().trim().isEmpty()){
                if(passwordEncryption.GenerateHash(txtOudeWachtwoord.getText()).equals(beheerder.getWachtwoord())){

                    beheerder.setWachtwoord(passwordEncryption.GenerateHash(txtNieuweWachtwoord.getText().trim()));
                }else{
                    melding.showWarningMessage("Warning!!","Wijzigen wachtwoord","De opgegeven wachtwoord komt niet overeen");
                }
            }
            beheerderDao.setBeheerder(beheerder);
            beheerderDao.update(beheerder.getId());
            if(beheerderDao.getRows() >0){
                melding.showNormalMessage("Warning!!", "Wijzigen wachtwoord", "De wijzigingen zijn opgeslagen");
            }
        }
        stage.close();
        this.stage.close();
    }

    /**
     * Deze methode zorgt ervoor dat de stage wordt afgesloten.
     * @param actionEvent
     */
    public void cancelInstellingen(ActionEvent actionEvent) {
        this.stage.close();
    }

    /**
     * Deze methode is verantwoordelijk voor het validaton van alle textfield binnen deze class.
     * @return formValidator.getValid();
     */
    private boolean validator(){
        formValidator.setHasError(true);
        formValidator.emailValidator(txtEmail);
        formValidator.hasFormValidator(txtTelefoon);
        return formValidator.getValid();
    }

    /**
     * Deze methode zorgt ervoor dat de beheerder wordt geset.
     * @param beheerder
     */
    public void setBeheerder(Beheerder beheerder) {
        this.beheerder = beheerder;
    }

    /**
     * Deze methode zorgt ervoor dat de loginController wordt geset.
     * @param loginController
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Deze methode zorgt ervoor dat de stage wordt geset.
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Deze methode zorgt ervoor dat de css wordt geset.
     * @param css
     */
    public void setCss(String css) {
        this.css = css;
    }
}
