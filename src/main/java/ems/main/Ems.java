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
import static ems.util.Constants.PATH_FONT_UNICODE;
import static ems.util.Constants.PATH_FONT_UNICODE_;
import static ems.util.Constants.PATH_REPORT_1;
import static ems.util.Constants.PATH_REPORT_1_;
import static ems.util.Constants.PATH_REPORT_2;
import static ems.util.Constants.PATH_REPORT_2_;
import static ems.util.Constants.PATH_REPORT_3;
import static ems.util.Constants.PATH_REPORT_3_;
import static ems.util.Constants.PATH_REPORT_4;
import static ems.util.Constants.PATH_REPORT_4_;
import static ems.util.Constants.PATH_TEMP;
import static ems.util.Constants.PATH_TEMP_DB;
import static ems.util.Constants.PATH_TEMP_DB_;
import static ems.util.Constants.TITLE_HOME;
import ems.util.MyUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;

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
        final Task task = new Task() {
            @Override
            protected Void call() throws InterruptedException, IOException {

                Files.walk(Paths.get(PATH_TEMP))
                        .map(Path::toFile)
                        .sorted((o1, o2) -> -o1.compareTo(o2))
                        .forEach(File::delete);

                Thread.sleep(400);
                updateMessage("Loading please wait . . .");
                //Make temp folders
                updateMessage("Loading . . . folders");
                File tempFolder = new File(PATH_TEMP);
                if (!tempFolder.exists()) {
                    tempFolder.mkdirs();
                }
                updateProgress(10, 100);

                //Make auth db
                updateMessage("Loading . . . Authentication Database");
                File authFile = new File(PATH_AUTH_DB);
                if (!authFile.exists()) {
                    MyUtils.createAuthDB();
                }
                updateProgress(20, 100);

                //copy temp db
                updateMessage("Loading . . . Voters Database");
                File tempDBFile = new File(PATH_TEMP_DB_);
                if (!tempDBFile.exists()) {
                    MyUtils.copyFile(PATH_TEMP_DB, PATH_TEMP_DB_);
                }
                updateProgress(30, 100);

                //copy font
                updateMessage("Loading . . . fonts");
                File fontFile = new File(PATH_FONT_UNICODE);
                if (!fontFile.exists()) {
                    MyUtils.copyFile(PATH_FONT_UNICODE, PATH_FONT_UNICODE_);
                }
                updateProgress(40, 100);

                //copy reports
                updateMessage("Loading . . . reports");
                File reportFile = new File(PATH_REPORT_1);
                if (!reportFile.exists()) {
                    MyUtils.copyFile(PATH_REPORT_1, PATH_REPORT_1_);
                }
                updateProgress(70, 100);
                reportFile = new File(PATH_REPORT_2);
                if (!reportFile.exists()) {
                    MyUtils.copyFile(PATH_REPORT_2, PATH_REPORT_2_);
                }
                updateProgress(80, 100);
                reportFile = new File(PATH_REPORT_3);
                if (!reportFile.exists()) {
                    MyUtils.copyFile(PATH_REPORT_3, PATH_REPORT_3_);
                }
                updateProgress(85, 100);
                reportFile = new File(PATH_REPORT_4);
                if (!reportFile.exists()) {
                    MyUtils.copyFile(PATH_REPORT_4, PATH_REPORT_4_);
                }
                updateProgress(90, 100);

                Thread.sleep(400);
                updateMessage("All modules loaded.");

                return null;
            }
        };

        showSplash(initStage, task, () -> showLoginStage(initStage));
        new Thread(task).start();
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
//                Stage loginStage = new Stage(StageStyle.DECORATED);
//                Parent root = FXMLLoader.load(getClass().getResource("/ems/fxml/Login.fxml"));
//                Scene scene = new Scene(root);
//                loginStage.setTitle(TITLE_HOME);
//                loginStage.setScene(scene);
//                loginStage.getIcons().add(new Image(IMAGE_FAVICON));
//                loginStage.show();

                Stage homeStage = new Stage(StageStyle.DECORATED);
                Parent root = FXMLLoader.load(getClass().getResource("/ems/fxml/Home.fxml"));
                Scene scene = new Scene(root);
                homeStage.setTitle(TITLE_HOME);
                homeStage.setScene(scene);
//            homeStage.setMaximized(true);
                homeStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent window) {
                        Notifications.create().title("Welcome back!").text("We missed you.").showWarning();
                    }
                });

                homeStage.getIcons().add(new Image(IMAGE_FAVICON));
                homeStage.show();
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
