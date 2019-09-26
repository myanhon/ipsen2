package Controller;

import DAO.NotitieDAO;
import Model.Klant;
import Model.Notitie;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Deze class wordt gebruikt als de controller van de KlantenNotitieTab view.
 * Hierin staat alle logica die nodig zijn voor de KlantenNotitieTab View.
 * Created by  Mike  on 05-10-16.
 */
public class KlantenNotitieTab implements Initializable {
    /**
     * Hier wordt  een object van de TableView gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableView<Notitie> tableViewNotitie;
    /**
     * Hier wordt  een object van de TextArea gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TextArea textAreaNotitie;
    /**
     * Hier wordt  een object van de TableColumn gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?, ?> datumCol, beschrijvingCol,titelCol;
    /**
     * Hier wordt  een object van de notitieDao gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieDAO notitieDAO;
    /**
     * Hier wordt  een object van de Klant gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Klant klant;
    /**
     * Hier wordt  een object van de ObserverableList<notitie> gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private ObservableList<Notitie> notitiesObservers;


    /**
     * Deze initialize methode wordt direct uitgevoerd.
     * In deze methode wordt de column namen gezet e
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datumCol.setCellValueFactory(new PropertyValueFactory<>("datum"));
        beschrijvingCol.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
        titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
    }

    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt
     * met alle notities van de klant
     */
    public void fillTable() {
        notitieDAO.setKlant(klant);
        notitieDAO.filterKlantNotitie();
        notitiesObservers = notitieDAO.getObserversNotities();
        tableViewNotitie.setItems(notitiesObservers);
        textAreaNotitie.setEditable(false);

        if (notitiesObservers.size() == 1) {
            textAreaNotitie.setText(notitieDAO.getNotitie().getBeschrijving());
        } else {
            sortNotitieDatum();
        }
    }

    /**
     * Deze methode zorgt ervoor dat de notities op datum wordt gesorteerd
     */
    private void sortNotitieDatum() {
        SortedList sortedList = new SortedList<Notitie>(notitiesObservers,
                (Notitie notitie1, Notitie notitie2) -> {
                    if (notitie1.getDatum().before(notitie2.getDatum())) {
                        textAreaNotitie.setText(notitie1.getBeschrijving());
                        return -1;
                    } else if (notitie1.getDatum().after(notitie2.getDatum())) {
                        textAreaNotitie.setText(notitie2.getBeschrijving());
                        return 1;
                    } else
                        return 0;

                });

    }

    /**
     * Deze methode zorgt ervoor
     * dat de notitieDAO wordt geset.
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
}
