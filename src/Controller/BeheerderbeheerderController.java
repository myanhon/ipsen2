package Controller;

import DAO.BeheerderDAO;
import Main.Main;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *  * Deze class wordt gebruikt als de controller van dae employeeview.
 * Hierin staat alle logica die nodig zijn voor de employeeview.
 * Created by murtazaaydogdu on 25-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class BeheerderbeheerderController implements Initializable {

    /**
     * Hier wordt van het object stage een instance
     * aangemaakt.
     */
    private Stage stage;

    /**
     * Hier wordt de een object van de Label gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt om de
     * naam van de beheerder te setten.
     */
    @FXML
    private Label lblUsername;

    /**
     * Hier wordt de een object van de TabelView gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
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
    private TableColumn<?,?> adresCol;

    /**
     * Hier wordt de een object van de TableColumn gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?,?> emailCol;

    /**
     * Hier wordt de een object van de ObservableList<Beheerder> gedacaleerd.
     * Zodat deze in een methode kan worden gebruikt.
     */
    private ObservableList<Beheerder> beheerders = FXCollections.observableArrayList();

    /**
     * Hier wordt een nieuwe object van de  BeheerderDAO aangemaakt.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BeheerderDAO beheerderDAO = new BeheerderDAO();

    /**
     * Hier wordt de String van de naam van de beheerder aangemaakt.
     */
    private String beheerderNaamInLabel;

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
        adresCol.setCellValueFactory(new PropertyValueFactory<>("Adres"));
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
     * @param event
     */
    public void home(Event event) {
        stage.close();
    }

    /**
     * Deze methode zorgt ervoor dat de naam van de beheerder in de label wordt geset.
     * @param beheerderNaamInLabel
     */
    public void setBeheerderNaamInLabel(String beheerderNaamInLabel){
        lblUsername.setText(beheerderNaamInLabel);
    }
}
