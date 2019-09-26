package Controller;

import DAO.TagDAO;
import Model.Tag;
import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * De DynamischTags is verantwoordlijke voor het plaatsen van checkboxen van alle tags op een pane.
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class DynamischTags {

    /**
     * Hier wordt de ArrayList van JFXCheckBox gedeclareerd,
     * hierin staat een checkbox object van elke tag.
     */
    private ArrayList<JFXCheckBox> tagCheckboxes;

    /**
     * Hier wordt de TagDOA object aangemaakt,
     * Hier kan een list van alle tags worden opgevraagd
     */
    private TagDAO tagDAO;

    /**
     * Hier wordt aangegeven hoeveel checkboxes onder elkaar mogen komen.
     */
    private int size = 9;

    /**
     * De constructor van de DynamischTags.
     * @param tagDAO
     */
    public DynamischTags(TagDAO tagDAO){
        this.tagDAO = tagDAO;
    }

    /**
     * Deze methode zorgt ervoor dat de arraylist wordt gevult met een checkbox voor elke tag in de database.
     */
    private void vulTagCheckboxes(){
        tagCheckboxes = new ArrayList<>();
        tagDAO.select();
        for (Tag t: tagDAO.getObserversTags()) {
            JFXCheckBox checkbox = new JFXCheckBox(t.getNaam());
            checkbox.setId(Integer.toString(t.getId()));
            checkbox.setSelected(false);
            checkbox.setTextFill(Color.WHITE);
            checkbox.setPadding(new Insets(5,0,5,0));
            tagCheckboxes.add(checkbox);
        }
    }

    /**
     * Deze methode zet de context van de arraylist tagCheckboxes op de pane die hij meekrijgt.
     * @param pane
     */
    public void vultags(Pane pane){
        int teller = 0;
        HBox hbox = new HBox(10);
        VBox vbox = new VBox();
        pane.getChildren().add(hbox);
        hbox.getChildren().add(vbox);
        for (JFXCheckBox checkbox: tagCheckboxes) {
            if (teller > size){
                vbox = new VBox();
                hbox.getChildren().add(vbox);
                teller = 0;
            }
            teller++;
            vbox.getChildren().add(checkbox);
        }
    }

    /**
     * Deze methode zet de context van de arraylist tagCheckboxes op de pane die hij meekrijgt en
     * als de checkbox id overeenkomt met een id in de Arraylist arrTags wordt het aangevinkt.
     * @param pane      hierop wordt de checkboxes geplaast.
     * @param arrTags   arraylist met de tags die de klant/bedrijf heeft
     */
    public void vultags(Pane pane, ArrayList<Integer> arrTags){
        int teller = 0;
        HBox hbox = new HBox(10);
        VBox vbox = new VBox();
        pane.getChildren().add(hbox);
        hbox.getChildren().add(vbox);
        for (JFXCheckBox checkbox: tagCheckboxes) {
            if (teller > size){
                vbox = new VBox();
                hbox.getChildren().add(vbox);
                teller = 0;
            }
            for (Integer id: arrTags) {
                if(id == Integer.parseInt(checkbox.getId())){
                    checkbox.setSelected(true);
                }
            }
            teller++;
            vbox.getChildren().add(checkbox);
        }
    }

    /**
     * Deze methode roept de vulTagCheckboxes methode aan en geeft de Arraylist met checkboxes terug.
     */
    public ArrayList<JFXCheckBox> getTagCheckboxes() {
        vulTagCheckboxes();
        return tagCheckboxes;
    }
}
