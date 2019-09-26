package DAO;

import Model.Klant;

import java.sql.SQLException;

/**
 * De KlantTagDOA is de DOA die wordt gebruikt voor het communiceren met de Klant_has_tag Tabel.
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class KlantTagDAO extends ConnectDAO{

    /**
     * Hier wordt wordt een object van klant opgeslagen.
     * Deze bevat de klantgegevens die wordt gebruikt in de statements
     */
    private Klant klant;

    /**
     * Hier wordt het klantId opgeslagen voor het toevoegen van tags
     */
    private int klantID;

    /**
     * Hier wordt opgeslagen welke tag wordt toegevoegd
     */
    private int tagID;

    /**
     * Deze methode wordt niet gebruikt.
     */
    @Override
    public void update(int id) {
    }

    /**
     * Deze methode is verantwoordelijk voor het verwijderen van een klant en tags relatie in de tabel.
     */
    @Override
    public void delete(int id) {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("DELETE FROM klant_has_tag Where klant_id =?");
            preparedStatement.setInt(1,klantID);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het toevoegen van een klant tags relatie in de tabel
     */
    @Override
    public void insert() {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("INSERT INTO klant_has_tag (tag_id,klant_id) VALUES (?,?)");
            preparedStatement.setInt(1,tagID);
            preparedStatement.setInt(2,klantID);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het op halen van alle tags waar een klant aan gekoppelt is.
     */
    @Override
    public void select() {
        klant.getArrTags().clear();
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("SELECT tag_id FROM klant_has_tag Where klant_id = ?");
            preparedStatement.setInt(1,klant.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                klant.setArrTags(resultSet.getInt("tag_id"));
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de klantid geset.
     * @param klantID
     */
    public void setKlantID(int klantID) {
        this.klantID = klantID;

    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de tagID geset.
     * @param tagID
     */
    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de klant geset.
     * @param klant
     */
    public void setKlant(Klant klant) {
        this.klant = klant;
    }
}
