package Controller;

import DAO.BeheerderDAO;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 *  Deze class wordt gebruikt als de controller van de beheerderView.
 * Hierin staat alle logica die nodig zijn voor de beheerderView.
 * Created by murtazaaydogdu on 01-11-16.
 * @author Murtaza Aydogdu
 * @version 1.0 Nov 2016
 */
public class BeheerdersControllerVoorBeheerders implements Initializable {

    /**
     * Hier wordt de stage gedacaleerd.
     * aangemaakt.
     */
    private Stage stage;

    /**
     * Hier wordt een object van meldingen aangemaakt
     * zodat je de error message kunt benaderen waarnodig
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt de een object van de JFXButton gedacaleerd.
     * Zodat deze in een methdoe kan worden gebruikt.
     */
    @FXML
    private JFXButton btnWijzig, btnVerwijder,btnEmployee;

    /**
     * Hier wordt de een object van de Label gedacaleerd.
     */
    @FXML
    private Label lblUsername;

    /**
     * Hier wordt de een object van de TabelView gedacaleerd.
     */
    @FXML
    public TableView<Beheerder> tableView;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?,?> voornaamCol;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?,?>  achternaamCol;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?,?>  woonplaatsCol;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?,?> emailCol;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?,?> isActiefCol;

    /**
     * Hier wordt de een object van de ObservableList<Beheerder> gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt.
     */
    private ObservableList<Beheerder> beheerders = FXCollections.observableArrayList();

    /**
     * Hier wordt de BeheerderDAO ojbect aangemaakt.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BeheerderDAO beheerderDAO = new BeheerderDAO();

    /**
     * Deze methode wordt aangeroepen door de FXML file.
     * Deze opent de toevoegenBeheerders view.
     */
    @FXML
    private TextField txtSearch;

    /**
     * Hier wordt een string van css aangemaakt..
     */
    private String css;
    private Stage hoofdStage;

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

        fillTable();
    }

    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt
     * met alle beheerders.
     */
    public void fillTable(){
        beheerderDAO.selectBeheerder();
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
            SortedList<Beheerder> sortedList = new SortedList<Beheerder>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        });
    }

    /**
     * Deze methode zorgt ervoor dat de css wordt geset.
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



