/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.main;

import static ems.util.Constants.HEADER_SPLASH;
import static ems.util.Constants.IMAGE_FAVICON;
import static ems.util.Constants.IMAGE_SPLASH;
import static ems.util.Constants.PATH_AUTH_DB;
import static ems.util.Constants.PATH_TEMP;
import static ems.util.Constants.PATH_TEMP_DB_;
import static ems.util.Constants.TITLE_HOME;
import ems.util.MyUtils;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import org.apache.log4j.Logger;

/**
 *
 * @author tanujv
 */
public class Ems extends Application {

    public static final String APPLICATION_ICON = IMAGE_SPLASH;
    public static final String SPLASH_IMAGE = IMAGE_SPLASH;

    private VBox splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Label space;
    private Label space1;
    private Label space2;
    private Label header;
    private static final int SPLASH_WIDTH = 500;
    private static final int SPLASH_HEIGHT = 350;
    public static Logger log = Logger.getLogger(Ems.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(
                SPLASH_IMAGE
        ));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 10);
        progressText = new Label("Please wait loading in progress . . .");
        header = new Label(HEADER_SPLASH);
        space = new Label("\n");
        space1 = new Label("\n");
        space2 = new Label("\n");
        header.setStyle(
                "-fx-font-size: 30px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: grey;");
        splashLayout = new VBox();
        splashLayout.setAlignment(Pos.CENTER);
        splashLayout.getChildren().addAll(splash, space, header, space1, space2, loadProgress, progressText);
        progressText.setAlignment(Pos.BASELINE_LEFT);
        splashLayout.setStyle(
                "-fx-padding: 5; "
                + "-fx-background-color: white; "
                + "-fx-border-width: 2; "
                + "-fx-border-radius: 8px;"
                + "-fx-border-color: grey;"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {

                ObservableList<String> foundFriends = FXCollections.<String>observableArrayList();
                ObservableList<String> availableFriends = FXCollections.observableArrayList(
                        "database", "modules"
                );

                updateMessage("Loading please wait . . .");
                for (int i = 0; i < availableFriends.size(); i++) {
                    Thread.sleep(1000);
                    File tempFolder = new File(PATH_TEMP);
                    if (!tempFolder.exists()) {
                        tempFolder.mkdirs();
                    }
                    File authFile = new File(PATH_AUTH_DB);
                    if (!authFile.exists()) {
                        MyUtils.createAuthDB();
                    }
                    File tempDBFile = new File(PATH_TEMP_DB_);
                    if (!tempDBFile.exists()) {
                        MyUtils.copyTempDB();
                    }
                    updateProgress(i + 1, availableFriends.size());
                    String nextFriend = availableFriends.get(i);
                    foundFriends.add(nextFriend);
                    updateMessage("Loading . . . " + nextFriend);
                }
                Thread.sleep(400);
                updateMessage("All modules loaded.");

                return foundFriends;
            }
        };

        showSplash(initStage, friendTask, () -> showLoginStage(initStage));
        new Thread(friendTask).start();
    }

    private void showLoginStage(Stage stage) {
        try {
            if (MyUtils.checkIfAlreadyRunning()) {
                log.error("Application is already running.");
                Alert alert = new Alert(AlertType.WARNING);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.setTitle("Warning");
                alert.setHeaderText("Application already running!");
                alert.setContentText("An instance of the application is already running. "
                        + "Please close all the instances and run again.");
                alert.show();
            } else {
                log.info("Application started.");
                Stage loginStage = new Stage(StageStyle.DECORATED);
                Parent root = FXMLLoader.load(getClass().getResource("/ems/fxml/Login.fxml"));
                Scene scene = new Scene(root);
                loginStage.setTitle(TITLE_HOME);
                loginStage.setScene(scene);
                loginStage.getIcons().add(new Image(IMAGE_FAVICON));
                loginStage.show();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {

        void complete();
    }

}
