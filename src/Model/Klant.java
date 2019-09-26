package Model;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Dit is de klant class. Hier worden gegevens van de klant opgeslagen
 * Created by Mike on 12-10-2016.
 */
public class Klant {
    /**
     * Dit zijn de standaard waardes van de klant
     * id, voornaam, achternaam, adres, postcode,
     * woonplaats, geboorteDatum, telefoon, LinkedIn en
     * email
     */
    private int id;
    private String voornaam;
    private String achternaam;
    private String adres;
    private String postcode;
    private String woonplaats;
    private Date geboorteDatum;
    private String telefoon;
    private String linkedIn;
    private String email;
    private ArrayList<Integer> arrTags = new ArrayList<>();
    private ArrayList arrBedrijven = new ArrayList();

    /**
     * Methode die de geboortedatum van de klant set
     */
    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }
    /**
     * Methode die de geboortedatum van de klant returned
     * @return geboorteDatum
     */
    public Date getGeboorteDatum() {
        return geboorteDatum;
    }
    /**
     * Methode die de id van de klant returned
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * Methode die de id van de klant set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Methode die de voornaam van de klant returned
     * @return voornaam
     */
    public String getVoornaam() {
        return voornaam;
    }
    /**
     * Methode die de voornaam van de klant set
     */
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    /**
     * Methode die de achternaam van de klant returned
     * @return achternaam
     */
    public String getAchternaam() {
        return achternaam;
    }
    /**
     * Methode die de achternaam van de klant set
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
    /**
     * Methode die de adres van de klant returned
     * @return adres
     */
    public String getAdres() {
        return adres;
    }
    /**
     * Methode die de adres van de klant set
     */
    public void setAdres(String adres) {
        this.adres = adres;
    }
    /**
     * Methode die de postcode van de klant returned
     * @return postcode
     */
    public String getPostcode() {
        return postcode;
    }
    /**
     * Methode die de postcode van de klant set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    /**
     * Methode die de woonplaats van de klant returned
     * @return woonplaats
     */
    public String getWoonplaats() {
        return woonplaats;
    }
    /**
     * Methode die de woonplaats van de klant set
     */
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    /**
     * Methode die de telefoon van de klant returned
     * @return telefoon
     */
    public String getTelefoon() {
        return telefoon;
    }
    /**
     * Methode die de telefoon van de klant set
     */
    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }
    /**
     * Methode die de LinkedIn van de klant returned
     * @return LinkedIn
     */
    public String getLinkedIn() {
        return linkedIn;
    }
    /**
     * Methode die de linkedin van de klant set
     */
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
    /**
     * Methode die de Tags van de klant returned
     * @return arrTags
     */
    public ArrayList<Integer> getArrTags() {
        return arrTags;
    }
    /**
     * Methode die de tags van de klant set
     */
    public void setArrTags(int id) {
        arrTags.add(id);
    }
    /**
     * Methode die de bedrijven van de klant returned
     * @return bedrijven
     */
    public ArrayList<Bedrijf> getArrBedrijven() {
        return arrBedrijven;
    }
    /**
     * Methode die de bedrijven van de klant set
     */
    public void addArrBedrijven(Bedrijf bedrijf) {
        arrBedrijven.add(bedrijf);
    }
    /**
     * Methode die de email van de klant returned
     * @return email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Methode die de email van de klant set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return voornaam + " " + achternaam;
    }
}
