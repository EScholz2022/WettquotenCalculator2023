package sample;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * Class is used to represent detailed informations of each team
 * used to init all teams from Bundesliga
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 25.08.2022
 */
public class Mannschaft {

    private int TopTransferCounter = 0;
    private String Liga = "";
    private int AnzahlTopSpieler = 0;
    private String Position = null;
    private TreeMap<String, String> Mannschaftswerte = new TreeMap<>();
    ;

    static String Name;
    static String Spiele = null;

    //constructor
    public Mannschaft(String teamname, TreeMap teamlist) {
        if (MannschaftenBundesliga1 == null) {
            MannschaftenBundesliga1 = new LinkedList<>();
            initMannschaftenBundesliga1();
        }

        this.Mannschaftswerte = teamlist;
        this.Name = teamname;
        teamlist.put("Name", teamname);
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getSpiele() {
        return Spiele;
    }

    public static String getSiege() {
        return Siege;
    }

    public static String getUnentschieden() {
        return Unentschieden;
    }

    public static String getNiederlagen() {
        return Niederlagen;
    }

    public static String getTordifferenz() {
        return Tordifferenz;
    }

    static String Siege = null;
    static String Unentschieden = null;
    static String Niederlagen = null;
    static String Tore = null;
    static String Tordifferenz = null;
    static String Punkte = null;
    static String MarktwertMannschaft = null;
    static String SpieleHeim = null;
    static String SpieleGast = null;
    static String PunkteHeim = null;
    static String PunkteGast = null;
    static String SiegeHeim = null;
    static String SiegeGast = null;
    static String NiederlagenHeim = null;
    static String NiederlagenGast = null;
    static String TordifferenzHeim = null;
    static String TordifferenzGast = null;
    static String FormSpiele = null;
    static String FormPunkte = null;
    static String FormSiege = null;
    static String FormNiederlagen = null;
    static String FormTorDifferenz = null;
    static String FormTore = null;
    static String EwigeSpiele = null;
    static String EwigeSiege = null;
    static String EwigeNiederlagen = null;
    static String EwigePunkte = null;
    private static float SCORE = 0.f;
    public static List<String> MannschaftenBundesliga1 = null;

    public Mannschaft(String name) {

        MannschaftenBundesliga1 = new LinkedList<>();
        initMannschaftenBundesliga1();

        //Werte gesetzt
        this.Liga = "1. Bundesliga";
        this.Name = name;
        this.Position = "";
        this.Spiele = "";
        this.Siege = "";
        this.Unentschieden = "";
        this.Niederlagen = "";
        this.Tore = "";
        this.Tordifferenz = "";
        this.Punkte = "";
        this.SpieleHeim = "";
        this.SiegeHeim = "";
        this.NiederlagenHeim = "";
        this.TordifferenzHeim = "";
        this.PunkteHeim = "";
        this.SpieleGast = "";
        this.PunkteGast = "";
        this.SiegeGast = "";
        this.NiederlagenGast = "";
        this.TordifferenzGast = "";
        this.EwigeSpiele = "";
        this.EwigeSiege = "";
        this.EwigeNiederlagen = "";
        this.EwigePunkte = "";
        this.MarktwertMannschaft = "";
        this.FormSpiele = "";
        this.FormPunkte = "";
        this.FormTorDifferenz = "";
        this.FormTore = "";
        this.FormNiederlagen = "";
        this.FormSiege = "";

        //Werte offen
        this.SCORE = 0.f;
    }

    public Mannschaft() {
    }


    private void initMannschaftenBundesliga1() {
        MannschaftenBundesliga1.add(Sport.BAYERN);
        MannschaftenBundesliga1.add(Sport.DORTMUND);
        MannschaftenBundesliga1.add(Sport.LEVERKUSEN);
        MannschaftenBundesliga1.add(Sport.UNIONBERLIN);
        MannschaftenBundesliga1.add(Sport.FREIBURG);
        MannschaftenBundesliga1.add(Sport.KOELN);
        MannschaftenBundesliga1.add(Sport.LEIPZIG);
        MannschaftenBundesliga1.add(Sport.HOFFENHEIM);
        MannschaftenBundesliga1.add(Sport.FRANKFURT);
        MannschaftenBundesliga1.add(Sport.MAINZ);
        MannschaftenBundesliga1.add(Sport.BOCHUM);
        MannschaftenBundesliga1.add(Sport.GLADBACH);
        MannschaftenBundesliga1.add(Sport.HERTHA);
        //MannschaftenBundesliga1.add(Sport.BIELEFELD);
        MannschaftenBundesliga1.add(Sport.AUGSBURG);
        MannschaftenBundesliga1.add(Sport.WOLFSBURG);
        MannschaftenBundesliga1.add(Sport.STUTTGART);
        //MannschaftenBundesliga1.add(Sport.FUERTH);

        /**
         * new for Saison 2022 / 2023
         */
        //-Fuerth -Bielefeld  +Werder + Schalke
        //MannschaftenBundesliga1.remove(Sport.FUERTH);
        //MannschaftenBundesliga1.remove(Sport.BIELEFELD);
        MannschaftenBundesliga1.add(Sport.BREMEN);
        //MannschaftenBundesliga1.add(Sport.SCHALKE);
        /**
         * new for Saison 2023 / 2024
         */
        MannschaftenBundesliga1.remove(Sport.SCHALKE);
        MannschaftenBundesliga1.remove(Sport.HERTHA);
        MannschaftenBundesliga1.add(Sport.HEIDENHEIM);
        MannschaftenBundesliga1.add(Sport.DARMSTADT);
    }

    public TreeMap getMannschaftswerte() {
        return Mannschaftswerte;
    }

    public void setMannschaftswerte(TreeMap mannschaftswerte) {
        Mannschaftswerte = mannschaftswerte;
    }

    public int getTopTransferCounter() {
        return TopTransferCounter;
    }

    public void setTopTransferCounter(int topTransferCounter) {
        TopTransferCounter = topTransferCounter;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getLiga() {
        return Liga;
    }

    public void setLiga(String liga) {
        Liga = liga;
    }

    public int getAnzahlTopSpieler() {
        return AnzahlTopSpieler;
    }

    public void setAnzahlTopSpieler(int anzahlTopSpieler) {
        AnzahlTopSpieler = anzahlTopSpieler;
    }

    public String getPunkte() {
        return Punkte;
    }

    public static String getSpieleHeim() {
        return SpieleHeim;
    }

    public static void setSpieleHeim(String spieleHeim) {
        SpieleHeim = spieleHeim;
    }

    public static String getSpieleGast() {
        return SpieleGast;
    }

    public static void setSpieleGast(String spieleGast) {
        SpieleGast = spieleGast;
    }

    public static String getPunkteHeim() {
        return PunkteHeim;
    }

    public static void setPunkteHeim(String punkteHeim) {
        PunkteHeim = punkteHeim;
    }

    public static String getPunkteGast() {
        return PunkteGast;
    }

    public static void setPunkteGast(String punkteGast) {
        PunkteGast = punkteGast;
    }

    public static String getSiegeHeim() {
        return SiegeHeim;
    }

    public static void setSiegeHeim(String siegeHeim) {
        SiegeHeim = siegeHeim;
    }

    public static String getSiegeGast() {
        return SiegeGast;
    }

    public static void setSiegeGast(String siegeGast) {
        SiegeGast = siegeGast;
    }

    public static String getNiederlagenHeim() {
        return NiederlagenHeim;
    }

    public static void setNiederlagenHeim(String niederlagenHeim) {
        NiederlagenHeim = niederlagenHeim;
    }

    public static String getNiederlagenGast() {
        return NiederlagenGast;
    }

    public static void setNiederlagenGast(String niederlagenGast) {
        NiederlagenGast = niederlagenGast;
    }

    public static String getTordifferenzHeim() {
        return TordifferenzHeim;
    }

    public static void setTordifferenzHeim(String tordifferenzHeim) {
        TordifferenzHeim = tordifferenzHeim;
    }

    public static String getTordifferenzGast() {
        return TordifferenzGast;
    }

    public static void setTordifferenzGast(String tordifferenzGast) {
        TordifferenzGast = tordifferenzGast;
    }

    public static String getFormSpiele() {
        return FormSpiele;
    }

    public static void setFormSpiele(String formSpiele) {
        FormSpiele = formSpiele;
    }

    public static String getFormPunkte() {
        return FormPunkte;
    }

    public static void setFormPunkte(String formPunkte) {
        FormPunkte = formPunkte;
    }

    public static String getFormSiege() {
        return FormSiege;
    }

    public static void setFormSiege(String formSiege) {
        FormSiege = formSiege;
    }

    public static String getFormTorDifferenz() {
        return FormTorDifferenz;
    }

    public static void setFormTorDifferenz(String formTorDifferenz) {
        FormTorDifferenz = formTorDifferenz;
    }

    public static String getEwigeSpiele() {
        return EwigeSpiele;
    }

    public static void setEwigeSpiele(String ewigeSpiele) {
        EwigeSpiele = ewigeSpiele;
    }

    public static String getEwigeSiege() {
        return EwigeSiege;
    }

    public static void setEwigeSiege(String ewigeSiege) {
        EwigeSiege = ewigeSiege;
    }

    public static String getEwigeNiederlagen() {
        return EwigeNiederlagen;
    }

    public static void setEwigeNiederlagen(String ewigeNiederlagen) {
        EwigeNiederlagen = ewigeNiederlagen;
    }

    public static String getEwigePunkte() {
        return EwigePunkte;
    }

    public static void setEwigePunkte(String ewigePunkte) {
        EwigePunkte = ewigePunkte;
    }

    public static float getSCORE() {
        return SCORE;
    }

    public void setSCORE(float SCORE) {
        this.SCORE = SCORE;
    }

    public static String getMarktwertMannschaft() {
        return MarktwertMannschaft;
    }

    public static void setMarktwertMannschaft(String marktwertMannschaft) {
        MarktwertMannschaft = marktwertMannschaft;
    }

    public static List<String> getMannschaftenBundesliga1() {
        return MannschaftenBundesliga1;
    }

    public static void setMannschaftenBundesliga1(List<String> mannschaftenBundesliga1) {
        MannschaftenBundesliga1 = mannschaftenBundesliga1;
    }

    public void setSpiele(String spiele) {
        Spiele = spiele;
    }

    public void setSiege(String siege) {
        Siege = siege;
    }

    public void setUnentschieden(String unentschieden) {
        Unentschieden = unentschieden;
    }

    public void setNiederlagen(String niederlagen) {
        Niederlagen = niederlagen;
    }

    public void setTordifferenz(String tordifferenz) {
        Tordifferenz = tordifferenz;
    }

    public static String getTorDifferenz() {
        return Tordifferenz;
    }

    public static String getFormNiederlagen() {
        return FormNiederlagen;
    }

    public static void setFormNiederlagen(String formNiederlagen) {
        FormNiederlagen = formNiederlagen;
    }

    public void setTore(String tore) {
        Tore = tore;
    }

    public String getTore() {
        return Tore;
    }

    public void setPunkte(String punkte) {
        Punkte = punkte;
    }

    public static void setFormTore(String formTore) {
        FormTore = formTore;
    }

    public String getFormTore() {
        return FormTore;
    }
}
