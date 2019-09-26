package Model;

import java.util.ArrayList;

/**
 * Dit is de bedrijf model class. Deze is verantwoordelijk voor alle gegevens
 * van de bedrijf.
 * @author Mohamed El Baze
 * @version 0.1
 * @date 10/13/16
 */
public class Bedrijf {
    /**
     * Dit zijn de standaard waardes van de bedrijven
     * id, bedrijfsnaam, adres, postcode, woonplaats,
     * plaats, contactpersoon, telefoon, email en Tags
     */
    private int id;
    private String bedrijfsnaam;
    private String adres;
    private String postcode;
    private String woonplaats;
    private String plaats;
    private String contactpersoon;
    private String telefoon;
    private String email;
    private ArrayList<Integer> arrTags = new ArrayList<>();
    /**
     * Methode die de id van de bedrijf returned
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * Methode die de id van de bedrijf set.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Methode die de bedrijfsnaam van de bedrijf returned
     * @return bedrijfsnaam
     */
    public String getBedrijfsnaam() {
        return bedrijfsnaam;
    }
    /**
     * Methode die de bedrijfsnaam van de bedrijf returned
     * @param bedrijfsnaam
     */
    public void setBedrijfsnaam(String bedrijfsnaam) {
        this.bedrijfsnaam = bedrijfsnaam;
    }
    /**
     * Methode die de adres van de bedrijf returned
     * @return adres
     */
    public String getAdres() {
        return adres;
    }
    /**
     * Methode die de adres van de bedrijf set
     * @param adres
     */
    public void setAdres(String adres) {
        this.adres = adres;
    }
    /**
     * Methode die de getPostcode van de bedrijf returned
     * @return getPostcode
     */
    public String getPostcode() {
        return postcode;
    }
    /**
     * Methode die de postcode van de bedrijf returned
     * @param postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    /**
     * Methode die de woonplaats van de bedrijf returned
     * @return woonplaats
     */
    public String getWoonplaats() {
        return woonplaats;
    }
    /**
     * Methode die de woonplaats van de bedrijf returned
     * @param woonplaats
     */
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    /**
     * Methode die de plaats van de bedrijf returned
     * @return plaats
     */
    public String getPlaats() {
        return plaats;
    }
    /**
     * Methode die de plaats van de bedrijf returned
     * @param plaats
     */
    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }
    /**
     * Methode die de contactpersoon van de bedrijf returned
     * @return contactpersoon
     */
    public String getContactpersoon() {
        return contactpersoon;
    }
    /**
     * Methode die de contactpersoon van de bedrijf returned
     * @param contactpersoon
     */
    public void setContactpersoon(String contactpersoon) {
        this.contactpersoon = contactpersoon;
    }
    /**
     * Methode die de telefoon van de bedrijf returned
     * @return telefoon
     */
    public String getTelefoon() {
        return telefoon;
    }
    /**
     * Methode die de telefoon van de bedrijf returned
     * @param telefoon
     */
    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }
    /**
     * Methode die de email van de bedrijf returned
     * @return email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Methode die de email van de bedrijf returned
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Methode die de arrTags van de bedrijf returned
     * @return arrTags
     */
    public ArrayList<Integer> getArrTags() {
        return arrTags;
    }
    /**
     * Methode die de arrTags van de bedrijf returned
     * @param id
     */
    public void setArrTags(int id) {
        arrTags.add(id);
    }

    /**
     * Methode die de bedrijfsnaam en adres van de bedrijf returned
     * @return bedrijfsnaam adres
     */
    @Override
    public String toString() {
        return bedrijfsnaam+", "+adres;
    }
}
