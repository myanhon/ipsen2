package Controller;

import DAO.KlantDAO;
import DAO.KlantTagDAO;
import Model.Klant;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Deze class wordt gebruikt als de controller van de ToevoegenKlant view.
 * Hierin staat alle logica die nodig zijn voor de ToevoegenKlant View.
 * Created by Shaban , Mike  on 05-10-16.
 *
 * @author Shaban , Mike, Mohamed EL Baze
 */
public class ToevoegenKlantController {

    /**
     * Hier wordt  een object van de Pane gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private Pane tkPane;
    /**
     * Hier wordt  een object van de TextField gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextField txtVoornaam, txtAchternaam, txtAdresHuisnr, txtPostcode,
            txtWoonplaats, txtTelefoon, txtEmail, txtLinkedin;
    /**
     * Hier wordt  een object van de DataPicker gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXDatePicker datepicker;
    /**
     * Hier wordt  een object van de arraylist checkboxes gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private ArrayList<JFXCheckBox> jfxCheckBoxes;
    /**
     * Hier wordt  een object van de DynamischTags gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private DynamischTags dynamischTags;
    /**
     * Hier wordt  een object van de KlantenController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantenController klantenController;
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Hier wordt  een object van de Melding gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Melding melding;
    /**
     * Hier wordt  een object van de formValidator geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private FormValidator formValidator = new FormValidator();
    /**
     * Hier wordt  een object van de KlantDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantDAO klantDAO = new KlantDAO();
    /**
     * Hier wordt  een object van de klantTagDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantTagDAO klantTagDAO = new KlantTagDAO();
    /**
     * Hier wordt  een object van de Klant geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Klant klant = new Klant();

    /**
     * Zodra deze methode wordt aangeroepen, wordt de een
     * klant toegevoegd.
     */
    public void klantenOpslaan() {
        if (validator() == true) {
            klant.setVoornaam(txtVoornaam.getText());
            klant.setAchternaam(txtAchternaam.getText());
            klant.setAdres(txtAdresHuisnr.getText());
            klant.setPostcode(txtPostcode.getText());
            klant.setGeboorteDatum(Date.valueOf(datepicker.getValue()));
            klant.setWoonplaats(txtWoonplaats.getText());
            klant.setTelefoon(txtTelefoon.getText());
            klant.setLinkedIn(txtLinkedin.getText());
            klant.setEmail(txtEmail.getText());
            klantDAO.setKlant(klant);
            klantDAO.insert();
            if (klantDAO.getRows() > 0) {
                klantTagDAO.setKlantID(klantDAO.getID());
                for (JFXCheckBox c : jfxCheckBoxes) {
                    if (c.isSelected()) {
                        klantTagDAO.setTagID(Integer.parseInt(c.getId()));
                        klantTagDAO.insert();
                    }
                }
                melding.showNormalMessage("Succes", "Klant succesvol toegevoegd.", "Het toevoegen van de Klant: " + klant
                        .getVoornaam() + " is gelukt");
                stage.close();
                klantenController.refreshTable();
            } else {
                melding.showWarningMessage("Warning", "Toevoegen van klant is mislukt", klantDAO.getErrorBeschrijving());
            }
        } else {
            melding.showNormalMessage("Error", "Toevoegen van Klant", "De gegevens zijn niet toegevoegd.");
        }

    }

    /**
     * Zodra deze methode wordt aangeroepen, worden de textFields in een validator gestopt
     * om te checken of ze 1 van ze lege waarde bevat.
     */
    private boolean validator() {
        formValidator.setHasError(true);
        formValidator.hasFormValidator(txtVoornaam);
        formValidator.hasFormValidator(txtAchternaam);
        formValidator.hasFormValidator(txtAdresHuisnr);
        formValidator.hasFormValidator(txtPostcode);
        formValidator.hasFormValidator(txtWoonplaats);
        formValidator.emailValidator(txtEmail);
        formValidator.hasFormValidator(txtTelefoon);
        formValidator.hasFormValidator(txtAdresHuisnr);
        return formValidator.getValid();
    }

    /**
     * Sluit de huidge stage af/view
     */
    public void cancelToevoegenKlanten(ActionEvent actionEvent) {
        this.stage.close();
    }

    /**
     * Hier worden de tags gevuld
     */
    public void vultags() {
        jfxCheckBoxes = dynamischTags.getTagCheckboxes();
        dynamischTags.vultags(tkPane);
    }

    /**
     * Deze methode zorgt ervoor
     * dat de stage wordt geset.
     *
     * @param stage
     */
    public void stage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de klantencontroller wordt geset.
     *
     * @param klantenController
     */
    public void setKlantenController(KlantenController klantenController) {
        this.klantenController = klantenController;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de melding wordt geset.
     *
     * @param melding
     */
    public void setMelding(Melding melding) {
        this.melding = melding;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de DynamischeTags wordt geset.
     *
     * @param dynamischTags
     */
    public void setDynamischTags(DynamischTags dynamischTags) {
        this.dynamischTags = dynamischTags;
        vultags();
    }

}
