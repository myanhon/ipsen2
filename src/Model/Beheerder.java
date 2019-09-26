package Model;

/**
 * Dit is de beheerder model class. Deze is verantwoordelijk voor alle gegevens
 * van de beheerder.
 * Created by murtazaaydogdu on 12-10-16.
 * @author Murtaza Aydogdu
 * @version 1.0, Nov 2016
 */
public class Beheerder {
    /**
     * Dit zijn de standaard waardes van de beheerders
     * id, voornaam, achternaam, adres, postcode,
     * woonplaats, telefoon, email, wachtwoord en
     * rechten_id, Actief.
     */
    private int id;
    private String voornaam;
    private String achternaam;
    private String adres;
    private String postcode;
    private String woonplaats;
    private String telefoon;
    private String email;
    private String wachtwoord;
    private int rechten_id;
    private boolean Actief = false;

    /**
     * Methode die de id van de beheerder returned
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Methode die de id van de beheerder set.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Methode die de voornaam van de beheerder returned
     * @return id
     */
    public String getVoornaam() {
        return voornaam;
    }

    /**
     * Methode die de voornaam van de beheerder set.
     * @param voornaam
     */
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    /**
     * Methode die de achternaam van de beheerder returend
     * @return achternaam
     */
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * Methode die de achternaam van de beheerder set.
     * @param achternaam
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    /**
     * Methode die de adres van de beheerder returned.
     * @return adres
     */
    public String getAdres() {
        return adres;
    }

    /**
     * Methode die de adres van de beheerder set.
     * @param adres
     */
    public void setAdres(String adres) {
        this.adres = adres;
    }

    /**
     * Methode die de postcode van de beheerder returned
     * @return postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Methode die de postcode van de beheerder set.
     * @param postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Methode die de woonplaats van de beheerder returned.
     * @return woonplaats.
     */
    public String getWoonplaats() {
        return woonplaats;
    }


    /**
     * Methode die de woonpaats van de beheerder set.
     * @param woonplaats
     */
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    /**
     * Methode die de telefoon van de beheerder returned
     * @return telefoon
     */
    public String getTelefoon() {
        return telefoon;
    }

    /**
     * Methode die de telefoon van de beheerder set.
     * @param telefoon
     */
    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }

    /**
     * Methode die de email van de beheerder returned.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Methode die de email van de beheerder set.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Methode die de wachtwoord van de beheerder returned.
     * @return wachtwoord.
     */
    public String getWachtwoord() {
        return wachtwoord;
    }

    /**
     * Methode die de wachtwoord van de beheerder set.
     * @param wachtwoord
     */
    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    /**
     * Methode die de rechten_id van de beheerder returned.
     * @return rechten_id
     */
    public int getRechten_id() {
        return rechten_id;
    }

    /**
     * Methode die de rechten_id van de beheerder set.
     * @param rechten_id
     */
    public void setRechten_id(int rechten_id) {
        this.rechten_id = rechten_id;
    }

    /**
     * Methode die de isactief van de beheerder returned
     * @return Actief
     */
    public boolean isActief() {
        return Actief;
    }

    /**
     * Methode die de isactie van de beheerder set.
     * @param actief
     */
    public void setActief(boolean actief) {
        Actief = actief;
    }

}
