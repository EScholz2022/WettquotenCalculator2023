package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class represents core informations of team as SimpleStringProperties
 * creating an instance of Team class calls class constructor to setup all properties
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 25.08.2022
 */
public class Team {

    private final SimpleStringProperty mannschaft;
    private final SimpleStringProperty spiele;
    private final SimpleStringProperty siege;
    private final SimpleStringProperty unentschieden;
    private final SimpleStringProperty niederlagen;
    private final SimpleStringProperty tore;
    private final SimpleStringProperty tordifferenz;
    private final SimpleStringProperty punkte;
    private final SimpleStringProperty marktwert;
    private final SimpleStringProperty transfers;

    public Team(String mannschaft, String spiele, String siege, String unentschieden, String niederlagen, String tore, String differenz, String punkte, String marktwert, String transfers) {
        this.mannschaft = new SimpleStringProperty(mannschaft);
        this.spiele = new SimpleStringProperty(spiele);
        this.unentschieden = new SimpleStringProperty(unentschieden);
        this.siege = new SimpleStringProperty(siege);
        this.niederlagen = new SimpleStringProperty(niederlagen);
        this.tore = new SimpleStringProperty(tore);
        this.tordifferenz = new SimpleStringProperty(differenz);
        this.punkte = new SimpleStringProperty(punkte);
        this.marktwert = new SimpleStringProperty(marktwert);
        this.transfers = new SimpleStringProperty(transfers);
    }

    public String getMannschaft() {
        return mannschaft.get();
    }

    public SimpleStringProperty mannschaftProperty() {
        return mannschaft;
    }

    public void setMannschaft(String mannschaft) {
        this.mannschaft.set(mannschaft);
    }

    public String getSpiele() {
        return spiele.get();
    }

    public SimpleStringProperty spieleProperty() {
        return spiele;
    }

    public void setSpiele(String spiele) {
        this.spiele.set(spiele);
    }

    public String getSiege() {
        return siege.get();
    }

    public SimpleStringProperty siegeProperty() {
        return siege;
    }

    public void setSiege(String siege) {
        this.siege.set(siege);
    }

    public String getUnentschieden() {
        return unentschieden.get();
    }

    public SimpleStringProperty unentschiedenProperty() {
        return unentschieden;
    }

    public void setUnentschieden(String unentschieden) {
        this.unentschieden.set(unentschieden);
    }

    public String getNiederlagen() {
        return niederlagen.get();
    }

    public SimpleStringProperty niederlagenProperty() {
        return niederlagen;
    }

    public void setNiederlagen(String niederlagen) {
        this.niederlagen.set(niederlagen);
    }

    public String getTore() {
        return tore.get();
    }

    public SimpleStringProperty toreProperty() {
        return tore;
    }

    public void setTore(String tore) {
        this.tore.set(tore);
    }

    public String getTordifferenz() {
        return tordifferenz.get();
    }

    public SimpleStringProperty tordifferenzProperty() {
        return tordifferenz;
    }

    public void setTordifferenz(String tordifferenz) {
        this.tordifferenz.set(tordifferenz);
    }

    public String getPunkte() {
        return punkte.get();
    }

    public SimpleStringProperty punkteProperty() {
        return punkte;
    }

    public void setPunkte(String punkte) {
        this.punkte.set(punkte);
    }

    public String getMarktwert() {
        return marktwert.get();
    }

    public SimpleStringProperty marktwertProperty() {
        return marktwert;
    }

    public void setMarktwert(String marktwert) {
        this.marktwert.set(marktwert);
    }

    public String getTransfers() {
        return transfers.get();
    }

    public SimpleStringProperty transfersProperty() {
        return transfers;
    }

    public void setTransfers(String tranfers) {
        this.transfers.set(tranfers);
    }
}
