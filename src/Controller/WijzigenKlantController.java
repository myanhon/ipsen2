package Controller;

import DAO.KlantDAO;
import DAO.KlantTagDAO;
import Model.Klant;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Deze class wordt gebruikt als de controller van de WijzigenKlant view.
 * Hierin staat alle logica die nodig zijn voor de WijzigenKlant View.
 * Created by Shaban , Mike  on 05-10-16.
 */
public class WijzigenKlantController {
    /**
     * Hier wordt  een object van de TextField gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextField txtVoornaam, txtAchternaam, txtAdresHuisnr,
            txtPostcode, txtWoonplaats, txtTelefoon, txtLinkedin, txtEmail;
    /**
     * Hier wordt  een object van de Pane gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private Pane wkPane;
    /**
     * Hier wordt  een object van de DataPicker gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXDatePicker datePicker;
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
     * Hier wordt  een object van de Klant gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Klant klant;
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Hier wordt  een object van de klantDao gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantDAO klantDao;
    /**
     * Hier wordt  een object van de klantenController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantenController klantenController;
    /**
     * Hier wordt  een object van de Melding gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Melding melding;
    /**
     * Hier wordt  een object van de klantTagDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantTagDAO klantTagDAO = new KlantTagDAO();
    /**
     * Hier wordt  een object van de formValidator geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private FormValidator formValidator = new FormValidator();

    /**
     * Zodra deze methode wordt aangeroepen, wordt de een
     * klant toegevoegd.
     */
    public void opslaanKlant() {
        if (validator() == true) {
            klant.setVoornaam(txtVoornaam.getText());
            klant.setAchternaam(txtAchternaam.getText());
            klant.setAdres(txtAdresHuisnr.getText());
            klant.setPostcode(txtPostcode.getText());
            klant.setGeboorteDatum(Date.valueOf(datePicker.getValue()));
            klant.setWoonplaats(txtWoonplaats.getText());
            klant.setTelefoon(txtTelefoon.getText());
            klant.setLinkedIn(txtLinkedin.getText());
            klant.setEmail(txtEmail.getText());
            klantDao.setKlant(klant);
            klantDao.update(klant.getId());
            if (klantDao.getRows() > 0) {
                klantTagDAO.setKlantID(klant.getId());
                klantTagDAO.delete(klant.getId());
                for (JFXCheckBox c : jfxCheckBoxes) {
                    if (c.isSelected()) {
                        klantTagDAO.setTagID(Integer.parseInt(c.getId()));
                        klantTagDAO.insert();
                    }
                }
                melding.showNormalMessage("Succes", "Klant succesvol gewijzigd.", "Het wijzigen van de Klant: " + klant
                        .getVoornaam() + " is gelukt");
                stage.close();
                klantenController.refreshTable();
            } else {
                melding.showWarningMessage("Warning", "Wijzigen van klant is mislukt", klantDao.getErrorBeschrijving());
                klantenController.refreshTable();
            }
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
//        formValidator.dataValidator(datePicker);
        formValidator.hasFormValidator(txtPostcode);
        formValidator.hasFormValidator(txtWoonplaats);
        formValidator.hasFormValidator(txtTelefoon);
        formValidator.emailValidator(txtEmail);
        formValidator.hasFormValidator(txtAdresHuisnr);
        return formValidator.getValid();
    }

    /**
     * Sluit de huidge stage af/view
     */
    public void cancelToevoegenKlant() {
        stage.close();
    }

    /**
     * Zodra deze methode wordt aangeroepen, worden de textFields gezet met de data van de klant
     */
    public void init() {
        txtVoornaam.setText(klant.getVoornaam());
        txtAchternaam.setText(klant.getAchternaam());
        txtAdresHuisnr.setText(klant.getAdres());
        txtPostcode.setText(klant.getPostcode());
        txtWoonplaats.setText(klant.getWoonplaats());
        txtTelefoon.setText(klant.getTelefoon());
        txtEmail.setText(klant.getEmail());
        datePicker.setValue(klant.getGeboorteDatum().toLocalDate());
        txtLinkedin.setText(klant.getLinkedIn());
        klant.setEmail(txtEmail.getText());
        klantTagDAO.setKlant(klant);
        klantTagDAO.select();

    }

    /**
     * Hier worden de tags gevuld
     */
    public void vultags() {
        jfxCheckBoxes = dynamischTags.getTagCheckboxes();
        dynamischTags.vultags(wkPane, klant.getArrTags());
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
     * dat de klant wordt geset.
     *
     * @param klant
     */
    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de klantDao wordt geset.
     *
     * @param klantDao
     */
    public void setKlantDao(KlantDAO klantDao) {
        this.klantDao = klantDao;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de DynamischTags wordt geset.
     *
     * @param dynamischTags
     */
    public void setDynamischTags(DynamischTags dynamischTags) {
        this.dynamischTags = dynamischTags;
        vultags();
    }
}
