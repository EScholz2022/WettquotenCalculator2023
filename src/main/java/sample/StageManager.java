package sample;

import javafx.stage.Stage;

/**
 * Class is used to represent different Stages of Frontend Application
 * to change between primary Stage and other stages, this class handles stage exchange
 *
 * @author Enrico Scholz
 * @version 1.0
 * @since 25.08.2022
 */
public class StageManager {

    private static StageManager instance;
    private static Stage primaryStage;
    private static Stage popupStage;

    //private constructor, so you cant modify
    private StageManager() {
    }

    /**
     * function used to call functions from primarystage
     *
     * @return singleton instance
     */
    public synchronized static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPopupStage() {
        return popupStage;
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }
}

