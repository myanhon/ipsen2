package Controller;

import DAO.NotitieDAO;
import Model.Bedrijf;
import Model.Klant;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * De ComboBoxAutoComplete is een autocompleet functionaliteit klasse voor comboboxen.
 * @author Mohamed El Baze
 * @version 0.1
 * @param <T>
 */
public class ComboBoxAutoComplete<T> implements EventHandler<KeyEvent> {

    /**
     * Hier wordt  een object van de klantenLijst geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private static final ObservableList<Klant> klantenLijst = FXCollections.observableArrayList();
    /**
     * Hier wordt  een object van de bedrijvenLijst geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private static final ObservableList<Bedrijf> bedrijvenLijst = FXCollections.observableArrayList();
    /**
     * Hier wordt  een object van de notitieDAO geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private NotitieDAO notitieDAO = new NotitieDAO();
    /**
     * Hier wordt  een object van de comboBox geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private JFXComboBox comboBox;
    /**
     * Hier wordt  een object van de StringBuilder geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private StringBuilder sb;
    /**
     * Hier wordt  een object van de ObservableList met de data geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private ObservableList<T> data;
    /**
     * Hier wordt  een object van de moveCaretToPos met de data geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private boolean moveCaretToPos = false;
    /**
     * Hier wordt  een object van de caretPos met de data geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private int caretPos;
    /**
     * Dit is de constructor van de ComboBoxAutoComplete.
     * @param comboBox
     */
    public ComboBoxAutoComplete(final JFXComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(ComboBoxAutoComplete.this);
    }
    /**
     * Dit is de constructor van de ComboBoxAutoComplete.
     */
    public ComboBoxAutoComplete() {}

    /**
     * Deze methode houd de events van de comboboxen bij.
     * @param event
     */
    @Override
    public void handle(KeyEvent event) {

        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList list = FXCollections.observableArrayList();
        for (int i=0; i<data.size(); i++) {
            if(data.get(i).toString().toLowerCase().startsWith(
                    ComboBoxAutoComplete.this.comboBox
                            .getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }

    /**
     * Deze methode zorgt voor het verplaasen van de Caret van de combobox.
     * @param textLength
     */
    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

    /**
     * Deze methode zorg voor het toevoegen van AutoComplete functionaliteit aan comboboxen voor klanten.
     * @param klantcmb
     */
    public void getAutoCompleteKlant(JFXComboBox<Klant> klantcmb) {
        klantcmb.setItems(klantenLijst);
        klantcmb.getJFXEditor().setOnKeyTyped(EventHandler -> {
            ObservableList<Klant> klant = notitieDAO.getSuggestionsKlant(String.valueOf(klantcmb.getJFXEditor().getText()));
                klantcmb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Klant>() {
                    @Override
                    public void changed(ObservableValue<? extends Klant> observable, Klant oldValue, Klant klant) {
                        if (klant != null) {
                            klantcmb.getJFXEditor().setId(String.valueOf(klant.getId()));
                        }
                    }
                });
                klantenLijst.clear();
                klantenLijst.addAll(klant);
        });
        new ComboBoxAutoComplete<Klant>(klantcmb);


    }
    /**
     * Deze methode zorg voor het toevoegen van AutoComplete functionaliteit aan comboboxen voor bedrijven.
     * @param bedrijfcmd
     */
    public void getAutoCompleteBedrijf(JFXComboBox<Bedrijf> bedrijfcmd) {
        bedrijfcmd.setItems(bedrijvenLijst);
        bedrijfcmd.getJFXEditor().setOnKeyReleased(EventHandler -> {
            ObservableList<Bedrijf> bedrijf = notitieDAO.getSuggestionsBedrijf(bedrijfcmd.getJFXEditor().getText());
            bedrijfcmd.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Bedrijf>() {
                @Override
                public void changed(ObservableValue<? extends Bedrijf> arg0, Bedrijf arg1, Bedrijf bedrijf) {
                    if (bedrijf != null) {
                        bedrijfcmd.getJFXEditor().setId(String.valueOf(bedrijf.getId()));
                    }
                }
            });
            bedrijvenLijst.clear();
            bedrijvenLijst.addAll(bedrijf);
        });
        new ComboBoxAutoComplete<Klant>(bedrijfcmd);
    }

}