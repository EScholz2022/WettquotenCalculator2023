package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jxl.format.Alignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.Mannschaft;
import sample.MathOperations;
import sample.ReadWebContent;
import sample.Team;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StartPageController implements Initializable, ActionListener {

    private static final Logger logger = LogManager.getLogger("StartPageController");
    public static TreeMap<String, String> Bayern = new TreeMap();
    public static TreeMap<String, String> Dortmund = new TreeMap();

    LinkedList<String> BayernWerte = null;
    LinkedList<String> DortmundWerte = null;
    LinkedList<String> LeverkusenWerte = null;
    LinkedList<String> UnionWerte = null;
    LinkedList<String> FreiburgWerte = null;
    LinkedList<String> KoelnWerte = null;
    LinkedList<String> LeipzigWerte = null;
    LinkedList<String> HoffenheimWerte = null;
    LinkedList<String> FrankfurtWerte = null;
    LinkedList<String> MainzWerte = null;
    LinkedList<String> BochumWerte = null;
    LinkedList<String> GladbachWerte = null;
    LinkedList<String> HerthaWerte = null;
    //LinkedList<String> BielefeldWerte = null;
    LinkedList<String> AugsburgWerte = null;
    LinkedList<String> WolfsburgWerte = null;
    LinkedList<String> StuttgartWerte = null;
    //LinkedList<String> FuerthWerte = null;
    LinkedList<String> BremenWerte = null;
    LinkedList<String> SchalkeWerte = null;
    static HashMap<String, String> KalenderSpieltageBundesliga1 = new HashMap<>();

    LinkedList<String> listTeamsNextSpieltag = null;
    LinkedList<String> AnsetzungenNextSpieltag = null;
    LinkedList<String> ErgebnisVorhersagen = null;

    private static LinkedHashMap<String, Double> basisScoreMap;
    private static LinkedHashMap<String, Double> statistikScoreMap;
    private static LinkedHashMap<String, Double> gesamtScoreMap;

    public static String spieltagAuswahl;
    public static String saisonAuswahl;
    public static String nextSpielTag;
    public static String currentSaison;
    boolean refreshSpieltagData = false;
    boolean loadErgebnisVorhersage = false; //used to load the data only once, no twice click on button allowed
    boolean loadNextSpieltag = false;
    boolean loadScore = false;
    boolean loadGesamtScore = false;
    private Stage requestStage;
    boolean workBookCreated = false;

    ReadWebContent webContent = null;
    //instantiate Mannschaft objects Bundesliga 1
    private static Mannschaft m1 = null;
    private static Mannschaft m2 = null;
    private static Mannschaft m3 = null;
    private static Mannschaft m4 = null;
    private static Mannschaft m5 = null;
    private static Mannschaft m6 = null;
    private static Mannschaft m7 = null;
    private static Mannschaft m8 = null;
    private static Mannschaft m9 = null;
    private static Mannschaft m10 = null;
    private static Mannschaft m11 = null;
    private static Mannschaft m12 = null;
    private static Mannschaft m13 = null;
    private static Mannschaft m14 = null;
    private static Mannschaft m15 = null;
    private static Mannschaft m16 = null;
    private static Mannschaft m17 = null;
    private static Mannschaft m18 = null;


    //used fixed URLS to read data from
    private static final String fullDataTableURL = "https://www.fussballdaten.de/bundesliga/tabelle/";
    private static final String Liga1HeimTableURL = "https://www.fussballdaten.de/bundesliga/heimtabelle/";
    private static final String Liga1GastTableURL = "https://www.fussballdaten.de/bundesliga/2024/auswaertstabelle/";
    private static final String Liga1EwigeTabelleURL = "https://www.fussballdaten.de/bundesliga/2024/ewige-tabelle/";
    private static final String MarkwerteTableURL = "https://www.transfermarkt.de/bundesliga/marktwerteverein/wettbewerb/L1";
    private static final String MarkwerteFormTableURL = "https://www.fussballdaten.de/bundesliga/2024/formtabelle/"; //TODO: Not Set
    private static final String MarkwerteAbwehrTableURL = "https://www.transfermarkt.de/bundesliga/marktwerte/wettbewerb/L1/plus//galerie/0?pos=Abwehr&detailpos=&altersklasse=alle";
    private static final String MarkwerteMittelfeldTableURL = "https://www.transfermarkt.de/bundesliga/marktwerte/wettbewerb/L1/plus//galerie/0?pos=Mittelfeld&detailpos=&altersklasse=alle";
    private static final String MarkwerteTorwartTableURL = "https://www.transfermarkt.de/bundesliga/marktwerte/wettbewerb/L1/plus//galerie/0?pos=Torwart&detailpos=&altersklasse=alle";
    private static final String MarkwerteStuermerTableURL = "https://www.transfermarkt.de/bundesliga/marktwerte/wettbewerb/L1/plus//galerie/0?pos=Sturm&detailpos=&altersklasse=alle";


    @FXML
    Group aktuellerSpieltagDataGroup;

    @FXML
    Group uebertragungSenderGroup;

    @FXML
    Group BasisScoreMannschaftTextFields;

    @FXML
    Group BasisScoreWertTextFields;

    @FXML
    Group SaisonSpieltagGroup;

    @FXML
    Group StatistikScoreMannschaftenTextFields;

    @FXML
    Group StatistikScoreWertTextFields;

    /**
     * Menu Tab Aktuelle Tabelle
     */
    @FXML
    Label mainlabel;

    @FXML
    TableView mainTable;

    @FXML
    TableColumn<Team, String> columnMannschaft, columnSpiele, columnSiege, columnUnentschieden, columnNiederlagen, columnTore, columnTordifferenz, columnPunkte, columnMarktwert, columnTransfers;

    /**
     * Menu Tab Aktueller Spieltag
     */
    @FXML
    TextField spieltagVorhersageTextField;

    @FXML
    Label SaisonLabel, SpieltagLabel;

    @FXML
    SplitMenuButton saisonMenu, spieltagMenu;

    @FXML
    MenuButton saisonMenuButton, spieltagMenuButton;

    @FXML
    Label HeimmannschaftLabel, ErgebnisSpielzeitLabel, GastmannschaftLabel, AnsetzungLabel, SenderLabel1;

    @FXML
    TextField heimmannschaft0TextField, heimmannschaft1TextField, heimmannschaft2TextField, heimmannschaft3TextField, heimmannschaft4TextField, heimmannschaft5TextField, heimmannschaft6TextField, heimmannschaft7TextField, heimmannschaft8TextField;

    @FXML
    TextField ansetzung0VorhersageTextField, ansetzung1VorhersageTextField, ansetzung2VorhersageTextField, ansetzung3VorhersageTextField, ansetzung4VorhersageTextField, ansetzung5VorhersageTextField, ansetzung6VorhersageTextField, ansetzung7VorhersageTextField, ansetzung8VorhersageTextField;

    @FXML
    TextField ergebnisVorhersage0TextField, ergebnisVorhersage1TextField, ergebnisVorhersage2TextField, ergebnisVorhersage3TextField, ergebnisVorhersage4TextField, ergebnisVorhersage5TextField, ergebnisVorhersage6TextField, ergebnisVorhersage7TextField, ergebnisVorhersage8TextField;

    @FXML
    TextField seasonVorhersageTextField;

    @FXML
    TextField ergebnisSpieltag0TextField, ergebnisSpieltag1TextField, ergebnisSpieltag2TextField, ergebnisSpieltag3TextField, ergebnisSpieltag4TextField, ergebnisSpieltag5TextField, ergebnisSpieltag6TextField, ergebnisSpieltag7TextField, ergebnisSpieltag8TextField;

    @FXML
    TextField gastMannschaft0TextField, gastMannschaft1TextField, gastMannschaft2TextField, gastMannschaft3TextField, gastMannschaft4TextField, gastMannschaft5TextField, gastMannschaft6TextField, gastMannschaft7TextField, gastMannschaft8TextField;

    @FXML
    TextField ansetzung0TextField, ansetzung1TextField, ansetzung2TextField, ansetzung3TextField, ansetzung4TextField, ansetzung5TextField, ansetzung6TextField, ansetzung7TextField, ansetzung8TextField;

    @FXML
    TextField sender1TextField, sender2TextField, sender3TextField, sender4TextField, sender5TextField, sender6TextField, sender7TextField, sender8TextField;

    @FXML
    Button loadDataButton, loadDatainTableButton;

    @FXML
    VBox vorhersageTextFields;

    @FXML
    Group HeimGastTextFields, AnsetzungTextFields;

    /**
     * Menu Tab Basis Score
     */

    @FXML
    AnchorPane anchPane;

    @FXML
    Label MannschaftBasisScoreLabel1, WertBasisScoreLabel1, MannschaftBasisScoreLabel2, WertBasisScoreLabel2, ErgebnisVorhersageLabel, GastmannschaftLabel1;

    @FXML
    TextField Mannschaft1TextField, Mannschaft2TextField, Mannschaft3TextField, Mannschaft4TextField, Mannschaft5TextField, Mannschaft6TextField, Mannschaft7TextField, Mannschaft8TextField, Mannschaft9TextField;

    @FXML
    TextField scoreWertTextField1, scoreWertTextField2, scoreWertTextField3, scoreWertTextField4, scoreWertTextField5, scoreWertTextField6, scoreWertTextField7, scoreWertTextField8, scoreWertTextField9;

    @FXML
    TextField scoreWertTextField10, scoreWertTextField11, scoreWertTextField12, scoreWertTextField13, scoreWertTextField14, scoreWertTextField15, scoreWertTextField16, scoreWertTextField17, scoreWertTextField18;

    @FXML
    Button loadGesamtScoreButton;

    /**
     * Menu Tab Statistik Score
     */
    @FXML
    TextField StatistikScoreTextField1, StatistikScoreTextField2, StatistikScoreTextField3, StatistikScoreTextField4, StatistikScoreTextField5, StatistikScoreTextField6, StatistikScoreTextField7, StatistikScoreTextField8, StatistikScoreTextField9;

    @FXML
    Button loadDataButtonStatistik;

    @FXML
    Button loadBasicScoreButton;

    @FXML
    Button sortGesamtScoreButton;

    @FXML
    Tab AktuellerSpieltagMenuTab;

    @FXML
    Group BasisScoreTextFields1, BasisScoreTextFields2, StatistikScoreTextFields1, StatistikScoreTextFields2;

    @FXML
    Group BasisScoreMannschaftTextFields2, GesamtScoreTextFields;

    @FXML
    private void loadDataButtonAction(ActionEvent event) {
        logger.info("##### Button loadData pressed and loadDataButtonAction called #####");

        /**
         * new Mannschaft
         * Name
         * Liste
         * Name und Liste übergeben
         * Werte holen und in Name.Liste
         *
         */
        mainTable = new TableView();
        mainTable.setEditable(true);
        Collection<String> values = ReadWebContent.getListAllData().values();
        Iterator<String> valueIterator = values.iterator();

        if (ReadWebContent.getListAllData().values().isEmpty() || values.isEmpty() || !valueIterator.hasNext()) {
            System.out.println("Game Over Because of No Data du Seppel!");
        }

        //init all 18 teams of Bundesliga 1
        System.out.println("MyCollection: " + values);
        System.out.println("Init 18 Team LinkLists now for Bundesliga1");
        BayernWerte = new LinkedList();
        DortmundWerte = new LinkedList();
        LeverkusenWerte = new LinkedList();
        UnionWerte = new LinkedList();
        FreiburgWerte = new LinkedList();
        KoelnWerte = new LinkedList();
        LeipzigWerte = new LinkedList();
        HoffenheimWerte = new LinkedList();
        FrankfurtWerte = new LinkedList();
        MainzWerte = new LinkedList();
        BochumWerte = new LinkedList();
        GladbachWerte = new LinkedList();
        HerthaWerte = new LinkedList();
        //BielefeldWerte = new LinkedList();
        AugsburgWerte = new LinkedList();
        WolfsburgWerte = new LinkedList();
        StuttgartWerte = new LinkedList();
        //FuerthWerte = new LinkedList();
        BremenWerte = new LinkedList();
        SchalkeWerte = new LinkedList();

        System.out.println("zeig die values:");
        while (valueIterator.hasNext()) {
            System.out.println(valueIterator.next());
        }

        //using ObservableList as collection and iterate over each collection entry
        final ObservableList<Team> data =
                FXCollections.observableArrayList(
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next()),
                        new Team(valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next(), valueIterator.next())

                );

        //for each core information of team a PropertyValueFactory is instantiated
        columnMannschaft.setCellValueFactory(
                new PropertyValueFactory<Team, String>("mannschaft"));

        columnSpiele.setCellValueFactory(
                new PropertyValueFactory<Team, String>("spiele"));

        columnSiege.setCellValueFactory(
                new PropertyValueFactory<Team, String>("siege"));

        columnUnentschieden.setCellValueFactory(
                new PropertyValueFactory<Team, String>("unentschieden"));

        columnNiederlagen.setCellValueFactory(
                new PropertyValueFactory<Team, String>("niederlagen"));

        columnTore.setCellValueFactory(
                new PropertyValueFactory<Team, String>("tore"));

        columnTordifferenz.setCellValueFactory(
                new PropertyValueFactory<Team, String>("tordifferenz"));

        columnPunkte.setCellValueFactory(
                new PropertyValueFactory<Team, String>("punkte"));

        columnMarktwert.setCellValueFactory(
                new PropertyValueFactory<Team, String>("marktwert"));

        columnTransfers.setCellValueFactory(
                new PropertyValueFactory<Team, String>("transfers"));

        mainTable.setItems(data);
        logger.info("##### Button loadData pressed ended #####");
    }

    @FXML
    private void loadDatainTableButtonAction(ActionEvent event) throws IOException {
        logger.info("##### Button loadData in Table pressed and loadDatainTableButtonAction called #####");

        String injuryURL = "https://www.transfermarkt.de/1-bundesliga/verletztespieler/wettbewerb/L1/plus/1";
        ReadWebContent readInjuryWebcontent = new ReadWebContent();
        ReadWebContent.readInjuryCoronaPlayer(injuryURL);

        logger.info("##### Button loadData in Table ended #####");
    }

    /**
     * function not used
     *
     * @param event for the ActionEvent
     * @throws IOException
     */
    @FXML
    private void berechneSpieltagErgebnisseButtonAction(ActionEvent event) {
        logger.info("##### Button berechneSpieltagErgebnisse pressed and berechneSpieltagErgebnisseButtonAction called");
        logger.info("##### Button berechneSpieltagErgebnisse ended #####");

    }

    @FXML
    private void loadDataSpieltagButtonAction(ActionEvent event) {
        logger.info("##### Button Load Spieltag Data pressed and loadDataSpieltagButtonAction called #####");
        /**
         *
         *
         * Achtung: hier passiert ein Kreuzaufruf auf den Tab Aktueller Spieltag und die Daten dort werden geladen...
         *
         *
         */
        currentSaison = "Saison 2022-2023";
        //currentSaison = "Saison 2021-2022";

        saisonAuswahl = saisonMenuButton.getText();
        spieltagAuswahl = spieltagMenuButton.getText();
        System.out.println("Saison Auswahl " + saisonAuswahl);
        System.out.println("Spieltag Auswahl " + spieltagAuswahl);
        if (saisonAuswahl.isEmpty() || saisonAuswahl == "" || saisonAuswahl.length() == 0 || saisonAuswahl == null) {
            saisonAuswahl = currentSaison;
        }
        if (spieltagVorhersageTextField.getText().isEmpty()) {
            spieltagVorhersageTextField.setText(spieltagAuswahl);
            System.out.println("Case spieltagVorhersageTextField leer, manuell eintragen " + spieltagAuswahl);
        }
        if (seasonVorhersageTextField.getText().isEmpty()) {
            seasonVorhersageTextField.setText(saisonAuswahl);
            System.out.println("Case seasonVorhersageTextField leer, manuell eintragen " + spieltagAuswahl);
        }

        ReadWebContent spieltag = new ReadWebContent();
        if (refreshSpieltagData) {
            spieltag.flushAllLists();
        }
        try {
            spieltag.readSpieltag(spieltagAuswahl, saisonAuswahl);
            System.out.println("Spieltag wird gelesen mit " + spieltagAuswahl + " als Spieltagauswahl und " + saisonAuswahl + " als Saisonauswahl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Node> values = aktuellerSpieltagDataGroup.getChildren();
        ObservableList<Node> valuesSender = uebertragungSenderGroup.getChildren();
        Collection<String> spielTagDataCol = ReadWebContent.getListSpieltagData().values();

        ObservableList<Node> nodesHeimGast = HeimGastTextFields.getChildren();
        ObservableList<Node> nodesAnsetzung = AnsetzungTextFields.getChildren();

        Iterator<Node> HeimGastIterator = nodesHeimGast.iterator();
        Consumer<? super Node> TextField = null;
        Runnable runnable = () -> HeimGastTextFields.getChildren().forEach(node -> System.out.println("printing" + node));
        while (HeimGastIterator.hasNext()) {
            Node tmpNode = HeimGastIterator.next();
            String mannschaftsID = tmpNode.getId();
            TextField myTextField = (TextField) tmpNode;
        }

        Iterator<String> spielTagDataValue = spielTagDataCol.iterator();
        Iterator<String> spielTagDataKey = ReadWebContent.getListSpieltagData().keySet().iterator();
        while (valuesSender.iterator().hasNext()) {
            values.add(valuesSender.iterator().next());
        }
        Iterator<Node> nodesSpieltagIterator = values.iterator();

        String currentData = "";
        TextField currentNode = null;

        //interate over all nodes
        while (nodesSpieltagIterator.hasNext()) {
            currentNode = (TextField) nodesSpieltagIterator.next();
            currentData = spielTagDataValue.next();
            String currentKey = spielTagDataKey.next();

            String currentNodeID = currentNode.getId();
            System.out.println("#####check Node informations#####");
            System.out.println("key: " + currentKey);
            System.out.println("noteID: " + currentNodeID);
            System.out.println("value: " + currentData);
            System.out.println("Node: " + currentNode);
            TextField tmpTextField = new TextField();
            String tmpTextFieldValue = "";

            if (currentNodeID.contains("heimmannschaft")) {
                String heimNumber = currentNodeID.replaceAll("[^0-9]", "");
                int heimInt = Integer.parseInt(heimNumber);
                heimInt = heimInt * 2;
                tmpTextField.setEditable(true);
                tmpTextField.setId("heimmannschaft" + heimInt);
                tmpTextFieldValue = ReadWebContent.getListSpieltagData().get("heimmannschaft" + heimInt);
                currentNode.setText(tmpTextFieldValue);
            }
            if (currentNodeID.contains("gastMannschaft")) {
                String gastNumber = currentNodeID.replaceAll("[^0-9]", "");
                int gastInt = Integer.parseInt(gastNumber);
                gastInt = (gastInt * 2) + 1;
                tmpTextField.setEditable(true);
                tmpTextField.setId("gastMannschaft" + gastInt);
                tmpTextFieldValue = ReadWebContent.getListSpieltagData().get("gastmannschaft" + gastInt);
                currentNode.setText(tmpTextFieldValue);
            }
            if (currentNodeID.contains("ansetzung")) {
                String ansetzungNumber = currentNodeID.replaceAll("[^0-9]", "");
                tmpTextField.setEditable(true);
                tmpTextField.setId("Ansetzung" + ansetzungNumber);
                tmpTextFieldValue = ReadWebContent.getListSpieltagData().get("ansetzung" + ansetzungNumber);
                currentNode.setText(tmpTextFieldValue);
            }
            if (currentNodeID.contains("ergebnis")) {
                String ergebnisNumber = currentNodeID.replaceAll("[^0-9]", "");
                int ergebnisInt = Integer.parseInt(ergebnisNumber);
                ergebnisInt = ergebnisInt * 2;
                tmpTextField.setEditable(true);
                tmpTextField.setId("ergebnis" + ergebnisInt);
                tmpTextFieldValue = ReadWebContent.getListSpieltagData().get("ergebnis" + ergebnisInt);
                if (tmpTextFieldValue == null) {
                    tmpTextFieldValue = "tba";
                }
                currentNode.setText(tmpTextFieldValue);
            }
            if (currentNodeID.contains("sender")) {
                String ergebnisNumber = currentNodeID.replaceAll("[^0-9]", "");
                tmpTextField.setEditable(true);
                tmpTextField.setId("sender" + ergebnisNumber);
                tmpTextFieldValue = ReadWebContent.getListSpieltagData().get("uebertragung" + ergebnisNumber);
                currentNode.setText(tmpTextFieldValue);
            }
        }

        refreshSpieltagData = true;
        logger.info("##### Button Load Spieltag Data ended #####");
    }

    @FXML
    private void loadSpieltagVorhersageDataButtonAction(ActionEvent event) {
        logger.info("##### Button Lade Daten von Heim und Gast Team im Spieltag Vorhersage Menu Tab called #####");
        if (refreshSpieltagData) {
            System.out.println("#INFOPRINT Daten Konflikt, da zuerst alte Spieltag Daten geladen. ");

            String conflictRequest = "ACHTUNG - Daten Konflikt";
            String conflictFXML = "/fxml/conflict.fxml";
            RestartApplicationController.showConflictStage(conflictRequest, conflictFXML);

        } else {
            loadNextSpieltag = true;
            currentSaison = "Saison 2023-2024";
            nextSpielTag = getNextSpieltag();
            scoreWertTextField12.setEditable(true);
            seasonVorhersageTextField.setText(currentSaison);
            spieltagVorhersageTextField.setText(nextSpielTag);

            ReadWebContent nextSpieltag = new ReadWebContent();

            try {
                nextSpieltag.readSpecificSpieltag(nextSpielTag, currentSaison);
            } catch (IOException e) {
                e.printStackTrace();
            }

            listTeamsNextSpieltag = new LinkedList<>();
            AnsetzungenNextSpieltag = new LinkedList<>();

            Collection<String> values = ReadWebContent.getListSpieltagData().values();
            Iterator<String> valueIterator = values.iterator();

            while (valueIterator.hasNext()) {
                String currentValue = valueIterator.next();
                listTeamsNextSpieltag.add(currentValue);
            }

            for (String entry : listTeamsNextSpieltag) {
                if (entry.contains("tag")) {
                    AnsetzungenNextSpieltag.add(entry);
                }
            }
            int subListSize = listTeamsNextSpieltag.size() - AnsetzungenNextSpieltag.size();
            System.out.println("SubList SIze: " + subListSize);

            ObservableList<Node> nodes = HeimGastTextFields.getChildren();
            Iterator<Node> nodeIterator = nodes.iterator();

            ObservableList<Node> nodesAnsetzung = AnsetzungTextFields.getChildren();
            Iterator<Node> nodeAnsetzungIterator = nodesAnsetzung.iterator();

            TextField t = new TextField();
            Node currentNode = null;
            System.out.println("size Teams Next Day: " + listTeamsNextSpieltag.size());
            for (int x = 0; x < subListSize; x++) {
                currentNode = nodeIterator.next();
                System.out.println("cu NDOE: " + currentNode);
                String currentTeam = listTeamsNextSpieltag.get(x);
                System.out.println("cu TEAM: " + currentTeam);
                t = (TextField) currentNode;
                t.setText(currentTeam);
                System.out.println("added: " + currentTeam + " to node: " + currentNode.getId());
            }

            for (int y = 0; y < AnsetzungenNextSpieltag.size(); y++) {
                currentNode = nodeAnsetzungIterator.next();
                String currentAnsetzung = AnsetzungenNextSpieltag.get(y);
                t = (TextField) currentNode;
                t.setText(currentAnsetzung);
                System.out.println("added: " + currentAnsetzung + " to node: " + currentNode.getId());
            }
        }
        logger.info("##### Button Lade Daten von Heim und Gast Team im Spieltag Vorhersage Menu Tab ended #####");
    }

    @FXML
    private void berechneSpieltagErgebnisseVorhersageButtonAction(ActionEvent event) {
        logger.info("##### Button berechne Daten für Vorhersage im Tab Spieltag Vorhersage called #####");


        /**
         * direkte Vergleiche Spiele, Siege, Niederlagen, Formstärke, ScoreWert ...
         * Wichtiger Faktor ist Formstärke letzte 5 Spiele
         *
         * Score Differenz geht von 0 bis 60
         * 17
         * 21
         * 19
         * 6
         * 28
         * 30
         * 23
         * 1
         * 9
         *
         * Differenz 0-10 -> Unentschieden vermutet
         * Diferenz 10-20 -> Sieg mit 1 oder 2 Toren Unterschied
         * Differenz 20-60 -> Sieg mit mindestens 2 Toren Unterschied
         */


        if (!loadNextSpieltag) {
            String requestTitle = "ERROR - Spieltag Daten";
            String requestFXML = "/fxml/requestvorhersagespieltag.fxml";
            openRequestStage(requestTitle, requestFXML);
        }

        if (!loadGesamtScore) {
            String requestTitle = "ERROR - Score Daten";
            String requestFXML = "/fxml/requestscoredaten.fxml";
            openRequestStage(requestTitle, requestFXML);
        }

        if (!loadErgebnisVorhersage) {
            System.out.println("Spieltag Vorhersage Gruppe1 HeimGastTextFields: " + HeimGastTextFields);
            System.out.println("Spieltag Vorhersage Gruppe1 HeimGastTextFields getChilds: " + HeimGastTextFields.getChildren());
            System.out.println("Spieltag Vorhersage Gruppe1 HeimGastTextFields getChilds Unmodifiable: " + HeimGastTextFields.getChildrenUnmodifiable());
            System.out.println("Spieltag Vorhersage Gruppe1 HeimGastTextFields size: " + HeimGastTextFields.getChildren().size());

            ErgebnisVorhersagen = new LinkedList<>();
            Set<String> liveScores = MathOperations.getBasicLiveScoreData().keySet();
            Set<String> gesamtScores = gesamtScoreMap.keySet();
            System.out.println("Live Scores: " + liveScores);
            logger.info("Gesamt Scores: " + gesamtScores);

            ObservableList<Node> nodes = vorhersageTextFields.getChildren();
            Iterator<Node> nodeIterator = nodes.iterator();

            TextField t = new TextField();
            Node currentNode = null;
            Tooltip tooltip = new Tooltip();
            int scoreDifferenz = 0;
            int formToreDifferenz = 0;
            String warscheinlichSieger = " Vorteil ";
            for (int y = 0; y < vorhersageTextFields.getChildren().size(); y++) {
                int teamCounter = y * 2;
                String compTeam1 = listTeamsNextSpieltag.get(teamCounter);
                String compTeam2 = listTeamsNextSpieltag.get(teamCounter + 1);

                String trimValuecompTeam1 = trimStringValueToCompare(compTeam1);
                System.out.println("Teamname getrimmt: " + trimValuecompTeam1);
                String trimValuecompTeam2 = trimStringValueToCompare(compTeam2);
                System.out.println("Teamname getrimmt: " + trimValuecompTeam2);

                String compFormToreDifferenzTeam1 = ReadWebContent.getListScoreData().get("FormTore" + trimValuecompTeam1);
                String compFormToreDifferenzTeam2 = ReadWebContent.getListScoreData().get("FormTore" + trimValuecompTeam2);
                System.out.println("FormToreDifferenzTeam1: " + compFormToreDifferenzTeam1);
                System.out.println("FormToreDifferenzTeam2: " + compFormToreDifferenzTeam2);
                double FormToreDifferenzTeam1 = Double.parseDouble(compFormToreDifferenzTeam1);
                double FormToreDifferenzTeam2 = Double.parseDouble(compFormToreDifferenzTeam2);
                formToreDifferenz = (int) (FormToreDifferenzTeam1 - FormToreDifferenzTeam2);


                double scoreCompTeam1 = gesamtScoreMap.get("Score".concat(trimValuecompTeam1));
                double scoreCompTeam2 = gesamtScoreMap.get("Score".concat(trimValuecompTeam2));

                logger.info("Gesamtscore Team 1 = " + scoreCompTeam1 + " from " + trimValuecompTeam1);
                logger.info("Gesamtscore Team 2 = " + scoreCompTeam2 + " from " + trimValuecompTeam2);

                scoreDifferenz = (int) (scoreCompTeam1 - scoreCompTeam2);
                logger.info("Gesamtscore = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " und " + scoreCompTeam2);
                if (scoreDifferenz < 0) {
                    scoreDifferenz = scoreDifferenz * -1;
                }
                System.out.println("score compTeam1: " + scoreCompTeam1);
                System.out.println("score compTeam2: " + scoreCompTeam2);
                System.out.println("Score Differenz: " + scoreDifferenz);
                currentNode = nodeIterator.next();
                t = (TextField) currentNode;
                String infoText = "";
                String randVorhersage = "";

                if (scoreDifferenz < 10) {
                    logger.info("UNENTSCHIEDEN  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);
                    randVorhersage = randErgebnisUnentschieden();
                    ErgebnisVorhersagen.add(randVorhersage);
                    infoText = infoText + randVorhersage;
                    infoText = infoText + ("   Unentschieden");
                    infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                }

                if (scoreCompTeam1 > scoreCompTeam2) {
                    warscheinlichSieger = warscheinlichSieger.concat(compTeam1);

                    if (scoreDifferenz >= 10 && scoreDifferenz < 20) {
                        if (FormToreDifferenzTeam1 >= 5) {
                            logger.info("DEUTLICHER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisHighVictory(false);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Deutlicher Sieg - 2+ Tore");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                        if (FormToreDifferenzTeam1 < 5) {
                            logger.info("KNAPPER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisLowVictory(false);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Knapper Sieg - 1-2 Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                    }
                    if (scoreDifferenz >= 20) {
                        if (FormToreDifferenzTeam1 < 0) {
                            logger.info("KNAPPER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisLowVictory(false);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Knapper Sieg - 1-2 Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                        if (FormToreDifferenzTeam1 >= 0) {
                            logger.info("DEUTLICHER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisHighVictory(false);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Deutlicher Sieg - 2+ Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                    }

                } else {
                    warscheinlichSieger = warscheinlichSieger.concat(compTeam2);
                    if (scoreDifferenz >= 10 && scoreDifferenz < 20) {
                        if (FormToreDifferenzTeam2 >= 5) {
                            logger.info("DEUTLICHER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisHighVictory(true);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Deutlicher Sieg - 2+ Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                        if (FormToreDifferenzTeam2 < 5) {
                            logger.info("KNAPPER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisLowVictory(true);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Knapper Sieg - 1-2 Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                    }
                    if (scoreDifferenz >= 20) {
                        if (FormToreDifferenzTeam2 < 0) {
                            logger.info("KNAPPER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisLowVictory(true);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Knapper Sieg - 1-2 Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }
                        if (FormToreDifferenzTeam2 >= 0) {
                            logger.info("DEUTLICHER SIEG  --  Ansetzung " + trimValuecompTeam1 + " vs " + trimValuecompTeam2 + " mit " + " Gesamtscore/Scoredifferent = " + scoreDifferenz + " berechnet aus " + scoreCompTeam1 + " - " + scoreCompTeam2);

                            randVorhersage = randErgebnisHighVictory(true);
                            ErgebnisVorhersagen.add(randVorhersage);
                            infoText = infoText + randVorhersage;
                            infoText = infoText + ("   Deutlicher Sieg - 2+ Tore ");
                            infoText = infoText + (" SCORE Differenz: " + scoreDifferenz);
                        }

                    }
                }
                System.out.println(infoText + warscheinlichSieger);
                t.setText(infoText + warscheinlichSieger);
                warscheinlichSieger = " Vorteil ";
                tooltip.setText("Score Differenz: " + scoreDifferenz);
                t.setTooltip(tooltip);
                t.setAlignment(Pos.TOP_LEFT);
            }

            final int scoreDifferenzEventOutput = scoreDifferenz;
            EventHandler filter = new EventHandler<InputEvent>() {
                public void handle(InputEvent event) {
                    System.out.println("Event " + event.getEventType() + " detected");
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText("#############TEST############## \n\t" +
                            "Differenz: " + scoreDifferenzEventOutput);
                    for (Node node : vorhersageTextFields.getChildren()) {
                        if (node instanceof TextField) {
                            ((TextField) node).setTooltip(tooltip);
                        }
                    }
                }
            };
            ergebnisVorhersage2TextField.addEventFilter(MouseEvent.MOUSE_CLICKED, filter);

            ergebnisVorhersage0TextField.addEventFilter(MouseEvent.MOUSE_CLICKED,
                    mouseEvent -> {
                        System.out.println("Click");
                    });


            TextField ergebnisVorhersage0TextField, ergebnisVorhersage1TextField, ergebnisVorhersage2TextField, ergebnisVorhersage3TextField, ergebnisVorhersage4TextField, ergebnisVorhersage5TextField, ergebnisVorhersage6TextField, ergebnisVorhersage7TextField, ergebnisVorhersage8TextField;
            loadErgebnisVorhersage = true;
        } else {
            String requestTitle2 = "ATTENTION";
            String requestFXML2 = "/fxml/requestvorhersage.fxml";
            openRequestStage(requestTitle2, requestFXML2);
        }

        /**
         * not working Java 8
         */
//        TextField textFieldInside = new TextField();
//        vorhersageTextFields.getChildren().forEach(TextField -> textFieldInside.setAlignment(Pos.TOP_LEFT));

        /**
         * general Code Textfield Nodes Setting
         */
//        for (Node child : vorhersageTextFields.getChildren()) {
//            TextField tf = (TextField) child;
//            if (tf.getText().isEmpty()) {
//                tf.setAlignment(Pos.TOP_LEFT);
//                tf.setText("TEAM1_DATA - TEAM2_DATA");
//            }
//        }
        logger.info("##### Button berechne Daten für Vorhersage im Tab Spieltag Vorhersage ended #####");
    }

    @FXML
    private void saveSpieltagErgebnisseVorhersageButtonAction(ActionEvent event) throws IOException, WriteException {
        logger.info("##### Button Spieltag Ergebnisse speichern called #####");


        if (!loadErgebnisVorhersage) {
            String requestTitle = "ERROR Vorhersage Daten";
            String requestFXML = "/fxml/requestvorhersagespeichern.fxml";
            openRequestStage(requestTitle, requestFXML);
        }

        //old
        String vergangenerSpieltag = getSpieltagDone();
        int nextSpieltag = Integer.parseInt(vergangenerSpieltag);
        nextSpieltag++;


        ReadWebContent nextSpieltagErgebnisse = new ReadWebContent();
        nextSpieltagErgebnisse.readErgebnisseNextSpieltagForXLS();

        String fileName = "VorhersageSpieltag".concat(String.valueOf(nextSpieltag)).concat(".xls");
        System.out.println("Save to: " + fileName);

        //Todo: check if XLS is created, muss mit FileReader passieren
        if (workBookCreated) {
            logger.info("File and data already created, inform user that save action already performed");
            String requestTitle = "ERROR Speichern";
            String requestFXML = "/fxml/requestspeichernnotpossible.fxml";
            openRequestStage(requestTitle, requestFXML);
        }

        WritableWorkbook workbook = jxl.Workbook.createWorkbook(new File(fileName));
        workBookCreated = true;
        WritableSheet sheet = workbook.createSheet("Spielansetzung", 0);

        /**
         * formatting the cell values, width and alignment are needed to change
         */
        WritableCellFormat cellFormatCol1 = new WritableCellFormat();
        cellFormatCol1.setAlignment(Alignment.CENTRE);
        WritableCellFormat cellFormatCol2 = new WritableCellFormat();
        cellFormatCol2.setAlignment(Alignment.CENTRE);
        WritableCellFormat cellFormatCol3 = new WritableCellFormat();
        cellFormatCol3.setAlignment(Alignment.CENTRE);

        int cellWidthCol1_2 = 25;
        int cellWidthCol3_4 = 15;

        sheet.setColumnView(0, cellWidthCol1_2);
        sheet.setColumnView(1, cellWidthCol1_2);
        sheet.setColumnView(2, cellWidthCol3_4);
        sheet.setColumnView(3, cellWidthCol3_4);
        sheet.setColumnView(4, cellWidthCol3_4);

        try {
            jxl.write.Label labelSpieltagTitel = new jxl.write.Label(0, 0, "SPIELTAG BEGEGNUNG", cellFormatCol1);
            sheet.addCell(labelSpieltagTitel);
            jxl.write.Label labelSpieltagVorhersage = new jxl.write.Label(2, 0, "VORHERSAGE", cellFormatCol1);
            sheet.addCell(labelSpieltagVorhersage);
            jxl.write.Label labelSpieltagReal = new jxl.write.Label(3, 0, "ERGEBNIS REAL", cellFormatCol1);
            sheet.addCell(labelSpieltagReal);
            jxl.write.Label labelScoreDifferenz = new jxl.write.Label(4, 0, "SCORE DIFFERENZ", cellFormatCol1);
            sheet.addCell(labelScoreDifferenz);
        } catch (WriteException e) {
            e.printStackTrace();
        }

        String lineToWrite = "";
        FileWriter txtWriter = null;
        try {
            txtWriter = new FileWriter("vorhersage.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set tmpErgebnisseNextSpieltag = nextSpieltagErgebnisse.getListNextSpieltagErgebnisse().entrySet();
        Iterator tmpErgebnisseNextSpieltagIterator = tmpErgebnisseNextSpieltag.iterator();

        int y = 1;
        int z = 0;
        int countRowColumn = 2;
        int countRowColumnReal = 2;

        for (int x = 0; x < HeimGastTextFields.getChildren().size() / 2; x++) {
            z = x * 2;
            y = z + 1;
            Node node = HeimGastTextFields.getChildren().get(z);
            Node nextNode = HeimGastTextFields.getChildren().get(y);
            TextField tmpField = (TextField) node;
            TextField tmpField2 = (TextField) nextNode;
            lineToWrite = tmpField.getText() + "               " + tmpField2.getText() + "                " + ErgebnisVorhersagen.get(x) + "\n";
            String team1 = tmpField.getText();
            String team2 = tmpField2.getText();
            String spielTagErgebnisVorhersage = ErgebnisVorhersagen.get(x);
            /**
             * write the team matchup and the calculated result in xls, placeholder for real result also good idea
             */
            try {
                sheet.addCell(new jxl.write.Label(0, 1, "Team 1", cellFormatCol1));
                sheet.addCell(new jxl.write.Label(1, 1, "Team 2", cellFormatCol1));
                sheet.addCell(new jxl.write.Label(0, countRowColumn, team1, cellFormatCol1));
                sheet.addCell(new jxl.write.Label(1, countRowColumn, team2, cellFormatCol2));
                sheet.addCell(new jxl.write.Label(2, countRowColumn, spielTagErgebnisVorhersage, cellFormatCol3));
            } catch (WriteException e) {
                e.printStackTrace();
            }
            while (tmpErgebnisseNextSpieltagIterator.hasNext()) {
                String spielTagErgebnisReal = tmpErgebnisseNextSpieltagIterator.next().toString();
                sheet.addCell(new jxl.write.Label(3, countRowColumnReal, spielTagErgebnisReal, cellFormatCol3));
                countRowColumnReal++;
            }
            double tmpScoreTeam1 = gesamtScoreMap.get("Score".concat(trimStringValueToCompare(team1)));
            double tmpScoreTeam2 = gesamtScoreMap.get("Score".concat(trimStringValueToCompare(team2)));
            double tmpDifferenz = tmpScoreTeam1 - tmpScoreTeam2;
            String scoreDifferenz = String.valueOf(Math.round(tmpDifferenz));

            sheet.addCell(new jxl.write.Label(4, countRowColumn, scoreDifferenz, cellFormatCol1));

            countRowColumn++;
        }
        try {
            txtWriter.write(lineToWrite);
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

        try {
            FileReader fileReader = new FileReader("vorhersage.txt");
            String requestTitle = "CONFIRMED Speichern";
            String requestFXML = "/fxml/requestspeichernconfirmed.fxml";
            openRequestStage(requestTitle, requestFXML);
            fileReader.close();
        } catch (FileNotFoundException e) {
            String requestTitle = "ERROR Speichern";
            String requestFXML = "/fxml/requestspeichernnotconfirmed.fxml";
            openRequestStage(requestTitle, requestFXML);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("##### Button Spieltag Ergebnisse speichern ended #####");
    }

    @FXML
    private void loadBasicScoreButtonAction(ActionEvent event) {
        logger.info("##### Button Lade Daten in Basis Statistik Score Mannschaften Tab called #####");

        loadScore = true;
        nextSpielTag = getNextSpieltag();
        //currentSaison = "Saison 2021-2022";
        //currentSaison = "Saison 2023-2024";
        currentSaison = "Saison 2023-2024";
        scoreWertTextField12.setEditable(true);

        ReadWebContent nextSpieltag = new ReadWebContent();
        try {
            nextSpieltag.readNextSpieltag(nextSpielTag, currentSaison);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String marktwertMax = nextSpieltag.getMarktwertMax();
        MathOperations.calculateScore("Hoffenheim", webContent, marktwertMax);
        MathOperations.calculateScore("Bayern", webContent, marktwertMax);
        MathOperations.calculateScore("Frankfurt", webContent, marktwertMax);
//        MathOperations.calculateScore("Bielefeld", webContent, marktwertMax);
//        MathOperations.calculateScore("Fürth", webContent, marktwertMax);
        MathOperations.calculateScore("Bremen", webContent, marktwertMax);
//        MathOperations.calculateScore("Schalke", webContent, marktwertMax);

        MathOperations.calculateScore("RB Leipzig", webContent, marktwertMax);
        MathOperations.calculateScore("Union Berlin", webContent, marktwertMax);
        MathOperations.calculateScore("Köln", webContent, marktwertMax);
        MathOperations.calculateScore("VfL Bochum", webContent, marktwertMax);
        MathOperations.calculateScore("Freiburg", webContent, marktwertMax);
        MathOperations.calculateScore("Leverkusen", webContent, marktwertMax);
//        MathOperations.calculateScore("Hertha BSC", webContent, marktwertMax);
        MathOperations.calculateScore("Dortmund", webContent, marktwertMax);
        MathOperations.calculateScore("Augsburg", webContent, marktwertMax);
        MathOperations.calculateScore("M'gladbach", webContent, marktwertMax);
        MathOperations.calculateScore("Wolfsburg", webContent, marktwertMax);
        MathOperations.calculateScore("Mainz", webContent, marktwertMax);
        MathOperations.calculateScore("Stuttgart", webContent, marktwertMax);

        MathOperations.calculateScore("Heidenheim", webContent, marktwertMax);
        MathOperations.calculateScore("Darmstadt", webContent, marktwertMax);

        BasisScoreTextFields1.getChildren().addAll(BasisScoreTextFields2.getChildren());

        ObservableList<Node> BasisScoreTextFields = BasisScoreTextFields1.getChildren();
        //BasisScoreTextFields.addAll(BasisScoreWertTextFields2.getChildren());
        ObservableList<Node> MannschaftTextFields = BasisScoreMannschaftTextFields.getChildren();

        Iterator<Node> iteratorBasisScore = BasisScoreTextFields.iterator();
        Iterator<Node> iteratorMannschaftTextFields = MannschaftTextFields.iterator();

        Set<String> liveScoreKeys = MathOperations.getBasicLiveScoreData().keySet();
        Collection<Double> liveScoreValues = MathOperations.getBasicLiveScoreData().values();
        Iterator<Double> iterateLiveScore = liveScoreValues.iterator();

        basisScoreMap = new LinkedHashMap<>();

        Node currentNode = null;
        TextField tmpTextField = new TextField();

        String currentNodeID = "";
        String tmpNodeTrim = "";
        String mannschaft = "";
        Double scoreValue = 0.0;
        TextField myNewTextField;

        for (int x = 0; x < Mannschaft.getMannschaftenBundesliga1().size(); x++) {
            while (iteratorMannschaftTextFields.hasNext()) {
                currentNode = iteratorMannschaftTextFields.next();
                currentNodeID = currentNode.getId();
                System.out.println("Cu Node MannschaftTextField:" + currentNode);
                System.out.println("Cu Node ID MannschaftTextField:" + currentNodeID);
                myNewTextField = new TextField();
                myNewTextField.setEditable(true);
                myNewTextField = (TextField) currentNode;
                myNewTextField.setAlignment(Pos.CENTER);
                tmpNodeTrim = myNewTextField.getId().replaceAll("[^0-9]", "");
                System.out.println("tmp Node Trim: " + tmpNodeTrim);
                mannschaft = Mannschaft.getMannschaftenBundesliga1().get(x);
                System.out.println("current Mannschaft: " + mannschaft);
                myNewTextField.setText(mannschaft);
                break;
            }
        }
        String currentIDCompare = "";
        Node tmpNode = null;
        Node mannschaftsNode = null;
        System.out.println("Mannschaften size: " + Mannschaft.getMannschaftenBundesliga1().size());

        while (iteratorBasisScore.hasNext()) {
            tmpNode = iteratorBasisScore.next();
            System.out.println("Node Wert " + tmpNode);
            currentNodeID = tmpNode.getId();
            System.out.println("NodeID Wert " + currentNodeID);
            currentNodeID = currentNodeID.replaceAll("[^0-9]", "");
            String buildTextField = "Mannschaft" + currentNodeID + "TextField";
            System.out.println("gebautes TextField " + buildTextField);
            for (int y = 0; y < MannschaftTextFields.size(); y++) {
                Node tmpMannschaftsNode = MannschaftTextFields.get(y);
                System.out.println("aktuelle Mannschafts Node: " + tmpMannschaftsNode);
                String tmpMannschaftsNodeID = tmpMannschaftsNode.getId();
                System.out.println("aktuelle Mannschafts Node ID: " + tmpMannschaftsNode);
                if (buildTextField.equalsIgnoreCase(tmpMannschaftsNodeID)) {
                    TextField tmpField = new TextField();
                    tmpField = (TextField) tmpMannschaftsNode;
                    System.out.println("Mannschafts Node gecastet nach TextField: ");
                    String valueOfMannschaftsTextField = ((TextField) tmpMannschaftsNode).getText();
                    System.out.println("Wert von Mannschafts TextField, also team: " + valueOfMannschaftsTextField);
                    String trimValue = trimStringValueToCompare(valueOfMannschaftsTextField);
                    System.out.println("Teamname getrimmt: " + trimValue);
                    for (String key : liveScoreKeys) {
                        System.out.println("current Key: " + key);
                        System.out.println("compared with trim: : " + trimValue);
                        if (key.contains(trimValue)) {
                            System.out.println("key " + key + " matcht " + trimValue);
                            double currentScore = MathOperations.getBasicLiveScoreData().get(key);
                            System.out.println("current Score aus MATH OPERATIONS: " + currentScore);
                            TextField finalField = new TextField();
                            System.out.println("current textField casten mit neu erstelltem");
                            finalField = (TextField) tmpNode;
                            finalField.setAlignment(Pos.CENTER);
                            System.out.println("aktuellen Score setzen " + currentScore + " für richtiges TextField " + finalField.getId());
                            finalField.setText(String.valueOf(currentScore));
                            basisScoreMap.put(trimValue, currentScore);
                            System.out.println("Basis Score Mannschaft: " + mannschaft + " Score to put: " + currentScore);
                        }
                    }
                }
            }
        }
        logger.info("##### Button Lade Daten in Basis Statistik Score Mannschaften Tab ended #####");
    }

    @FXML
    private void loadDataButtonStatistikAction(ActionEvent event) {
        logger.info("##### Button Lade Daten in Statistik Score Tab called #####");

        /**
         * Verletzte Allgemein?  Corona?
         *
         * Heimstärke ab 60% Heimsiege
         * Auswärtsstärke von 35% bis 50% GastSiege
         * Auswärtsstärke Enorm ab 50% GastSiege
         *
         */

        nextSpielTag = getNextSpieltag();
        //currentSaison = "Saison 2021-2022";
        currentSaison = "Saison 2023-2024";

        ReadWebContent nextSpieltag = new ReadWebContent();
        try {
            nextSpieltag.readNextSpieltag(nextSpielTag, currentSaison);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StatistikScoreTextFields1.getChildren().addAll(StatistikScoreTextFields2.getChildren());

        ObservableList<Node> nodesStatistikScoreTextFields = StatistikScoreTextFields1.getChildren();
        Iterator<Node> nodesStatistikScoreIterator = nodesStatistikScoreTextFields.iterator();

        ObservableList<Node> nodesMannschaften = BasisScoreMannschaftTextFields.getChildren();
        Iterator<Node> nodesMannschaftenIterator = nodesMannschaften.iterator();

        statistikScoreMap = new LinkedHashMap<>();

        //default init of score map, each team gets a score of 0.0
        Node currentNode = null;
        for (int x = 0; x < Mannschaft.getMannschaftenBundesliga1().size(); x++) {
            String mannschaft = Mannschaft.getMannschaftenBundesliga1().get(x);
            System.out.println("Mannschaft x: " + mannschaft);
            currentNode = nodesMannschaftenIterator.next();
            System.out.println("current Node: " + currentNode);
            TextField textfield = (TextField) currentNode;
            textfield.setText(mannschaft);
            statistikScoreMap.put(mannschaft, 0.0);
        }

        System.out.println("Mannschaften: " + Mannschaft.getMannschaftenBundesliga1().toString());
        System.out.println();
        boolean heimStaerke = false;
        boolean gastStaerke = false;
        boolean gastStaerkePlus = false;
        Node currentStatistikNode = null;
        double score = 0.0;
        for (int x = 0; x < Mannschaft.getMannschaftenBundesliga1().size(); x++) {
            /**
             * important to note: the team names have different strings in some cases, that makes comparing difficult
             * in this case get the team name of the TextField from Group HeimGastTextFields at first -> mannschaftOfTextField
             * than get the team name that is hard coded from the Sport.java class -> mannschaftOfSportClass
             */
            TextField t = (TextField) HeimGastTextFields.getChildren().get(x);
            String mannschaftOfTextField = t.getText();
            String mannschaftOfSportClass = Mannschaft.getMannschaftenBundesliga1().get(x);
            score = statistikScoreMap.get(mannschaftOfSportClass);
            currentStatistikNode = nodesStatistikScoreIterator.next();
            TextField textfield = (TextField) currentStatistikNode;
            //important to operate with mannschaftOfTextField here
            String trimValue = trimStringValueToCompare(mannschaftOfTextField);
            String heimspiele = ReadWebContent.getListScoreData().get("SpieleHeim" + trimValue);
            String heimsiege = ReadWebContent.getListScoreData().get("SiegeHeim" + trimValue);
            double SiegeHeimProzentual = MathOperations.convertStringToDouble(heimsiege) / MathOperations.convertStringToDouble(heimspiele);
            String auswaertsspiele = ReadWebContent.getListScoreData().get("SpieleGast" + trimValue);
            String auswaertssiege = ReadWebContent.getListScoreData().get("SiegeGast" + trimValue);
            double SiegeGastProzentual = MathOperations.convertStringToDouble(auswaertssiege) / MathOperations.convertStringToDouble(auswaertsspiele);

            //get the specific name of the textfield node, that is in direct connection with the team name
            //this connection comes from loaded web data -> load next spieltag button
            Node tmpNode = HeimGastTextFields.getChildren().get(x);
            String tmpNodeID = tmpNode.getId();

            /**
             * change the values from statistic score, in case of SiegeHeimProzentual or SiegeGastProzentual are good enough
             * the textfield node must contain "heim" or "gast", then all teams in statistic score map are iterated
             * it is needed to trim the statistikScoreMap key entries, therefore function trimStringValueToCompare(s) is used
             */
            if (SiegeHeimProzentual >= 0.60) {
                heimStaerke = true;
                if (tmpNodeID.contains("heim")) {
                    score = 5.0;
                    for (String s : statistikScoreMap.keySet()) {
                        //need to trim String of statistic score map keys to compare with current team -> mannschaftOfTextField
                        String trimS = trimStringValueToCompare(s);
                        if (trimS.contains(mannschaftOfTextField) || mannschaftOfTextField.contains(trimS)) {
                            statistikScoreMap.replace(s, score);
                        }
                    }
                    System.out.println("Team " + mannschaftOfTextField + " mit Score " + score + " aktualisiert");
                }
            }
            if (SiegeGastProzentual >= 0.35 && SiegeGastProzentual <= 0.50) {
                gastStaerke = true;
                if (tmpNodeID.contains("gast")) {
                    score = 5.0;
                    for (String s : statistikScoreMap.keySet()) {
                        //need to trim String of statistic score map keys to compare with current team -> mannschaftOfTextField
                        String trimS = trimStringValueToCompare(s);
                        if (trimS.contains(mannschaftOfTextField) || mannschaftOfTextField.contains(trimS)) {
                            statistikScoreMap.replace(s, score);
                        }
                    }
                    System.out.println("Team " + mannschaftOfTextField + " mit Score " + score + " aktualisiert");
                }
            }
            if (SiegeGastProzentual >= 0.50) {
                gastStaerkePlus = true;
                if (tmpNodeID.contains("gast")) {
                    score = 7.5;
                    for (String s : statistikScoreMap.keySet()) {
                        //need to trim String of statistic score map keys to compare with current team -> mannschaftOfTextField
                        String trimS = trimStringValueToCompare(s);
                        if (trimS.contains(mannschaftOfTextField) || mannschaftOfTextField.contains(trimS)) {
                            statistikScoreMap.replace(s, score);
                        }
                    }
                    System.out.println("Team " + mannschaftOfTextField + " mit Score " + score + " aktualisiert");
                }
            }
        }

        /**
         * Verletzte
         */
        List<String> injuryList = new ArrayList();
        String injuryURL = "https://www.transfermarkt.de/1-bundesliga/verletztespieler/wettbewerb/L1/plus/1";
        try {
            injuryList = ReadWebContent.readInjuryCoronaPlayer(injuryURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String entry : injuryList) {
            if (entry.contains("HardInjury")) {
                String injuryTeam = entry.substring(0, entry.indexOf('_'));
                for (String mannschaft : Mannschaft.getMannschaftenBundesliga1()) {
                    if (mannschaft.contains(injuryTeam)) {
                        double currentScore = statistikScoreMap.get(mannschaft);
                        currentScore = currentScore - 2.5;
                        statistikScoreMap.replace(mannschaft, currentScore);
                    }
                }
            }
        }


        for (Node node : BasisScoreMannschaftTextFields.getChildren()) {
            TextField mannschaftsField = (TextField) node;
            String textFieldID = mannschaftsField.getId();
            String fieldNr = textFieldID.replaceAll("[^0-9]", "");
            int fieldNrInt = Integer.parseInt(fieldNr);
            String mannschaft = mannschaftsField.getText();

            for (Node node2 : StatistikScoreTextFields1.getChildren()) {
                TextField tmpField = (TextField) node2;
                tmpField.setAlignment(Pos.CENTER);
                String textFieldID2 = tmpField.getId();
                String fieldNr2 = textFieldID2.replaceAll("[^0-9]", "");
                int fieldNrInt2 = Integer.parseInt(fieldNr2);
                if (fieldNrInt == fieldNrInt2) {
                    double scoreToSet = statistikScoreMap.get(mannschaft);
                    tmpField.setText(String.valueOf(scoreToSet));
                }
            }
        }

        logger.info("##### Button Lade Daten in Statistik Score Tab ended #####");
    }

    @FXML
    private void loadGesamtScoreButtonAction(ActionEvent event) {
        logger.info("##### Button Lade Daten in Gesamt Score Mannschaften Tab called #####");

        nextSpielTag = getNextSpieltag();
        //currentSaison = "Saison 2021-2022";
        currentSaison = "Saison 2023-2024";

        ObservableList<Node> nodesMannschaften = BasisScoreMannschaftTextFields2.getChildren();
        Iterator<Node> nodesMannschaftenIterator = nodesMannschaften.iterator();

        ObservableList<Node> nodesGesamtScore = GesamtScoreTextFields.getChildren();
        Iterator<Node> nodesGesamtScoreIterator = nodesGesamtScore.iterator();
        gesamtScoreMap = new LinkedHashMap<>();

        BasisScoreTextFields1.getChildren().addAll(BasisScoreTextFields2.getChildren());
        ObservableList<Node> BasisScoreTextFields = BasisScoreTextFields1.getChildren();
        StatistikScoreTextFields1.getChildren().addAll(StatistikScoreTextFields2.getChildren());
        ObservableList<Node> StatistikScoreTextFields = StatistikScoreTextFields1.getChildren();

//        while (nodesMannschaftenIterator.hasNext()) {
//            Node currentNodeMannschaft = nodesMannschaftenIterator.next();
//            System.out.println("Node Mannschaft: " + currentNodeMannschaft);
//            System.out.println("Node Mannschaft ID: " + currentNodeMannschaft.getId());
//            System.out.println("Node Mannschaft toString: " + currentNodeMannschaft.toString());
//        }
//
//        while(nodesGesamtScoreIterator.hasNext()) {
//            Node currentGesamtScoreNode = nodesGesamtScoreIterator.next();
//            System.out.printf("Node GesamtScore: " + currentGesamtScoreNode);
//            System.out.printf("Node GesamtScore ID: " + currentGesamtScoreNode.getId());
//            System.out.printf("Node GesamtScore toString: " + currentGesamtScoreNode.toString());
//        }

        Node currentNode;
        String mannschaftsNodeID = "";
        int mannschaftsFieldidNrInt = 0;
        TextField tmpField;
        String currentNodeID = "";
        String tmpNodeTrim = "";
        String currentMannschaft = "";
        double GesamtScoretmp = 0;
        double BasisScore = 0;
        double StatistikScore = 0;
        for (int x = 0; x < Mannschaft.getMannschaftenBundesliga1().size(); x++) {
            while (nodesMannschaftenIterator.hasNext()) {
                currentNode = nodesMannschaftenIterator.next();
                currentNodeID = currentNode.getId();
                tmpField = new TextField();
                tmpField.setEditable(true);
                tmpField = (TextField) currentNode;
                tmpNodeTrim = tmpField.getId().replaceAll("[^0-9]", "");
                currentMannschaft = Mannschaft.getMannschaftenBundesliga1().get(x);
                String trimValue = trimStringValueToCompare(currentMannschaft);
                tmpField.setText(currentMannschaft);
                for (Node node : GesamtScoreTextFields.getChildren()) {
                    TextField GesamtScoreTextField = (TextField) node;
                    GesamtScoreTextField.setAlignment(Pos.CENTER);
                    String tmpFieldId = GesamtScoreTextField.getId();
                    String tmpFieldIdNr = tmpFieldId.replaceAll("[^0-9]", "");
                    if (tmpFieldIdNr.equals(tmpNodeTrim)) {
                        BasisScore = basisScoreMap.get(trimValue);
                        StatistikScore = statistikScoreMap.get(currentMannschaft);
                        GesamtScoretmp = BasisScore + StatistikScore;
                        double GesamtScore = Math.round(GesamtScoretmp * 100.0) / 100.0;
                        GesamtScoreTextField.setText(String.valueOf(GesamtScore));
                        gesamtScoreMap.put("Score".concat(trimValue), GesamtScore);
                        logger.info("Gesamtscore puttet: " + GesamtScore);
                    }
                }
                break;
            }
        }
        loadGesamtScore = true;
        logger.info("##### Button Lade Daten in Gesamt Score Mannschaften Tab ended #####");
    }

    @FXML
    private void sortGesamtScoreButtonAction(ActionEvent event) {
        logger.info("##### Button sortGesamtScoreButtonAction called and sortGesamtScoreButton clicked #####");

        ObservableList<Node> nodesMannschaften = BasisScoreMannschaftTextFields2.getChildren();
        Iterator<Node> nodesMannschaftenIterator = nodesMannschaften.iterator();


        ObservableList<Node> nodesGesamtScore = GesamtScoreTextFields.getChildren();
        Iterator<Node> nodesGesamtScoreIterator = nodesGesamtScore.iterator();

        Map<String, Double> sortedGesamtScore = gesamtScoreMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        List<Node> sortedNodesScores = nodesGesamtScore.stream()
                .sorted(Comparator.comparing(i -> i.getId().toString()))
                .collect(Collectors.toList());

        logger.info("Sorted Gesamt Score last try: " + sortedGesamtScore.toString());
        for (Node node : sortedNodesScores) {
            logger.info("Node: " + node);
            logger.info("Node: " + node.toString());
        }

        TextField tmpField = new TextField();
        String tmpFieldID = "";
        while (nodesMannschaftenIterator.hasNext()) {
            Node currentNodeMannschaft = nodesMannschaftenIterator.next();
            tmpField = (TextField) currentNodeMannschaft;
            tmpFieldID = tmpField.getId();
            System.out.println("Node Mannschaft: " + currentNodeMannschaft);
            System.out.println("Node Mannschaft ID: " + currentNodeMannschaft.getId());
            System.out.println("Node Mannschaft toString: " + currentNodeMannschaft.toString());
            for (int x = 1; x < Mannschaft.getMannschaftenBundesliga1().size(); x++) {
                String fieldNr = tmpFieldID.replaceAll("[^0-9]", "");
                int fieldNrInt = Integer.parseInt(fieldNr);
                if (fieldNrInt == x) {
                    String nodeValue = String.valueOf(sortedNodesScores.get(x)).replace("Score", "");
                    tmpField.setText(nodeValue);
                }
            }
        }

        while (nodesGesamtScoreIterator.hasNext()) {
            Node currentGesamtScoreNode = nodesGesamtScoreIterator.next();
            System.out.printf("Node GesamtScore: " + currentGesamtScoreNode);
            System.out.printf("Node GesamtScore ID: " + currentGesamtScoreNode.getId());
            System.out.printf("Node GesamtScore toString: " + currentGesamtScoreNode.toString());
        }


//        while(nodesMannschaftenIterator.hasNext()){
//            TextField tmpField = new TextField();
//            Node tmpnode = nodesMannschaftenIterator.next();
//            tmpField = (TextField) tmpnode;
//            tmpField.setText(sortedGesamtScore.get());
//            for(Node node : )
//        }
        logger.info("##### Button sortGesamtScoreButtonAction ended #####");
    }

    private void openRequestStage(String nameOfRequest, String nameOfFXML) {
        logger.info("##### Function openRequestStage called #####");

        requestStage = new Stage();
        requestStage.setTitle(nameOfRequest);
        requestStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(nameOfFXML));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root, 400, 100);
            requestStage.setScene(scene);
            requestStage.show();

            Platform.runLater(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requestStage.close();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("##### Function openRequestStage ended #####");
    }

    public static String trimStringValueToCompare(String valueOfMannschaftsTextField) {
        logger.info("##### Function trimStringValueToCompare called #####");
//        if(valueOfMannschaftsTextField.contains("VfL Bochum") || valueOfMannschaftsTextField.contains("Hertha BSC") || valueOfMannschaftsTextField.contains("Union Berlin")) {
//        return valueOfMannschaftsTextField;
//        }
        String trimNumbers = valueOfMannschaftsTextField.replaceAll("[0-9]", "");
        String trimLetters = trimNumbers.replace("VfL", "")
                .replace("Vfl", "").replace("FC", "")
                .replace("SC", "").replace("TSG", "")
                .replace("FSV", "").replace("SpVgg ", "")
                .replace("VfB", "").replace("Vfl ", "")
                .replace("SV", "").replace("-", "");
        String trimTeams = trimLetters.replace("RasenBallsport", "RB ").replace("Berlin", "")
                .replace("Borussia", "").replace("Eintracht", "").replace("München", "")
                .replace("Arminia", "").replace("Greuther", "").replace("Bayer ", "")
                .replace("Mönchengladbach", "M'gladbach").replace("Werder", "")
                .replace("SportClub", "").replace("Sport-Club", "");
        String trimValue = trimTeams.replace(" ", "").replace(".", "");
        String trimExtraValueLeipzig = trimValue.replace("RBLeipzig", "RB Leipzig");
        String trimExtraValueBochum = trimExtraValueLeipzig.replace("Bochum", "VfL Bochum");
        String trimExtraValueUnion = trimExtraValueBochum.replace("Union", "Union Berlin");
        String trimExtraValueHertha = trimExtraValueUnion.replace("HerthaB", "Hertha BSC");
        System.out.println("Name of trimmed team: " + trimExtraValueHertha);
        logger.info("Name of trimmed team: " + trimExtraValueHertha);

        logger.info("##### Function trimStringValueToCompare ended #####");
        return trimExtraValueHertha;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("##### Function initialize called #####");

        initMannschaften();
        initMenuItems();
        initKalenderwocheSpieltag();
        logger.info("##### Function initialize called #####");
    }

    private void initMenuItems() {
        logger.info("##### Function initMenuItems called #####");

        /**
         * new for Saison 2022 / 2023
         */
        MenuItem currentSaison = new MenuItem("Saison 2022-2023");
        MenuItem lastSaison = new MenuItem("Saison 2021-2022");
        MenuItem preLastSaison = new MenuItem("Saison 2020-2021");
        //MenuItem currentSaison = new MenuItem("Saison 2021-2022");
        //MenuItem lastSaison = new MenuItem("Saison 2020-2021");

        saisonMenuButton.getItems().add(currentSaison);
        saisonMenuButton.getItems().add(lastSaison);
        saisonMenuButton.getItems().add(preLastSaison);
        currentSaison.setOnAction(event -> {
            saisonAuswahl = "";
            saisonAuswahl = currentSaison.getText();
            saisonMenuButton.setText(saisonAuswahl);
        });
        lastSaison.setOnAction(event -> {
            saisonAuswahl = "";
            saisonAuswahl = lastSaison.getText();
            saisonMenuButton.setText(saisonAuswahl);
        });
        lastSaison.setOnAction(event -> {
            saisonAuswahl = "";
            saisonAuswahl = preLastSaison.getText();
            saisonMenuButton.setText(saisonAuswahl);
        });

        for (int x = 1; x < 35; x++) {
            String spieltag = "" + x;
            MenuItem menuItem = new MenuItem(spieltag);
            spieltagMenuButton.getItems().add(menuItem);
            menuItem.setOnAction(event -> {
                spieltagAuswahl = "";
                spieltagAuswahl = menuItem.getText();
                spieltagMenuButton.setText(spieltagAuswahl);
            });
        }

        saisonMenuButton.setText(saisonMenuButton.getItems().get(0).getText());
        //spieltagMenuButton.setText(spieltagMenuButton.getItems().get(10).getText());
        /**
         * new for Saison 2022 / 2023
         */
        spieltagMenuButton.setText(spieltagMenuButton.getItems().get(3).getText());
        //TODO: int Variable, die den tatsächlichen Spieltag abfragt, kein fester Wert 3

        /**
         * only for test case
         */
        MenuItem testMenuSelection = new MenuItem("test");
        spieltagMenuButton.getItems().add(testMenuSelection);
        testMenuSelection.setOnAction(event -> {
            System.out.println("Option test selected");
        });
        logger.info("##### Function initMenuItems ended #####");
    }

    private static void initMannschaften() {
        logger.info("##### Function initMannschaften called #####");

        m1 = new Mannschaft("Bayern");
        ReadWebContent webContent = new ReadWebContent(m1);
        initWebContent(webContent);
        m2 = new Mannschaft("Dortmund");
        initWebContent(webContent);
        m3 = new Mannschaft("Leverkusen");
        initWebContent(webContent);
        m4 = new Mannschaft("RB Leipzig");
        initWebContent(webContent);
        m5 = new Mannschaft("Hoffenheim");
        initWebContent(webContent);
        m6 = new Mannschaft("Freiburg");
        initWebContent(webContent);
        m7 = new Mannschaft("Köln");
        initWebContent(webContent);
        m8 = new Mannschaft("Mainz");
        initWebContent(webContent);
        m9 = new Mannschaft("Union Berlin");
        initWebContent(webContent);
        m10 = new Mannschaft("Frankfurt");
        initWebContent(webContent);
        m11 = new Mannschaft("VfL Bochum");
        initWebContent(webContent);
        m12 = new Mannschaft("Wolfsburg");
        initWebContent(webContent);
        m13 = new Mannschaft("M'gladbach");
        initWebContent(webContent);
//        m14 = new Mannschaft("Bielefeld");
//        initWebContent(webContent);
        m14 = new Mannschaft("Bremen");
        initWebContent(webContent);
//        m15 = new Mannschaft("Hertha BSC");
//        initWebContent(webContent);
        m15 = new Mannschaft("Heidenheim");
        initWebContent(webContent);
        m16 = new Mannschaft("Augsburg");
        initWebContent(webContent);
        m17 = new Mannschaft("Stuttgart");
        initWebContent(webContent);
//        m18 = new Mannschaft("Fürth");
//        initWebContent(webContent);
//        m18 = new Mannschaft("Schalke");
//        initWebContent(webContent);
        m18 = new Mannschaft("Darmstadt");
        initWebContent(webContent);

        logger.info("##### Function initMannschaften ended #####");
    }

    public static String getSpieltagDone() {
        logger.info("##### Function getaktueller Spieltag called #####");

        Calendar calendar = Calendar.getInstance();
        int Kalenderwoche = calendar.get(Calendar.WEEK_OF_YEAR);
        int lastSpieltagWeek = Kalenderwoche - 1;
        String KalenderwocheForSpieltage = String.valueOf(lastSpieltagWeek);
        String spieltagLinkedKalenderwoche = KalenderSpieltageBundesliga1.get(KalenderwocheForSpieltage);

        System.out.println("Absolvierte Spieltage: " + spieltagLinkedKalenderwoche);
        System.out.println("KW: " + Kalenderwoche);
        System.out.println("letzter Spieltag: " + lastSpieltagWeek);
        logger.info("##### Function getSpieltagDone ended #####");

        return spieltagLinkedKalenderwoche;

    }

    public static String getNextSpieltag() {
        logger.info("##### Function getNextSpieltag called #####");

        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        String weekOfYear = String.valueOf(week);
        String nextSpieltag = KalenderSpieltageBundesliga1.get(weekOfYear);
        if (nextSpieltag == null) {
            week++;
            weekOfYear = String.valueOf(week);
            nextSpieltag = KalenderSpieltageBundesliga1.get(weekOfYear);
        }
        System.out.println("next Spieltag to return: " + nextSpieltag);
        logger.info("##### Function getNextSpieltag ended #####");

        return nextSpieltag;

    }

    public static void initKalenderwocheSpieltag() {

        KalenderSpieltageBundesliga1.put("10", "26");  //10
        KalenderSpieltageBundesliga1.put("11", "27");  //11
        KalenderSpieltageBundesliga1.put("12", "28");  //12
        KalenderSpieltageBundesliga1.put("13", "28");  //13
        KalenderSpieltageBundesliga1.put("14", "29");  //14
        KalenderSpieltageBundesliga1.put("15", "30");  //15
        KalenderSpieltageBundesliga1.put("16", "31");  //16
        KalenderSpieltageBundesliga1.put("17", "32");  //17
        KalenderSpieltageBundesliga1.put("18", "33");  //18
        KalenderSpieltageBundesliga1.put("19", "34");  //18
        /**
         * new for Saison 2023 / 2024
         */
        KalenderSpieltageBundesliga1.put("33", "1");  //KW = Spieltag 1
        KalenderSpieltageBundesliga1.put("34", "2");  //KW = Spieltag 2
        KalenderSpieltageBundesliga1.put("35", "3");  //KW = Spieltag 3
        KalenderSpieltageBundesliga1.put("37", "4");  //KW = Spieltag 4
        KalenderSpieltageBundesliga1.put("38", "5");  //KW = Spieltag 7
        KalenderSpieltageBundesliga1.put("39", "6");  //KW = Spieltag 8
        KalenderSpieltageBundesliga1.put("40", "7");  //KW = Spieltag 9
        KalenderSpieltageBundesliga1.put("42", "8");  //KW = Spieltag 10
        KalenderSpieltageBundesliga1.put("42", "9");  //KW = Spieltag 11
        KalenderSpieltageBundesliga1.put("44", "10");  //KW = Spieltag 12
        KalenderSpieltageBundesliga1.put("45", "11");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("47", "12");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("48", "13");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("49", "14");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("50", "15");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("51", "16");  //KW = Spieltag 13

        //TODO: noch offen
        KalenderSpieltageBundesliga1.put("43", "17");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "18");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "19");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "20");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "21");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "22");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "23");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "24");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "25");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "26");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "27");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "28");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "29");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "30");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "31");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "32");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "33");  //KW = Spieltag 13
        KalenderSpieltageBundesliga1.put("43", "34");  //KW = Spieltag 13


        //TODO: Konflikt für KW 45, da 2 Spieltage (14 und 15) in dieser KW stattfinden
        KalenderSpieltageBundesliga1.put("45", "14");  //KW = Spieltag 14
        KalenderSpieltageBundesliga1.put("45", "15");  //KW = Spieltag 15


        logger.info("##### Function initKalenderwocheSpieltag ended #####");
    }

    private static void initWebContent(ReadWebContent webContent) {
        logger.info("##### Function initWebContent called #####");

        try {
            webContent.readDataFullTable(fullDataTableURL);
            webContent.readDataHomeTable(Liga1HeimTableURL);
            webContent.readDataGastTable(Liga1GastTableURL);
            ReadWebContent.readDataFormTable(MarkwerteFormTableURL);
            webContent.readDataEwigeTabelle(Liga1EwigeTabelleURL);
            ReadWebContent.readMarktWert(MarkwerteTableURL);
            ReadWebContent.readSpielerMarktwert(MarkwerteStuermerTableURL);
            ReadWebContent.readSpielerMarktwert(MarkwerteTorwartTableURL);
            ReadWebContent.readSpielerMarktwert(MarkwerteAbwehrTableURL);
            ReadWebContent.readSpielerMarktwert(MarkwerteMittelfeldTableURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("##### Function initWebContent ended #####");
    }

    public static String randErgebnisUnentschieden() {
        logger.info("##### Function randErgebnisUnentschieden called #####");

        String randErgebnisUnentschieden = "";
        String remis1 = "0:0";
        String remis2 = "1:1";
        String remis3 = "2:2";
        ArrayList<String> remisList = new ArrayList<>();
        remisList.add(remis1);
        remisList.add(remis2);
        remisList.add(remis3);
        int sizeOfList = remisList.size();
        int randIndex = (int) (Math.random() * sizeOfList);

        randErgebnisUnentschieden = remisList.get(randIndex);
        System.out.println("Rand Unentschieden: " + randErgebnisUnentschieden);

        logger.info("##### Function randErgebnisUnentschieden ended #####");

        return randErgebnisUnentschieden;

    }

    public static String randErgebnisLowVictory(boolean reverse) {
        logger.info("##### Function randErgebnisLowVictory called #####");

        String randErgebnisLowVictory = "";
        String LowVictory1 = "1:0";
        String LowVictory2 = "2:0";
        String LowVictory3 = "2:1";
        String LowVictory4 = "3:2";


        ArrayList<String> lowVictoryList = new ArrayList<>();
        lowVictoryList.add(LowVictory1);
        lowVictoryList.add(LowVictory2);
        lowVictoryList.add(LowVictory3);
        lowVictoryList.add(LowVictory4);

        int sizeOfList = lowVictoryList.size();
        int randIndex = (int) (Math.random() * sizeOfList);

        randErgebnisLowVictory = lowVictoryList.get(randIndex);
        System.out.println("Rand Knapper Sieg: " + randErgebnisLowVictory);

        if (reverse) {
            String[] splitChars = randErgebnisLowVictory.split("");
            String randErgebnisLowVictoryReverse = splitChars[2] + splitChars[1] + splitChars[0];
            System.out.println("Rand Knapper Sieg Reverse Print: " + randErgebnisLowVictoryReverse);
            return randErgebnisLowVictoryReverse;
        }
        logger.info("##### Function randErgebnisLowVictory ended #####");

        return randErgebnisLowVictory;

    }

    public static String randErgebnisHighVictory(boolean reverse) {
        logger.info("##### Function randErgebnisHighVictory called #####");

        String randErgebnisHighVictory = "";
        String HighVictory1 = "3:0";
        String HighVictory2 = "3:1";
        String HighVictory3 = "4:0";
        String HighVictory4 = "5:0";
        String HighVictory5 = "4:1";
        String HighVictory6 = "5:1";

        ArrayList<String> highVictoryList = new ArrayList<>();
        highVictoryList.add(HighVictory1);
        highVictoryList.add(HighVictory2);
        highVictoryList.add(HighVictory3);
        highVictoryList.add(HighVictory4);
        highVictoryList.add(HighVictory5);
        highVictoryList.add(HighVictory6);

        int sizeOfList = highVictoryList.size();
        int randIndex = (int) (Math.random() * sizeOfList);

        randErgebnisHighVictory = highVictoryList.get(randIndex);
        System.out.println("Rand Deutlicher Sieg: " + randErgebnisHighVictory);

        if (reverse) {
            String[] splitChars = randErgebnisHighVictory.split("");
            String randErgebnisHighVictoryReverse = splitChars[2] + splitChars[1] + splitChars[0];
            System.out.println("Rand Deutlicher Sieg Reverse Print: " + randErgebnisHighVictory);
            return randErgebnisHighVictoryReverse;
        }
        logger.info("##### Function randErgebnisHighVictory ended #####");

        return randErgebnisHighVictory;
    }

    @FXML
    private void spieltagTextFieldAction(ActionEvent event) {
    }

    @FXML
    private void seasonTextFieldAction(ActionEvent event) {
    }

    @FXML
    private void saisonMenuAction(ActionEvent event) {
        saisonMenu.setOnAction((e) -> {
            System.out.println("MenuButton saisonMenu clicked!");
        });
    }

    @FXML
    private void saisonMenuButtonAction(ActionEvent event) {
        saisonMenu.setOnAction((e) -> {
            System.out.println("MenuButton saisonMenu clicked!");
        });
    }

    @FXML
    private void spieltagMenuAction(ActionEvent event) {
        spieltagMenu.setOnAction((e) -> {
            System.out.println("SplitMenuButton spieltagMenu clicked!");
        });
    }

    @FXML
    private void spieltagMenuButtonAction(ActionEvent event) {

    }


    @FXML
    private void heimmannschaft3TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft1TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField4Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField7Action(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField7Action(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft5TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft0TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField1Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField1Action(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField3Action(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField5Action(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField8Action(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft7TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField0Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField3Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField5Action(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft8TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField2Action(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft2TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField3Action(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField4Action(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField6Action(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft6TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void heimmannschaft4TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField5Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField0Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField2Action(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField2Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField6Action(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField4Action(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField8Action(ActionEvent event) {
    }

    @FXML
    private void AnsetzungTextField7Action(ActionEvent event) {
    }


    @FXML
    private void GastMannschaftTextField1Action(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField6Action(ActionEvent event) {
    }

    @FXML
    private void GastMannschaftTextField0Action(ActionEvent event) {
    }

    @FXML
    private void ErgebnisSpieltagTextField8Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField1Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField2Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField3Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField4Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField5Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField6Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField7Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField8Action(ActionEvent event) {
    }

    @FXML
    private void SenderTextField9Action(ActionEvent event) {
    }

    /**
     * Basis Score Mannschaften Menu Tab All Action Field for TextField, Labels and Buttons
     *
     * @param event
     */
    @FXML
    private void Mannschaft9TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField3Action(ActionEvent event) {
    }

    @FXML
    private void Mannschaft7TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void Mannschaft3TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField1Action(ActionEvent event) {
    }

    @FXML
    private void Mannschaft5TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField7Action(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField9Action(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField6Action(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField2Action(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField5Action(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField4Action(ActionEvent event) {
    }

    @FXML
    private void Mannschaft2TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void Mannschaft6TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void Mannschaft1TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void Mannschaft4TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void Mannschaft8TextFieldAction(ActionEvent event) {
    }

    @FXML
    private void scoreWertTextField8Action(ActionEvent event) {
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        logger.info("##### Function actionPerformed called #####");

        System.out.println("du oller Seppel");
        logger.info("##### Function actionPerformed ended #####");

    }

}