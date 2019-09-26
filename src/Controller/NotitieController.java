package Controller;

import DAO.NotitieDAO;
import Model.Beheerder;
import Model.Notitie;
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
 * De TagController is de controller voor tagView(Overzicht Tags).
 *
 * @author Shaban Jama, Mohamed El Baze
 * @version 1.0, November 2016
 */
public class NotitieController implements Initializable {

    /**
     * Hier wordt de een object van de Textfield gedeclareerd.
     * De invoer text van dit object wordt gebruikt voor het zoekfunctie.
     */
    @FXML
    private TextField txtSearch;

    /**
     * Hier wordt de een object van de Label gedeclareerd.
     * Hierin weergeven wij de naam van de beheerder die is ingelogd.
     */
    @FXML
    private Label lblUsername;

    /**
     * Hier wordt de een object van de Tableview gedeclareerd.
     * Hierin weergeven wij de data van het Notitie tabel.
     */
    public TableView<Notitie> tableView;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column title in de Notitie tabel.
     */
    @FXML
    private TableColumn<?, ?> titel;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column beschrijving in de Notitie tabel.
     */
    @FXML
    private TableColumn<?, ?> beschrijving;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column datum in de Notitie tabel.
     */
    @FXML
    private TableColumn<?, ?> datum;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column bedrijfsid in de Notitie tabel.
     */
    @FXML
    private TableColumn<?, ?> bedrijf;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column klantid in de Notitie tabel.
     */
    @FXML
    private TableColumn<?, ?> klant;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column gebruikerid in de Notitie tabel.
     */
    @FXML
    private TableColumn<?, ?> gebruiker;

    /**
     * Hier wordt een NotitieDAO object aangemaakt.
     * hiermee kan het class communiceren met het Notitie tabel.
     */
    private NotitieDAO notitieDAO = new NotitieDAO();

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
     * Hier wordt een String object aangemaakt.
     * Hierin staat het locatie van het css die van toepassing is.
     */
    private String css;

    /**
     * Hier wordt een Beheerder object aangemaakt.
     * Hierin wordt een object opgeslagen van de beheerder die is ingelogd.
     */
    private Beheerder beheerder;
    private Stage hoofdStage;

    /**
     * Deze methode wordt aangeroepen als de controller is aangemaakt.
     * de methode is verantwoordelijk voor het koppelen van de tabel en column met de verwijzing naar hun waardes.
     */
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setPlaceholder(new Label("Er zijn geen notities gevonden."));
        titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        beschrijving.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        bedrijf.setCellValueFactory(new PropertyValueFactory<>("bedrijfNaam"));
        klant.setCellValueFactory(new PropertyValueFactory<>("klantNaam"));
        gebruiker.setCellValueFactory(new PropertyValueFactory<>("gebruiker"));
        fillTable();
    }

    /**
     * Deze methode is verantwoordelijk voor het opstarten van de Toevoegen Notitie scherm.
     */
    public void toevoegenNotitie() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/toevoegenNotitie.fxml"));
            Parent root1 = fxmlLoader.load();
            ToevoegenNotitieController kc = fxmlLoader.getController();
            kc.autoComplete();
            kc.setBeheerder(beheerder);
            kc.setMelding(melding);
            kc.setNotitieController(this);
            Stage stage = new Stage();
            kc.Stage(stage);
            stage.setTitle("Toevoegen Notitie");
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
     * Deze methode is verantwoordelijk voor het verwijderen van een Notitie.
     */
    public void verwijderNotitie() {
        if(tableView.getSelectionModel().getSelectedItem() == null ){
            melding.showNormalMessage("Fout!","Geen Notitie geselecteerd","U moet eerst een notitie selecteren als u " +
                    "deze wilt verwijderen");
        }else {
            Notitie notitie = tableView.getSelectionModel().getSelectedItem();
            melding.showWarningMessage("Waarschuwing!", "Verwijderen notitie", "Weet u het zeker u de volgende " +
                    "notitie wilt verwijderen. Notitie title: " + notitie.getTitel());
            if(melding.isVoorActie()) {
                notitieDAO.setNotitie(notitie);
                notitieDAO.delete(notitie.getId());     // waarom its meegeven ??????????
                if (notitieDAO.getRows() > 0) {
                    refreshTable();
                    melding.showNormalMessage("Succes", "Notitie succesvol verwijdert.", "Het verwijderen van de " +
                            "Notitie: " + notitie.getTitel() + " is gelukt");
                } else {
                    melding.showNormalMessage("Warning", "verwijderen van notitie is mislukt",
                            notitieDAO.getErrorBeschrijving());
                }
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het opstarten van de Bewerken Notitie scherm.
     */
    public void wijzigNotitie() {
        if(tableView.getSelectionModel().getSelectedItem() == null ){
            melding.showNormalMessage("Fout!","Geen notitie geselecteerd","U moet eerst een notitie selecteren als u " +
                    "deze wilt wijzigen");
        }else{
            Notitie notitie = tableView.getSelectionModel().getSelectedItem();
            melding.showWarningMessage("Waarschuwing!", "Wijzigen notitie", "Weet u het zeker u de volgende notitie " +
                    "wilt wijzigen. Notitie titel: " + notitie.getTitel());
            if (melding.isVoorActie() == true) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/wijzigenNotitie.fxml"));
                    Parent root1 = fxmlLoader.load();
                    WijzigenNotitieController wijzigenNotitieController = fxmlLoader.getController();
                    wijzigenNotitieController.setNotitie(notitie);
                    wijzigenNotitieController.setBeheerder(beheerder);
                    wijzigenNotitieController.setNotitieDAO(notitieDAO);
                    wijzigenNotitieController.setMelding(melding);
                    wijzigenNotitieController.setNotitieController(this);
                    wijzigenNotitieController.init();
                    Stage stage = new Stage();
                    wijzigenNotitieController.stage(stage);
                    stage.setTitle("Wijzigen Notitie");
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
    public void refreshTable() {
        tableView.getItems().clear();
        fillTable();
    }

    /**
     * Deze methode is verantwoordelijk voor het vullen van de data in tableview.
     */
    private void fillTable() {
        notitieDAO.select();
        tableView.setItems(notitieDAO.getObserversNotities());
    }

    /**
     * Deze methode is verantwoordelijk voor het zoeken naar data uit de tableview die overeenkomt met de waardes uit
     * uit de search textfield.
     * hiervan wordt er een sortedlist gemaakt en geplaats in tableview.
     */
    public void searchNotitie() {
        FilteredList<Notitie> filteredList = new FilteredList<>(notitieDAO.getObserversNotities(), e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Notitie>) n -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (n.getTitel().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (n.getKlantNaam().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (n.getBedrijfNaam().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            SortedList<Notitie> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    /**
     * Deze methode is verantwoordelijk voor het afsluiten van de view.
     */
    public void home() {
        this.stage.close();
        getHoofdStage().show();
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van een stage object in de Stage stage.
     * @param stage het stage object van Tagview.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van text in String css.
     * @param css locatie van de css dat wordt toegepast.
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van een beheerder object in de Beheerder beheerder.
     * @param beheerder huidige gebruiker model.
     */
    public void setBeheerder(Beheerder beheerder) {
        this.beheerder = beheerder;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van text in de label lblUsername.
     * @param beheerderInLabel Achternaam en voornaam van de beheerder.
     */
    public void setlblUsername(String beheerderInLabel){
        lblUsername.setText(beheerderInLabel);
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
}
