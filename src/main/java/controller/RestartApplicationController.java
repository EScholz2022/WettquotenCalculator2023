package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.StageManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller class to handle application restarts
 * in case of restart, popup stage will inform about restarting
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 25.08.2022
 */
public class RestartApplicationController implements Initializable {

    @FXML
    Label restartApplicationLabel;

    @FXML
    AnchorPane anchorPaneConflictFXML;

    private static Stage conflictStage;

    /**
     * function to inform about data conflicts
     * fxml file will be called in new stage as popup
     *
     * @param specificConflictRequest used for the popup title
     * @param conflictFXML            used for the fxml file name
     */
    public static void showConflictStage(String specificConflictRequest, String conflictFXML) {
        conflictStage = new Stage();
        conflictStage.setTitle(specificConflictRequest);
        conflictStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(RestartApplicationController.class.getResource(conflictFXML));

        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root, 400, 200);
            conflictStage.setScene(scene);
            conflictStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * function is used to restart application with mouseclick
     *
     * @param event for the Actionevent of mouseclick
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    private void restartApplicationMouseClick(ActionEvent event) throws IOException, InterruptedException {
        System.out.println("#IMPORTANT-INFO Mouse Clicked on Restart Application Field");
        Stage primaryStage = StageManager.getInstance().getPrimaryStage();
        conflictStage.close();
        Platform.exit();
        Thread.sleep(3000);

        StageManager.getInstance().setPrimaryStage(primaryStage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/startpage.fxml"));

        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();

        primaryStage.setTitle("Wettquoten Calculator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void restartApplicationMousePressed(ActionEvent event) {
        System.out.println("#IMPORTANT-INFO Mouse Pressed on Restart Application Field");

    }

    /**
     * function called automatically at the beginning
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMouseOverEvents();
    }

    /**
     * function to handle MouseOverEvents
     */
    public void setMouseOverEvents() {
        restartApplicationLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                anchorPaneConflictFXML.getChildren().remove(0);
                restartApplicationLabel.setLayoutX(40);
                restartApplicationLabel.setLayoutY(50);
                restartApplicationLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
                restartApplicationLabel.setTextAlignment(TextAlignment.CENTER);
                restartApplicationLabel.setText("   Bitte warten... \n\t Anwendung wird neu gestartet");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage currentPrimaryStage = StageManager.getInstance().getPrimaryStage();
                        try {
                            Main.restartApplication(currentPrimaryStage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            conflictStage.close();

                        }
                    }
                });

            }
        });
    }
}
