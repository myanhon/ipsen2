package Controller;

import DAO.KlantBedrijfDOA;
import DAO.NotitieDAO;
import Model.Bedrijf;
import Model.Klant;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * De BedrijvenTabPersoonController is de controller voor tab personen in bedrijfview.
 *
 * @author Shaban Jama, Mohammed El Baze
 * @version 1.0, November 2016
 */
public class BedrijvenTabPersoonController {

    /**
     * Hier wordt de een object van Textfield gedeclareerd.
     * De invoer text van dit object wordt gebruikt voor het zoekfunctie.
     */
    @FXML
    private TextField txtSearch;

    /**
     * Hier wordt de een objecten van TableColumn gedacaleerd.
     * in de volgende colummen weergeven wij de data in de Klant_has_bedrijf tabel.
     */
    @FXML
    private TableColumn<?, ?> t1voornaamCol, t1achternaamCol ,t1woonplaatsCol,t1adresCol,
            t1geboorteDatum,t1telefoon,t1email,t2voornaamCol, t2achternaamCol ,t2woonplaatsCol,t2adresCol,
            t2geboorteDatum,t2telefoon,t2email;

    /**
     * Hier wordt de een object van Tableview gedeclareerd.
     * Hierin weergeven wij de data van het Klant_has_bedrijf tabel.
     */
    @FXML
    private TableView<Klant> klantenlijst,toevoegenKlantenlijst;

    /**
     * Hier wordt een Melding object aangemaakt.
     * hiermee kunnen we een meldingen weergeven.
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt een Beheerder object aangemaakt.
     * Hierin wordt de geslecteerde beheerder model uit de tabelview in beherderview gezet.
     */
    private Bedrijf bedrijf;

    /**
     * Hier wordt een klant object aangemaakt.
     * Hierin wordt de geslecteerde klant model uit de tabelview gezet.
     */
    private Klant klant;

    /**
     * Hier wordt een KlantBedrijfDOA object aangemaakt.
     * hiermee kan het class communiceren met het Klant_has_bedrijf tabel.
     */
    private KlantBedrijfDOA klantBedrijfDOA = new KlantBedrijfDOA();

    /**
     * Hier wordt een String object aangemaakt.
     * Hierin staat het locatie van het css die van toepassing is.
     */
    private String css;


    /**
     * Deze methode wordt aangeroepen als de controller is aangemaakt.
     * de methode is verantwoordelijk voor het koppelen van de tabel en column met de verwijzing naar hun waardes.
     */
    public void init() {
        t1achternaamCol.setCellValueFactory(new PropertyValueFactory<>("achternaam"));
        t1adresCol.setCellValueFactory(new PropertyValueFactory<>("adres"));
        t1geboorteDatum.setCellValueFactory(new PropertyValueFactory<>("geboorteDatum"));
        t1telefoon.setCellValueFactory(new PropertyValueFactory<>("telefoon"));
        t1voornaamCol.setCellValueFactory(new PropertyValueFactory<>("voornaam"));
        t1woonplaatsCol.setCellValueFactory(new PropertyValueFactory<>("woonplaats"));
        t1email.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        t2achternaamCol.setCellValueFactory(new PropertyValueFactory<>("achternaam"));
        t2adresCol.setCellValueFactory(new PropertyValueFactory<>("adres"));
        t2geboorteDatum.setCellValueFactory(new PropertyValueFactory<>("geboorteDatum"));
        t2telefoon.setCellValueFactory(new PropertyValueFactory<>("telefoon"));
        t2voornaamCol.setCellValueFactory(new PropertyValueFactory<>("voornaam"));
        t2woonplaatsCol.setCellValueFactory(new PropertyValueFactory<>("woonplaats"));
        t2email.setCellValueFactory(new PropertyValueFactory<>("email"));

        filltable();
    }

    /**
     * Deze methode is verantwoordelijk voor het vullen van de data in de twee tableviews.
     */
    private void filltable() {
        klantBedrijfDOA.setBedrijf(bedrijf);
        klantBedrijfDOA.selectOverigeKlanten();
        klantBedrijfDOA.selectWerkzameKlanten();
        klantenlijst.setItems(klantBedrijfDOA.getWerkzameKlanten());
        toevoegenKlantenlijst.setItems(klantBedrijfDOA.getOverigeKlanten());
    }

