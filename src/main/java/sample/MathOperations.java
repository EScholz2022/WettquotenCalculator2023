package sample;

;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MathOperations {
    public static Mannschaft currentTeam = null;
    private static double finalScore = 0;

    public static String getMannschaft() {
        return mannschaft;
    }

    private static String mannschaft = "";

    public static SortedMap<String, Double> getBasicLiveScoreData() {
        return BasicLiveScoreData;
    }

    static SortedMap<String, Double> BasicLiveScoreData = new TreeMap<>();

    private static final Logger logger = LogManager.getLogger("StartPageController");


    public static double getLiveScoreForTeam(String teamname) {
        double liveScore = 0.0;
        for (String key : BasicLiveScoreData.keySet()) {
            System.out.println("current Key: " + key);
            System.out.println("compared with trim: " + teamname);
            if (key.contains(teamname)) {
                System.out.println("Keyyy matching");
                System.out.println("key " + key + " matcht " + teamname);
                liveScore = MathOperations.getBasicLiveScoreData().get(key);
                System.out.println("LIVE SCORE: " + liveScore);
                break;
            } else {
                System.out.println("Sorry, no valid team to check for Score");
                liveScore = 0.0;
            }
        }
        return liveScore;
    }


    public static void calculateScore(String mannschaft1, ReadWebContent webcontent, String marktwertMax) {

        LinkedHashMap<String, String> myValues = webcontent.getListScoreData();
        System.out.println("##Full Value Output Start##");
        System.out.println(myValues);
        System.out.println("##Full Value Output End##");

        System.out.println("Vals: " + myValues.toString());
        mannschaft = mannschaft1;
        String spiele = myValues.get("Spiele".concat(mannschaft));
        String punkte = myValues.get("Punkte".concat(mannschaft));
        String siege = myValues.get("Siege".concat(mannschaft));
        String unentschieden = myValues.get("Unentschieden".concat(mannschaft));
        String niederlagen = myValues.get("Niederlagen".concat(mannschaft));
        String tore = myValues.get("Tore".concat(mannschaft));
        String tordifferenz = myValues.get("Tordifferenz".concat(mannschaft));
        String spieleHeim = myValues.get("SpieleHeim".concat(mannschaft));
        String siegeHeim = myValues.get("SiegeHeim".concat(mannschaft));
        String niederlagenHeim = myValues.get("NiederlagenHeim".concat(mannschaft));
        String tordifferenzHeim = myValues.get("TordifferenzHeim".concat(mannschaft));
        String punkteHeim = myValues.get("PunkteHeim".concat(mannschaft));
        String spieleGast = myValues.get("SpieleGast".concat(mannschaft));
        String siegeGast = myValues.get("SiegeGast".concat(mannschaft));
        String niederlagenGast = myValues.get("NiederlagenGast".concat(mannschaft));
        String tordifferenzGast = myValues.get("TordifferenzGast".concat(mannschaft));
        String punkteGast = myValues.get("PunkteGast".concat(mannschaft));
        String ewigeSpiele = myValues.get("EwigeSpiele".concat(mannschaft));
        String ewigeSiege = myValues.get("EwigeSiege".concat(mannschaft));
        String ewigeNiederlagen = myValues.get("EwigeNiederlagen".concat(mannschaft));
        String ewigePunkte = myValues.get("EwigePunkte".concat(mannschaft));

        String formspiele = myValues.get("FormSpiele".concat(mannschaft));
        if (formspiele.equals("-") || formspiele.contains("-")) {
            formspiele = "5";
        }
        String formsiege = myValues.get("FormSiege".concat(mannschaft));
        if (formsiege.equals("not set") || formsiege.contains("not set")) {
            formsiege = "2";
        }
        String formniederlagen = myValues.get("FormNiederlagen".concat(mannschaft));
        if (formniederlagen.equals("not set") || formniederlagen.contains("not set")) {
            formniederlagen = "2";
        }
        String formtordifferenz = myValues.get("FormTorDifferenz".concat(mannschaft));
        if (formtordifferenz.equals("not set") || formtordifferenz.contains("not set")) {
            formtordifferenz = "2";
        }
        String formpunkte = myValues.get("FormPunkte".concat(mannschaft));
        if (formpunkte.equals("not set") || formpunkte.contains("not set")) {
            formpunkte = "3";
        }
        String marktwert = myValues.get("Marktwert".concat(mannschaft));
        System.out.println("gib den Marktwert her: " + marktwert);
        String topPlayer = myValues.get("Transfers".concat(mannschaft));

        //currentTeam = mannschaft;

        System.out.println("Berechne Werte für Team " + mannschaft);
        System.out.println("Marktwert to convert: " + marktwert);
        float WertKaderGesamt = (convertStringToFloat(marktwert) * 100);
        System.out.println("Marktwert nach convert: " + marktwert);
        System.out.println("Get Kaderwert Gesamt: " + WertKaderGesamt);
        System.out.println("berechnet aus Marktwert " + marktwert + " * 100");
        float PunkteProzentual = (100 / (convertStringToFloat(spiele) * 3)) * convertStringToFloat(punkte);
        System.out.println("Punkte Prozentual : " + PunkteProzentual);
        float PunkteHeimProzentual = (100 / (convertStringToFloat(spieleHeim) * 3)) * convertStringToFloat(punkteHeim);
        System.out.println("PunkteHeimProzentual : " + PunkteHeimProzentual);
        float PunkteGastProzentual = (100 / (convertStringToFloat(spieleGast) * 3)) * convertStringToFloat(punkteGast);
        System.out.println("PunkteGastProzentual : " + PunkteGastProzentual);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "siege : " + siege + "spiele : " + spiele + " VORHER");
        float SiegeProzentual = ((convertStringToFloat(siege) / convertStringToFloat(spiele)) * 100);
        System.out.println("SiegeProzentual : " + SiegeProzentual);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "SiegeProzentual : " + SiegeProzentual);
        float SiegeHeimProzentual = ((convertStringToFloat(siegeHeim) / convertStringToFloat(spieleHeim)) * 100);
        System.out.println("SiegeHeimProzentual : " + SiegeHeimProzentual);
        float SiegeGastProzentual = ((convertStringToFloat(siegeGast) / convertStringToFloat(spieleGast)) * 100);
        System.out.println("SiegeGastProzentual : " + SiegeGastProzentual);
        float NiederlagenProzentual = ((convertStringToFloat(niederlagen) / convertStringToFloat(spiele)) * 100);
        System.out.println("NiederlagenProzentual : " + NiederlagenProzentual);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "NiederlagenProzentual : " + NiederlagenProzentual);
        float NiederlagenHeimProzentual = ((convertStringToFloat(niederlagenHeim) / convertStringToFloat(spieleHeim)) * 100);
        System.out.println("NiederlagenHeimProzentual : " + NiederlagenHeimProzentual);
        float NiederlagenGastProzentual = ((convertStringToFloat(niederlagenGast) / convertStringToFloat(spieleGast)) * 100);
        System.out.println("NiederlagenGastProzentual : " + NiederlagenGastProzentual);
        float FormSiegeProzentual = ((convertStringToFloat(formsiege) / 5) * 100);
        System.out.println("FormSiegeProzentual : " + FormSiegeProzentual);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "FormSiegeProzentual : " + FormSiegeProzentual);
        float FormNiederlagenProzentual = ((convertStringToFloat(formniederlagen) / 5) * 100);
        System.out.println("FormNiederlagenProzentual : " + FormNiederlagenProzentual);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "FormNiederlagenProzentual : " + FormNiederlagenProzentual);
        float FormPunkteProzentual = ((convertStringToFloat(formpunkte) / 15) * 100);
        System.out.println("FormPunkteProzentual : " + FormPunkteProzentual);
        float ToreDifferenzGesamt = calcCorrectTorDifferenz(tordifferenz);

        //float ToreDifferenzGesamt = convertStringToFloat(tordifferenz);
        System.out.println("Tore Differenz Gesamt : " + ToreDifferenzGesamt);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "Tore Differenz Gesamt : " + ToreDifferenzGesamt);

        /**
         * volle Relevanz für Berechnung
         */
        // Wert Kader muss evtl mit 100 multipliziert werden
