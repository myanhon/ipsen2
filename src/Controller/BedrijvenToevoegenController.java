package Controller;

import DAO.BedrijfDAO;
import DAO.BedrijfTagDAO;
import Model.Bedrijf;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * De BedrijfToevoegenController is de controller voor toevoegen bedrijf scherm.
 *
 * @author Mohamed El Baze
 * @version 0.1
 */
public class BedrijvenToevoegenController {
    /**
     * Hier wordt een BedrijfDAO object aangemaakt.
     * hiermee kan het class communiceren met het Bedrijf tabel.
     */
    private BedrijfDAO bedrijfDAO = new BedrijfDAO();
    /**
     * Hier wordt  een object van de Bedrijf model geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Bedrijf bedrijf = new Bedrijf();
    /**
     * Hier worden de dynamische checkboxen voor de tags begehouden.
     */
    private ArrayList<JFXCheckBox> jfxCheckBoxes;
    /**
     * Dit is de model die gevens van de dynamische tags bijhoud.
     */
    private DynamischTags dynamischTags;
    /**
     * Op deze pane worden de tags geplaats.
     */
    @FXML
    private Pane tbPane;
    /**
     * Hier worden objecten van de Textfield gedeclareerd.
     * De invoer text van deze objecten worden gebruikt voor invoeringen van gegevens in formulier te definiëren.
     */
    @FXML
    private JFXTextField txtId, txtBedrijfsnaam, txtAdres,
            txtPostcode,txtWoonplaats,txtPlaats,txtContactpersoon,
            txtTelefoon,txtEmail;
    /**
     * Deze objecten defineren de opslaan en afsluiten buttons van de bedrijf toevoegen gegevens formulier
     */
    @FXML
    private JFXButton btnOpslaan,btnCancel;
    /**
     * Hier wordt de een object van de BedrijfViewcontroller gedeclareerd.
     * De object wordt gebruikt om de tabel te refreshen nadat een nieuw bedrijf object is toegevoegd.
     */
    private BedrijvenViewController viewController;

    /**
     * Hier wordt een FormValidator object aangemaakt.
     * hiermee valideren we of de waarde in de textfield voldoen aan een paar eisen.
     */
    private FormValidator formValidator = new FormValidator();
    /**
     * Hier wordt een Melding object aangemaakt.
     * hiermee kunnen we een meldingen weergeven.
     */
    private Melding melding = new Melding();
    /**
     * Hier wordt  een object van de bedrijfDAO geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BedrijfTagDAO bedrijfTagDAO = new BedrijfTagDAO();
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Deze methode is verantwoordelijk voor het opslaan van de gegevens in de textfield in een Tag model. dit model
     * door te geven aan de bedrijfDOA en de insert methode aanteroepen. Deze methode weergeeft een melding.
     */
    public void opslaanBedrijven() {
        if (validator() == true) {
            bedrijf.setBedrijfsnaam(txtBedrijfsnaam.getText());
            bedrijf.setAdres(txtAdres.getText());
            bedrijf.setPostcode(txtPostcode.getText());
            bedrijf.setWoonplaats(txtWoonplaats.getText());
            bedrijf.setPlaats(txtPlaats.getText());
            bedrijf.setContactpersoon(txtContactpersoon.getText());
            bedrijf.setTelefoon(txtTelefoon.getText());
            bedrijf.setEmail(txtEmail.getText());
            bedrijfDAO.setBedrijf(bedrijf);
            bedrijfDAO.insert();
            bedrijfTagDAO.setBedrijfID(bedrijfDAO.getID());
            for (JFXCheckBox c : jfxCheckBoxes) {
                if (c.isSelected()) {
                    bedrijfTagDAO.setTagID(Integer.parseInt(c.getId()));
                    bedrijfTagDAO.insert();
                }
            }
            if(bedrijfDAO.getRows() > 0){
                melding.showNormalMessage("Succes","Bedrijf succesvol toegevoegd.","Het toevoegen van de bedrijf: " +
                        ""+bedrijf.getBedrijfsnaam()+" is gelukt");
                cancelToevoegenBedrijven();
            }else{
                melding.showWarningMessage("Warning","Toevoegen van Bedrijf is mislukt", bedrijfDAO.getErrorBeschrijving());
            }
        } else {
            melding.showNormalMessage("Warning!", "Action failed", "Opslaan is mislukt!");
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het afsluiten van de view.
     */
    public void cancelToevoegenBedrijven() {
        viewController.refreshTable();
        stage.close();
    }

    /**
     * Deze methode is verantwoordelijk voor valideren van de ingevoerde gegevens.
     */
    public boolean validator() {
        formValidator.setHasError(true);
        formValidator.hasFormValidator(txtBedrijfsnaam);
        formValidator.hasFormValidator(txtAdres);
        formValidator.hasFormValidator(txtPostcode);
        formValidator.hasFormValidator(txtWoonplaats);
        formValidator.hasFormValidator(txtPlaats);
        formValidator.hasFormValidator(txtContactpersoon);
        formValidator.hasFormValidator(txtTelefoon);
        formValidator.emailValidator(txtEmail);
        return formValidator.getValid();
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de controller in BedrijvenViewController viewController.
     *
     * @param viewController controller object van de bedrijfview.
     */
    public void setViewController(BedrijvenViewController viewController) {
        this.viewController = viewController;
    }

    /**
     * Hier wordt de DynamischTags obeject gevuld. Deze methode wordt aangeroepen door de hoofdview, zodat de het
     * toevoegen en wijzigen scherm gevuld kunnen worden met tags.
     *
     * @param dynamischTags
     */
    public void setDynamischTags(DynamischTags dynamischTags) {
        this.dynamischTags = dynamischTags;
        vultags();
    }

    /**
     * Deze methode is verantwoordelijk voor vullen van Tags in gegevens formulier.
     */
    public void vultags() {
        jfxCheckBoxes = dynamischTags.getTagCheckboxes();
        dynamischTags.vultags(tbPane);
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van een stage object in de Stage stage.
     * @param stage het stage object van Bedrijfview.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}



