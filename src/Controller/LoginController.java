package Controller;


import DAO.BeheerderDAO;
import DAO.ConnectDAO;
import Main.Main;
import Main.ThemaEnum;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.Properties;

/**
 * Deze class wordt gebruikt om de applicatie te starten
 * Created by murtazaaydogdu on 11-10-16.
 * @author Murtaza Aydogdu, Mohamed EL Baze
 * @version 1.0 Nov 2016
 */

public class LoginController {

    /**
     * Hier wordt de JFXButton gedeclareerd,
     * zodat FXML weet dat dezae gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXButton btnLogin;

    /**
     * Hier wordt de JFXTextField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXTextField userField;

    /**
     * Hier wordt de JFXPasswordField gedeclareerd,
     * zodat FXML weet dat deze gedeclareerd is en dat wij er gebruik van kunnen maken.
     */

    @FXML
    private JFXPasswordField passField;

    /**
     * Hier wordt de Text gedeclareerd,
     * zodat FXML weet dat de aangemaakt is en als
     * de login is mislukt er een melding komt.
     */
    @FXML
    public Text errorMessage;

    /**
     * Hier wordt de JFXButton gedeclareerd,
     * zodat FXML weet dat dezae gedeclareerd is en dat wij er gebruik van kunnen maken.
     */
    @FXML
    private JFXButton btnDatabase;

    /**
     * Hier wordt de BeheerderDAO object aangemaakt,
     * Deze kan worden gebruikt bij de login.
     */
    private BeheerderDAO beheerderDAO = new BeheerderDAO();

    /**
     * Hier wordt de Beheerder gedeclareerd,
     * zodat de gegevens van de ingeloggede
     * beheerder kan worden gebruikt.
     */
    private Beheerder beheerder;

    /**
     * Hier wordt de PasswordEncryption object aangemaakt,
     * zodat er tijdens het inloggen de ingevulde password kan worden geencrypt.
     */
    private PasswordEncryption passwordEncryption = new PasswordEncryption();

    /**
     * Hier wordt de Melding object aangemaakt,
     * zodra er iemand op de knop van de database klikt er eerst een melding kan komen.
     */
    private Melding melding = new Melding();

    /**
     * Hier wordt de standaard thema geset.
     */
    private String css = ThemaEnum.DEFAULT.getThema();

    /**
     * Hier wordt de FormValidator object aangemaakt,
     * zodat deze kan worden gebruikt op de txtUsername en de txtPassword.
     */
    private FormValidator formValidator = new FormValidator();

    /**
     * Hier wordt de ConnectDAO object aangemaakt,
     * zodat er een config file bestaat de attributten in de deze config file wordt geset in de ConnectDAO.
     */
    private ConnectDAO connectDAO = new ConnectDAO() {
        @Override
        public void update(int id) {

        }

        @Override
        public void delete(int id) {

        }

        @Override
        public void insert() {

        }

        @Override
        public void select() {

        }
    };

    /**
     * Deze methode zorgt ervoor dat je inlogt maar ook checkt deze methode of de config.properties bestaat en de
     * juist database instellingen worden geset.
     */
    public void login(){
        Properties properties = new Properties();
        File file;
        InputStream inputStream;
        try {
            file = new File("/Users/murtazaaydogdu/Desktop/ipsen2/src/resources/config/config.properties");

            if(file.exists()){
                inputStream = new FileInputStream(file);
                properties.load(inputStream);

                String DatabaseIp = properties.getProperty("Database ip");
                String Poortnummer = properties.getProperty("Poortnummer");
                String Gebruikersnaam = properties.getProperty("Gebruikersnaam");
                String Wachtwoord = properties.getProperty("Wachtwoord");
                String DatabaseNaam = properties.getProperty("Database naam");

                connectDAO.setIp(DatabaseIp);
                connectDAO.setPoortnummer(Poortnummer);
                connectDAO.setDbGebruikersnaam(Gebruikersnaam);
                connectDAO.setDbWachtwoord(Wachtwoord);
                connectDAO.setDbNaam(DatabaseNaam);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Validator();
    }

    /**
     * Deze methode zorgt ervoor dat als de ingevulde gebruikersnaam en wachtwoordt klopt,
     * dat de hoofdview wordt geopent.
     */
    public void openHoofdView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/hoofdViewtest.fxml"));
            Parent root1 = fxmlLoader.load();
            HoofdViewController hoofdViewController = fxmlLoader.getController();
            hoofdViewController.setBeheerderNaamInLabel(beheerder.getAchternaam()+", "+beheerder.getVoornaam());
            hoofdViewController.setLoginController(this);
            hoofdViewController.setBeheerder(beheerder);
            hoofdViewController.setCss(css);
            Stage stage = new Stage();
            hoofdViewController.setHoofdViewStage(stage);
            stage.setTitle("HoofdView");
            Scene scene = new Scene(root1,1024,768);
            stage.setScene(scene);
            scene.getStylesheets().add(css);
            stage.show();
            stage.setResizable(false);
            Main.closeWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat de ingelogde beheerder
     * @param beheerder
     */
    public void setBeheerder(Beheerder beheerder) {
        this.beheerder = beheerder;
    }

    /**
     * Deze methode zorgt ervoor dat de databaseinstellingen view owordt geopend.
     */
    public void openDatabaseInstellingen() {

        melding.showWarningMessage("Warning!!","Openen database instellingen","Weet u het zeker dat u de" +
                "database instellingen wilt openen");

        if(melding.isVoorActie()){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/DatabaseInstellingen.fxml"));
                Parent root1 = fxmlLoader.load();
                DatabaseInstellingenController databaseInstellingenController = fxmlLoader.getController();
                Stage stage = new Stage();
                databaseInstellingenController.setStage(stage);
                stage.setTitle("Database instellingen");
                Scene scene = new Scene(root1);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deze methode zorgt ervoor dat als een nieuwe thema wordt geselecteerd, dat dan deze vanaf de hoofdview wordt
     * geset.
     * @param css
     */
    public void setCss(String css) {
        this.css = css;
        openHoofdView();
    }

    /**
     * Deze methode zorgt ervoor dat zodra je op de txtPassword op enter drukt, dat je dan ook wordt ingelogd.
     */
    public void onEnter() {
        login();
    }

    /**
     * Deze methode zorgt ervoor dat er een validatie komt op de txtUsername en op de txtPassword
     */
    public void Validator () {
        formValidator.setHasError(true);
        formValidator.hasFormValidator(userField);
        formValidator.passwordValidator(passField);
        String username = userField.getText().toLowerCase().trim();
        String password = passwordEncryption.GenerateHash(passField.getText().trim());
        if (formValidator.getValid() == true) {
            beheerderDAO.setLoginController(this);
            beheerderDAO.login(username, password);
        }
    }
}