    /**
     * Deze methode is verantwoordelijk voor het vernieuwen van de data in de twee tableviews.
     */
    public void refresh() {
        toevoegenKlantenlijst.getItems().clear();
        klantenlijst.getItems().clear();
        filltable();
    }

    /**
     * Deze methode is verantwoordelijk voor het toevoegen van de persoon in de Klant_has_bedrijf tabel.
     */
    public void toevoegenPersoon() {
        if (toevoegenKlantenlijst.getSelectionModel().getSelectedItem()!= null){
            klantBedrijfDOA.setKlant(toevoegenKlantenlijst.getSelectionModel().getSelectedItem());
            klantBedrijfDOA.insert();
            refresh();
        }else{
            melding.showWarningMessage("Warning!","toevoegen persoon","Selecteer een persoon in het onderste tabel");
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het verwijderen van de persoon in de Klant_has_bedrijf tabel.
     */
    public void verwijderPersoon() {
        if (klantenlijst.getSelectionModel().getSelectedItem() != null){
            klantBedrijfDOA.setKlant(klantenlijst.getSelectionModel().getSelectedItem());
            klantBedrijfDOA.delete(klantenlijst.getSelectionModel().getSelectedItem().getId());
            refresh();
        }else{
            melding.showWarningMessage("Warning!","Verwijderen persoon","Selecteer een persoon in het bovenste tabel");
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het deselecteren van de geslecteerde waarde
     * in de toevoegenKlantenlijst tableview.
     */
    public void klantenLijstDeslect(){
        toevoegenKlantenlijst.getSelectionModel().clearSelection();
    }

    /**
     * Deze methode is verantwoordelijk voor het deselecteren van de geslecteerde waarde
     * in de klantenlijst tableview.
     */
    public void toevoegenKlantenLijstDeslect(){
        klantenlijst.getSelectionModel().clearSelection();
    }

    /**
     * Deze methode is verantwoordelijk voor het openen van de klantview.
     */
    public void openKlantView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/klantenView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            KlantenViewController klantenViewController = fxmlLoader.getController();
            klantenViewController.setMelding(melding);
            klantenViewController.setNotitieDao(new NotitieDAO());
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
    }

    /**
     * Deze methode is verantwoordelijk voor het vullen van de geslecteerde klant in de tabel
     * als de klant opject. anders geeft deze methode een melding.
     */
    public void klantView(){
        if (klantenlijst.getSelectionModel().getSelectedItem() != null){
            klant = klantenlijst.getSelectionModel().getSelectedItem();
            openKlantView();
        }else if (toevoegenKlantenlijst.getSelectionModel().getSelectedItem() != null){
            klant = toevoegenKlantenlijst.getSelectionModel().getSelectedItem();
            openKlantView();
        }else{
            melding.showWarningMessage("Warning!","Verwijderen persoon","Selecteer een persoon in een van de twee " +
                    "tabellen");
        }

    }

    /**
     * Deze methode is verantwoordelijk voor maken van sortedlisten en aanroepen van de predict methode hiervoor..
     */
    public void searchKlant() {
        FilteredList<Klant> werkzameKlanten = new FilteredList<Klant>(klantBedrijfDOA.getWerkzameKlanten(), e -> true);
        predict(werkzameKlanten,klantenlijst);
        FilteredList<Klant> overigeKlanten = new FilteredList<Klant>(klantBedrijfDOA.getOverigeKlanten(), e -> true);
        predict(overigeKlanten,toevoegenKlantenlijst);
    }

    /**
     * Deze methode is verantwoordelijk voor het zoeken naar data uit de tableview die overeenkomt met de waardes uit
     * uit de search textfield.
     * @param filteredList dit is gefilterd lijst van de data.
     * @param tableview dit is de tableview waarvoor de list is bedoeld.
     */
    public void predict(FilteredList<Klant> filteredList, TableView<Klant> tableview){
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
            sortedList.comparatorProperty().bind(tableview.comparatorProperty());
            tableview.setItems(sortedList);
        });
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de geslecteerde tag object in Tag tag.
     * @param bedrijf geselecteerde bedrijf model van de tableview in bedrijfview.
     */
    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van text in String css.
     * @param css locatie van de css dat wordt toegepast.
     */
    public void setCss(String css) {
        this.css = css;
    }
}
