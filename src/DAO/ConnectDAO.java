package DAO;

import java.sql.*;

/**
 * Dit is een abstracte class, de dao klasses
 * extenden van deze klas.
 * Created by Mike,Shaban,Murtaza,Mohamed on 12-10-16.
 *
 * @author Mike, Shaban, Mohamed El Baze, Murtaza
 * @version 0.1
 */
public abstract class ConnectDAO {

    /**
     * Hier wordt de connection  opgeslagen.
     */
    protected Connection connection;
    /**
     * Hier wordt de statement  opgeslagen.
     */
    protected Statement statement;
    /**
     * Hier wordt de PreparedStatement  opgeslagen.
     */
    protected PreparedStatement preparedStatement;
    /**
     * Hier wordt de ResultSet  opgeslagen.
     */
    protected ResultSet resultSet;
    /**
     * Hier wordt de IP  opgeslagen.
     */
    protected String ip = "localhost";
    /**
     * Hier wordt de poortnummer  opgeslagen.
     */
    protected String poortnummer = "5432";
    /**
     * Hier wordt de Dbnaam  opgeslagen.
     */
    protected String DbNaam = "ipsen2";
    /**
     * Hier wordt de DbGebruikersnaam  opgeslagen.
     */
    protected String DbGebruikersnaam = "postgres";
    /**
     * Hier wordt de DbWachtwoord  opgeslagen.
     */
    protected String DbWachtwoord = "root";

    /**
     * Deze methode zorgt ervoor dat de driver ingeladen wordt
     * Vervolgens wordt er een connectie gemaakt.
     */
    public void connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + poortnummer + "/" +
                    DbNaam, DbGebruikersnaam, DbWachtwoord);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat de connectie en statement afgesloten wordt
     */
    public void closeConnection() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat preparedstatetment uitgevoerd worden
     */
    public void runPreparedStatemant(String query) {
        connectToDB();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Deze methode zorgt ervoor dat die een specifieke id returned
     */
    public int runIDstatement(String query) {
        int id = 0;
        connectToDB();
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt("id");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Subclasses worden gedwongen om deze methode te verkrijgen
     */
    public abstract void update(int id);

    /**
     * Subclasses worden gedwongen om deze methode te verkrijgen
     */
    public abstract void delete(int id);

    /**
     * Subclasses worden gedwongen om deze methode te verkrijgen
     */
    public abstract void insert();

    /**
     * Subclasses worden gedwongen om deze methode te verkrijgen
     */
    public abstract void select();

    /**
     * Zodra deze methode wordt aangeroepen wordt de ip geset.
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de poortnummer geset.
     *
     * @param poortnummer
     */
    public void setPoortnummer(String poortnummer) {
        this.poortnummer = poortnummer;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de databasenaam geset.
     *
     * @param dbNaam
     */
    public void setDbNaam(String dbNaam) {
        DbNaam = dbNaam;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de DatabaseGebruikersnaam geset.
     *
     * @param dbGebruikersnaam
     */
    public void setDbGebruikersnaam(String dbGebruikersnaam) {
        DbGebruikersnaam = dbGebruikersnaam;
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt de DatabaseWachtwoord geset.
     *
     * @param dbWachtwoord
     */
    public void setDbWachtwoord(String dbWachtwoord) {
        DbWachtwoord = dbWachtwoord;
    }

}
