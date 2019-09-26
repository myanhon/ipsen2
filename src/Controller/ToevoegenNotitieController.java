package Controller;

import DAO.NotitieDAO;
import Model.Beheerder;
import Model.Notitie;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Deze class wordt gebruikt als de controller van de ToevoegenNotitie view.
 * Hierin staat alle logica die nodig zijn voor de ToevoegenNotitie View.
 * @author Mohamed El Baze
 * @version 0.1
 * @date 10/05/16
 */
public class ToevoegenNotitieController {

    /**
     * Hier wordt  een object van de NotitieDAO gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieDAO notitieDAO = new NotitieDAO();
    /**
     * Hier wordt  een object van de JFXComboBox gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXComboBox txtPersoon,txtBedrijf;
    /**
     * Hier wordt  een object van de TextField gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextField txtOnderwerp;
    /**
     * Hier wordt  een object van de TextArea gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextArea txtOmschrijving;
    /**
     * Hier wordt  een object van de FormValidator geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private FormValidator formValidator = new FormValidator();
    /**
     * Hier wordt  een object van de ComboBoxAutoComplete geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private static ComboBoxAutoComplete autoComplete = new ComboBoxAutoComplete();
    /**
     * Hier wordt  een object van de NotitieController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieController notitieController;
    /**
     * Hier wordt  een object van de Melding gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Melding melding;
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Hier wordt  een object van de Beheerder gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Beheerder beheerder;

    /**
     * Zodra deze methode wordt aangeroepen, wordt de een
     * notitie toegevoegd.
     */
    public void opslaanNotitie() {
        if(validator() == true) {
            Notitie notitie = new Notitie();
            notitie.setTitel(txtOnderwerp.getText());
            notitie.setBeschrijving(txtOmschrijving.getText());
            notitie.setGebruikerID(beheerder.getId());
            notitie.setKlantID(Integer.parseInt(txtPersoon.getJFXEditor().getId()));
            notitie.setBedrijfID(Integer.parseInt(txtBedrijf.getJFXEditor().getId()));
            notitie.setTitel(txtOnderwerp.getText());
            notitie.setBeschrijving(txtOmschrijving.getText());
            notitieDAO.setNotitie(notitie);
            notitieDAO.insert();
            if(notitieDAO.getRows() > 0){
                melding.showNormalMessage("Succes","Notitie succesvol toegevoegd.","Het toevoegen van de Notitie: " +
                        ""+notitie.getTitel()+" is gelukt");
                notitieController.refreshTable();
                stage.close();
            }else{
                melding.showWarningMessage("Warning","Toevoegen van Notitie is mislukt", notitieDAO.getErrorBeschrijving());
            }
        }else{
            melding.showNormalMessage("Warning","Lege waardes", "Een of meerdere velden zijn niet ingevuld");
        }
    }

    /**
     * Zodra deze methode wordt aangeroepen, worden de textFields in een validator gestopt
     * om te checken of ze 1 van ze lege waarde bevat.
     */
    private boolean validator(){
        formValidator.setHasError(true);
        formValidator.hasFormValidator((JFXTextField) txtPersoon.getJFXEditor());
        formValidator.hasFormValidator((JFXTextField) txtBedrijf.getJFXEditor());
        formValidator.hasFormValidator(txtOnderwerp);
        return formValidator.getValid();
    }

    /**
     * Sluit de huidge stage af/view
     */
    public void cancelNotitie() {
        this.stage.close();
    }

    /**
     *Tijdens het typen van een naam , wordt er een lijst getoond met data
     */
    public void autoComplete() {
        autoComplete.getAutoCompleteKlant(txtPersoon);
        autoComplete.getAutoCompleteBedrijf(txtBedrijf);
    }

    /**
     * Deze methode zorgt ervoor
     * dat de melding wordt geset.
     * @param melding
     */
    public void setMelding(Melding melding) {
        this.melding = melding;
    }
    /**
     * Deze methode zorgt ervoor
     * dat de stage wordt geset.
     *
     * @param stage
     */
    public void Stage(Stage stage){
        this.stage = stage;
    }
    /**
     * Deze methode zorgt ervoor
     * dat de notitieController wordt geset.
     *
     * @param notitieController
     */
    public void setNotitieController(NotitieController notitieController) {
        this.notitieController = notitieController;
    }
    /**
     * Deze methode zorgt ervoor
     * dat de beheerder wordt geset.
     *
     * @param beheerder
     */
    public void setBeheerder(Beheerder beheerder) {
        this.beheerder = beheerder;
    }
}
