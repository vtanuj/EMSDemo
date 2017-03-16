/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.task;

import ems.controller.StatusUpdateController;
import ems.model.MyModelSimpleStringProperty;
import static ems.util.Constants.IMAGE_FAVICON;
import static ems.util.Constants.TITLE_ABOUT;
import ems.util.DataHandler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author tanujv
 */
public class GetData extends Task<Void> {

    String reportType;
    String data;
    List<MyModelSimpleStringProperty> statusUpdateDetails;

    public GetData(Stage stage, Window window, String reportType, String data) {
        this.reportType = reportType;
        this.data = data;
        setOnSucceeded((WorkerStateEvent event) -> {
            try {
                Stage statusUpdateStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ems/fxml/StatusUpdate.fxml"));
                Parent root = loader.load();
                StatusUpdateController statusUpdateController = loader.<StatusUpdateController>getController();
                statusUpdateController.initStatusUpdateDetails(statusUpdateDetails);
                Scene scene = new Scene(root);
                statusUpdateStage.setTitle(TITLE_ABOUT);
                statusUpdateStage.setScene(scene);
                statusUpdateStage.getIcons().add(new Image(IMAGE_FAVICON));
                statusUpdateStage.show();
                if (stage != null) {
                    window.getScene().getRoot().setEffect(null);
                    stage.hide();
                }
            } catch (IOException ex) {
                Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    protected Void call() throws Exception {
        statusUpdateDetails = DataHandler.getReportDetails(reportType, data);
        return null;
    }

}
