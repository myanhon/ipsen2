package Controller;

import DAO.NotitieDAO;
import Model.Beheerder;
import Model.Klant;
import Model.Notitie;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Deze class wordt gebruikt als de controller van de WijzigenNotitie view.
 * Hierin staat alle logica die nodig zijn voor de WijzigenNotitie View.
 * Created by Shaban , Mike , Mo  on 05-10-16.
 */
public class WijzigenNotitieController {
    /**
     * Hier wordt  een object van de TextField gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextField txtOnderwerp;
    /**
     * Hier wordt  een object van de JFXComboBox gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXComboBox txtPersoon;
    @FXML
    private JFXComboBox txtBedrijf;
    /**
     * Hier wordt  een object van de TextArea gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextArea txtOmschrijving;
    /**
     * Hier wordt  een object van de Notitie gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Notitie notitie;
    /**
     * Hier wordt  een object van de Klant gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Klant klant;
    /**
     * Hier wordt  een object van de NotitieDAO gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieDAO notitieDAO;
    /**
     * Hier wordt  een object van de Beheerder gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Beheerder beheerder;
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
     * Hier wordt  een object van de NotitieController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieController notitieController;
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
     * Zodra deze methode wordt aangeroepen, worden de textFields gezet met de data van de notitie
     */
    public void init() {
        autoComplete();
        txtOnderwerp.setText(notitie.getTitel());
        txtOmschrijving.setText(notitie.getBeschrijving());
        txtPersoon.setValue(notitie.getKlantNaam());
        txtBedrijf.setValue(notitie.getBedrijfNaam());
        txtPersoon.getJFXEditor().setId(String.valueOf(notitie.getKlantID()));
        txtBedrijf.getJFXEditor().setId(String.valueOf(notitie.getBedrijfID()));
    }

    /**
     * Zodra deze methode wordt aangeroepen, wordt de een
     * notitie toegevoegd.
     */
    public void opslaanNotitie() {
        if (validator() == true) {
            notitie.setTitel(txtOnderwerp.getText());
            notitie.setBeschrijving(txtOmschrijving.getText());
            notitie.setBedrijfID(Integer.parseInt(txtBedrijf.getJFXEditor().getId()));
            notitie.setKlantID(Integer.parseInt(txtPersoon.getJFXEditor().getId()));
            notitie.setGebruikerID(beheerder.getId());
            notitieDAO.setNotitie(notitie);
            notitieDAO.update(notitie.getId());
            if (notitieDAO.getRows() > 0) {
                melding.showNormalMessage("Succes", "Notitie succesvol gewijzigd.", "Het wijzigen van de notitie " +
                        notitie.getTitel() + " is gelukt");
                stage.close();
                notitieController.refreshTable();
            } else {
                melding.showWarningMessage("Warning", "Wijzigen van notitie is mislukt",
                        notitieDAO.getErrorBeschrijving());
            }
        } else {
            melding.showNormalMessage("Warning", "Lege waardes", "Een of meerdere velden zijn niet ingevuld");
        }
    }

    /**
     * Zodra deze methode wordt aangeroepen, worden de textFields in een validator gestopt
     * om te checken of ze 1 van ze lege waarde bevat.
     */
    private boolean validator() {
        formValidator.setHasError(true);
        formValidator.hasFormValidator((JFXTextField) txtPersoon.getJFXEditor());
        formValidator.hasFormValidator((JFXTextField) txtBedrijf.getJFXEditor());
        formValidator.hasFormValidator(txtOnderwerp);
        return formValidator.getValid();
    }

    /**
     *Tijdens het typen van een naam , wordt er een lijst getoond met data
     */
    public void autoComplete() {
        autoComplete.getAutoCompleteKlant(txtPersoon);
        autoComplete.getAutoCompleteBedrijf(txtBedrijf);
    }

    /**
     * Sluit de huidge stage af/view
     */
    public void cancelNotitie() {
        stage.close();
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
     * dat de stage wordt geset.
     *
     * @param stage
     */
    public void stage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de notitie wordt geset.
     *
     * @param notitie
     */
    public void setNotitie(Notitie notitie) {
        this.notitie = notitie;
    }

    /**
     * Deze methode zorgt ervoor
     * dat de notitieDao wordt geset.
     *
     * @param notitieDAO
     */
    public void setNotitieDAO(NotitieDAO notitieDAO) {
        this.notitieDAO = notitieDAO;
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
     * dat de beheerder wordt geset.
     *
     * @param beheerder
     */
    public void setBeheerder(Beheerder beheerder) {
        this.beheerder = beheerder;
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
}
