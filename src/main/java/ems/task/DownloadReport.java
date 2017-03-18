/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.task;

import static ems.main.Ems.log;
import ems.util.DataHandler;
import ems.util.JavaFXUtils;
import ems.util.MyUtils;
import java.io.File;
import java.util.Optional;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author tanujv
 */
public class DownloadReport extends Task<Void> {

    String[] params;
    File file;
    boolean status;

    public DownloadReport(Stage stage, Window window, File file, String... params) {
        this.params = params;
        this.file = file;
        setOnSucceeded((WorkerStateEvent event) -> {
            if (stage != null) {
                window.getScene().getRoot().setEffect(null);
                stage.hide();
            }
            if (status) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Export successful!");
                alert.setContentText("Successfully exported the report.");
                ButtonType buttonTypeOne = new ButtonType("Open Folder");
                ButtonType buttonTypeTwo = new ButtonType("Open File");
                ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeOk);

                Optional<ButtonType> result = alert.showAndWait();
                try {
                    if (result.get() == buttonTypeOne) {
                        MyUtils.openFolderWithFileSelected(file.getAbsolutePath());
                    } else if (result.get() == buttonTypeTwo) {
                        MyUtils.openFile(file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    JavaFXUtils.exceptionDialog(e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failure");
                alert.setHeaderText("Export unsuccessful!");
                alert.setContentText("Unable to export the report.");
                alert.showAndWait();
            }
        });
    }

    @Override
    protected Void call() throws Exception {
        try {
            status = DataHandler.exportData(file, params);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
