package sample;

;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;

public class UsefulHelperMethodsCollection {

    /**
     * method not used any more, but a good example how 2 write from Java direct into xls
     *
     * @throws IOException
     * @throws WriteException
     */
    private static void writeDataToExcel() throws IOException, WriteException {

        //init process
        String path = "pathToMyXLS";
        WritableWorkbook workbook = jxl.Workbook.createWorkbook(new File(path));
        WritableSheet sheet = workbook.createSheet("Excel Sheet Name", 0);

        //cells A1, B1, C1 are setted, in this case as the headline
        jxl.write.Label labelSpieltagTitel = new jxl.write.Label(0, 0, "SPIELTAG BEGEGNUNG");
        sheet.addCell(labelSpieltagTitel);
        jxl.write.Label labelVorhersageTitel = new jxl.write.Label(1, 0, "VORHERSAGE");
        sheet.addCell(labelVorhersageTitel);
        jxl.write.Label labelErgebnisTitel = new jxl.write.Label(2, 0, "ERGEBNIS REAL");
        sheet.addCell(labelErgebnisTitel);

        /*sheet.addCell(new jxl.write.Label(0, 1, "TEAM1"));
        sheet.addCell(new jxl.write.Label(0, 2, "TEAM2"));
        sheet.addCell(new jxl.write.Label(0, 3, "TEAM3"));
        sheet.addCell(new jxl.write.Label(0, 4, "TEAM4"));
        sheet.addCell(new jxl.write.Label(0, 5, "TEAM5"));
        sheet.addCell(new jxl.write.Label(0, 6, "TEAM6"));
        sheet.addCell(new jxl.write.Label(0, 7, "TEAM7"));
        sheet.addCell(new jxl.write.Label(0, 8, "TEAM8"));
        sheet.addCell(new jxl.write.Label(0, 9, "TEAM9"));

        sheet.addCell(new jxl.write.Label(1, 1, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 2, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 3, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 4, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 5, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 6, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 7, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 8, "Vorher"));
        sheet.addCell(new jxl.write.Label(1, 9, "Vorher"));

        sheet.addCell(new jxl.write.Label(2, 1, "Real"));
        sheet.addCell(new jxl.write.Label(2, 2, "Real"));
        sheet.addCell(new jxl.write.Label(2, 3, "Real"));
        sheet.addCell(new jxl.write.Label(2, 4, "Real"));
        sheet.addCell(new jxl.write.Label(2, 5, "Real"));
        sheet.addCell(new jxl.write.Label(2, 6, "Real"));
        sheet.addCell(new jxl.write.Label(2, 7, "Real"));
        sheet.addCell(new jxl.write.Label(2, 8, "Real"));
        sheet.addCell(new jxl.write.Label(2, 9, "Real"));*/
    }
}
