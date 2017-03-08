/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

/**
 *
 * @author tanujv
 */
public class JavaFXUtils {

    public static Stage dialog(Stage dialog, Window stage) {
        dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        ProgressIndicator pi = new ProgressIndicator();
        pi.setMinWidth(100);
        pi.setMinHeight(100);
        dialogVbox.getChildren().add(pi);
        dialogVbox.setStyle("-fx-background-color: transparent");
        Scene dialogScene = new Scene(dialogVbox, 100, 100, Color.TRANSPARENT);
        dialog.setScene(dialogScene);
        return dialog;
    }

    public static void dim(Window stage) {
        final BoxBlur soften = new BoxBlur();
        final ColorAdjust dim = new ColorAdjust();
        {
            dim.setInput(soften);
            dim.setBrightness(-0.5);
        }
        stage.getScene().getRoot().setEffect(dim);
    }

    public static Glyph getGraphic(String fontFamily, FontAwesome.Glyph icon, int size, Color color) {
        Glyph glyph = new Glyph(fontFamily, icon);
        glyph.setFontSize(size);
        glyph.setColor(color);
        return glyph;
    }

    public static void exceptionDialog(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Look, an Exception Dialog");
        alert.setContentText("Could not find file blabla.txt!");
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

}
