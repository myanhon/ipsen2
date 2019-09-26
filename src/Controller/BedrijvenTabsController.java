package Controller;

import DAO.BedrijfTagDAO;
import DAO.TagDAO;
import Model.Bedrijf;
import javafx.fxml.FXML;

/**
 * De BedrijvenTabsController is de controller voor tabs bedrijven in bedrijfview.
 * Created by murtazaaydogdu on 02-11-16.
 * @author murtazaaydogdu
 * @version 0.1
 */
public class BedrijvenTabsController {

    BedrijfTagDAO bedrijfTagDAO = new BedrijfTagDAO();

    /**
     * Hier wordt de een object van de BedrijvenTabController gedeclareerd.
     * De object wordt gebruikt om de tabel te refreshen nadat een nieuw bedrijf object is toegevoegd.
     */
    @FXML
    private BedrijvenTabController bedrijvenTabController;
    /**
     * Hier wordt de een object van de BedrijvenTabPersoonController gedeclareerd.
     * De object wordt gebruikt om de tabel te refreshen nadat een nieuw bedrijf object is toegevoegd.
     */
    @FXML
    private BedrijvenTabPersoonController bedrijvenTabPersoonController;
    /**
     * Hier wordt een String object aangemaakt.
     * Hierin staat het locatie van het css die van toepassing is.
     */
    private String css;


    /**
     * Deze methode zorgt ervoor
     * dat de Bedrijf wordt geset.
     * @param bedrijf
     */
    public void setBedrijf(Bedrijf bedrijf) {
        bedrijfTagDAO.setBedrijf(bedrijf);
        bedrijfTagDAO.select();
        bedrijvenTabController.setBedrijf(bedrijf);
        bedrijvenTabController.init();
        bedrijvenTabController.setDynamischTags(new DynamischTags(new TagDAO()));
        bedrijvenTabPersoonController.setBedrijf(bedrijf);
        bedrijvenTabPersoonController.init();
        bedrijvenTabPersoonController.setCss(css);
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van text in String css.
     * @param css locatie van de css dat wordt toegepast.
     */
    public void setCss(String css) {
        this.css = css;
    }
}