//        float WertKaderGesamt = (convertStringToFloat(currentTeam.getMarktwertMannschaft()) * 100);
//        System.out.println("Wert Kader: " + WertKaderGesamt);
//        float PunkteProzentual = (100 / (convertStringToFloat(currentTeam.getSpiele()) * 3)) * convertStringToFloat(currentTeam.getPunkte());
//        System.out.println("Punkte Prozentual : " + PunkteProzentual);
//        float PunkteHeimProzentual = (100 / (convertStringToFloat(currentTeam.getSpieleHeim()) * 3)) * convertStringToFloat(currentTeam.getPunkteHeim());
//        System.out.println("PunkteHeimProzentual : " + PunkteHeimProzentual);
//        float PunkteGastProzentual = (100 / (convertStringToFloat(currentTeam.getSpieleGast()) * 3)) * convertStringToFloat(currentTeam.getPunkteGast());
//        System.out.println("PunkteGastProzentual : " + PunkteGastProzentual);
//        float SiegeProzentual = ((convertStringToFloat(currentTeam.getSiege()) / convertStringToFloat(currentTeam.getSpiele())) * 100);
//        System.out.println("SiegeProzentual : " + SiegeProzentual);
//        float SiegeHeimProzentual = ((convertStringToFloat(currentTeam.getSiegeHeim()) / convertStringToFloat(currentTeam.getSpieleHeim())) * 100);
//        System.out.println("SiegeHeimProzentual : " + SiegeHeimProzentual);
//        float SiegeGastProzentual = ((convertStringToFloat(currentTeam.getSiegeGast()) / convertStringToFloat(currentTeam.getSpieleGast())) * 100);
//        System.out.println("SiegeGastProzentual : " + SiegeGastProzentual);
//        float NiederlagenProzentual = ((convertStringToFloat(currentTeam.getNiederlagen()) / convertStringToFloat(currentTeam.getSpiele())) * 100);
//        System.out.println("NiederlagenProzentual : " + NiederlagenProzentual);
//        float NiederlagenHeimProzentual = ((convertStringToFloat(currentTeam.getNiederlagenHeim()) / convertStringToFloat(currentTeam.getSpieleHeim())) * 100);
//        System.out.println("NiederlagenHeimProzentual : " + NiederlagenHeimProzentual);
//        float NiederlagenGastProzentual = ((convertStringToFloat(currentTeam.getNiederlagenGast()) / convertStringToFloat(currentTeam.getSpieleGast())) * 100);
//        System.out.println("NiederlagenGastProzentual : " + NiederlagenGastProzentual);
//        float FormSiegeProzentual = ((convertStringToFloat(currentTeam.getFormSiege()) / 5) * 100);
//        System.out.println("FormSiegeProzentual : " + FormSiegeProzentual);
//        float FormPunkteProzentual = ((convertStringToFloat(currentTeam.getFormPunkte()) / 15) * 100);
//        System.out.println("FormPunkteProzentual : " + FormPunkteProzentual);
//        float ToreDifferenzGesamt = convertStringToFloat(currentTeam.getTorDifferenz());
//        System.out.println("Tore Differenz Gesamt : " + ToreDifferenzGesamt);

        float UnentschiedenProzentual = ((convertStringToFloat(unentschieden) / convertStringToFloat(spiele)) * 100);
        System.out.println("UnentschiedenProzentual : " + UnentschiedenProzentual);
        float ToreHeimVergleich = convertStringToFloat(tordifferenzHeim);
        System.out.println("ToreHeimVergleich : " + ToreHeimVergleich);
        float ToreGastVergleich = convertStringToFloat(tordifferenzGast);
        System.out.println("ToreGastVergleich : " + ToreGastVergleich);

        float FormToreDifferenzVergleich = calcCorrectTorDifferenz(formtordifferenz);
        System.out.println("FormToreDifferenzVergleich : " + FormToreDifferenzVergleich);
        System.out.println("DEBUG " + "Team " + mannschaft1 + "FormToreDifferenzVergleich : " + FormToreDifferenzVergleich);

        float EwigeSiegeVergleich = ((convertStringToFloat(ewigeSiege) / convertStringToFloat(ewigeSpiele)) * 100);
        System.out.println("EwigeSiegeVergleich : " + EwigeSiegeVergleich);
        float EwigeNiederlagenVergleich = ((convertStringToFloat(ewigeNiederlagen) / convertStringToFloat(ewigeSpiele)) * 100);
        System.out.println("EwigeNiederlagenVergleich : " + EwigeNiederlagenVergleich);
        float EwigePunkteVergleich = convertStringToFloat(ewigePunkte) / (convertStringToFloat(ewigeSpiele) * 3) * 100;
        System.out.println("EwigePunkteVergleich : " + EwigePunkteVergleich);


        /**
         * geringe Relevanz für Berechnung
         */
