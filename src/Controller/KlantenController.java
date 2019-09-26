package Controller;

import DAO.KlantDAO;
import DAO.NotitieDAO;
import DAO.TagDAO;
import Model.Klant;
import Model.Tag;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;


/**
 * Deze class wordt gebruikt als de controller van de userView.
 * Hierin staat alle logica die nodig zijn voor de userView.
 * Created by Mike on 05-10-16.
 */
public class KlantenController implements Initializable {
    /**
     * Hier wordt  een object van de Label gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt om de
     * naam van de beheerder te setten.
     */
    @FXML
    private Label lblUsername;
    /**
     * Hier wordt  een object van de TableView gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableView<Klant> tableView;
    /**
     * Hier wordt  een object van de TableColumn gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?, ?> voornaamCol, achternaamCol, adresCol,
            postcodeCol, woonplaatsCol, geboorteDatum, telefoonCol, linkedInCol,emailCol;
    /**
     * Hier wordt  een object van de TextField gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TextField txtSearch;
    /**
     * Hier wordt  een object van de ComboBox gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXComboBox cbWoonplaats, cbTag;
    /**
     * Hier wordt de een object van de ObservableList<Klant> gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt.
     */
    private ObservableList<Klant> klantenObservers;
    /**
     * Hier wordt  een object van de klantDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantDAO klantDAO = new KlantDAO();
    /**
     * Hier wordt  een object van de Melding geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Melding melding = new Melding();
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Hier wordt  een object van de klantDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieDAO notitieDAO = new NotitieDAO();
    /**
     * Hier wordt  een object van de css gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private String css;
    /**
     * Hier wordt  een object van de tagDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private TagDAO tagDAO = new TagDAO();

    /**
     * Hier wordt de DynamischTags object aangemaakt.
     * Zodat deze in een methode kan worden gebruikt
     */
    private DynamischTags dynamischTags;
    private Stage hoofdStage;

