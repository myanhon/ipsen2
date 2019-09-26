package Controller;

import DAO.BeheerderDAO;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 *  Deze class wordt gebruikt als de controller van de wijzigenBeheerderView.
 * Hierin staat alle logica die nodig zijn voor de wijzigenBeheerderView.
 * Created by murtazaaydogdu on 16-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class WijzigenBeheerdersController {

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
     * Hier wordt de BeheerderController gedacaleerd.
     */
    private BeheerderController controller;

    /**
     * Hier wordt de model van de Beherder gedacaleerd.
     */
    private Beheerder beheerder;

    /**
     * Hier wordt de stage gedacaleerd.
     */
    private Stage stage;

    /**
     * Hier wordt een object van de BeheerderDAO aangemaakt.
     */
    private BeheerderDAO beheerderDAO = new BeheerderDAO();

    /**
     * Hier wordt een object van de PasswordEncryption aangemaakt.
     */
    private PasswordEncryption passwordEncryption = new PasswordEncryption();

    /**
     * Hier wordt een object de FormValidator aangemaakt.
     */
    private FormValidator formValidator = new FormValidator();

    /**
     * Hier wordt een object van Meldingen aangemaakt.
     */
    private Melding melding = new Melding();

    /**
     * Deze methode zorgt ervoor dat als eerst de textfield worden gevalideerd en daarna wordt opgeslagen in de
     * database.
     */
    public void opslaanBeheerders(){

        if(validator() == true){
            beheerder.setVoornaam(txtVoornaam.getText().trim());
            beheerder.setAchternaam(txtAchternaam.getText().trim());
            beheerder.setAdres(txtAdresHuisnr.getText().trim());
            beheerder.setPostcode(txtPostcode.getText().trim());
            beheerder.setWoonplaats(txtWoonplaats.getText().trim());
            beheerder.setTelefoon(txtTelefoon.getText().trim());
            beheerder.setEmail(txtEmail.getText().trim());
            if(!txtWachtwoord.getText().trim().isEmpty()){
                beheerder.setWachtwoord(passwordEncryption.GenerateHash(txtWachtwoord.getText()).trim());
            }
            beheerderDAO.setBeheerder(beheerder);
            beheerderDAO.update(beheerder.getId());

            if(beheerderDAO.getRows() > 0){
                melding.showNormalMessage("Succes","Wijzigen beheerders","Het wijzgen van beheerders is gelukt");
                this.stage.close();
                controller.refreshTable();
            }else {
                melding.showWarningMessage("Warning","Wijzigen beheerders","Het wijzigen van beheerders is mislukt");
            }
        }
        else{
            melding.showWarningMessage("Warning!!","Verplichte velden","Vul alle verplichte velden in");
        }
    }

    /**
     * Deze methode zorgt ervoor dat de WijzgenBeheerdersView wordt afgesloten.
     */
    public void cancelToevoegenBeheerders(){
        stage.close();
    }

    /**
     * Deze methode is verantwoordelijk dat de textfield worden gevuld met de geselecteerde beheerder.
     * @param beheerder
     */
    public void init(Beheerder beheerder){
        this.beheerder = beheerder;
        txtVoornaam.setText(beheerder.getVoornaam());
        txtAchternaam.setText(beheerder.getAchternaam());
        txtAdresHuisnr.setText(beheerder.getAdres());
        txtPostcode.setText(beheerder.getPostcode());
        txtWoonplaats.setText(beheerder.getWoonplaats());
        txtTelefoon.setText(beheerder.getTelefoon());
        txtEmail.setText(beheerder.getEmail());
        if(beheerder.getRechten_id() == 1){
            rbAdmin.setSelected(true);
        }
        else{
            rbBeheerder.setSelected(true);
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
        formValidator.hasFormValidator(txtEmail);
        formValidator.emailValidator(txtEmail);
        return formValidator.getValid();
    }

    /**
     * Deze methode zorgt ervoor dat de beheerderDAO deze stage krijgt.
     * @param stage
     */
    public void stage(Stage stage) {
        beheerderDAO.stage(stage);
        this.stage = stage;
    }

    /**
     * Deze methode zorgt ervoor dat de BeheerderController wordt geset.
     * @param controller
     */
    public void setController(BeheerderController controller) {
        this.controller = controller;
    }
}
