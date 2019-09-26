package DAO;

import Controller.LoginController;
import Model.Beheerder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 * Deze class wordt gebruikt met de database te praten.
 * Het is verantwoordelijk om query uit te voeren.
 * Created by murtazaaydogdu on 12-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class BeheerderDAO extends ConnectDAO {
    /**
     * Hier wordt de LoginController gedacaleerd.
     */
    private LoginController loginController;

    /**
     * Hier wordt de een object van de ObservableList<Beheerder> gedacaleerd.
     */
    public ObservableList<Beheerder> beheerders = FXCollections.observableArrayList();

    /**
     * Zodra deze methode wordt aangeroepen returnt deze de beheerders.
     * @return beheerders
     */
    public ObservableList<Beheerder> getBeheerders(){
        return beheerders;
    }

    /**
     * Hier wordt de object van de beheerder gedacaleerd.
     */
    private Beheerder beheerder;

    /**
     * Hier word een nieuwe object gedacaleerd.
     */
    private Stage stage;

    /**
     * Zodra deze methode wordt aangeroepen wordt de beheerder geset.
     * @param beheerder
     */
    public void setBeheerder(Beheerder beheerder){
        this.beheerder = beheerder;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de stage geset.
     * @param stage
     */
    public void stage(Stage stage){
        this.stage = stage;
    }

    /**
     * Hier word
     */
    private int rows;
    /**
     * Hier wordt een String aangemaakt voor de errorBeschrijving.
     */
    private String errorBeschrijving;

    /**
     * Deze methode zorgt ervoor dat de update wordt uitgevoerd om
     * de nieuwe waarde van de beheerder in te vullen.
     * @param id
     */
    @Override
    public void update(int id) {
        connectToDB();
        try {
            preparedStatement = connection.prepareStatement("UPDATE gebruiker SET voornaam = ?, achternaam = ?, adres =" +
                    " ?, postcode = ?, woonplaats = ?, telefoon = ?,email = ?, wachtwoord = ? WHERE id = '"+beheerder
                    .getId()+"' ");
            preparedStatement.setString(1,beheerder.getVoornaam());
            preparedStatement.setString(2,beheerder.getAchternaam());
            preparedStatement.setString(3,beheerder.getAdres());
            preparedStatement.setString(4,beheerder.getPostcode());
            preparedStatement.setString(5,beheerder.getWoonplaats());
            preparedStatement.setString(6,beheerder.getTelefoon());
            preparedStatement.setString(7,beheerder.getEmail());
            preparedStatement.setString(8,beheerder.getWachtwoord());
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(Integer.parseInt(e.getSQLState()) == 23505){
                errorBeschrijving = "Email: "+ beheerder.getEmail() +" bestaat al in het database.";
            }
            else {
                errorBeschrijving = e.getLocalizedMessage();
            }
        }
        closeConnection();
    }
    /**
     * Deze methode zorgt ervoor dat de delete wordt uitgevoerd om
     * geselecteerde beheerder te verwijderen.
     * @param id
     */
    @Override
    public void delete(int id) {
        connectToDB();
        try {
            preparedStatement = connection.prepareStatement("UPDATE gebruiker set isActief = TRUE WHERE ID = '"+id+"'");
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }

        closeConnection();
    }
    public void deleteUndo(int id, boolean actief) {
        connectToDB();
            try {
                preparedStatement = connection.prepareStatement("UPDATE gebruiker set isactief = '"+actief+"' WHERE ID = '"+id+"'");
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        closeConnection();
    }

    /**
     * Deze methode zorgt ervoor dat de insert wordt uitgevoerd om
     * de nieuwe een nieuwe beheerder toe te voegen.
     */
    @Override
    public void insert() {
        connectToDB();
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO gebruiker (voornaam,achternaam,adres,postcode," +
                    "woonplaats,telefoon,email,wachtwoord,rechten_id) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, beheerder.getVoornaam());
            preparedStatement.setString(2, beheerder.getAchternaam());
            preparedStatement.setString(3, beheerder.getAdres());
            preparedStatement.setString(4, beheerder.getPostcode());
            preparedStatement.setString(5, beheerder.getWoonplaats());
            preparedStatement.setString(6, beheerder.getTelefoon());
            preparedStatement.setString(7, beheerder.getEmail());
            preparedStatement.setString(8, beheerder.getWachtwoord());
            preparedStatement.setInt(9, beheerder.getRechten_id());
            rows = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            if(Integer.parseInt(e.getSQLState()) == 23505){
                errorBeschrijving = "Beheerdernaam: "+ beheerder.getVoornaam() +" bestaat al in het database.";
            }
            else {
                errorBeschrijving = e.getLocalizedMessage();
            }
        }
        closeConnection();
    }
    /**
     * Deze methode zorgt ervoor dat de select wordt uitgevoerd om
     * alle beheerders te selecteren.
     */
    @Override
    public void select() {

        String query = "SELECT * FROM gebruiker ORDER BY id";
        setBeheerderGegevens(query);
    }
    /**
     * Deze methode zorgt ervoor dat de select wordt uitgevoerd om
     * alle beheerders te selecteren.
     */
    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }

    /**
     * Deze methode is verantwoordelijk voor het inloggen van de gebruikers.
     * @param username
     * @param password
     */
    public void login(String username, String password){
        Beheerder beheerder = new Beheerder();
        try {
        connectToDB();
            String query = ("SELECT * FROM gebruiker WHERE isactief = true and email = '"+ username +
                    "' and wachtwoord = '" + password + "'");
            resultSet = statement.executeQuery(query);

            int count = 0;
            while(resultSet.next()){
                beheerder.setId(resultSet.getInt("id"));
                beheerder.setVoornaam(resultSet.getString("voornaam"));
                beheerder.setAchternaam(resultSet.getString("achternaam"));
                beheerder.setAdres(resultSet.getString("adres"));
                beheerder.setPostcode(resultSet.getString("postcode"));
                beheerder.setWoonplaats(resultSet.getString("woonplaats"));
                beheerder.setTelefoon(resultSet.getString("telefoon"));
                beheerder.setEmail(resultSet.getString("email"));
                beheerder.setWachtwoord(resultSet.getString("wachtwoord"));
                beheerder.setRechten_id(resultSet.getInt("rechten_id"));
                beheerder.setActief(resultSet.getBoolean("isactief"));
                count +=1;
            }
            if(count == 1){
                loginController.setBeheerder(beheerder);
                loginController.openHoofdView();
            }
            else {
                loginController.errorMessage.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Deze methode zorgt ervoor dat zodra deze wordt aangeroepen die de rows returnd.
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Deze methode zorgt ervoor dat de query wordt uitgevoerd en dat de uitgevoerde query in de model Beheerder
     * wordt geset.
     * @param query
     */
    public void setBeheerderGegevens(String query){
        connectToDB();
        try{
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Beheerder beheerder = new Beheerder();

                beheerder.setId(resultSet.getInt("id"));
                beheerder.setVoornaam(resultSet.getString("voornaam"));
                beheerder.setAchternaam(resultSet.getString("achternaam"));
                beheerder.setAdres(resultSet.getString("adres"));
                beheerder.setPostcode(resultSet.getString("postcode"));
                beheerder.setWoonplaats(resultSet.getString("woonplaats"));
                beheerder.setTelefoon(resultSet.getString("telefoon"));
                beheerder.setEmail(resultSet.getString("email"));
                beheerder.setWachtwoord(resultSet.getString("wachtwoord"));
                beheerder.setRechten_id(resultSet.getInt("rechten_id"));
                beheerder.setActief(resultSet.getBoolean("isactief"));

                beheerders.add(beheerder);
                connection.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat alle beheerders wordt geselecteerd die actief zijn.
     */
    public void selectBeheerder(){
        String query = ("SELECT * FROM gebruiker WHERE isactief = true ORDER BY id");
        setBeheerderGegevens(query);
    }

    /**
     * Deze methode zorgt ervoor dat als deze wordt aangesproken die de errorBeschrijvnig returnd.
     * @return errorBeschrijving
     */
    public String getErrorBeschrijving() {
        return errorBeschrijving;
    }
}
