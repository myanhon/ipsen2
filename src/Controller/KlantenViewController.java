package Controller;


import DAO.KlantTagDAO;
import DAO.NotitieDAO;
import DAO.TagDAO;
import Model.Klant;
import javafx.fxml.FXML;


/**
 * Deze class wordt gebruikt als de controller van de WijzigenNotitie view.
 * Hierin staat alle logica die nodig zijn voor de WijzigenNotitie View.
 * Created by Mike   on 05-10-16.
 */
public class KlantenViewController {
    /**
     * Hier wordt  een object van de klantenTabController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private KlantenTabController klantenTabController;
    /**
     * Hier wordt  een object van de klantenNotitieTabController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private KlantenNotitieTab klantenNotitieTabController;
    /**
     * Hier wordt  een object van de klantenTabDiplomaController gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private KlantenTabDiplomaController klantenTabDiplomaController;

    /**
     * Hier wordt  een object van de KlantTagDAO gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    KlantTagDAO klantTagDAO= new KlantTagDAO();

    /**
     * Deze methode zorgt ervoor
     * dat de klant wordt geset.
     * En geeft gelijk door aan de klantenTabDiplomaController , klantenNotitieTabController en klantenTabController.
     * Daarnaast worden de methodes init,setDynamischTags en filltable uitegevoerd
     */
    public void setKlant(Klant klant) {
        klantTagDAO.setKlant(klant);
        klantTagDAO.select();
        klantenTabController.setKlant(klant);
        klantenNotitieTabController.setKlant(klant);
        klantenTabDiplomaController.setKlant(klant);
        klantenTabDiplomaController.init();
        klantenTabController.init();
        klantenTabController.setDynamischTags(new DynamischTags(new TagDAO()));
        klantenNotitieTabController.fillTable();
    }

    /**
     * Deze methode zorgt ervoor
     * dat de Melding wordt geset.
     * En geeft gelijk door aan de klantenTabDiplomaController
     */
    public void setMelding(Melding melding) {
        klantenTabDiplomaController.setMelding(melding);

    }

    /**
     * Deze methode zorgt ervoor
     * dat de beheerder wordt geset.
     * En geeft gelijk door aan de klantenTabDiplomaController.
     */
    public void setNotitieDao(NotitieDAO notitieDAO) {
        klantenNotitieTabController.setNotitieDAO(notitieDAO);
    }

}
