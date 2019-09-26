package Controller;

import DAO.TagDAO;
import Model.Tag;
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

/**
 * De TagController is de controller voor tagView(Overzicht Tags).
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class TagController implements Initializable {

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
     * Hierin weergeven wij de data van het Tag tabel.
     */
    @FXML
    public TableView<Tag> tableView;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column naam in de Tag tabel.
     */
    @FXML
    private TableColumn<?,?> tagnaam;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Hierin weergeven wij de data van het column beschrijving in de Tag tabel.
     */
    @FXML
    private TableColumn<?,?> tagbeschrijving;

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
     * Hier wordt een String object aangemaakt.
     * Hierin staat het locatie van het css die van toepassing is.
     */
    private String css;
    private Stage hoofdStage;

    /**
     * Deze methode wordt aangeroepen als de controller is aangemaakt.
     * de methode is verantwoordelijk voor het koppelen van de tabel en column met de verwijzing naar hun waardes.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setPlaceholder(new Label("Er zijn geen tags gevonden."));
        tagnaam.setCellValueFactory(new PropertyValueFactory<>("naam"));
        tagbeschrijving.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
        fillTable();
    }

    /**
     * Deze methode is verantwoordelijk voor het opstarten van de Toevoegen Tags scherm.
     */
    public void toevoegenTag(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/toevoegenTags.fxml"));
            Parent root1 = fxmlLoader.load();
            ToevoegenTagsController toevoegenTagsController = fxmlLoader.getController();
            toevoegenTagsController.setController(this);
            Stage stage = new Stage();
            toevoegenTagsController.setStage(stage);
            stage.setTitle("Toevoegen tags");
            Scene scene = new Scene(root1);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Toevoegen Tag");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het verwijderen van een Tag.
     */
    public void verwijderTag(){
        if(tableView.getSelectionModel().getSelectedItem() == null ){
            melding.showNormalMessage("Fout!","Geen tag geselecteerd","U moet eerst een tag selecteren als u deze " +
                    "wilt verwijderen");
        }else {
            melding.showWarningMessage("Waarschuwing!", "Verwijderen tag", "Weet u het zeker u de volgende tag wilt " +
                    "verwijderen \nTagnaam: " + tableView.getSelectionModel().getSelectedItem().getNaam());
            if(melding.isVoorActie()) {
                Tag tag = tableView.getSelectionModel().getSelectedItem();
                tagDAO.setTag(tag);
                tagDAO.delete(tag.getId());
                if (tagDAO.getRows() > 0) {
                    refreshTable();
                    melding.showNormalMessage("Succes", "Tag succesvol verwijdert.", "Het verwijderen van de Tag: " +
                            tag.getNaam() + " is gelukt");
                } else {
                    melding.showWarningMessage("Warning", "verwijderen van Tag is mislukt", tagDAO
                            .getErrorBeschrijving());
                }
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het opstarten van de Bewerken Tags scherm.
     */
    public void wijzigTag(){
       if(tableView.getSelectionModel().getSelectedItem() == null ){
            melding.showNormalMessage("Fout!","Geen tag geselecteerd","U moet eerst een tag selecteren als u deze " +
                    "wilt wijzigen");
       }
        else{
           Tag tag = tableView.getSelectionModel().getSelectedItem();
           melding.showWarningMessage("Waarschuwing!", "Wijzigen tag", "Weet u het zeker u de volgende tag wilt " +
                   "wijzigen \nTagnaam: " + tag.getNaam());
           if( melding.isVoorActie()) {
               try {
                   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/wijzigenTags.fxml"));
                   Parent root1 = fxmlLoader.load();
                   WijzigenTagsController wtc = fxmlLoader.getController();
                   wtc.setController(this);
                   wtc.setTag(tag);
                   wtc.init();
                   Stage stage = new Stage();
                   wtc.setStage(stage);
                   stage.setTitle("Wijzigen tags");
                   Scene scene = new Scene(root1);
                   scene.getStylesheets().add(css);
                   stage.setScene(scene);
                   stage.setResizable(false);
                   stage.setTitle("Bewerken Tag");
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
        tagDAO.select();
        tableView.setItems(tagDAO.getObserversTags());
    }

    /**
     * Deze methode is verantwoordelijk voor het zoeken naar data uit de tableview die overeenkomt met de waardes uit
     * uit de search textfield.
     * hiervan wordt er een sortedlist gemaakt en geplaats in tableview.
     */
    public void searchTags() {
        FilteredList<Tag> filteredList = new FilteredList<>(tagDAO.getObserversTags(), e -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate( b -> {
                boolean leeg = newValue == null || newValue.isEmpty();
                return leeg||b.getNaam().toLowerCase().contains(newValue.toLowerCase());
            });
            SortedList<Tag> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    /**
     * Deze methode is verantwoordelijk voor het afsluiten van de view.
     */
    public void home() {
        stage.close();
        getHoofdStage().show();
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van text in String css.
     * @param css locatie van de css dat wordt toegepast.
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van text in de label lblUsername.
     * @param beheerderInLabel Achternaam en voornaam van de beheerder.
     */
    public void setBeheerderInLabel(String beheerderInLabel) {
        lblUsername.setText(beheerderInLabel);
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van een stage object in de Stage stage.
     * @param stage het stage object van Tagview.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Stage getHoofdStage() {
        return hoofdStage;
    }

    public void setHoofdStage(Stage hoofdStage) {
        this.hoofdStage = hoofdStage;
    }
}
