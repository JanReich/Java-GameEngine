package Engine.Networking;

import java.util.ArrayList;

/**
 * Ein "Datapackage" beinhaltet die Daten, die vom Server und Client übermittelt
 * werden sollen
 */
public class Datapackage extends ArrayList<Object> {

    private String senderID = "NoID";
    private String senderGroupName = "NoGroup";

    /**
     * In dem Konstrucktor wird ein Datenpacket erstellt, dieses besteht aus der
     * ID und den Daten, die verschickt werden sollen
     *
     * @param o - Der Inhalt des Datenpakets
     * @param id - Die ID wird benutzt um gesendete Packet zu identifizieren
     */
    public Datapackage(String id, Object... o) {

        this.add(0, id);
        for (Object current : o)
            this.add(current);

    }

    /**
     * Returnt die ID des Datenpacket.
     *
     * @return - ID des Datenpaket
     */
    public String getID() {
        if (!(this.get(0) instanceof String)) {
            throw new IllegalArgumentException("Identifier of Datapackage is not a String");
        }
        return (String) this.get(0);
    }

    /**
     * Returnt die ID des Senders des Pakets. Diese ID macht die Zuordnung leichter
     *
     * @return - ID oder den Standardwert "NoID"
     */
    public String getSenderID() {

        return this.senderID;
    }

    /**
     * Returnt den Namen der Sendergruppe des Pakets. Diese ID macht die Zuordnung leichter
     *
     * @return - Sendergruppe oder den Standardwert "NoGroup"
     */
    public String getSenderGroup() {

        return this.senderGroupName;
    }

    /**
     * Diese Methode sorgt für eine Identifizierung des Datenpakets. Diese
     * Methode sollte nur vom Sender aufgerufen werden.<br>
     *
     * Mit diesen Informationen kann weis der Empfänger die eingehenden
     * Datenpakte identifizieren.
     *
     * @param senderID Die ID des Senders
     * @param senderGroup Der Name der Sendergruppe
     */
    protected void sign(String senderID, String senderGroup) {

        this.senderID = senderID;
        this.senderGroupName = senderGroup;
    }

    /**
     * Returnt das Datenpacket als ArrayList
     * Auf Index 0 befindet sich die ID und von Index 1 bis zum letzten
     * Element der ArrayListe befindet sich der Inhalt des Datenpakets
     *
     * @return Returnt das Datenpaket als ArrayList
     */
    public ArrayList<Object> open() {

        return this;
    }
}