    /**
     * Deze methode wordt aangeroepen door de FXML file.
     * Deze opent de toevoegenKlanten.
     */
    public void toevoegenKlanten() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/toevoegenKlanten.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ToevoegenKlantController toevoegenKlantController = fxmlLoader.getController();
            toevoegenKlantController.setKlantenController(this);
            toevoegenKlantController.setMelding(melding);
            Stage stage = new Stage();
            toevoegenKlantController.stage(stage);
            toevoegenKlantController.setDynamischTags(dynamischTags);
            stage.setTitle("Toevoegen Klant Gegevens");
            Scene scene = new Scene(root1);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deze methode wordt aangeroepen door de FXML file.
     * Deze opent de toevoegenKlanten.
     */
    public void showKlantGegevens() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/klantenView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                KlantenViewController klantenViewController = fxmlLoader.getController();
                klantenViewController.setMelding(melding);
                klantenViewController.setNotitieDao(notitieDAO);
                Klant klant = tableView.getSelectionModel().getSelectedItem();
                klantenViewController.setKlant(klant);
                Stage stage = new Stage();
                stage.setTitle("View Klant");
                Scene scene = new Scene(root1);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            melding.showNormalMessage("Fout!", "Wijzigen Klant", "Selecteer een klant");
        }

    }

    /**
     * Deze methode wordt aangeroepen door de FXML file.
     * Deze opent de wijzigenKlantView.
     */
    public void wijzigKlantgegevens() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            melding.showWarningMessage("Warning!", "Wijzigen klant", "Weet u het zeker?");
            if (melding.isVoorActie() == true) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/wijzigenKlantView.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    WijzigenKlantController wijzigenKlantController = fxmlLoader.getController();
                    wijzigenKlantController.setKlant(tableView.getSelectionModel().getSelectedItem());
                    wijzigenKlantController.setKlantenController(this);
                    wijzigenKlantController.setMelding(melding);
                    wijzigenKlantController.setKlantDao(klantDAO);
                    wijzigenKlantController.init();
                    wijzigenKlantController.setDynamischTags(dynamischTags);
                    Stage stage = new Stage();
                    wijzigenKlantController.stage(stage);
                    stage.setTitle("Wijzigen Klant");
                    Scene scene = new Scene(root1);
                    scene.getStylesheets().add(css);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else melding.showNormalMessage("Warning!", "Wijzigen klant", "Selecteer een klant");
    }

    /**
     * Deze methode wordt aangeroepen door de FXML file.
     * Zodra deze methode wordt aangeroepen, wordt de geselecteerde
     * klant verwijderd.
     */
    public void verwijderKlant() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            melding.showWarningMessage("Warning!", "Verwijderen klant", "Weet u het zeker?");
            if (melding.isVoorActie() == true) {
                Klant klant = tableView.getSelectionModel().getSelectedItem();
                klantDAO.delete(klant.getId());
                melding.showNormalMessage("Succesvolle actie", "Verwijderen Klant",
                        "De gegevens zijn verwijderd. Dit scherm zal worden afgesloten");
                refreshTable();
            }
        } else {
            melding.showNormalMessage("Warning!", "Verwijderen klant", "Selecteer een klant");
        }
    }

    /**
     * Deze methode cleart de observer lijst daarna vult wordt de tableView gevuld
     */
    public void refreshTable() {
        klantenObservers.clear();
        fillTable();
    }

    /**
     * Deze initialize methode wordt direct uitgevoerd.
     * In deze methode wordt de column namen gezet en wordt
     * de filltabel methode aangeroepen.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        voornaamCol.setCellValueFactory(new PropertyValueFactory<>("Voornaam"));
        achternaamCol.setCellValueFactory(new PropertyValueFactory<>("Achternaam"));
        adresCol.setCellValueFactory(new PropertyValueFactory<>("Adres"));
        postcodeCol.setCellValueFactory(new PropertyValueFactory<>("Postcode"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        woonplaatsCol.setCellValueFactory(new PropertyValueFactory<>("Woonplaats"));
        geboorteDatum.setCellValueFactory(new PropertyValueFactory<>("GeboorteDatum"));
        telefoonCol.setCellValueFactory(new PropertyValueFactory<>("Telefoon"));
        linkedInCol.setCellValueFactory(new PropertyValueFactory<>("LinkedIn"));
        fillTable();
    }

    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt
     * met alle klanten.
     */
    public void fillTable() {
        tableView.setPlaceholder(new Label("Er zijn geen klanten gevonden."));
        klantDAO.select();
        klantenObservers = klantDAO.getObserversKlanten();
        tableView.setItems(klantenObservers);
    }

    /**
     * Deze methode zorgt ervoor dat de
     * klantenView wordt afgesloten.
     */
    public void Home() {
        this.stage.close();
        getHoofdStage().show();
    }

    /**
     * Deze methode zorgt ervoor
     * dat de stage wordt geset.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Hier wordt de DynamischTags obeject gevuld.
     * Deze methode wordt aangeroepen door de hoofdview,
     * zodat de het toevoegen en wijzigen scherm gevuld kunnen worden met tags.
     */
    public void setDynamischTags(DynamischTags dynamischTags) {
        this.dynamischTags = dynamischTags;
    }

    /**
     * Deze methode zorgt ervoor dat een klant kan worden gezocht.
     */
    public void searchKlant() {
        FilteredList<Klant> filteredList = new FilteredList<Klant>(klantenObservers, e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Klant>) k -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (k.getVoornaam().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (k.getAchternaam().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Klant> sortedList = new SortedList<Klant>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    /**
     * Deze methode zorgt ervoor
     * dat de css wordt geset.
     *
     * @param css
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * Deze methode zorgt ervoor dat een tag kan worden gefilterd.
     */
    public void filterTag() {
        tagDAO.getObserversTags().clear();
        tagDAO.select();
        cbTag.getItems().clear();
        for (Tag tag : tagDAO.getObserversTags()) {
            cbTag.getItems().add(tag.getNaam());
        }
        cbTag.valueProperty().addListener(e -> {
            klantDAO.filterTag(String.valueOf(cbTag.getSelectionModel().getSelectedItem()));
        });
        klantenObservers.clear();
        klantenObservers = klantDAO.getObserversKlanten();
        tableView.setItems(klantenObservers);
    }

    /**
     * Deze methode zorgt ervoor dat een woonplaats kan worden gefilterd.
     */
    public void filterWoonplaats() {
        klantenObservers.clear();
        klantDAO.select();
        cbWoonplaats.getItems().clear();
        for (Klant klant : klantenObservers) {
            cbWoonplaats.getItems().add(klant.getWoonplaats());
        }
        FilteredList<Klant> filteredList = new FilteredList<Klant>(klantenObservers, e -> true);
        cbWoonplaats.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Klant>) k -> {
                if (newValue == null || newValue.toString().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toString().toLowerCase();
                if (k.getWoonplaats().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Klant> sortedList = new SortedList<Klant>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });

    }

    /**
     * Deze methode roept de refreshtable, filtertag en filtewoonplaats op
     */
    public void clearFilter() {
        refreshTable();
        filterTag();
        filterWoonplaats();
    }

    /**
     * Deze methode zorgt ervoor
     * dat de css wordt geset.
     */
    public void setBeheerderNaam(String beheerderNaam) {
        lblUsername.setText(beheerderNaam);

    }

    public Stage getHoofdStage() {
        return hoofdStage;
    }

    public void setHoofdStage(Stage hoofdStage) {
        this.hoofdStage = hoofdStage;
    }
}

