package DAO;

import Model.Bestand;
import Model.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.SQLException;
import java.sql.Types;

/**
 * De KlantFileDao is de DAO voor het opslaan en ophalen van bestanden uit database.
 * Created by drynl on 3-11-2016.
 * @author drynl, Mohammed El Baze
 * @version 0.1
 */
public class KlantFileDao extends ConnectDAO {
    /**
     * Hier wordt de klant  opgeslagen
     */
    private Klant klant;
    /**
     * Hier worden de klanten in een ObserverableList opgeslagen.
     */
    private ObservableList<Bestand> observersBestand = FXCollections.observableArrayList();
    /**
     * Hier wordt de file  opgeslagen
     */
    private File file = null;
    /**
     * Hier wordt de file  opgeslagen
     */
    private File theFile = null;
    /**
     * Hier wordt de FileInputStream  opgeslagen
     */
    private FileInputStream fis;
    /**
     * Hier wordt de Bestand  opgeslagen
     */
    private Bestand bestand;

    /**
     * Deze methode zorgt ervoor dat de delete wordt uitgevoerd om
     * geselecteerde bestand te wijzigen.
     */
    @Override
    public void update(int id) {

    }
    /**
     * Deze methode zorgt ervoor dat de delete wordt uitgevoerd om
     * geselecteerde bestand te verwijderen.
     */
    @Override
    public void delete(int id) {
        String query = "Delete FROM klant_has_file WHERE id = " + id;
        runPreparedStatemant(query);

    }

    /**
     * Deze methode zorgt ervoor dat de insert wordt uitgevoerd om
     * een bestand toe te voegen.
     */
    @Override
    public void insert() {
        connectToDB();
        String query = "INSERT INTO klant_has_file (klant_id,filename,file) VALUES (?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, klant.getId());
            preparedStatement.setString(2, file.getName());
            uploadFile(3);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode zorgt ervoor dat de select wordt uitgevoerd om
     * alle bestanden te selecteren.
     */
    @Override
    public void select() {
        String query = "SELECT * FROM klant_has_file WHERE klant_id =" + klant.getId();
        connectToDB();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bestand = new Bestand();
                bestand.setBestand_Id(resultSet.getInt("id"));
                bestand.setKlant_Id(resultSet.getInt("klant_id"));
                bestand.setFileName(resultSet.getString("filename"));
                observersBestand.add(bestand);
            }
            preparedStatement.execute();
            preparedStatement.close();
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Zodra deze methode wordt aangeroepen wordt, wordt de geselecteerde bestand gedownload
     */
    public void downloadFile() {
        connectToDB();
        String query = "SELECT * FROM klant_has_file Where id = " + bestand.getBestand_Id() + "";
        try {
            resultSet = statement.executeQuery(query);
            if (file != null) {
                theFile = new File(file.getName());
            } else {
                theFile = new File(bestand.getFileName());
            }
            FileOutputStream output = new FileOutputStream(theFile);

            while (resultSet.next()) {
                InputStream inputStream = resultSet.getBinaryStream("file");
                byte[] buffer = new byte[1024];
                while (inputStream.read(buffer) > 0) {
                    output.write(buffer);
                }
                output.close();
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Zodra deze methode wordt aangeroepen wordt, verwacht de methode
     * een int voor de positie van de value te plaatsen. Daarnaast check die of het fis null  zo niet
     * dan wordt in de preparedstatement gezet
     */
    private void uploadFile(int number) throws IOException, SQLException {
        if (file != null) {
            fis = new FileInputStream(file);
            preparedStatement.setBinaryStream(number, fis);
            fis.close();
        } else {
            preparedStatement.setNull(number, Types.OTHER);
        }
    }
    /**
     * Zodra deze methode wordt aangeroepen wordt de bestand geset.
     *
     * @param bestand
     */
    public void setBestand(Bestand bestand) {
        this.bestand = bestand;
    }
    /**
     * Methode die de bestanden van de klant returned
     * @return bestand
     */
    public ObservableList<Bestand> getObserversBestand() {
        return observersBestand;
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
     * Zodra deze methode wordt aangeroepen wordt de file geset.
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }
    /**
     * Methode die bestand returned
     * @return bestand
     */
    public Bestand getBestand() {
        return bestand;
    }
    /**
     * Methode die file returned
     * @return theFIle
     */
    public File getTheFile() {
        return theFile;
    }
}
