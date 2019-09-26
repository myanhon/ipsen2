package Main;

/**
 * Deze class is verantwoordelijk voor de gekozen thema in te laden.
 * Created by murtazaaydogdu on 02-11-16.
 * @author Murtaza Aydogdu
 * @author Shaban Jama
 * @version 1.0, Nov 2016
 */
public enum ThemaEnum {

    /**
     * Hier maken wij een private enum van alle thema soorten.
     */
    DEFAULT("/resources/css/default.css"),
    ROOD("/resources/css/rood.css"),
    HAZELBRUIN("/resources/css/hazelbruin.css"),
    ORANJE("/resources/css/oranje.css"),
    BLAUW("/resources/css/blauw.css");

    /**
     * Hier wordt een String gedeclareerd zodat deze kan worden gebruikt om een thema te kunnen kiezen.
     */
    public final String s;

    /**
     * Dit is de constructor van de ThemaEnum class. Hier wordt de gekozen thema geset.
     * @param s
     */
    ThemaEnum(String s) {
        this.s = s;
    }

    /**
     * Deze methode returned de gekozen themea.
     * @return s
     */
    public String getThema(){
        return s;
    }

}
