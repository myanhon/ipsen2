package Controller;

import Model.Bedrijf;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * De BedrijvenTabController is de controller voor tab bedrijven in bedrijfview.
 * Created by murtazaaydogdu on 02-11-16.
 * @author Mohamed El Baze, murtazaaydogdu
 * @version 0.1
 */
    public class BedrijvenTabController {

    /**
     * Op deze pane worden de tags geplaats.
     */
    @FXML
    private Pane wbPane;
    /**
     * Hier wordt een Beheerder object aangemaakt.
     * Hierin wordt de geslecteerde beheerder model uit de tabelview in beherderview gezet.
     */
    private Bedrijf bedrijf;
    /**
     * Dit is de model die gevens van de dynamische tags bijhoud.
     */
    private DynamischTags dynamischTags;
    /**
     * Hier worden de dynamische checkboxen voor de tags begehouden.
     */
    private ArrayList<JFXCheckBox> jfxCheckBoxes;
    /**
     * Hier wordt de een object van Textfield gedeclareerd.
     * De invoer text van dit object wordt gebruikt voor het viewfunctie.
     */
    @FXML
    private JFXTextField txtBedrijfsnaam, txtAdresHuisnr,
            txtPostcode, txtPlaats,txtContactpersoon,
            txtTelefoon,txtEmail;

    /**
     * Deze methode wordt aangeroepen als de controller is aangemaakt.
     * de methode is verantwoordelijk voor het koppelen van de tabel en column met de verwijzing naar hun waardes.
     */
    public void init(){
        txtBedrijfsnaam.setText(bedrijf.getBedrijfsnaam());
        txtAdresHuisnr.setText(bedrijf.getAdres());
        txtPostcode.setText(bedrijf.getPostcode());
        txtPlaats.setText(bedrijf.getPlaats());
        txtContactpersoon.setText(bedrijf.getContactpersoon());
        txtTelefoon.setText(bedrijf.getTelefoon());
        txtEmail.setText(bedrijf.getEmail());
    }

    /**
     * Deze methode is verantwoordelijk voor het zetten van de geslecteerde tag object in Tag tag.
     * @param bedrijf geselecteerde bedrijf model van de tableview in bedrijfview.
     */
    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }
    /**
     * Hier wordt de DynamischTags obeject gevuld. Deze methode wordt aangeroepen door de hoofdview, zodat de het
     * toevoegen en wijzigen scherm gevuld kunnen worden met tags.
     *
     * @param dynamischTags
     */
    public void setDynamischTags(DynamischTags dynamischTags) {
        this.dynamischTags = dynamischTags;
        vultags();
    }
    /**
     * Deze methode is verantwoordelijk voor vullen van Tags in gegevens formulier.
     */
    public void vultags(){
        jfxCheckBoxes = dynamischTags.getTagCheckboxes();
        dynamischTags.vultags(wbPane,bedrijf.getArrTags());
    }
}
