/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.screens;

import static ems.utils.Constants.HEADER_ABOUT;
import static ems.utils.Constants.IMAGE_LOGO;
import static ems.utils.Constants.VENDOR_NAME;
import static ems.utils.Constants.VERSION;
import ems.utils.JavaFXUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;

/**
 *
 * @author tanujv
 */
public class AboutController implements Initializable {

    @FXML
    private ImageView aboutImage;
    @FXML
    private Label headerAbout;
    @FXML
    private Label vendor;
    @FXML
    private Label version;

    @FXML
    private Button closeButton;

    @FXML
    private void cancelAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set Image
        aboutImage.setImage(new Image(IMAGE_LOGO));
        //Set Header
        headerAbout.setText(HEADER_ABOUT);
        //Set Header
        vendor.setText(VENDOR_NAME);
        //Set Header
        version.setText(VERSION);
        
        closeButton.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CHECK, 16, Color.GREEN));

    }

}
