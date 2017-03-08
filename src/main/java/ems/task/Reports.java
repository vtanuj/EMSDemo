/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.task;

import ems.utils.DataHandler;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author tanujv
 */
public class Reports extends Task<Void> {

    private final String[] params;

    public Reports(Stage stage, Window window, String... params) {

        this.params = params;

        setOnSucceeded((WorkerStateEvent event) -> {
            if (stage != null) {
                window.getScene().getRoot().setEffect(null);
                stage.hide();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Okay");
            alert.showAndWait();
        });
    }

    @Override
    protected Void call() throws Exception {
        try {
            DataHandler.getReport(params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
