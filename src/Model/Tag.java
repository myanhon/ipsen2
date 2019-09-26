package Model;

/**
 * Dit is de Notitie class. Hier worden gegevens van de notitie opgeslagen
 *
 * @author Shaban Jama
 * @version 1.0, November 2016
 */
public class Tag {
    /**
     * Dit zijn de standaard waardes van de tag
     * id, naam en beschrijving
     */
    private int id;
    private String naam;
    private String beschrijving;

    /**
     * Methode die de id van de notitie returned
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Methode die de id van de tag set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Methode die de id van de tag returned
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Methode die de naam van de tag set.
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * Methode die de id van de tag returned
     * @return beschrijving
     */
    public String getBeschrijving() {
        return beschrijving;
    }

    /**
     * Methode die de beschrijving van de tag set.
     */
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

}
