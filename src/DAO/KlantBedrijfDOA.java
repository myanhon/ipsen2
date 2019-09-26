package DAO;

import Model.Bedrijf;
import Model.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * De KlantBedrijfDOA is de DOA die wordt gebruikt voor het communiceren met de Klant_has_bedrijf Tabel.
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class KlantBedrijfDOA extends ConnectDAO{

    /**
     * Hier wordt wordt een object van klant opgeslagen.
     * Deze bevat de klantgegevens die wordt gebruikt in de statements
     */
    private Klant klant;

    /**
     * Hier wordt wordt een object van bedrijf opgeslagen.
     * Deze bevat de bedrijfgegevens die wordt gebruikt in de statements
     */
    private Bedrijf bedrijf;

    /**
     * Hier wordt wordt een Observable list gedeclareerd.
     * Hierin worden alle klanten die werkzaam zijn bij een specifieke bedrijf in opgeslagen.
     */
    private ObservableList<Klant> werkzameKlanten = FXCollections.observableArrayList();

    /**
     * Hier wordt wordt een Observable list gedeclareerd.
     * Hierin worden alle klanten die niet werkzaam zijn bij een specifieke bedrijf in opgeslagen.
     */
    private ObservableList<Klant> overigeKlanten = FXCollections.observableArrayList();

    /**
     * Hier wordt wordt een Observable list gedeclareerd.
     * Hierin worden alle bedrijven waar een specifiek klant werkzaam is in opgeslagen.
     */
    private ObservableList<Bedrijf> werkzameBedrijven = FXCollections.observableArrayList();

    /**
     * Hier wordt wordt een Observable list gedeclareerd.
     * Hierin worden alle bedrijven waar een specifiek klant niet werkzaam is in opgeslagen.
     */
    private ObservableList<Bedrijf> overigebedrijven = FXCollections.observableArrayList();

    /**
     * Deze methode wordt niet gebruikt.
     */
    @Override
    public void update(int id) {
    }

    /**
     * Deze methode is verantwoordelijk voor het verwijderen van een klant bedrijf relatie in de tabel.
     */
    @Override
    public void delete(int id) {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("DELETE FROM klant_has_bedrijf Where klant_id =? AND " +
                    "bedrijf_id =?");
            preparedStatement.setInt(1,klant.getId());
            preparedStatement.setInt(2,bedrijf.getId());
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het toevoegen van een klant bedrijf relatie in de tabel
     */
    @Override
    public void insert() {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("INSERT INTO klant_has_bedrijf (klant_id,bedrijf_id) VALUES (?,?)");
            preparedStatement.setInt(1,klant.getId());
            preparedStatement.setInt(2,bedrijf.getId());
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het op halen van alle bedrijven waar een klant werkzaam is.
     */
    @Override
    public void select() {
        werkzameBedrijven.clear();
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM klant_has_bedrijf as kb " +
                    "join bedrijf as b on b.id = kb.bedrijf_id WHERE klant_id = ?");

            preparedStatement.setInt(1,klant.getId());
            resultSet = preparedStatement.executeQuery();
            setBedrijfGegevens(werkzameBedrijven);
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het op halen van alle klanten die werkzaam zijn bij een bedrijf.
     */
    public void selectWerkzameKlanten() {
        werkzameKlanten.clear();
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM klant_has_bedrijf as kb join klant as k " +
                    "on k.id = kb.klant_id WHERE bedrijf_id =?");

            preparedStatement.setInt(1,bedrijf.getId());
            resultSet = preparedStatement.executeQuery();
            setKlantGegevens(werkzameKlanten);
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het op halen van alle klanten die niet werkzaam zijn bij een bedrijf.
     */
    public void selectOverigeKlanten() {
        overigeKlanten.clear();
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM klant where id NOT IN " +
                    "(SELECT klant_id FROM klant_has_bedrijf WHERE  bedrijf_id = ?)");

            preparedStatement.setInt(1,bedrijf.getId());
            resultSet = preparedStatement.executeQuery();
            setKlantGegevens(overigeKlanten);
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deze methode is verantwoordelijk voor het op halen van alle bedrijven waar een klant niet werkzaam is.
     */
    public void selectOverigebedrijven() {
        overigebedrijven.clear();
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM bedrijf where id NOT IN " +
                            "(SELECT bedrijf_id FROM klant_has_bedrijf WHERE  klant_id = ?)");

            preparedStatement.setInt(1,klant.getId());
            resultSet = preparedStatement.executeQuery();
            setBedrijfGegevens(overigebedrijven);
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deze methode is verantwoordelijk voor het maken van de klant models gebaseerd op de resultset.
     * Deze models slaat hij op in de list.
     * @param list krijgt een observable list mee om te vullen.
     */
    private void setKlantGegevens(ObservableList list) throws SQLException {
        while (resultSet.next()) {
            klant = new Klant();
            klant.setId(resultSet.getInt("id"));
            klant.setVoornaam(resultSet.getString("Voornaam"));
            klant.setAchternaam(resultSet.getString("Achternaam"));
            klant.setWoonplaats(resultSet.getString("Woonplaats"));
            klant.setAdres(resultSet.getString("Adres"));
            klant.setPostcode(resultSet.getString("Postcode"));
            klant.setGeboorteDatum(resultSet.getDate("Geboortedatum"));
            klant.setTelefoon(resultSet.getString("Telefoon"));
            klant.setEmail(resultSet.getString("email"));
            klant.setLinkedIn(resultSet.getString("LinkedIn"));
            list.add(klant);
        }
    }

    /**
     * Deze methode is verantwoordelijk voor het maken van de bedrijf models gebaseerd op de resultset.
     * Deze models slaat hij op in de list.
     * @param list krijgt een observable list mee om te vullen.
     */
    private void setBedrijfGegevens(ObservableList list) throws SQLException {
        while (resultSet.next()) {
            bedrijf = new Bedrijf();
            bedrijf.setId(resultSet.getInt("id"));
            bedrijf.setBedrijfsnaam(resultSet.getString("bedrijfsnaam"));
            bedrijf.setAdres(resultSet.getString("adres"));
            bedrijf.setPostcode(resultSet.getString("postcode"));
            bedrijf.setWoonplaats(resultSet.getString("woonplaats"));
            bedrijf.setPlaats(resultSet.getString("plaats"));
            bedrijf.setContactpersoon(resultSet.getString("contactpersoon"));
            bedrijf.setTelefoon(resultSet.getString("telefoon"));
            bedrijf.setEmail(resultSet.getString("email"));
            list.add(bedrijf);
        }
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de klant geset.
     * @param klant
     */
    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de bedrijf geset.
     * @param bedrijf
     */
    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    /**
     * Zodra deze methode wordt aangeroepen geeft het de observable list van alle werkzame klanten van een bedrijf.
     */
    public ObservableList<Klant> getWerkzameKlanten() {
        return werkzameKlanten;
    }

    /**
     * Zodra deze methode wordt aangeroepen geeft het de observable list van alle overige klanten.
     */
    public ObservableList<Klant> getOverigeKlanten() {
        return overigeKlanten;
    }

    /**
     * Zodra deze methode wordt aangeroepen geeft het de observable list van alle bedrijf waar iemand werkzaam is.
     */
    public ObservableList<Bedrijf> getWerkzameBedrijven() {
        return werkzameBedrijven;
    }

    /**
     * Zodra deze methode wordt aangeroepen geeft het de observable list van alle overige bedrijven.
     */
    public ObservableList<Bedrijf> getOverigebedrijven() {
        return overigebedrijven;
    }

}
