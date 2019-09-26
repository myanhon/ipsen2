package Controller;

import DAO.BedrijfDAO;
import DAO.BedrijfTagDAO;
import DAO.TagDAO;
import Model.Bedrijf;
import Model.Tag;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * De BedrijfViewController is de controller voor het weergeven van bedrijfsgegevens scherm.
 *
 * @author Mohamed El Baze
 * @version 0.1
 */
public class BedrijvenViewController implements Initializable {
    /**
     * Hier wordt de DynamischTags object aangemaakt.
     * Zodat deze in een methode kan worden gebruikt
     */
    private DynamischTags dynamischTags;
    /**
     * Hier wordt  een object van de bedrijfDAO geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BedrijfDAO bedrijfDAO = new BedrijfDAO();
    /**
     * Hier wordt  een object van de bedrijfTagsDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BedrijfTagDAO bedrijfTagDAO = new BedrijfTagDAO();
    /**
     * Hier wordt  een object van de klantDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private TagDAO tagDAO = new TagDAO();
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
    private JFXComboBox cbTag,cbPlaats;
    /**
     * Hier wordt  een object van de Buttons gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXButton btnWijzig, btnVerwijder,btnVoegBedrijfToe,btnViewBedrijven;
    /**
     * Hier wordt  een object van de ImageView gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private ImageView btnHome;
    /**
     * Hier wordt  een object van de TableView gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    public TableView<Bedrijf> tableView;
    /**
     * Hier wordt  een object van de TableColumn gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?, ?> clmBedrijfId,clmBedrijfNaam,clmBedrijfAdres,clmBedrijfPostcode,
            clmBedrijfWoonplaats,clmBedrijfPlaats,clmBedrijfContactpersoon,clmBedrijfTelefoon,clmBedrijfEmail;
    /**
     * Hier worden alle bedrijven bijgehouden voor het vullen van de tabel.
     */
    private ObservableList<Bedrijf> bedrijvenObservers = FXCollections.observableArrayList();;
    /**
     * Hier wordt  een object van de Stage gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Stage stage;
    /**
     * Hier wordt  een object van de Melding geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Melding melding = new Melding();
    /**
     * Hier wordt  een object van de Label gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt om de
     * naam van de beheerder te setten.
     */
    @FXML
    private Label lblUsername;
    /**
     * Hier wordt  een object van de css gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private String css;
    private Stage hoofdStage;

    /**
     * Deze methode wordt aangeroepen als de controller is aangemaakt.
     * de methode is verantwoordelijk voor het koppelen van de tabel en column met de verwijzing naar hun waardes.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmBedrijfNaam.setCellValueFactory(new PropertyValueFactory<>("Bedrijfsnaam"));
        clmBedrijfAdres.setCellValueFactory(new PropertyValueFactory<>("Adres"));
        clmBedrijfPostcode.setCellValueFactory(new PropertyValueFactory<>("Postcode"));
        clmBedrijfWoonplaats.setCellValueFactory(new PropertyValueFactory<>("Woonplaats"));
        clmBedrijfPlaats.setCellValueFactory(new PropertyValueFactory<>("Plaats"));
        clmBedrijfContactpersoon.setCellValueFactory(new PropertyValueFactory<>("Contactpersoon"));
        clmBedrijfTelefoon.setCellValueFactory(new PropertyValueFactory<>("Telefoon"));
        clmBedrijfEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        fillTable();
    }

    /**
     * Deze methode is verantwoordelijk voor het opstarten van de Toevoegen Bedrijf scherm.
     */
    public void toevoegenBedrijven(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/toevoegenBedrijven.fxml"));
            Parent root1 = fxmlLoader.load();
            BedrijvenToevoegenController controller = fxmlLoader.getController();
            controller.setViewController(this);
            controller.setDynamischTags(dynamischTags);
            Stage stage = new Stage();
            controller.setStage(stage);
            stage.setTitle("Toevoegen bedrijven");
            Scene scene = new Scene(root1);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het verwijderen van een Bedrijf.
     */
    public void verwijderBedrijven() {
        if(tableView.getSelectionModel().getSelectedItem() == null ){
            melding.showNormalMessage("Fout!","Geen bedrijf geselecteerd","U moet eerst een bedrijf selecteren als u deze " +
                    "wilt verwijderen");
        }else {
            melding.showWarningMessage("Waarschuwing!", "Verwijderen bedrijf", "Weet u het zeker dat u de volgende bedrijf wilt " +
                    "verwijderen \n Bedrijfsnaam: " + tableView.getSelectionModel().getSelectedItem().getBedrijfsnaam());
                if (melding.isVoorActie()) {
                    Bedrijf bedrijf = tableView.getSelectionModel().getSelectedItem();
                    bedrijfDAO.delete(bedrijf.getId());
                    if (bedrijfDAO.getRows() > 0) {
                        refreshTable();
                        melding.showNormalMessage("Succes", "Bedrijf is succesvol verwijdert.", "Het verwijderen van de Bedrijf: " +
                                bedrijf.getBedrijfsnaam() + " is gelukt");
                    } else {
                        melding.showWarningMessage("Warning", "verwijderen van Bedrijf is mislukt", bedrijfDAO
                                .getErrorBeschrijving());
                    }
                }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het opstarten van de Bewerken Bedrijven scherm.
     */
    public void wijzigBedrijven() {
        if(tableView.getSelectionModel().getSelectedItem() == null ){
            melding.showNormalMessage("Fout!","Geen bedrijf geselecteerd","U moet eerst een bedrijf selecteren als u deze " +
                    "wilt wijzigen");
        } else {
            Bedrijf bedrijf = tableView.getSelectionModel().getSelectedItem();
            bedrijfTagDAO.setBedrijf(bedrijf);
            bedrijfTagDAO.select();
            melding.showWarningMessage("Waarschuwing!", "Wijzigen bedrijf", "Weet u het zeker u de volgende tag wilt " +
                    "wijzigen \n bedrijfsnaam: " + bedrijf.getBedrijfsnaam());
            if (melding.isVoorActie()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/wijzigenBedrijven.fxml"));
                    Parent root1 = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Wijzigen bedrijven");
                    BedrijvenWijzigenController controller = fxmlLoader.getController();
                    controller.setBedrijf(bedrijf);
                    controller.fillData();
                    controller.setStage(stage);
                    controller.setBedrijfTagDAO(bedrijfTagDAO);
                    controller.setDynamischTags(dynamischTags);
                    controller.setViewController(this);
                    Scene scene = new Scene(root1);
                    scene.getStylesheets().add(css);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het vernieuwen van de data in tableview.
     */
    public void refreshTable(){
        bedrijvenObservers.clear();
        fillTable();
    }

    /**
     * Deze methode is verantwoordelijk voor het sluiten van de bedrijven weergeven view.
     */
    public void openHoofdMenu(){
        stage.close();
        getHoofdStage().show();
    }

    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt
     * met alle bedrijven.
     */
    public  void fillTable() {
        bedrijfDAO.select();
        bedrijvenObservers = bedrijfDAO.getObserversBedrijven();
        //Soteert de bedrijvenLijst
        SortedList<Bedrijf> sortedItems = new SortedList<>(bedrijvenObservers);
        //Bind de gesoteerde lijst met de table view
        sortedItems.comparatorProperty().bind(tableView.comparatorProperty());
        //Voegt de gesoteerde lijst to aan table view
        tableView.setItems(sortedItems);
    }
    /**
     * Deze methode wordt aangeroepen door de FXML file.
     * Deze opent de view bedrijven.
     */
    public void viewBedrijven() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/bedrijvenTabs.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Bekijken bedrijven");
                BedrijvenTabsController bedrijvenTabsController = fxmlLoader.getController();
                bedrijvenTabsController.setCss(css);
                bedrijvenTabsController.setBedrijf(tableView.getSelectionModel().getSelectedItem());
                Scene scene = new Scene(root1);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            melding.showNormalMessage("Fout!", "Bekijken Klant", "Selecteer een bedrijf");
        }
    }
    /**
     * Deze methode zorgt ervoor dat een bedrijf kan worden gezocht.
     */
    public void searchBedrijf() {
        FilteredList<Bedrijf> filteredList = new FilteredList<Bedrijf>(bedrijvenObservers, e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Bedrijf>) b -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (b.getBedrijfsnaam().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Bedrijf> sortedList = new SortedList<Bedrijf>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }
    /**
     * Deze methode zorgt ervoor dat een woonplaats kan worden gefilterd.
     */
    public void filterWoonplaats() {
        bedrijvenObservers.clear();
        bedrijfDAO.select();
        cbPlaats.getItems().clear();
        for(Bedrijf bedrijf : bedrijvenObservers){
            cbPlaats.getItems().add(bedrijf.getPlaats());
        }
        FilteredList<Bedrijf> filteredList = new FilteredList<Bedrijf>(bedrijvenObservers, e -> true);
        cbPlaats.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Bedrijf>) b -> {
                if (newValue == null || newValue.toString().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toString().toLowerCase();
                if (b.getPlaats().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Bedrijf> sortedList = new SortedList<Bedrijf>(filteredList);
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
     * Deze methode zorgt ervoor dat een tag kan worden gefilterd.
     */
    public void filterTag() {
        tagDAO.getObserversTags().clear();
        tagDAO.select();
        cbTag.getItems().clear();
        for(Tag tag : tagDAO.getObserversTags()) {
            cbTag.getItems().add(tag.getNaam());
        }
        cbTag.valueProperty().addListener(e->{
            bedrijfDAO.filterTag(String.valueOf(cbTag.getSelectionModel().getSelectedItem()));
        });
        bedrijvenObservers.clear();
        bedrijvenObservers = bedrijfDAO.getObserversBedrijven();
        tableView.setItems(bedrijvenObservers);
    }

    /** /**
     * Deze methode zorgt ervoor
     * dat de beheerdersnaam wordt gezet.
     * @param beheerderNaamInLabel
     */
    public void setBeheerderNaamInLabel(String beheerderNaamInLabel) {
        lblUsername.setText(beheerderNaamInLabel);
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
     * Deze methode zorgt ervoor
     * dat de css wordt geset.
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * Deze methode is verantwoordelijk voor het ophalen van de stage van de hoofdmenuview.
     * @return hoofdStage
     */
    public Stage getHoofdStage() {
        return hoofdStage;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de stage van de hoofdmenuview
     * @param hoofdStage
     */
    public void setHoofdStage(Stage hoofdStage) {
        this.hoofdStage = hoofdStage;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de stage van de bedrijvenView
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}


