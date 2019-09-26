package Model;


/**
 * Created by drynl on 3-11-2016.
 */
public class Bestand {
    /**
     * Dit zijn de standaard waardes van de bestand
     * bestand_Id, Klant_Id, FileName
     */
    private int bestand_Id;
    private int Klant_Id;
    private String FileName;

    /**
     * Methode die de klant_id van de bestand set
     */
    public void setKlant_Id(int klant_Id) {
        Klant_Id = klant_Id;
    }
    /**
     * Methode die de fileName van de bestand returned
     * @return fileName
     */
    public String getFileName() {
        return FileName;
    }
    /**
     * Methode die de fileName van de bestand set
     */
    public void setFileName(String fileName) {
        FileName = fileName;
    }
    /**
     * Methode die de bestand_id van de bestand returned
     * @return bestand_id
     */
    public int getBestand_Id() {
        return bestand_Id;
    }
    /**
     * Methode die de bestandId van de bestand set
     */
    public void setBestand_Id(int bestand_Id) {
        this.bestand_Id = bestand_Id;
    }
}
