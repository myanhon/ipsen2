package Controller;


import Model.Klant;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


/**
 * Deze class wordt gebruikt als de controller van de KlantenTab view.
 * Hierin staat alle logica die nodig zijn voor de KlantenTab View.
 * Created by Shaban , Mike  on 05-10-16.
 */
public class KlantenTabController {
    /**
     * Hier wordt  een object van de Pane gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private Pane ktPane;
    /**
     * Hier wordt  een object van de TextField gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXTextField txtVoornaam, txtAchternaam, txtAdresHuisnr,
            txtPostcode, txtWoonplaats, txtTelefoon,txtEmail;
    /**
     * Hier wordt  een object van de DataPicker gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    @FXML
    private JFXDatePicker datePicker;
    /**
     * Hier wordt  een object van de klant gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Klant klant;
    /**
     * Hier wordt  een object van de arrayList CheckBoxes gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private ArrayList<JFXCheckBox> jfxCheckBoxes;
    /**
     * Hier wordt  een object van de dynamischTags gedeclareerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private DynamischTags dynamischTags;

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
     * Methode om de klant te returnen
     *
     * @return klant
     */
    public Klant getKlant() {
        return klant;
    }

    /**
     * Zodra deze methode wordt aangeroepen, worden de textFields gezet met de data van de klant
     */
    public void init() {
        txtVoornaam.setText(klant.getVoornaam());
        txtAchternaam.setText(klant.getAchternaam());
        txtAdresHuisnr.setText(klant.getAdres());
        txtPostcode.setText(klant.getPostcode());
        txtWoonplaats.setText(klant.getWoonplaats());
        txtTelefoon.setText(klant.getTelefoon());
        txtEmail.setText(klant.getEmail());
        datePicker.setValue(klant.getGeboorteDatum().toLocalDate());

    }

    /**
     * Hier worden de tags gevuld
     */
    public void vultags() {
        jfxCheckBoxes = dynamischTags.getTagCheckboxes();
        dynamischTags.vultags(ktPane, klant.getArrTags());
    }

    /**
     * Deze methode zorgt ervoor
     * dat de DynamischTags wordt geset.
     *
     * @param dynamischTags
     */
    public void setDynamischTags(DynamischTags dynamischTags) {
        this.dynamischTags = dynamischTags;
        vultags();
    }

}