//        float UnentschiedenProzentual = ((convertStringToFloat(currentTeam.getUnentschieden()) / convertStringToFloat(currentTeam.getSpiele())) * 100);
//        System.out.println("UnentschiedenProzentual : " + UnentschiedenProzentual);
//        float ToreHeimVergleich = convertStringToFloat(currentTeam.getTordifferenzGast());
//        System.out.println("ToreHeimVergleich : " + ToreHeimVergleich);
//        float ToreGastVergleich = convertStringToFloat(currentTeam.getTordifferenzGast());
//        System.out.println("ToreGastVergleich : " + ToreGastVergleich);
//        float FormToreDifferenzVergleich = convertStringToFloat(currentTeam.getFormTorDifferenz()) / 10;
//        System.out.println("FormToreDifferenzVergleich : " + FormToreDifferenzVergleich);
//        float EwigeSiegeVergleich = ((convertStringToFloat(currentTeam.getEwigeSiege()) / convertStringToFloat(currentTeam.getEwigeSpiele())) * 100);
//        System.out.println("EwigeSiegeVergleich : " + EwigeSiegeVergleich);
//        float EwigeNiederlagenVergleich = ((convertStringToFloat(currentTeam.getEwigeNiederlagen()) / convertStringToFloat(currentTeam.getEwigeSpiele())) * 100);
//        System.out.println("EwigeNiederlagenVergleich : " + EwigeNiederlagenVergleich);
//        float EwigePunkteVergleich = convertStringToFloat(currentTeam.getEwigePunkte()) / (convertStringToFloat(currentTeam.getEwigeSpiele()) * 3) * 100;
//        System.out.println("EwigePunkteVergleich : " + EwigePunkteVergleich);

        //Rang 1
        float PunkteSiegeDurchschnitt = (PunkteProzentual + PunkteHeimProzentual + PunkteGastProzentual + SiegeProzentual + SiegeHeimProzentual + SiegeGastProzentual + FormSiegeProzentual + FormPunkteProzentual) / 8;  //max 100
        System.out.println("PunkteSiegeDurchschnitt : " + PunkteSiegeDurchschnitt);
        float AlleNiederlagen = (NiederlagenProzentual + NiederlagenHeimProzentual + NiederlagenGastProzentual) / 3;
        System.out.println("AlleNiederlagen : " + AlleNiederlagen);

        double ScorePunkteNiederlagen = ((0.6 * PunkteSiegeDurchschnitt) + (0.4 * AlleNiederlagen));
        System.out.println("Erster Score: " + ScorePunkteNiederlagen);

        /**
         * Grund Score
         *          * Wert Kader 60 %  -> 815,75 Millionen
         *          * Ewige Siege 15 % -> 60 %
         *          * Ewige Niederlagen 12,5 % -> 18 %
         *          * Ewige Punkte 15 % -> 67 %
         *          * Anzahl Top Spieler
         */

        double KaderWertMax = convertStringToDouble(marktwertMax);
        System.out.println("get MarktwertMax: " + marktwertMax);
        System.out.println("get KaderwertMax: " + KaderWertMax);
        KaderWertMax = KaderWertMax * 100;
        System.out.println("Update KaderwertMax: " + KaderWertMax);
        double EwigeSiegeMax = Double.parseDouble(webcontent.getEwigeSiegeMax());
        double EwigeNiederlagenMin = Double.parseDouble(webcontent.getEwigeNiederlagenMin());
        double EwigePunkteMax = Double.parseDouble(webcontent.getEwigePunkteMax());

        System.out.println("final CALC: " + " WertKaderGesamt " + WertKaderGesamt + " dividiert durch " + KaderWertMax + " multipliziert mit 0,6");
        double scoreKader = (WertKaderGesamt / KaderWertMax) * 0.6;
        System.out.println("SCORE KADER: " + scoreKader);
        double scoreEwigeSiege = (convertStringToDouble(ewigeSiege) / EwigeSiegeMax) * 0.15;
        double scoreEwigeNiederlagen = (convertStringToDouble(ewigeNiederlagen) / EwigeNiederlagenMin) * 0.125;
        double scoreEwigePunkte = (convertStringToDouble(ewigePunkte) / EwigePunkteMax) * 0.15;
        //Anzahl der Topspieler wird ermittelt und mit Faktor 0,05 multipliziert und anschließend aufaddiert
        double scoreTopPlayerMultipklikator = convertStringToDouble(topPlayer) * 0.05;
        double GroundScoreGesamt = scoreKader + scoreEwigeNiederlagen + scoreEwigePunkte + scoreEwigeSiege + scoreTopPlayerMultipklikator;
        System.out.println("Basis Score Detail für Team " + mannschaft1 + " Kader: " + scoreKader + " ew Nieder: " + scoreEwigeNiederlagen
                + " ew Pkt: " + scoreEwigePunkte + " ew Siege: " + scoreEwigeSiege + " Top Player Multiply: " + scoreTopPlayerMultipklikator);
        System.out.println("Groundscore: " + GroundScoreGesamt);
        /**
         * Livescore
         * Spiele / Siege Verhältnis
         * Spiele / Niederlagen Verhältnis
         * Tore / Tordifferenz
         * Form Siege
         * Form Tore
         *
         * TODO: evtl Livedaten der letzten 10 Spiele einfließen lassen
         * URL: https://www.transfermarkt.de/bundesliga/formtabelle/wettbewerb/L1?saison_id=2021&min=27&max=32
         */
        double scoreLiveSiege = SiegeProzentual * 0.15;
        double scoreLiveNiederlagen = NiederlagenProzentual * 0.15;
        double scoreLiveTorDifferenz = ToreDifferenzGesamt * 0.10;
        double scoreFormSiege = FormSiegeProzentual * 0.25;
        double scoreFormNiederlagen = FormNiederlagenProzentual * 0.25;
        double scoreFormTore = FormToreDifferenzVergleich * 0.10;
        //Live Niederlagen und Form Niederlagen werden subtrahiert, der Rest wird addiert, Ergebnis ist der Live Score
        double LiveScoreGesamt = scoreLiveSiege - scoreLiveNiederlagen + scoreLiveTorDifferenz + scoreFormSiege - scoreFormNiederlagen + scoreFormTore;
        System.out.println("Statistik Score Detail für Team " + mannschaft1 + " Live Siege: " + scoreLiveSiege + " - Live Nieder: " + scoreLiveNiederlagen
                + " Live Tor: " + scoreLiveTorDifferenz + " Form Siege: " + scoreFormSiege + " - Form Nieder: " + scoreFormNiederlagen + " Form Tore: " + scoreFormTore);


        System.out.println("Live Score Gesamt: " + LiveScoreGesamt);
        double ErgebnisScore = GroundScoreGesamt + LiveScoreGesamt;
        double RoundErgebnisScore = Math.round(ErgebnisScore * 100.0) / 100.0;
        System.out.println("###### Ergebnis SCORE: " + RoundErgebnisScore + " #########");

        BasicLiveScoreData.put("Score".concat(mannschaft), RoundErgebnisScore);

        System.out.println("Data: " + BasicLiveScoreData.toString());
        System.out.println("Sorted Data Output from Basic Live Score Data:" + BasicLiveScoreData.toString());


        for (int x = 0; x < BasicLiveScoreData.size(); x++) {
            System.out.println("current live Score Value: " + BasicLiveScoreData.get("Score" + mannschaft1) + " on place " + x);
        }

        /**
         * Rang 1
         * 80,6 PunkteSiegeDurchschnitt  60 %   -> MaxValue 100
         * 15 AlleNiederlagen            40 %   -> MinValue 0
         *
         * Rang 2
         * 46 FormToreGesamtVergleich    40 %    -> MaxValue
         * 15 FormToreDifferenzVergleich 60 %    -> MaxValue
         *
         * Rang 3
         * 5 UnentschiedenProzentual     100%    -> MinValue
         *
         * Rang 4
         * 22 ToreHeimVergleich          55 %   -> MaxValue
         * 24 ToreGastVergleich          45 %   -> MaxValue
         *
         * Rang 5
         * 60 EwigeSiegeVergleich        35 %   -> MaxValue 100
         * 30 EwigeNiederlagenVergleich  35 %   -> MinValue 0
         * 67 EwigePunkteVergleich       30 %   -> MaxValue 100
         *
         */

        /**
         * Gewichtung für Score
         * Rang	Gewichtung
         * 1	40 %
         * 2	30 %
         * 3	15 %
         * 4	10 %
         * 5	5 %
         *
         * Berechnung Grundscore, max 100 Pkt
         * Wert Kader 60 %  -> 815,75 Millionen
         * Ewige Siege 15 % -> 60 %
         * Ewige Niederlagen 12,5 % -> 18 %
         * Ewige Punkte 15 % -> 67 %
         *
         * Mittelmaß Siege und Punkte ist 63,7% * 0,15 = 9,55
         * Niederlagen sind 18% * 0,125 = 2,25
         * Kader * 0,6 = 489,45
         */

        LinkedList<Float> myFloatList = new LinkedList<>();
        HashMap myHashValues = new HashMap();
        myHashValues.put("Kader Wert", WertKaderGesamt);
        myHashValues.put("Punkte", PunkteProzentual);
        myHashValues.put("PunkteHeim", PunkteHeimProzentual);
        myHashValues.put("PunkteGast", PunkteGastProzentual);
        myHashValues.put("SiegeHeim", SiegeHeimProzentual);
        myHashValues.put("SiegeGast", SiegeGastProzentual);
        myHashValues.put("Niederlagen", NiederlagenProzentual);
        myHashValues.put("NiederlagenHeim", NiederlagenHeimProzentual);
        myHashValues.put("NiederlagenGast", NiederlagenGastProzentual);
        myHashValues.put("FormSiege", FormSiegeProzentual);
        myHashValues.put("FormPunkte", FormPunkteProzentual);
        myHashValues.put("ToreDifferenz", ToreDifferenzGesamt);
        myHashValues.put("Unentschieden", UnentschiedenProzentual);
        myHashValues.put("ToreHeim", ToreHeimVergleich);
        myHashValues.put("ToreGast", ToreGastVergleich);
        myHashValues.put("FormTore", FormToreDifferenzVergleich);
        myHashValues.put("EwigeSiege", EwigeSiegeVergleich);
        myHashValues.put("EwigeNiederlagen", EwigeNiederlagenVergleich);
        myHashValues.put("EwigePunkte", EwigePunkteVergleich);

        myFloatList.add(WertKaderGesamt);
        myFloatList.add(PunkteProzentual);
        myFloatList.add(PunkteHeimProzentual);
        myFloatList.add(PunkteGastProzentual);
        myFloatList.add(SiegeProzentual);
        myFloatList.add(SiegeHeimProzentual);
        myFloatList.add(SiegeGastProzentual);
        myFloatList.add(NiederlagenProzentual);
        myFloatList.add(NiederlagenHeimProzentual);
        myFloatList.add(NiederlagenGastProzentual);
        myFloatList.add(FormSiegeProzentual);
        myFloatList.add(FormPunkteProzentual);
        myFloatList.add(ToreDifferenzGesamt);
        myFloatList.add(UnentschiedenProzentual);
        myFloatList.add(ToreHeimVergleich);
        myFloatList.add(ToreGastVergleich);
        myFloatList.add(FormToreDifferenzVergleich);
        myFloatList.add(EwigeSiegeVergleich);
        myFloatList.add(EwigeNiederlagenVergleich);
        myFloatList.add(EwigePunkteVergleich);
    }

    public static Float calcCorrectTorDifferenz(String tordifferenz) {
        boolean isNegative = false;
        float tmpTordifferenz = 0.f;
        float finalTordifferenz = 0.f;
        int firstNumber = 0;
        int secondNumber = 0;
        String tmpString = "";

        System.out.println("i got tordiff of: " + tordifferenz);

        if (tordifferenz.length() <= 1 || tordifferenz == null || tordifferenz.length() == 0 || tordifferenz.equals("-:-") || tordifferenz == "-:-") {
            System.out.println("Gotcha!");
            finalTordifferenz = (float) Math.random();
            tordifferenz = "15:7";
            return finalTordifferenz;
        }

        if (!tordifferenz.contains("-") && !tordifferenz.contains(":")) {
            float nothingChanged = (float) Math.random();
            return nothingChanged;
        }
        if (tordifferenz.contains("-")) {
            tordifferenz = tordifferenz.replaceAll("\\-", "");
            tmpTordifferenz = convertStringToFloat(tordifferenz);
            isNegative = true;
        }

        if (tordifferenz.contains(":")) {
            System.out.println("Tordiff:" + tordifferenz);
            String[] splitChars = tordifferenz.split(":");
            Iterator splitCharIterator = Arrays.stream(splitChars).iterator();
            firstNumber = Integer.parseInt(splitCharIterator.next().toString());
            secondNumber = Integer.parseInt(splitCharIterator.next().toString());
            finalTordifferenz = firstNumber - secondNumber;
        }

        if (isNegative) {
            finalTordifferenz = tmpTordifferenz - tmpTordifferenz - tmpTordifferenz;
        }
        return finalTordifferenz;
    }

    public static Float convertStringToFloat(String s) {
        System.out.println("convert String to float: " + s);
//        boolean one = s.isEmpty();
//        boolean two = s == null;
//        boolean three = s.contains("");
//        System.out.println(one);
//        System.out.println(two);
//        System.out.println(three);
//        if(three) {
//            for (int i = 0; i < s.length(); i++) {
//                System.out.println("all single chars: " + s.charAt(i));
//            }
//        }
        if (s.isEmpty() || s == null || s == "" || s.length() <= 1) {
            if (s.matches("[0-9]+")) {
                System.out.println("the String to convert is Numeric!");
                s = s.replaceAll("\\D+", "");
                float number = Float.parseFloat(s);
                return number;
            }
            float temp = (float) Math.random();
            System.out.println("is empty, so converted temp float: " + temp);
            return temp;
        }
        s = s.replaceAll("\\D+", "");
        float number = Float.parseFloat(s);
        return number;
    }

    public static Double convertStringToDouble(String s) {
        s = s.replaceAll("\\D+", "");
        double number = Double.parseDouble(s);
        return number;
    }

    public static List getSortedScoreValues() {
        List<Double> sortedScoreValues = new LinkedList<>();
        for (Double score : BasicLiveScoreData.values()) {
            sortedScoreValues.add(score);
        }
        Collections.sort(sortedScoreValues);
        System.out.println("Sorted Score Values: " + sortedScoreValues.toString());
        return sortedScoreValues;
    }

    public static List getSortedScoreKeys() {
        List<String> sortedScoreKeys = new LinkedList<>();
        for (String team : BasicLiveScoreData.keySet()) {
            team = team.replace("Score", "");
            sortedScoreKeys.add(team);
        }
        Collections.sort(sortedScoreKeys);
        System.out.println("Sorted Score Keys: " + sortedScoreKeys.toString());
        return sortedScoreKeys;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
