package Controller;

import DAO.TagDAO;
import Model.Beheerder;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static Main.Main.primaryStage;

/**
 * Deze class wordt gebruikt als de controller van de hoofdViewtest.
 * Hierin staat alle logica die nodig zijn voor de hoofdviewtest.
 * Created by murtazaaydogdu on 04-10-16.
 * @author Murtaza Aydogdu , Mohamed EL baze
 * @version 1.0 Nov 2016
 */
public class HoofdViewController {

    /**
     * Hier wordt de TagDAO object aangemaakt.
     */
    private TagDAO tagDAO = new TagDAO();

    /**
     * Hier wordt de DynamischTags object aangemaakt.
     */
    private DynamischTags dynamischTags = new DynamischTags(tagDAO);

    /**
     * Hier wordt de een object van de JFXButton gedacaleerd.
     */
    @FXML
    private JFXButton btnBeheerders, btnKlanten, btnBedrijven, btnTags;

    /**
     * Hier wordt een string aangemaakt zodat de beheerder naam in kan worden geset.
     */
    private String beheerderNaamInLabel;

    /**
     * Hier wordt de beheerder gedacaleerd.
     */
    private Beheerder beheerder;

    /**
     * Hier wordt een String aangemaakt zodat de css kan worden geset.
     */
    private String css;

    /**
     * Hier wordt de loginController gedacaleerd.
     */
    private LoginController loginController;

    /**
     * Hier wordt de stage gedacaleerd.
     */
    private Stage stage;

    /**
     * Deze methode kijkt wat voor rechten jij hebt en afhankelijk hiervan wordt een view geopent.
     */
    public void openBeheerders(){
        if(beheerder.getRechten_id() == 1){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/employeeView.fxml"));
                Parent root1 = fxmlLoader.load();
                BeheerderController beheerderController = fxmlLoader.getController();
                beheerderController.setBeheerderNaamInLabel(beheerderNaamInLabel);
                beheerderController.setCss(css);
                Stage stage = new Stage();
                beheerderController.setStage(stage);
                beheerderController.setHoofdStage(getStage());
                stage.setTitle("Admin");
                Scene scene = new Scene(root1);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/beheerderViewVoorBeheerders.fxml"));
                Parent root1 = fxmlLoader.load();
                BeheerdersControllerVoorBeheerders beheerdersControllerVoorBeheerders = fxmlLoader.getController();
                beheerdersControllerVoorBeheerders.setBeheerderNaamInLabel(beheerderNaamInLabel);
                beheerdersControllerVoorBeheerders.setCss(css);
                Stage stage = new Stage();
                beheerdersControllerVoorBeheerders.setStage(stage);
                beheerdersControllerVoorBeheerders.setHoofdStage(getStage());
                stage.setTitle("Beheerders");
                Scene scene = new Scene(root1);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het openen van de gebruikersview.
     */
    public void openKlanten(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/userView.fxml"));
            Parent root1 = fxmlLoader.load();
            KlantenController klantenController = fxmlLoader.getController();
            klantenController.setDynamischTags(dynamischTags);
            klantenController.setCss(css);
            klantenController.setBeheerderNaam(beheerderNaamInLabel);
            Stage stage = new Stage();
            klantenController.setStage(stage);
            klantenController.setHoofdStage(getStage());
            stage.setTitle("Overzicht Klanten");
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
     * Deze methode is verantwoordelijk voor het openen van de bedrijvenview.
     */
    public void openBedrijven(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Bedrijvenview.fxml"));
            Parent root1 = fxmlLoader.load();
            BedrijvenViewController bv = fxmlLoader.getController();
            bv.setBeheerderNaamInLabel(beheerderNaamInLabel);
            bv.setDynamischTags(dynamischTags);
            bv.setCss(css);
            Stage stage = new Stage();
            bv.setStage(stage);
            bv.setHoofdStage(getStage());
            stage.setTitle("Bedrijven");
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
     * Deze methode is verantwoordelijk voor het openen van de notitieview.
     */
    public void openNotitie(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/notitieView.fxml"));
            Parent root1 = fxmlLoader.load();
            NotitieController kc = fxmlLoader.getController();
            kc.setlblUsername(beheerderNaamInLabel);
            kc.setBeheerder(beheerder);
            kc.setCss(css);
            kc.setHoofdStage(getStage());
            Stage stage = new Stage();
            kc.setStage(stage);
            stage.setTitle("Notitie");
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
     * Deze methode is verantwoordelijk voor het openen van de tagView.
     */
    public void openTags(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/tagView.fxml"));
            Parent root1 = fxmlLoader.load();
            TagController tagController = fxmlLoader.getController();
            tagController.setCss(css);
            tagController.setBeheerderInLabel(beheerderNaamInLabel);
            Stage stage = new Stage();
            tagController.setStage(stage);
            tagController.setHoofdStage(getStage());
            stage.setTitle("Tags");
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
     * Deze methode is verantwoordelijk voor het openen van de instellingenView.
     */
    public void openInstellingen(){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/instellingenTab.fxml"));
                Parent root1 = fxmlLoader.load();
                InstellingenTabController instellingenTabController = fxmlLoader.getController();
                instellingenTabController.setLoginController(loginController);
                instellingenTabController.setBeheerder(beheerder);
                instellingenTabController.setCss(css);
                Stage stage = new Stage();
                instellingenTabController.setStage(stage);
                stage.setTitle("Instellingen Admin");
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
     * Deze methode is verantwoordelijk voor het openen van de logoutView.
     */
    public void openLogout(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/logoutView.fxml"));
            Parent root1 = fxmlLoader.load();
            LogoutController logoutController = fxmlLoader.getController();
            logoutController.setHoofdViewStage(stage);
            Stage stage = new Stage();
            logoutController.setLogOutStage(stage);
            stage.setTitle("Logout");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat de naam van de beheerder wordt geset.
     * @param beheerderNaamInLabel
     */
    public void setBeheerderNaamInLabel(String beheerderNaamInLabel) {
        this.beheerderNaamInLabel = beheerderNaamInLabel;
    }

    /**
     * Deze methode zorgt ervoor dat de beheerder wordt geset.
     * @param beheerder
     */
    public void setBeheerder(Beheerder beheerder) {
        this.beheerder = beheerder;
    }

    /**
     * Deze methode zorgt ervoor dat de loginController wordt geset, zodat bij het setten van je thema vanaf de
     * hoofdview wordt gedaan.
     * @param loginController
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Deze methode set de css.
     * @param css
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * Deze methode zorgt er voor dat de stage wordt geset.
     * @param stage
     */
    public void setHoofdViewStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Deze methode returnd de stage.
     * @return stage
     */
    public Stage getStage() {
        return stage;
    }

}
