package DAO;

import Model.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;


/**
 * Deze class wordt gebruikt met de database te communiceren.
 * Het is verantwoordelijk om query's uit te voeren.
 * Created by Mike,Shaban on 12-10-16.
 * @author Mike,Shaban, Mohamed El Baze
 * @version 0.1
 */
public class KlantDAO extends ConnectDAO {

    /**
     * Hier wordt de klant  opgeslagen
     */
    private Klant klant;
    /**
     * Hier worden de klanten in een ObserverableList opgeslagen.
     */
    private ObservableList<Klant> observersKlanten = FXCollections.observableArrayList();
    /**
     * Hier worden de query's opgeslagen.
     */
    private String query;
    /**
     * Hier wordt error bericht opgeslagen zodat deze geprint kan worden..
     */
    private String errorBeschrijving;
    /**
     * Hier wordt opgeslagen hoeveel row zijn verandert bij het uitvoeren van de preparedstatement.
     */
    private int rows;

    /**
     * Deze methode zorgt ervoor dat de select wordt uitgevoerd om
     * alle klanten te selecteren.
     */

    @Override
    public void select() {
        query = "SELECT * FROM klant";
        getKlantGegevens(query);
    }

    /**
     * Deze methode zorgt ervoor dat de delete wordt uitgevoerd om
     * geselecteerde klant te verwijderen.
     */
    @Override
    public void delete(int id) {
        query = "DELETE FROM Klant Where id =" + id;
        runPreparedStatemant(query);
    }

    /**
     * Deze methode zorgt ervoor dat de delete wordt uitgevoerd om
     * geselecteerde klant te verwijderen.
     */
    @Override
    public void update(int id) {
        query = "UPDATE klant SET voornaam = ?, achternaam = ?, adres " +
                "= ?, postcode = ?, woonplaats = ?, geboortedatum = ?," +
                "telefoon = ?, linkedin = ?,email = ?  WHERE id ='" + klant.getId() + "'";
        setKlantGegevens(query);
    }

    /**
     * Deze methode zorgt ervoor dat de insert wordt uitgevoerd om
     * een klant toe te voegen.
     */
    @Override
    public void insert() {
        query = "INSERT INTO klant (voornaam,achternaam,adres," +
                "postcode,woonplaats,geboortedatum,telefoon,linkedin,email) VALUES (?,?,?,?,?,?,?,?,?)";
        setKlantGegevens(query);
    }

    /**
     * Deze methode maakt een connectie met de database om
     * de gegevens van een klant in de database toe te voegen.
     */
    private void setKlantGegevens(String query) {
        connectToDB();
        try {
             /*connectToDB doet alleen van statement.conn */
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, klant.getVoornaam());
            preparedStatement.setString(2, klant.getAchternaam());
            preparedStatement.setString(3, klant.getAdres());
            preparedStatement.setString(4, klant.getPostcode());
            preparedStatement.setString(5, klant.getWoonplaats());
            preparedStatement.setDate(6, klant.getGeboorteDatum());
            preparedStatement.setString(7, klant.getTelefoon());
            preparedStatement.setString(8, klant.getLinkedIn());
            preparedStatement.setString(9, klant.getEmail());
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (Integer.parseInt(e.getSQLState()) == 23505) {
                errorBeschrijving = "Email "+ klant.getEmail() + " bestaal al in de database";
            } else{
                errorBeschrijving = e.getLocalizedMessage();
            }
        }
        closeConnection();
    }

    /**
     * Deze methode maakt een connectie met de database om
     * de gegevens van een klant in de database toe te voegen.
     */
    private void getKlantGegevens(String query) {
        observersKlanten.clear();
        connectToDB();
        try {
            /*connectToDB doet alleen van statement.conn */
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                klant = new Klant();
                klant.setId(resultSet.getInt("id"));
                klant.setVoornaam(resultSet.getString("voornaam"));
                klant.setAchternaam(resultSet.getString("achternaam"));
                klant.setWoonplaats(resultSet.getString("woonplaats"));
                klant.setAdres(resultSet.getString("adres"));
                klant.setPostcode(resultSet.getString("postcode"));
                klant.setGeboorteDatum(resultSet.getDate("geboortedatum"));
                klant.setTelefoon(resultSet.getString("telefoon"));
                klant.setLinkedIn(resultSet.getString("linkedIn"));
                klant.setEmail(resultSet.getString("email"));
                observersKlanten.add(klant);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode haalt een specifieke klant uit de database
     */
    public int getID() {
        String query = "SELECT id FROM klant WHERE voornaam ='" + klant.getVoornaam() + "'AND achternaam ='" + klant.getAchternaam() +
                "'AND geboortedatum='" + klant.getGeboorteDatum() + "'";
        return runIDstatement(query);
    }

    /**
     * Deze methode zorgt ervoor dat als deze wordt aangesproken die de errorBeschrijvnig returnd.
     *
     * @return errorBeschrijving
     */
    public String getErrorBeschrijving() {
        return errorBeschrijving;
    }

    /**
     * Zodra deze methode wordt aangeroepen returnt deze de klanten.
     *
     * @return observersKlanten
     */
    public ObservableList<Klant> getObserversKlanten() {
        return observersKlanten;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de klant geset.
     *
     * @param klant
     */
    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    /**
     * Zodra deze methode wordt aangeroepen worden de tags gefiltered.
     */
    public void filterTag(String filter) {

        String query = "select * from klant as k\n" +
                "join klant_has_tag as kt\n" +
                "on k.id = kt.klant_id\n" +
                "join tag as t\n" +
                "on kt.tag_id = t.id WHERE naam = '" + filter + "' ";
        getKlantGegevens(query);
    }

    /**
     * Deze methode zorgt ervoor dat zodra deze wordt aangeroepen die de rows returnd.
     */
    public int getRows() {
        return rows;
    }
}

