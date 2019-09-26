package Controller;

import DAO.BeheerderDAO;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
 * Deze class wordt gebruikt als de controller van dae employeeview.
 * Hierin staat alle logica die nodig zijn voor de employeeview.
 * Created by murtazaaydogdu on 05-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class BeheerderController implements Initializable {

    /**
     * Hier wordt de stage gedeclareerd.
     */
    private Stage stage;

    /**
     * Hier wordt een nieuwe object van Melding aangemaakt.
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt de een object van de JFXButton gedeclareerd.
     */
    @FXML
    private JFXButton btnWijzig, btnVerwijder,btnEmployee;

    /**
     * Hier wordt de een object van de Label gedeclareerd.
     */
    @FXML
    private Label lblUsername;

    /**
     * Hier wordt de een object van de TabelView gedeclareerd.
     */
    @FXML
    public TableView<Beheerder> tableView;

    /**
     * Hier wordt de een object van de TableColumn gedeclareerd.
     */
    @FXML
    private TableColumn<?,?>  voornaamCol;

    /**
     * Hier wordt de een object van de TableColumn gedeclareerd.
     */
    @FXML
    private TableColumn<?,?>  achternaamCol;

    /**
     * Hier wordt de een object van de TableColumn gedeclareerd.
     */
    @FXML
    private TableColumn<?,?>  woonplaatsCol;

    /**
     * Hier wordt de een object van de TableColumn gedeclareerd.
     */
    @FXML
    private TableColumn<?,?> emailCol;

    /**
     * Hier wordt de een object van de TableColumn gedeclareerd.
     */
    @FXML
    private TableColumn<?,?> isActiefCol;

    /**
     * Hier wordt de een object van de ObservableList<Beheerder> gedeclareerd.
     */
    private ObservableList<Beheerder> beheerders = FXCollections.observableArrayList();

    /**
     * Hier wordt een nieuwe object van de  BeheerderDAO aangemaakt.
     */
    private BeheerderDAO beheerderDAO = new BeheerderDAO();

    /**
     * Hier wordt een textfield gedeclareerd.
     */
    @FXML
    private TextField txtSearch;

    /**
     * Hier wordt een string van css aangemaakt..
     */
    private String css;
    private Stage hoofdStage;

    /**
     * Deze methode zorgt ervoor dat de toevoegenbeheerdersview wordt geopend.
     */
    public void toevoegenEmployee(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/toevoegenBeheerders.fxml"));
            Parent root1 = fxmlLoader.load();
            ToevoegenBeheerdersController toevoegenBeheerdersController = fxmlLoader.getController();
            Stage stage = new Stage();
            toevoegenBeheerdersController.setStage(stage);
            toevoegenBeheerdersController.setController(this);
            stage.setTitle("Toevoegen Beheerders");
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
     * Deze methode zorgt ervoor dat de wijzigenbeheerdersview wordt geopend.
     */
    public void wijzigEmployee() {
        if(tableView.getSelectionModel().getSelectedItem()!= null){
            melding.showWarningMessage("Warning!","Weet u het zeker","Weet je het zeker dat de beheerder wilt " +
                    "wijzigen?");
            if(melding.isVoorActie()){
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/wijzigenBeheerderView.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    WijzigenBeheerdersController wbc = fxmlLoader.getController();
                    wbc.init(tableView.getSelectionModel().getSelectedItem());
                    wbc.setController(this);
                    Stage stage = new Stage();
                    wbc.stage(stage);
                    stage.setTitle("Wijzigen beheerders");
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
        else{
            melding.showWarningMessage("Fout!","Wijzigen Beheerder","Selecteer een beheerder");
        }
    }
    /**
     * Deze methode zorgt ervoor dat de gekozen beheerder wordt verwijders (gearchiveerd).
     */
    public void verwijderEmployee() {
        if(tableView.getSelectionModel().getSelectedItem()!=null){
            melding.showWarningMessage("Warning!", "Verwijderen beheerder", "Weet u het zeker?");
            if(melding.isVoorActie() == true){
                Beheerder beheerder = tableView.getSelectionModel().getSelectedItem();
                beheerder.setActief(false);
                beheerderDAO.deleteUndo(beheerder.getId(),beheerder.isActief());
                melding.showNormalMessage("Succes","Verwijderen beheerder"," Beheerder is verwijderd");
            }
            else{
                melding.showNormalMessage("Warning!!","Verwijderen beheerder","Het verwijderen is gecanceld");
            }
            refreshTable();
        }
        else{
            melding.showWarningMessage("Warning!","Verwijderen beheerder","Selecteer een beheerder");

        }
    }

    /**
     * Deze methode is verantwoordelijk voor het refreshen van de tabel.
     */
    public void refreshTable(){
        beheerders.clear();
        fillTable();
    }
    /**
     * Deze initialize methode wordt direct uitgevoerd.
     * In deze methode wordt de column namen gezet en wordt
     * de filltabel methode aangeroepen.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        voornaamCol.setCellValueFactory(new PropertyValueFactory<>("Voornaam"));
        achternaamCol.setCellValueFactory(new PropertyValueFactory<>("Achternaam"));
        woonplaatsCol.setCellValueFactory(new PropertyValueFactory<>("Woonplaats"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        isActiefCol.setCellValueFactory(new PropertyValueFactory<>("Actief"));

        fillTable();
    }
    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuld met alle beheerders.
     */
    public void fillTable(){
        beheerderDAO.select();
        beheerders = beheerderDAO.getBeheerders();
        tableView.setItems(beheerders);
    }
    /**
     * Deze methode zorgt ervoor
     * dat de stage wordt geset.
     * @param stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    /**
     * Deze methode zorgt ervoor dat de
     * employeeView wordt afgesloten.
     */
    public void home() {
        stage.close();
        getHoofdStage().show();
    }

    /**
     * Deze methode is verantwoordelijk voor het setten van de juiste beheerder naam in de label.
     * @param beheerderNaamInLabel
     */
    public void setBeheerderNaamInLabel(String beheerderNaamInLabel){
      lblUsername.setText(beheerderNaamInLabel);
    }

    /**
     * Deze methode is verantwoordelijk om de verwijderede beheerder (geachiveerde) terug te zetten.
     * @param actionEvent
     */
    public void undoEmployee(ActionEvent actionEvent) {
        Beheerder beheerder = tableView.getSelectionModel().getSelectedItem();
        if(beheerder != null){
            if(beheerder.isActief() == !false){
                melding.showWarningMessage("Warning!","Undo beheerder","De kozen beheerder is actief");
            }
            else{
               beheerder.setActief(true);
                beheerderDAO.deleteUndo(beheerder.getId(),beheerder.isActief());
            }
            refreshTable();
        }
        else{
            melding.showWarningMessage("Warning!","Undo beheerder","Selecteer een beheerder");
        }

    }

    /**
     * Deze methode zorgt ervoor dat de je kan zoeken op een beheerder.
     * @param event
     */
    public void searchBeheerder(Event event) {
        FilteredList<Beheerder> filteredList = new FilteredList<Beheerder>(beheerders, e -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super Beheerder>) b->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(b.getVoornaam().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if(b.getAchternaam().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
            SortedList<Beheerder>sortedList = new SortedList<Beheerder>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    /**
     * Hier wordt de css geset zodat de juiste css kan worden ingeladen.
     * @param css
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
}
