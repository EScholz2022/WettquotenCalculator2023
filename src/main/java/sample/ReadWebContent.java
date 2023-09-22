package sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Enrico Scholz
 * <p>
 * Class to read webcontent from URLs
 * different kinds of table data are called to read specific table informations
 * for example full game day, market values or injury informations
 * using this different data to evaluate a data base
 * @author Enrico Scholz
 * @version 1.0
 * @since 25.08.2022
 */
public class ReadWebContent {

    private static final Logger logger = LogManager.getLogger("MathOperations");


    public static LinkedHashMap<String, String> getListAllData() {
        return listAllData;
    }

    //this Map is used to store all data from teams
    static LinkedHashMap<String, String> listAllData = new LinkedHashMap<String, String>();

    public static LinkedHashMap<String, String> getListScoreData() {
        return listScoreData;
    }

    //map is used to store all score data
    static LinkedHashMap<String, String> listScoreData = new LinkedHashMap<String, String>();

    public static LinkedHashMap<String, String> getListSpieltagData() {
        return listSpieltagData;
    }

    public static LinkedHashMap<String, String> getListNextSpieltagErgebnisse() {
        return listNextSpieltagErgebnisse;
    }


    public static LinkedHashMap<String, String> getListSpieltagDataSorted() {
        listSpieltagData.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> entry = entry);
        System.out.println("Maybe sorted ListSpieltagData: " + listSpieltagData);
        return listSpieltagData;
    }

    static LinkedHashMap<String, String> listSpieltagData = new LinkedHashMap<String, String>();
    static LinkedHashMap<String, String> listNextSpieltagErgebnisse = new LinkedHashMap<String, String>();

    public static Mannschaft currentTeam = new Mannschaft();
    static LinkedList<String> TabellenDatenLiga1 = new LinkedList<String>();
    static LinkedList<String> TabellenDatenLiga1Heim = new LinkedList<>();
    static LinkedList<String> TabellenDatenLiga1Gast = new LinkedList<>();
    static LinkedList<String> TabellenDatenLiga1Form = new LinkedList<>();
    static LinkedList<String> TabellenDatenLiga1Ewig = new LinkedList<>();
    static TreeMap<String, String> MarktwerteLiga1 = new TreeMap<>();
    static TreeMap<String, Object> StuermerMarktwerte = new TreeMap<String, Object>();
    static TreeMap<String, Object> AbwehrMarktwerte = new TreeMap<String, Object>();

    private String tmpErgebnis = "";
    private static String marktwertMax = "";
    private static String ewigeSiegeMax = "";
    private static String ewigeNiederlagenMin = "";
    private static String ewigePunkteMax = "";
    static int AnzahlTopSpieler = 0;
    private Elements spieltagErgebnis = null;
    private Elements spieltagErgebnisseAlternativ = null;

    //default constructor
    public ReadWebContent() {
    }

    //constructor
    public ReadWebContent(Mannschaft mannschaft) {
        currentTeam = mannschaft;
    }

    /**
     * function is used to read all data from the current football table
     * data is shown in main table of application for all 18 teams
     *
     * @param url for the table URL
     * @throws IOException
     */
    public void readDataFullTable(String url) throws IOException {

        Document doc = org.jsoup.Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Elements tableElements = table.getElementsByTag("td");

        //adding all data
        for (Element tabledata : tableElements) {
            TabellenDatenLiga1.add(tabledata.text());
        }

        //using listCounter to iterate over data entrys
        int listCounter = 0;
        for (int x = 0; x < TabellenDatenLiga1.size(); x++) {
            if (TabellenDatenLiga1.get(x).equalsIgnoreCase(currentTeam.getName())) {
                listCounter = x;
            }
        }
        System.out.println("Listcounter: " + TabellenDatenLiga1.get(listCounter));

        //put all relevant data in list
        listAllData.put("Name" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter));
        listAllData.put("Spiele" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 1));
        listAllData.put("Siege" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 2));
        listAllData.put("Unentschieden" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 3));
        listAllData.put("Niederlagen" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 4));
        listAllData.put("Tore" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 5));
        listAllData.put("Tordifferenz" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 6));
        listAllData.put("Punkte" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 7));

        //put all relevant score data in list
        listScoreData.put("Name" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter));
        listScoreData.put("Spiele" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 1));
        listScoreData.put("Siege" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 2));
        listScoreData.put("Unentschieden" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 3));
        listScoreData.put("Niederlagen" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 4));
        listScoreData.put("Tore" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 5));
        listScoreData.put("Tordifferenz" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 6));
        listScoreData.put("Punkte" + currentTeam.getName(), TabellenDatenLiga1.get(listCounter + 7));
        System.out.println("Lange Liste: " + listAllData.toString());
    }

    /**
     * function is used to read all data from the football home table
     * data is needed for math operations to calculate basic and statistic score
     *
     * @param url for the home table URL
     * @throws IOException
     */
    public void readDataHomeTable(String url) throws IOException {

        Document doc = org.jsoup.Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Elements tableElements = table.getElementsByTag("td");

        for (Element tabledata : tableElements) {
            TabellenDatenLiga1Heim.add(tabledata.text());
        }

        int listCounter = 0;
        for (int x = 0; x < TabellenDatenLiga1Heim.size(); x++) {
            String s = TabellenDatenLiga1Heim.get(x);
            if (s.equalsIgnoreCase(currentTeam.getName())) {
                listCounter = x;
            }
        }

        //set all relevant data for object from Mannschaft class
        currentTeam.setSpieleHeim(TabellenDatenLiga1Heim.get(listCounter + 1));
        currentTeam.setSiegeHeim(TabellenDatenLiga1Heim.get(listCounter + 2));
        currentTeam.setNiederlagenHeim(TabellenDatenLiga1Heim.get(listCounter + 4));
        currentTeam.setTordifferenzHeim(TabellenDatenLiga1Heim.get(listCounter + 6));
        currentTeam.setPunkteHeim(TabellenDatenLiga1Heim.get(listCounter + 7));

        //put all relevant data in list
        listScoreData.put("SpieleHeim" + currentTeam.getName(), TabellenDatenLiga1Heim.get(listCounter + 1));
        listScoreData.put("SiegeHeim" + currentTeam.getName(), TabellenDatenLiga1Heim.get(listCounter + 2));
        listScoreData.put("NiederlagenHeim" + currentTeam.getName(), TabellenDatenLiga1Heim.get(listCounter + 4));
        listScoreData.put("TordifferenzHeim" + currentTeam.getName(), TabellenDatenLiga1Heim.get(listCounter + 6));
        listScoreData.put("PunkteHeim" + currentTeam.getName(), TabellenDatenLiga1Heim.get(listCounter + 7));
    }

    /**
     * function is used to read all data from the football guest table
     * data is needed for math operations to calculate basic and statistic score
     *
     * @param url for the guest table URL
     * @throws IOException
     */
    public void readDataGastTable(String url) throws IOException {

        Document doc = org.jsoup.Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Elements tableElements = table.getElementsByTag("td");

        for (Element tabledata : tableElements) {
            TabellenDatenLiga1Gast.add(tabledata.text());
        }

        int listCounter = 0;
        for (int x = 0; x < TabellenDatenLiga1Gast.size(); x++) {
            String s = TabellenDatenLiga1Gast.get(x);
            if (s.equalsIgnoreCase(currentTeam.getName())) {
                listCounter = x;
            }
        }

        //set all relevant data for object from Mannschaft class
        currentTeam.setSpieleGast(TabellenDatenLiga1Gast.get(listCounter + 1));
        currentTeam.setSiegeGast(TabellenDatenLiga1Gast.get(listCounter + 2));
        currentTeam.setNiederlagenGast(TabellenDatenLiga1Gast.get(listCounter + 4));
        currentTeam.setTordifferenzGast(TabellenDatenLiga1Gast.get(listCounter + 6));
        currentTeam.setPunkteGast(TabellenDatenLiga1Gast.get(listCounter + 7));

        listScoreData.put("SpieleGast" + currentTeam.getName(), TabellenDatenLiga1Gast.get(listCounter + 1));
        listScoreData.put("SiegeGast" + currentTeam.getName(), TabellenDatenLiga1Gast.get(listCounter + 2));
        listScoreData.put("NiederlagenGast" + currentTeam.getName(), TabellenDatenLiga1Gast.get(listCounter + 4));
        listScoreData.put("TordifferenzGast" + currentTeam.getName(), TabellenDatenLiga1Gast.get(listCounter + 6));
        listScoreData.put("PunkteGast" + currentTeam.getName(), TabellenDatenLiga1Gast.get(listCounter + 7));
    }

    /**
     * function is used to read all data from the eternal table
     * data is needed for math operations to calculate basic and statistic score
     *
     * @param url for the eternal table URL
     * @throws IOException
     */
    public void readDataEwigeTabelle(String url) throws IOException {

        Document doc = org.jsoup.Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Elements tableElements = table.getElementsByTag("td");

        for (Element tabledata : tableElements) {
            TabellenDatenLiga1Ewig.add(tabledata.text());
        }

        //get relevant data from indexes
        ewigeSiegeMax = TabellenDatenLiga1Ewig.get(4);
        ewigeNiederlagenMin = TabellenDatenLiga1Ewig.get(6);
        ewigePunkteMax = TabellenDatenLiga1Ewig.get(9);

        int listCounter = 0;
        for (int x = 0; x < TabellenDatenLiga1Ewig.size(); x++) {
            String s = TabellenDatenLiga1Ewig.get(x);
            if (s.contains(currentTeam.getName()) || currentTeam.getName().contains(s)) {
                listCounter = x;
                break;
            }
        }
        //set all relevant data for object from Mannschaft class
        currentTeam.setEwigeSpiele(TabellenDatenLiga1Ewig.get(listCounter + 2));
        currentTeam.setEwigeSiege(TabellenDatenLiga1Ewig.get(listCounter + 3));
        currentTeam.setEwigeNiederlagen(TabellenDatenLiga1Ewig.get(listCounter + 5));
        currentTeam.setEwigePunkte(TabellenDatenLiga1Ewig.get(listCounter + 8));

        //put all relevant data in score list
        listScoreData.put("EwigeSpiele" + currentTeam.getName(), TabellenDatenLiga1Ewig.get(listCounter + 2));
        listScoreData.put("EwigeSiege" + currentTeam.getName(), TabellenDatenLiga1Ewig.get(listCounter + 3));
        listScoreData.put("EwigeNiederlagen" + currentTeam.getName(), TabellenDatenLiga1Ewig.get(listCounter + 5));
        listScoreData.put("EwigePunkte" + currentTeam.getName(), TabellenDatenLiga1Ewig.get(listCounter + 8));

    }

    /**
     * function is used to read all data from the market value table
     * data is needed for math operations to calculate basic and statistic score
     * there is also a connection with player market values
     *
     * @param url for the market value table URL
     * @throws IOException
     */
    public static void readMarktWert(String url) throws IOException {

        Document doc = org.jsoup.Jsoup.connect(url).get();
        Element table = doc.getElementById("yw1");
        Elements tableValues = table.getElementsByClass("rechts hauptlink");
        Elements tableTitle = table.getElementsByClass("hauptlink no-border-links");
        String marktwert = "";
        String mannschaft = "";

        List<String> listElements = new LinkedList();
        for (Element elementTitle : tableTitle) {
            mannschaft = elementTitle.text();
            listElements.add(mannschaft);
        }

        List<String> listMarktwerte = new LinkedList();
        for (Element elementValue : tableValues) {
            marktwert = elementValue.text();
            listMarktwerte.add(marktwert);
        }
        marktwertMax = listMarktwerte.get(0);

        for (int x = 0; x < listElements.size(); x++) {
            MarktwerteLiga1.put(listElements.get(x), listMarktwerte.get(x));
        }

        //get market value entrys and store them in different kind of lists
        //need special cases to handle team Strings correctly
        Set<Map.Entry<String, String>> marktwerteintraege = MarktwerteLiga1.entrySet();
        marktwerteintraege.forEach(entry -> {
            if (entry.getKey().contains(currentTeam.getName())) {
                String marktWert = entry.getValue();
                currentTeam.MarktwertMannschaft = marktWert;
                listAllData.put("Marktwert" + currentTeam.getName(), marktWert);
                listScoreData.put("Marktwert" + currentTeam.getName(), marktWert);
            } else if (currentTeam.getName().contains("Leipzig")) {
                listAllData.put("Marktwert" + currentTeam.getName(), MarktwerteLiga1.get("RasenBallsport Leipzig"));
                listScoreData.put("Marktwert" + currentTeam.getName(), MarktwerteLiga1.get("RasenBallsport Leipzig"));
            } else if (currentTeam.getName().contains("gladbach")) {
                listAllData.put("Marktwert" + currentTeam.getName(), MarktwerteLiga1.get("Borussia Mönchengladbach"));
                listScoreData.put("Marktwert" + currentTeam.getName(), MarktwerteLiga1.get("Borussia Mönchengladbach"));
            } else if (currentTeam.getName().contains("Bremen")) {
                listAllData.put("Marktwert" + "SVWerder" + currentTeam.getName(), MarktwerteLiga1.get("SV Werder Bremen"));
                listScoreData.put("Marktwert" + "SVWerder" + currentTeam.getName(), MarktwerteLiga1.get("SV Werder Bremen"));
            }
        });
    }

    /**
     * function is used to read all data from the team form table
     * data is needed for math operations to calculate basic and statistic score
     *
     * @param url for the team form table URL
     * @throws IOException
     */
    public static void readDataFormTable(String url) throws IOException {

        Document doc = org.jsoup.Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Elements tableElements = table.getElementsByTag("td");

        for (Element tabledata : tableElements) {
            TabellenDatenLiga1Form.add(tabledata.text());
        }

        int listCounter = 0;
        for (int x = 0; x < TabellenDatenLiga1Form.size(); x++) {
            String s = TabellenDatenLiga1Form.get(x);
            if (s.equalsIgnoreCase(currentTeam.getName())) {
                listCounter = x;
            }
        }

        //set all relevant data for object from Mannschaft class
        currentTeam.setFormSpiele(TabellenDatenLiga1Form.get(listCounter + 1));
        currentTeam.setFormSiege(TabellenDatenLiga1Form.get(listCounter + 2));
        currentTeam.setFormNiederlagen(TabellenDatenLiga1Form.get(listCounter + 4));
        currentTeam.setFormTorDifferenz(TabellenDatenLiga1Form.get(listCounter + 5));
        currentTeam.setFormPunkte(TabellenDatenLiga1Form.get(listCounter + 7));

        //put all relevant data in score list
        listScoreData.put("FormSpiele" + currentTeam.getName(), TabellenDatenLiga1Form.get(listCounter + 1));
        listScoreData.put("FormSiege" + currentTeam.getName(), TabellenDatenLiga1Form.get(listCounter + 2));
        listScoreData.put("FormNiederlagen" + currentTeam.getName(), TabellenDatenLiga1Form.get(listCounter + 4));
        listScoreData.put("FormTorDifferenz" + currentTeam.getName(), TabellenDatenLiga1Form.get(listCounter + 5));
        listScoreData.put("FormPunkte" + currentTeam.getName(), TabellenDatenLiga1Form.get(listCounter + 7));

        //using form goals to calculate own created goal difference
        String tmpFormTore = TabellenDatenLiga1Form.get(listCounter + 5);
        float formTore = MathOperations.calcCorrectTorDifferenz(tmpFormTore);
        currentTeam.setFormTore(String.valueOf(formTore));
        listScoreData.put("FormTore" + currentTeam.getName(), String.valueOf(formTore));

        System.out.println("Teamname listScoreData: " + currentTeam.getName());
    }

    /**
     * function is used to read striker market values from URL
     * data is needed to define top players of team
     *
     * @param urlStuermer for the striker goal table URL
     * @throws IOException
     */
    public static void readSpielerMarktwert(String urlStuermer) throws IOException {

        Document docStuermer = Jsoup.connect(urlStuermer).get();
        Element table = docStuermer.select("table").get(3);
        Elements tableValues = table.getElementsByClass("hauptlink");
        Elements tableImages = docStuermer.getElementsByTag("a");

        //put striker name and striker market value in TreeMap
        String Stuermer = "";
        Object StuermerMarktwert = "";
        for (int i = 0; i < (tableValues.size() / 2); i++) {
            int y = i + 1;
            if (StuermerMarktwerte.isEmpty()) {
                Stuermer = tableValues.get(i).text();
                StuermerMarktwert = tableValues.get(y).text();
                StuermerMarktwerte.put(Stuermer, StuermerMarktwert);
            } else {
                int z = i * 2;
                Stuermer = tableValues.get(z).text();
                StuermerMarktwert = tableValues.get(z + 1).text();
                StuermerMarktwerte.put(Stuermer, StuermerMarktwert);
            }
        }

        //using this list to store the number of top strikers from team
        LinkedList transferListe = new LinkedList();
        for (Element element : tableImages) {
            String tmpVerein = element.attr("title").toString();
            // break line for DIE LETZTEN MARKTWERT-UPDATES
            if (tmpVerein.equals("Seite 1")) {
                break;
            }
            if (tmpVerein != "" && Mannschaft.getMannschaftenBundesliga1().toString().contains(tmpVerein)) {
                transferListe.add(tmpVerein);
            }
        }
        //remove first 2 entries because of Rekordmeister and Amtierender Meister
        transferListe.removeFirst();
        transferListe.removeFirst();

        for (int x = 0; x < transferListe.size(); x++) {
            String s = (String) transferListe.get(x);
            if (s.contains(currentTeam.getName())) {
                AnzahlTopSpieler++;
            }
        }
        currentTeam.setAnzahlTopSpieler(AnzahlTopSpieler);
        listAllData.put("Transfers" + currentTeam.getName(), String.valueOf(AnzahlTopSpieler));
        listScoreData.put("Transfers" + currentTeam.getName(), String.valueOf(AnzahlTopSpieler));
        AnzahlTopSpieler = 0;
    }

    /**
     * function is used to read current game day data
     * all game results are needed for math operations to calculate basic and statistic score
     * using fixed table data URL in this function
     *
     * @param choosedSpieltag for the user selection of all 34 game days
     * @param choosedSaison   for the user selection of season
     * @throws IOException
     */
    public void readSpieltag(String choosedSpieltag, String choosedSaison) throws IOException {

        String tmpURL = "https://www.bundesliga.com/de/bundesliga/spieltag";
        String TmpURLSaisonSpieltag = "";
        TmpURLSaisonSpieltag = tmpURL + ("/") + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(12, 16) + ("/") + choosedSpieltag;
        System.out.println("TmpURLSaisonSpieltag: " + TmpURLSaisonSpieltag);

        //operating with String manipulation to get relevant characters
        String ansetzungURL = "https://www.dfb.de/bundesliga/spieltagtabelle/";
        String ansetzungURLSaisonSpieltag = "";
        ansetzungURLSaisonSpieltag = ansetzungURL + "/?spieledb_path=datencenter/bundesliga/" + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(14, 16)
                + "/current&spieledb_path=%2Fde%2Fcompetitions%2Fbundesliga%2Fseasons%2F" + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(14, 16)
                + "%2Fmatchday%2F" + choosedSpieltag;
        System.out.println("ansetzungURLSaisonSpieltag: " + ansetzungURLSaisonSpieltag);

        Document doc = org.jsoup.Jsoup.connect(TmpURLSaisonSpieltag).get();
        Elements spieltagTeams = doc.getElementsByClass("name d-none d-md-block");
        spieltagErgebnis = doc.getElementsByClass("score ng-star-inserted");
        spieltagErgebnisseAlternativ = doc.getElementsByClass("tlc");
        Document docAnsetzung = org.jsoup.Jsoup.connect(ansetzungURLSaisonSpieltag).get();
        Elements ansetzungElements = docAnsetzung.getElementsByClass("column-date");

        //1. put game day appointment in HashMap
        for (int i = 0; i < spieltagTeams.size(); i++) {
            if (i % 2 == 0) {
                listSpieltagData.put("heimmannschaft" + i, spieltagTeams.get(i).text());
            } else {
                listSpieltagData.put("gastmannschaft" + i, spieltagTeams.get(i).text());
            }
        }

        //2. put game day results in HashMap
        if (spieltagErgebnis.size() != 0) {
            for (int y = 0; y < spieltagErgebnis.size(); y++) {
                if (y % 2 == 0) {
                    tmpErgebnis = spieltagErgebnis.get(y).text().concat(" : ".concat(spieltagErgebnis.get(y + 1).text()));
                    listSpieltagData.put("ergebnis" + y, tmpErgebnis);
                } else {
                    tmpErgebnis = "tba";
                    listSpieltagData.put("ergebnis" + y, tmpErgebnis);
                }
            }
        } else {
            for (int i = 0; i < spieltagErgebnisseAlternativ.size(); i++) {
                if (i % 2 == 0) {
                    tmpErgebnis = spieltagErgebnisseAlternativ.get(i).text().concat(" : ".concat(spieltagErgebnisseAlternativ.get(i + 1).text()));
                    listSpieltagData.put("ergebnis" + i, tmpErgebnis);
                } else {
                    tmpErgebnis = "tba";
                    listSpieltagData.put("ergebnis" + i, tmpErgebnis);
                }
            }
        }

        //3. handle game day appointment date and tv channel / streaming service
        ansetzungElements.remove(ansetzungElements.first());
        for (int i = 0; i < ansetzungElements.size(); i++) {
            Element element = ansetzungElements.get(i);
            String ansetzung = element.text();
            ansetzung = ansetzung.substring(0, (ansetzung.length() - 4));

            listSpieltagData.put("ansetzung" + i, ansetzung);
            if (listSpieltagData.get("ansetzung" + i) == null) {
                listSpieltagData.put("ansetzung" + i + 1, ansetzung);
            }
            if (listSpieltagData.get("ansetzung" + i).contains("Freitag") || listSpieltagData.get("ansetzung" + i).contains("Sonntag")) {
                listSpieltagData.put("uebertragung" + (i + 1), "DAZN");
            } else {
                listSpieltagData.put("uebertragung" + (i + 1), "Sky");
            }
            System.out.println("Ansetzung: " + getListSpieltagData().get("ansetzung" + i));
        }

        System.out.println("Spieltag: " + listSpieltagData.toString());
    }

    /**
     * function is used to read game day data from following week
     * all game results are needed for math operations to calculate basic and statistic score
     * using fixed table data URL in this function
     *
     * @param choosedSpieltag for the user selection of all 34 game days
     * @param choosedSaison   for the user selection of season
     * @throws IOException
     */
    public void readNextSpieltag(String choosedSpieltag, String choosedSaison) throws IOException {

        String tmpURL = "https://www.bundesliga.com/de/bundesliga/spieltag";
        String TmpURLSaisonSpieltag = "";
        TmpURLSaisonSpieltag = tmpURL + ("/") + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(12, 16) + ("/") + choosedSpieltag;
        System.out.println("TmpURLSaisonSpieltag: " + TmpURLSaisonSpieltag);

        //get specific string characters
        String ansetzungURL = "https://www.dfb.de/bundesliga/spieltagtabelle/";
        String ansetzungURLSaisonSpieltag = "";
        ansetzungURLSaisonSpieltag = ansetzungURL + "/?spieledb_path=datencenter/bundesliga/" + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(14, 16)
                + "/current&spieledb_path=%2Fde%2Fcompetitions%2Fbundesliga%2Fseasons%2F" + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(14, 16)
                + "%2Fmatchday%2F" + choosedSpieltag;
        System.out.println("ansetzungURLSaisonSpieltag: " + ansetzungURLSaisonSpieltag);

        Document doc = org.jsoup.Jsoup.connect(TmpURLSaisonSpieltag).get();
        Elements spieltagTeams = doc.getElementsByClass("name d-none d-md-block");
        Document docAnsetzung = org.jsoup.Jsoup.connect(ansetzungURLSaisonSpieltag).get();
        Elements ansetzungElements = docAnsetzung.getElementsByClass("column-date");

        //1. store game day teams
        for (int i = 0; i < spieltagTeams.size(); i++) {
            if (i % 2 == 0) {
                listSpieltagData.put("heimmannschaft" + i, spieltagTeams.get(i).text());
            } else {
                listSpieltagData.put("gastmannschaft" + i, spieltagTeams.get(i).text());
            }
        }

        //remove first line with table header
        ansetzungElements.remove(ansetzungElements.first());

        //2. store game day appointment
        for (int i = 0; i < ansetzungElements.size(); i++) {
            Element element = ansetzungElements.get(i);
            String ansetzung = element.text();
            ansetzung = ansetzung.substring(0, (ansetzung.length() - 4));

            listSpieltagData.put("ansetzung" + i, ansetzung);
            if (listSpieltagData.get("ansetzung" + i) == null) {
                listSpieltagData.put("ansetzung" + i + 1, ansetzung);
            }
        }
    }

    /**
     * function is used to read game day data from specific week
     * all game results are needed for math operations to calculate basic and statistic score
     * using fixed table data URL in this function
     *
     * @param choosedSpieltag for the user selection of all 34 game days
     * @param choosedSaison   for the user selection of season
     * @throws IOException
     */
    public void readSpecificSpieltag(String choosedSpieltag, String choosedSaison) throws IOException {

        String tmpURL = "https://www.bundesliga.com/de/bundesliga/spieltag";
        String TmpURLSaisonSpieltag = "";
        TmpURLSaisonSpieltag = tmpURL + ("/") + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(12, 16) + ("/") + choosedSpieltag;
        System.out.println("TmpURLSaisonSpieltag: " + TmpURLSaisonSpieltag);

        //get specific string characters
        String ansetzungURL = "https://www.dfb.de/bundesliga/spieltagtabelle/";
        String ansetzungURLSaisonSpieltag = "";
        ansetzungURLSaisonSpieltag = ansetzungURL + "/?spieledb_path=datencenter/bundesliga/" + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(14, 16)
                + "/current&spieledb_path=%2Fde%2Fcompetitions%2Fbundesliga%2Fseasons%2F" + choosedSaison.substring(7, 11) + "-" + choosedSaison.substring(14, 16)
                + "%2Fmatchday%2F" + choosedSpieltag;
        System.out.println("ansetzungURLSaisonSpieltag: " + ansetzungURLSaisonSpieltag);

        Document doc = org.jsoup.Jsoup.connect(TmpURLSaisonSpieltag).get();
        Elements spieltagTeams = doc.getElementsByClass("name d-none d-md-block");
        Document docAnsetzung = org.jsoup.Jsoup.connect(ansetzungURLSaisonSpieltag).get();
        Elements ansetzungElements = docAnsetzung.getElementsByClass("column-date");

        //1. store game day teams
        for (int i = 0; i < spieltagTeams.size(); i++) {
            if (i % 2 == 0) {
                listSpieltagData.put("heimmannschaft" + i, spieltagTeams.get(i).text());
            } else {
                listSpieltagData.put("gastmannschaft" + i, spieltagTeams.get(i).text());
            }
        }

        //remove first line with table header
        ansetzungElements.remove(ansetzungElements.first());

        //2. store game day appointment
        for (int i = 0; i < ansetzungElements.size(); i++) {
            Element element = ansetzungElements.get(i);
            String ansetzung = element.text();
            ansetzung = ansetzung.substring(0, (ansetzung.length() - 4));

            listSpieltagData.put("ansetzung" + i, ansetzung);
            if (listSpieltagData.get("ansetzung" + i) == null) {
                listSpieltagData.put("ansetzung" + i + 1, ansetzung);
            }
        }

        System.out.println("Next Spieltag: " + listSpieltagData.toString());
    }

    /**
     * function is used to read health informations of teams and players
     * need this specific data to precise the statistic score of team
     * if team has 1 injured player at least, specific score is going lower
     * data is seperated in real injury and corona loss
     *
     * @param injuryURL for the data table of injury list
     * @throws IOException
     */
    public static List readInjuryCoronaPlayer(String injuryURL) throws IOException {

        System.out.println("#INFOPRINT read Injury Player List Method called");
        Document docInjuryCorona = Jsoup.connect(injuryURL).get();
        Element table = docInjuryCorona.select("table").get(0);
        Elements tableTeams = docInjuryCorona.getElementsByClass("zentriert no-border-rechts");
        Elements tdElements = docInjuryCorona.getElementsByTag("td");

        //save injury team names to compare
        List<String> injuryTeams = new ArrayList<>();
        for (Element element : tableTeams) {
            injuryTeams.add(element.getElementsByAttribute("alt").attr("alt"));
        }

        System.out.println("Size Table Teams: " + tableTeams.size());
        System.out.println("Size TD Elements: " + tdElements.size());
        System.out.println("Size injury Teams: " + injuryTeams.size());
        System.out.println("All injury Teams: " + injuryTeams.toString());
        System.out.println("Count Duplicates in Injury List: ");

        //using java 8 streams to collect how many injuries each team has
        List<Map.Entry<String, Long>> injuriesPerTeam = injuryTeams.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                // Convert back to stream to filter
                .stream()
                .filter(element -> element.getValue() > 1)
                // Collect elements to List and print out the values
                .collect(Collectors.toList());
        System.out.println("Injuries per Team: " + injuriesPerTeam.toString());

        /**
         * java 8 stream test printouts
         */
        //System.out.println("Result Match Team Lists: " + injuryTeams.stream().allMatch(str -> Mannschaft.getMannschaftenBundesliga1().contains(str)));
        //System.out.println("Manns: " + Mannschaft.getMannschaftenBundesliga1().get(2) + Mannschaft.getMannschaftenBundesliga1().toString());
        //System.out.println("Manns java 8: ");
        //Mannschaft.getMannschaftenBundesliga1().stream()
        //        .filter(s -> s.length() > 2)
        //        .forEach(System.out::println);

        //reading specific injury informations
        Elements players = table.getElementsByClass("hauptlink");
        Elements injuryQuitDate = table.getElementsByClass("zentriert");
        Elements playerValue = table.getElementsByClass("rechts");

        //remove table header
        playerValue.remove(playerValue.first());

        //using selection, to add all data, that can be removed later at once
        //removing empty injury quit date entries
        //removing entries with less than 4 chars, that entries are years of player
        Elements removable = new Elements();
        for (Element element : injuryQuitDate) {
            if (element.text() == "" || element.text().isEmpty() || element.text().length() < 4) {
                removable.add(element);
            }
        }

        //every uneven (ungerade) line can be removed
        for (int remove = 0; remove < injuryQuitDate.size(); remove++) {
            Element element = injuryQuitDate.get(remove);
            if (remove % 2 != 0) {
                removable.add(element);
            }
        }

        //trim operations, remove table header
        injuryQuitDate.remove(injuryQuitDate.first());
        injuryQuitDate.remove(injuryQuitDate.first());
        injuryQuitDate.remove(injuryQuitDate.first());
        injuryQuitDate.remove(injuryQuitDate.first());
        injuryQuitDate.remove(injuryQuitDate.first());

        //remove now all the collected table data in selection called removable
        injuryQuitDate.removeAll(removable);

        //test printout for specific injury informations
        players.forEach(element -> System.out.println("players: " + element.text()));
        injuryQuitDate.forEach(element -> System.out.println("injury: " + element.text()));
        playerValue.forEach(element -> System.out.println("playervalue: " + element.text()));

        System.out.println("Size of Teams with Injury: " + injuriesPerTeam.size());
        System.out.println("Size players: " + players.size());
        System.out.println("Size injury: " + injuryQuitDate.size());
        System.out.println("Size playervalue: " + playerValue.size());

        //need all injury informations in 1 string
        List allInjuryInfosOneString = new ArrayList();

        for (int x = 0; x < players.size(); x++) {

            /**
             * concat 6 informations
             * TEAM
             * PLAYER
             * INJURY-QUIT-DATE
             * PLAYER-MARKET-VALUE
             * GAME-MARKET-VALUE
             * HARD INJURY - in case of PLAYER MARKET VALUE is 5% of TEAM MARKET VALUE
             */
            String team = injuryTeams.get(x);
            team = trimStringValueToCompare(team);
            if (team.contains("BayernMunich")) {
                team = "Bayern";
            }
            if (team.contains("SVDarmstadt")) {
                team = "Darmstadt";
            }
            String player = players.get(x).text().concat("__");
            String injury = injuryQuitDate.get(x).text().concat("__");
            String pValue = playerValue.get(x).text();
            if (pValue.equals("-") || pValue.contains("-")) {
                pValue = "500000";
            }
            System.out.println("current pValue: " + pValue);
            String extraConcat = "__";
            String pValueTrim = pValue.replace(",", "");
            pValueTrim = pValueTrim.replace(" ", "");
            if (pValueTrim.contains("Mio.€")) {
                pValueTrim = pValueTrim.replace("Mio.€", "0000");
            }
            if (pValueTrim.contains("Tsd.€")) {
                pValueTrim = pValueTrim.replace("Tsd.€", "000");
            }
            System.out.println("Team: " + team + " PlayerMarktwert: " + pValueTrim);
            long playerMarktwert = Long.parseLong(pValueTrim);
            System.out.println("Check result trimmed Player Marktwert: " + playerMarktwert);
            System.out.println("Aufruf Marktliste aus listScoreData mit: " + team + " also Marktwert" + team);
            System.out.println("ListScoreDataContent: " + listScoreData.toString());
            String marktwert = listScoreData.get("Marktwert".concat(team));
            String tValue = marktwert;
            System.out.println("##tvalue1: " + tValue);

            tValue = tValue.replace(" Mio. €", "");
            tValue = tValue.replace(",", "000000");
            tValue = tValue.substring(0, tValue.length() - 2);

            long teamMarktwert = Long.parseLong(tValue);
            System.out.println("##teamMW: " + teamMarktwert);
            System.out.println("Check teamMarktwert: " + tValue);
            String concat5TeamPlayerInjuryDatePlayerValueTeamValue = team.concat(extraConcat).concat(player).concat(injury).concat(pValueTrim).concat(extraConcat).concat(tValue);

            //HARD INJURY - in case of PLAYER MARKET VALUE is 5% of TEAM MARKET VALUE
            if (playerMarktwert / teamMarktwert > 0.05) {
                concat5TeamPlayerInjuryDatePlayerValueTeamValue = concat5TeamPlayerInjuryDatePlayerValueTeamValue.concat(extraConcat).concat("HardInjury");
            }
            allInjuryInfosOneString.add(concat5TeamPlayerInjuryDatePlayerValueTeamValue);
        }

        System.out.println("Output Injury List: " + allInjuryInfosOneString + "\t\n");
        return allInjuryInfosOneString;
    }

    /**
     * function is used to read game day appointments for following week
     * team matchup is needed as placeholder, to add real result later
     * for beginning, team matchup appointment is stored in ows XLS column
     * using fixed game day URL in this case
     *
     * @throws IOException
     */
    public void readErgebnisseNextSpieltagForXLS() throws IOException {
        String nextSpieltagURL = "https://www.bundesliga.com/de/bundesliga/spieltag";
        Document doc = org.jsoup.Jsoup.connect(nextSpieltagURL).get();
        Elements spieltagErgebnisseBlank = doc.getElementsByClass("tlc");
        logger.info("Next SPIELTAG ergs text: " + spieltagErgebnisseBlank.text());
        logger.info("Spieltag Ergebnisse Size: " + spieltagErgebnisseBlank.size());

        int y = 0;
        //getting specific team names from game day appointment of next week
        for (int x = 0; x < spieltagErgebnisseBlank.size(); x = x + 2) {
            y = x + 1;
            Element firstElement = spieltagErgebnisseBlank.get(x);
            Element secondElement = spieltagErgebnisseBlank.get(y);
            String tmpfirstElement = firstElement.text();
            String tmpsecondElement = secondElement.text();
            listNextSpieltagErgebnisse.put(tmpfirstElement, tmpsecondElement);
        }
        logger.info("listNextSpieltagErgebnisse: " + listNextSpieltagErgebnisse.toString());
    }

    /**
     * function to flush specific lists and reset specific values
     */
    public void flushAllLists() {
        listSpieltagData.clear();
        listAllData.clear();
        TabellenDatenLiga1.clear();
        TabellenDatenLiga1Gast.clear();
        TabellenDatenLiga1Heim.clear();
        TabellenDatenLiga1Ewig.clear();
        TabellenDatenLiga1Form.clear();
        MarktwerteLiga1.clear();
        StuermerMarktwerte.clear();
        AbwehrMarktwerte.clear();
        tmpErgebnis = "";
        AnzahlTopSpieler = 0;
    }

    /**
     * helper function to printout information
     */
    public static void printOutCheck() {

        System.out.println("Aktuelles Team: " + currentTeam.getName());
        System.out.println("Position = " + currentTeam.getPosition());
        System.out.println("Spiele = " + currentTeam.getSpiele());
        System.out.println("Siege = " + currentTeam.getSiege());
        System.out.println("Unentschieden =  " + currentTeam.getUnentschieden());
        System.out.println("Niederlagen = " + currentTeam.getNiederlagen());
        System.out.println("Tore = " + currentTeam.getTore());
        System.out.println("Tordifferenz = " + currentTeam.getTorDifferenz());
        System.out.println("Punkte = " + currentTeam.getPunkte());
        System.out.println("Marktwert = " + currentTeam.getMarktwertMannschaft());
        System.out.println("PunkteHeim = " + currentTeam.getPunkteHeim());
        System.out.println("PunkteGast= " + currentTeam.getPunkteGast());
        System.out.println("SiegeHeim = " + currentTeam.getSiegeHeim());
        System.out.println("SiegeGast = " + currentTeam.getSiegeGast());
        System.out.println("NiederlagenHeim = " + currentTeam.getNiederlagenHeim());
        System.out.println("NiederlageGast = " + currentTeam.getNiederlagenGast());
        System.out.println("TordifferenzHeim = " + currentTeam.getTordifferenzHeim());
        System.out.println("TordifferenzGast = " + currentTeam.getTordifferenzGast());
        System.out.println("FormSpiele = " + currentTeam.getFormSpiele());
        System.out.println("FormPunkte = " + currentTeam.getFormPunkte());
        System.out.println("FormSiege = " + currentTeam.getFormSiege());
        System.out.println("FormTorDifferenz = " + currentTeam.getFormTorDifferenz());
        System.out.println("FormTore = " + currentTeam.getFormTore());
        System.out.println("EwigeSpiele = " + currentTeam.getEwigeSpiele());
        System.out.println("EwigeSiege = " + currentTeam.getEwigeSiege());
        System.out.println("EwigeNiederlagen = " + currentTeam.getEwigeNiederlagen());
        System.out.println("EwigePunkte = " + currentTeam.getEwigePunkte());
        System.out.println("Anzahl TopTransfers = " + currentTeam.getAnzahlTopSpieler());
    }

    /**
     * function converts given String to a float value
     * using regex to perform operation
     *
     * @param s for the given string
     * @return number as the converted float value
     */
    public static Float convertStringToFloat(String s) {
        s = s.replaceAll("\\D+", "");
        float number = Float.parseFloat(s);
        return number;
    }

    /**
     * function converts given String to a double value
     * using regex to perform operation
     *
     * @param s for the given string
     * @return number as the converted double value
     */
    public static Double convertStringToDouble(String s) {
        s = s.replaceAll("\\D+", "");
        double number = Double.parseDouble(s);
        return number;
    }

    /**
     * function is used to trim game name string in specific kind
     * some numbers, letters, signs and team name constellations are not allowed
     *
     * @param mannschaft for the specific team to trim
     * @return trimmed team name
     */
    private static String trimStringValueToCompare(String mannschaft) {

        String trimNumbers = mannschaft.replaceAll("[0-9]", "");
        String trimLetters = trimNumbers.replace("VfL", "")
                .replace("Vfl", "").replace("FC", "")
                .replace("SC", "").replace("TSG", "")
                .replace("FSV", "").replace("SpVgg ", "")
                .replace("VfB", "").replace("Vfl ", "");
        String trimTeams = trimLetters.replace("RasenBallsport", "RB ").replace("Berlin", "")
                .replace("Borussia", "").replace("Eintracht", "").replace("München", "")
                .replace("Arminia", "").replace("Greuther", "").replace("Bayer ", "")
                .replace("Mönchengladbach", "M'gladbach");
        String trimValue = trimTeams.replace(" ", "").replace(".", "");
        String trimExtraValueLeipzig = trimValue.replace("RBLeipzig", "RB Leipzig");
        String trimExtraValueBochum = trimExtraValueLeipzig.replace("Bochum", "VfL Bochum");
        String trimExtraValueUnion = trimExtraValueBochum.replace("Union", "Union Berlin");
        String trimExtraValueHertha = trimExtraValueUnion.replace("HerthaB", "Hertha BSC");

        return trimExtraValueHertha;
    }

    /**
     * getter function
     *
     * @return maximum market value of all teams
     */
    public String getMarktwertMax() {
        return marktwertMax;
    }

    /**
     * getter function
     *
     * @return maximum victorys from team at all time
     */
    public static String getEwigeSiegeMax() {
        return ewigeSiegeMax;
    }

    /**
     * getter function
     *
     * @return maximum losses from team at all time
     */
    public static String getEwigeNiederlagenMin() {
        return ewigeNiederlagenMin;
    }

    /**
     * getter function
     *
     * @return maximum points from team at all time
     */
    public static String getEwigePunkteMax() {
        return ewigePunkteMax;
    }
}