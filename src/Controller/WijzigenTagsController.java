package Controller;

import DAO.TagDAO;
import Model.Tag;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * De WijzigenTagsController is de controller voor bewerken tags scherm.
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class WijzigenTagsController {

    /**
     * Hier wordt de een object van de Textfield gedeclareerd.
     * De invoer text van dit object wordt gebruikt als Tag naam te definiëren
     */
    @FXML
    private JFXTextField txtTag;

    /**
     * Hier wordt de een object van de TextArea gedeclareerd.
     * De invoer text van dit object wordt gebruikt als Tag beschrijving te definiëren.
     */
    @FXML
    private JFXTextArea txtBeschrijving;

    /**
     * Hier wordt de een object van de Tagcontroller gedeclareerd.
     * De object wordt gebruikt om de tabel te refreshen nadat een nieuw tagobject is toegevoegd.
     */
    private TagController tagController;

    /**
     * Hier wordt een TagDAO object aangemaakt.
     * hiermee kan het class communiceren met het Tag tabel.
     */
    private TagDAO tagDAO = new TagDAO();

    /**
     * Hier wordt een Melding object aangemaakt.
     * hiermee kunnen we een meldingen weergeven.
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt een Stage object aangemaakt.
     * hiermee kan we het scherm afsluiten.
     */
    private Stage stage;

    /**
     * Hier wordt een FormValidator object aangemaakt.
     * hiermee valideren we of de waarde in de textfield voldoen aan een paar eisen.
     */
    private FormValidator formValidator = new FormValidator();

    /**
     * Hier wordt een Tag object aangemaakt.
     * Hierin wordt de geslecteerde tag model uit de tabelview gezet.
     */
    private Tag tag;

    /**
     * Deze methode is verantwoordelijk zetten van de juiste waardes in de textfields.
     */
    public void init(){
        txtTag.setText(tag.getNaam());
        txtBeschrijving.setText(tag.getBeschrijving());
    }

    /**
     * Deze methode is verantwoordelijk voor het opslaan van de gegevens in de textfield in een Tag model.
     * dit model door te geven aan de tagDOA en de insert methode aanteroepen. Deze methode weergeeft een melding.
     */
    public void opslaanTag(){
        if(validator() == true) {
            tag.setNaam(txtTag.getText());
            tag.setBeschrijving(txtBeschrijving.getText());
            tagDAO.setTag(tag);
            tagDAO.update(tag.getId());
            if(tagDAO.getRows() > 0) {
                melding.showNormalMessage("Succes", "Tag succesvol gewijzigd.", "Het wijzigen van de Tag " + tag
                        .getNaam() + " is gelukt");
                stage.close();
                tagController.refreshTable();
            }else {
                melding.showWarningMessage("Warning", "Wijzigen van Tag is mislukt", tagDAO.getErrorBeschrijving());
            }
        }else{
            melding.showNormalMessage("Warning","Lege waardes", "Een of meerdere velden zijn niet ingevuld");
        }
    }

    /**
     * Deze methode is verantwoordelijk voor valideren van de text ingevoerd in de textfields.
     */
    private boolean validator(){
        formValidator.setHasError(true);
        formValidator.hasFormValidator(txtTag);
        return formValidator.getValid();
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de geslecteerde tag object in Tag tag.
     * @param tag geselecteerde tag model van de tableview in Tagview.
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van een stage object in de Stage stage.
     * @param stage het stage object van Tagview.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Deze methode is verantwoordelijk voor het afsluiten van de view.
     */
    public void cancelTag(){
        tagController.refreshTable();
        stage.close();
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de controller in TagController tagController.
     * @param tagController controller object van de tagview.
     */
    public void setController(TagController tagController){
        this.tagController = tagController;
    }
}
