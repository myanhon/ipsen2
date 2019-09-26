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
 * De BedrijfWijzigenController is de controller voor wijzigen bedrijf scherm.
 *
 * @author Mohamed El Baze
 * @version 0.1
 * @date 10/15/16
 */
public class BedrijvenWijzigenController {
    /**
     * Hier wordt een BedrijfDAO object aangemaakt.
     * hiermee kan het class communiceren met het Bedrijf tabel.
     */
    private BedrijfDAO bedrijfDAO = new BedrijfDAO();
    /**
     * Hier wordt  een object van de Bedrijf model geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Bedrijf bedrijf;
    /**
     * Hier worden objecten van de Textfield gedeclareerd.
     * De invoer text van deze objecten worden gebruikt voor invoeringen van gegevens in formulier te definiëren.
     */
    @FXML
    private JFXTextField txtId, txtBedrijfsnaam,txtAdres,txtPostcode,txtWoonplaats,txtPlaats,txtContactpersoon,txtTelefoon,txtEmail;
    /**
     * Deze objecten defineren de opslaan en afsluiten buttons van de bedrijf toevoegen gegevens formulier
     */
    @FXML
    private JFXButton btnOpslaan, btnCancel;

    @FXML
    private JFXCheckBox ckRolstoel, cbSpasme, cbKleurenblind;
    /**
     * Op deze pane worden de tags geplaats.
     */
    @FXML
    private Pane wbPane;
    /**
     * Hier wordt de een object van de BedrijfViewcontroller gedeclareerd.
     * De object wordt gebruikt om de tabel te refreshen nadat een nieuw tagobject is toegevoegd.
     */
    private BedrijvenViewController controller;
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
     * Hier worden de denamische checkboxen voor de tags begehouden.
     */
    private ArrayList<JFXCheckBox> jfxCheckBoxes;
    /**
     * Dit is de model die gevens van de dynamische tags bijhoud.
     */
    private DynamischTags dynamischTags;
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Hier wordt  een object van de bedrijfDAO geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BedrijfTagDAO bedrijfTagDAO;

    /**
     * Deze methode is verantwoordelijk voor het opslaan van de gegevens in de textfield in een Tag model. dit model
     * door te geven aan de bedrijfDOA en de insert methode aanteroepen. Deze methode weergeeft een melding.
     */
    public void opslaanBedrijven() {
        if (validator() == true) {
            bedrijf.setId(Integer.valueOf(txtId.getText()));
            bedrijf.setBedrijfsnaam(txtBedrijfsnaam.getText());
            bedrijf.setAdres(txtAdres.getText());
            bedrijf.setPostcode(txtPostcode.getText());
            bedrijf.setWoonplaats(txtWoonplaats.getText());
            bedrijf.setPlaats(txtPlaats.getText());
            bedrijf.setContactpersoon(txtContactpersoon.getText());
            bedrijf.setTelefoon(txtTelefoon.getText());
            bedrijf.setEmail(txtEmail.getText());
            bedrijfDAO.setBedrijf(bedrijf);
            bedrijfDAO.update(bedrijf.getId());
            controller.refreshTable();
            bedrijfTagDAO.setBedrijfID(bedrijf.getId());
            bedrijfTagDAO.delete(bedrijf.getId());
            for (JFXCheckBox c: jfxCheckBoxes){
                if(c.isSelected()){
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
            melding.showNormalMessage("Warning!","Action failed","Opslaan is mislukt!");
        }
    }
    /**
     * Deze methode is verantwoordelijk voor het afsluiten van de view.
     */
    public void cancelToevoegenBedrijven() {
        controller.refreshTable();
        stage.close();
    }
    /**
     * Deze methode is verantwoordelijk voor valideren van de ingevoerde gegevens.
     */
    public boolean validator() {
        formValidator.setHasError(true);
        formValidator.hasFormValidator(txtId);
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
     * Deze methode is verantwoordelijk vullen van de juiste waardes in de gegevensformulier.
     */
    public void fillData(){
            txtId.setText(String.valueOf(bedrijf.getId()));
            txtBedrijfsnaam.setText(bedrijf.getBedrijfsnaam().trim());
            txtAdres.setText(bedrijf.getAdres().trim());
            txtPostcode.setText(bedrijf.getPostcode().trim());
            txtWoonplaats.setText(bedrijf.getWoonplaats().trim());
            txtPlaats.setText(bedrijf.getPlaats().trim());
            txtContactpersoon.setText(bedrijf.getContactpersoon().trim());
            txtTelefoon.setText(bedrijf.getTelefoon().trim());
            txtEmail.setText(bedrijf.getEmail().trim());
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
    public void vultags(){
        jfxCheckBoxes = dynamischTags.getTagCheckboxes();
        dynamischTags.vultags(wbPane,bedrijf.getArrTags());
    }
    public void setBedrijf(Bedrijf bedrijf){
        this.bedrijf = bedrijf;
    }
    /**
     * Deze methode is verantwoordelijk voor het zetten van de controller in BedrijvenViewController viewController.
     *
     * @param controller controller object van de bedrijfview.
     */
    public void setViewController(BedrijvenViewController controller) {
        this.controller = controller;
    }
    /**
     * Hier wordt een object van de bedrijftagdao gezet.
     * @param bedrijfTagDAO
     */
    public void setBedrijfTagDAO(BedrijfTagDAO bedrijfTagDAO) {
        this.bedrijfTagDAO = bedrijfTagDAO;
    }
    /**
     * Deze methode is verantwoordelijk voor het zetten van een stage object in de Stage stage.
     * @param stage het stage object van Bedrijfview.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
