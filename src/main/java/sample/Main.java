package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Main class to handle the primary operations
 * application will be started with launch(args) command that calls start function
 * Frontend of application is realized with FXML, therefore FXMLLoader class is used
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 25.08.2022
 */
public class Main extends Application {

    private static final Logger logger = LogManager.getLogger("Main");

    /**
     * main function is called at first
     *
     * @param args
     */
    public static void main(String[] args) {
        //System.out.println("INFO: JAVA FX VERSION " + VersionInfo.getRuntimeVersion() + " USED");
        //logger.info("INFO: JAVA FX VERSION " + VersionInfo.getRuntimeVersion() + " USED");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Application starts");
        startApplication(primaryStage);
    }

    /**
     * function to perform restart of application
     * in some cases content data cant be read correctly and restart is necessary
     *
     * @param stage for the current stage to handle restart
     * @throws IOException
     */
    public static void restartApplication(Stage stage) throws IOException {
        //cleaning....
        startApplication(stage);
    }

    /**
     * function to start main application
     * a primary stage instance is given from start function to handle all operations
     *
     * @param stage is the primary stage
     * @throws IOException
     */
    private static void startApplication(Stage stage) throws IOException {
        StageManager.getInstance().setPrimaryStage(stage);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/fxml/startpage.fxml"));

        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();

        Platform.setImplicitExit(false);
        stage.setTitle("Wettquoten Calculator");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
