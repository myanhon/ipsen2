package Controller;

import DAO.BedrijfDAO;
import DAO.KlantBedrijfDOA;
import DAO.KlantFileDao;
import Model.Bedrijf;
import Model.Bestand;
import Model.Klant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Deze class wordt gebruikt als de controller van de KlantenTabDiploma view.
 * Hierin staat alle logica die nodig zijn voor de KlantenTabDiploma View.
 * Created by Shaban , Mike  on 05-10-16.
 */
public class KlantenTabDiplomaController {
    /**
     * Hier wordt  een object van de TableColumn gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableColumn<?, ?>   clmBedrijfNaam,clmBedrijfAdres,
                                clmBedrijfContactpersoon,clmBestandNaam;
    /**
     * Hier wordt  een object van de ComboBox gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private ComboBox<Bedrijf> bedrijflist;
    /**
     * Hier wordt  een object van de TableView gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableView<Bedrijf> klantbedrijftabel;
    /**
     * Hier wordt  een object van de TableView gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private TableView<Bestand> klantBestandTabel;
    /**
     * Hier wordt  een object van de bedrijfDAO geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private BedrijfDAO bedrijfDAO = new BedrijfDAO();
    /**
     * Hier wordt  een object van de klantBedrijfDoa geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantBedrijfDOA klantBedrijfDOA = new KlantBedrijfDOA();
    /**
     * Hier wordt  een object van de klantFileDao geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private KlantFileDao klantFileDao = new KlantFileDao();
    /**
     * Hier wordt  een object van de Klant gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Klant klant;
    /**
     * Hier wordt  een object van de File gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private File selectedFile = null;
    /**
     * Hier wordt  een object van de Melding gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Melding melding;

    /**
     * Zodra deze methode wordt aangeroepen, worden de textFields gezet met de data van de klant
     * En roept de methodes filltable en fillBestandTableAan
     */
    public void init() {
        bedrijfDAO.select(); // eerder doen ??
        clmBedrijfNaam.setCellValueFactory(new PropertyValueFactory<>("Bedrijfsnaam"));
        clmBedrijfAdres.setCellValueFactory(new PropertyValueFactory<>("Adres"));
        clmBedrijfContactpersoon.setCellValueFactory(new PropertyValueFactory<>("Contactpersoon"));
        clmBestandNaam.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        filltable();
        fillBestandTable();

    }
    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt,
     * met de bedrijven waar de klant werkt
     */
    public void filltable() {
        klantBedrijfDOA.setKlant(klant);
        klantBedrijfDOA.select();
        klantBedrijfDOA.selectOverigebedrijven();
        bedrijflist.setItems(klantBedrijfDOA.getOverigebedrijven());
        klantbedrijftabel.setItems(klantBedrijfDOA.getWerkzameBedrijven());
    }
    /**
     * Zodra deze methode wordt aangeroepen, wordt een bedrijf geupload
     */
    public void addBedrijf() {
        if (bedrijflist.getSelectionModel().getSelectedItem() != null){
            klantBedrijfDOA.setBedrijf(bedrijflist.getSelectionModel().getSelectedItem());
            klantBedrijfDOA.insert();
            refresh();
        }else{
            melding.showNormalMessage("Fout!","Geen bedrijf geselecteerd","Selecteer eerst een bedrijf in de droplist" +
                    " als u deze wilt toevoegen");
        }

    }
    /**
     * Zodra deze methode wordt aangeroepen, wordt de geselecteerde
     * bedrijf verwijderd
     */
    public void deleteBedrijf() {
        if (klantbedrijftabel.getSelectionModel().getSelectedItem() != null){
            klantBedrijfDOA.setBedrijf(klantbedrijftabel.getSelectionModel().getSelectedItem());
            klantBedrijfDOA.delete(klant.getId());
            refresh();
        }else{
            melding.showNormalMessage("Fout!","Geen bedrijf geselecteerd","Selecteer eerst een bedrijf in de tabel " +
                    "als u deze wilt verwijderen");
        }

    }
    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt
     * met alle bestanden van de klant.
     */
    public void refresh() {
        klantbedrijftabel.getItems().clear();
        bedrijflist.getItems().clear();
        filltable();
    }
    /**
     * Deze methode zorgt ervoor dat de tabel wordt geleegd en daarna weer gevuld
     */
    public void refreshBestandTabel() {
        klantFileDao.getObserversBestand().clear();
        fillBestandTable();
    }
    /**
     * Zodra deze methode wordt aangeroepen, wordt de geselecteerde
     * klant's bestand gedownload.
     */
    public void downloadFile() {
        if (klantBestandTabel.getSelectionModel().getSelectedItem() != null) {
            klantFileDao.setBestand(klantBestandTabel.getSelectionModel().getSelectedItem());
            klantFileDao.setFile(selectedFile);
            klantFileDao.downloadFile();
            SaveFile(klantFileDao.getTheFile());
        } else
            melding.showNormalMessage("Warning!", "Downloaden Bestand", "Selecteer een Bestand");
    }
    /**
     * Zodra deze methode wordt aangeroepen, wordt een betand geupload
     */
    public void uploadFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            klantFileDao.setFile(selectedFile);
            klantFileDao.insert();
            melding.showNormalMessage("Succesvolle actie", "Toevoegen Bestand", "De gegevens zijn toegevoegd. Dit scherm zal worden afgesloten");
            refreshBestandTabel();
        }
    }
    /**
     * Zodra deze methode wordt aangeroepen, wordt de geselecteerde
     * klant's bestand verwijderd.
     */
    public void deleteFile() {
        if (klantBestandTabel.getSelectionModel().getSelectedItem() != null) {
            melding.showWarningMessage("Warning!", "Verwijderen Bestand", "Weet u het zeker?");
            if (melding.isVoorActie() == true) {
                klantFileDao.setBestand(klantBestandTabel.getSelectionModel().getSelectedItem());
                klantFileDao.delete(klantFileDao.getBestand().getBestand_Id());
                refreshBestandTabel();
            }
        } else
            melding.showNormalMessage("Warning!", "Verwijderen Bestand", "Selecteer een Bestand");
    }
    /**
     * Deze methode zorgt ervoor dat de tabel wordt gevuldt
     * met alle bestanden van de klant.
     */
    public void fillBestandTable() {
        klantFileDao.setKlant(klant);
        klantFileDao.select();
        klantBestandTabel.setItems(klantFileDao.getObserversBestand());
    }
    /**
     * Deze methode zorgt ervoor
     * dat de file wordt opgeslagen.
     */
    private void SaveFile(File theFile){
        Stage window = new Stage();
        FileChooser fileChooser = new FileChooser();
        File dest = fileChooser.showSaveDialog(window);
        if (dest != null) {
            try {
                Files.copy(theFile.toPath(), dest.toPath());
                theFile.delete();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
     * dat de melding wordt geset.
     *
     * @param melding
     */
    public void setMelding(Melding melding) {
        this.melding = melding;
    }
}
