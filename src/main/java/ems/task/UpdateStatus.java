/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.task;

import ems.model.MyModelSimpleStringProperty;
import ems.util.DataHandler;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author tanujv
 */
public class UpdateStatus extends Task<Void> {

    ObservableList<MyModelSimpleStringProperty> list;
    String status;

    public UpdateStatus(Stage stage, Window window, ObservableList<MyModelSimpleStringProperty> list,
            String status) {
        this.list = list;
        this.status = status;
        setOnSucceeded((WorkerStateEvent event) -> {
            if (stage != null) {
                window.getScene().getRoot().setEffect(null);
                stage.hide();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Okay");
            alert.showAndWait();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        });
    }

    @Override
    protected Void call() throws Exception {
        DataHandler.updateVoterStatus(list, status);
        return null;
    }

}
