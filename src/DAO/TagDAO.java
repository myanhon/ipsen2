package DAO;

import Model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 * De TagDOA is de DOA die wordt gebruikt voor het communiceren met de tag Tabel.
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class TagDAO extends ConnectDAO{

    /**
     * Hier wordt wordt een object van Tag opgeslagen.
     * Deze bevat de Taggegevens die wordt gebruikt in de statements
     */
    private Tag tag;

    /**
     * Hier wordt wordt een ObservableList van Tag opgeslagen.
     * Deze bevat de tags die worden opgehaald met de select methode.
     */
    private ObservableList<Tag> observersTags = FXCollections.observableArrayList();

    /**
     * Hier wordt opgeslagen hoeveel row zijn verandert bij het uitvoeren van de preparedstatement.
     */
    private int rows;

    /**
     * Hier wordt error bericht opgeslagen zodat deze geprint kan worden..
     */
    private String errorBeschrijving;

    /**
     * Deze methode is verantwoordelijk voor het aanpassen van een tag in de tabel.
     */
    @Override
    public void update(int id) {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("UPDATE tag SET naam = ?,beschrijving = ? WHERE id = ?");
            preparedStatement.setString(1,tag.getNaam());
            preparedStatement.setString(2,tag.getBeschrijving());
            preparedStatement.setInt(3,tag.getId());
            rows = preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            if(Integer.parseInt(e.getSQLState()) == 23505){
                errorBeschrijving = "Tagnaam: "+ tag.getNaam() +" bestaat al in het database.";
            }
            else {
                errorBeschrijving = e.getLocalizedMessage();
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het verwijderen van een tag in de tabel.
     */
    @Override
    public void delete(int id) {

        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("DELETE FROM tag WHERE id = ?");
            preparedStatement.setInt(1,tag.getId());
            rows = preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            errorBeschrijving = e.getLocalizedMessage();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het toevoegen van een tag in de tabel.
     */
    @Override
    public void insert() {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("INSERT INTO tag (naam,beschrijving) VALUES (?,?)");
            preparedStatement.setString(1,tag.getNaam());
            preparedStatement.setString(2,tag.getBeschrijving());
            rows = preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            if(Integer.parseInt(e.getSQLState()) == 23505){
                errorBeschrijving = "Tagnaam: "+ tag.getNaam() +" bestaat al in het database.";
            }
            else {
                errorBeschrijving = e.getLocalizedMessage();
            }
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het uitlezen van een de tags in de tabel.
     * hiervan worden tag models gemaakt en opgeslagen in de observable list.
     */
    @Override
    public void select() {
        observersTags.clear();
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM tag");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                tag = new Tag();
                tag.setId(resultSet.getInt("id"));
                tag.setNaam(resultSet.getString("naam"));
                tag.setBeschrijving(resultSet.getString("beschrijving"));
                observersTags.add(tag);
            }
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat zodra deze wordt aangeroepen die de rows returnd.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Zodra deze methode wordt aangeroepen geeft het de observable list van alle tags
     */
    public ObservableList<Tag> getObserversTags() {
        return observersTags;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de tag geset.
     * @param tag
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * Deze methode zorgt ervoor dat zodra deze wordt aangeroepen die de errorBeschrijving returnd.
     */
    public String getErrorBeschrijving() {
        return errorBeschrijving;
    }
}
