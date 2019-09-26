package Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * De FormValidator is een validator voor de formulieren en
 * is verantwoordelijk voor het valideren van alle formulieren.
 * @author Mohamed El Baze
 * @version 0.1
 * @date 10/25/16
 */
public class FormValidator {
    /**
     * Hier wordt  een object van de requiredFieldValidatorl geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private RequiredFieldValidator requiredFieldValidator;
    /**
     * Hier wordt  een object van de hasError geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private boolean hasError = true;
    /**
     * Hier wordt  een object van de VALID_EMAIL_ADDRESS_REGEX geinitieerd.
     * Zodat deze in een methode kan worden gebruikt
     */
    private Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Dit is een methode voor het valideren van TextFielden in formulieren.
     * @param textField
     */
    public void hasFormValidator(JFXTextField textField) {
        textField.getText().trim();
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Input Required");
        textField.getValidators().add(requiredFieldValidator);
        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) textField.validate();
        });
        if (textField.validate() == false) {
            hasError = false;
        }else if (textField.getText().isEmpty() == true){
            hasError = false;
        }
    }

    /**
     * Dit is een methode voor het valideren van Datum/Tijden-velden in formulieren.
     * @param textField
     */
    public void dataValidator(JFXDatePicker textField) {
        textField.getAccessibleText();
        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal);
        });
       if (textField.getAccessibleText().isEmpty() == true) {
            hasError = false;
        }
        }

    /**
     * Dit is een methode voor het valideren van Email-velden in formulieren.
     * @param textField
     */
    public void emailValidator(JFXTextField textField) {
        String emailStr = textField.getText().trim();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Input Required");
        textField.getValidators().add(requiredFieldValidator);
        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) textField.validate();
        });
        if (matcher.find() == false) {
            hasError = false;
            textField.setStyle("-fx-focus-color: #D34336;-fx-unfocus-color: #D34336; ");
        }else if (textField.validate() == false) {
            textField.setStyle("-fx-focus-color: #D34336;-fx-unfocus-color: #D34336; ");
            hasError = false;
        }else if (textField.getText().isEmpty() == true){
            hasError = false;
            textField.setStyle("-fx-focus-color: #D34336;-fx-unfocus-color: #D34336; ");
        }else {
            textField.setStyle("-fx-focus-color: #549788;-fx-unfocus-color:#549788;");
        }
    }

    /**
     * Dit is een methode voor het valideren van wachtwoord-velden in formulieren.
     * @param passwordField
     */
    public void passwordValidator(JFXPasswordField passwordField){
        requiredFieldValidator = new RequiredFieldValidator();
        passwordField.getText().isEmpty();
        requiredFieldValidator.setMessage("Input Required");
        passwordField.getValidators().add(requiredFieldValidator);
        passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) passwordField.validate();
        });
        if (passwordField.validate() == false) {
            hasError = false;
        }else if (passwordField.getText().isEmpty() == true){
            passwordField.validate();
            hasError = false;
        }

    }

    /**
     * Dit is een methode voor het valideren van Comboboxen in formulieren.     * @param textField
     */
    public void hasFormValidator(JFXComboBox textField) {
        textField.getJFXEditor().getText().trim();
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Input Required");
        JFXTextField tf = (JFXTextField) textField.getJFXEditor();
        tf.getValidators().add(requiredFieldValidator);
        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) tf.validate();
        });
        if (tf.validate() == false) {
            hasError = false;
        }else if (tf.getText().isEmpty() == true){
            hasError = false;
        }
    }

    /**
     * Deze methode zorgt voor het bijhouden van de Errors/Fouten in velden
     * @return
     */
    public boolean getValid(){
        return hasError;
    }

    /**
     * Deze methode zorgt voor het zetten van de Errors/Fouten in velden
     * @param hasError
     */
    public void setHasError(boolean hasError){
        this.hasError = hasError;
    }

}
