/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.controller;

import static ems.main.Ems.log;
import ems.model.MyModel;
import ems.model.MyModelConverter;
import static ems.util.Constants.COLORS;
import static ems.util.Constants.GENDER;
import ems.util.DataHandler;
import ems.util.DateUtils;
import ems.util.JavaFXUtils;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author tanujv
 */
public class VoterDetailsController implements Initializable {

    @FXML
    private Label wardNo;
    @FXML
    private Label wardSrNo;
    @FXML
    private Label srNo;
    @FXML
    private Label accNo;
    @FXML
    private Label name;
    @FXML
    private Label nameUnicode;
    @FXML
    private Label address;
    @FXML
    private Label boothName;
    @FXML
    private TextField emailId;
    @FXML
    private TextField mobileNumber;
    @FXML
    private TextField altMobileNumber;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<MyModel> gender;
    private final ObservableList<MyModel> genderData = FXCollections.observableArrayList();
    @FXML
    private Label idCardNumber;
    @FXML
    private Label age;
    @FXML
    private ComboBox<MyModel> community;
    private final ObservableList<MyModel> communityData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<MyModel> color;
    private final ObservableList<MyModel> colorData = FXCollections.observableArrayList();

    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton;

    MyModel voterDetails;

    public void initVoterDetails(MyModel voterDetails) {
        this.voterDetails = voterDetails;
        wardNo.setText(voterDetails.getObj1());
        accNo.setText(voterDetails.getObj2());
        wardSrNo.setText(voterDetails.getObj3());
        srNo.setText(voterDetails.getObj4());
        name.setText(voterDetails.getObj5());
        address.setText(voterDetails.getObj6());
        boothName.setText(voterDetails.getObj7());
        emailId.setText(voterDetails.getObj8());
        mobileNumber.setText(voterDetails.getObj9());
        altMobileNumber.setText(voterDetails.getObj10());
        idCardNumber.setText(voterDetails.getObj14());
        if (voterDetails.getObj11() != null && !voterDetails.getObj11().isEmpty()) {
            dateOfBirth.setValue(LocalDate.parse(voterDetails.getObj11(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        age.setText(voterDetails.getObj13());

        if (voterDetails.getObj12() != null && !voterDetails.getObj12().isEmpty()) {
            gender.setValue(new MyModel(voterDetails.getObj12(), voterDetails.getObj12().equals("M") ? "Male" : "Female"));
        }

        if (voterDetails.getObj15() != null && !voterDetails.getObj15().isEmpty()) {
            community.setValue(new MyModel(voterDetails.getObj15(), voterDetails.getObj15()));
        }

        if (voterDetails.getObj16() != null && !voterDetails.getObj16().isEmpty()) {
            String colorName = "";
            switch (voterDetails.getObj16()) {
                case "1":
                    colorName = "Our";
                    break;
                case "2":
                    colorName = "Opposite";
                    break;
                case "3":
                    colorName = "Unpredictable";
                    break;
                case "4":
                    colorName = "Others";
                    break;
            }
            color.setValue(new MyModel(voterDetails.getObj16(), colorName));
        }

        nameUnicode.setText(voterDetails.getObj17());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dateOfBirth.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

//        dateOfBirth.setOnAction(event -> {
//            LocalDate date = dateOfBirth.getValue();
//            log.info("Selected date: " + date);
//            try {
//                String calculatedAge = DateUtils.calculateAge(date.toString());
//                age.setText(calculatedAge);
//            } catch (ParseException ex) {
//                log.error(ex.getMessage());
//            }
//        });
        gender.setConverter(new MyModelConverter());
        for (MyModel report : GENDER) {
            genderData.add(report);
        }
        gender.setItems(genderData);

        color.setConverter(new MyModelConverter());

        List<MyModel> COLORS = new LinkedList<>();
        COLORS.add(new MyModel("1", "Our"));
        COLORS.add(new MyModel("2", "Opposite"));
        COLORS.add(new MyModel("3", "Unpredictable"));
        COLORS.add(new MyModel("4", "Others"));
        for (MyModel report : COLORS) {
            colorData.add(report);
        }
        color.setItems(colorData);

        community.setConverter(new MyModelConverter());
        List<MyModel> communityList = DataHandler.getCommunityList();
        for (MyModel report : communityList) {
            communityData.add(report);
        }
        community.setItems(communityData);

        updateButton.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CHECK, 16, Color.GREEN));
        cancelButton.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CLOSE, 16, Color.RED));

    }

    @FXML
    private void cancelAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void updateAction(ActionEvent event) {

        DataHandler.updateVoterDetails(emailId.getText(), mobileNumber.getText(), altMobileNumber.getText(),
                dateOfBirth.getValue().toString(),
                this.color.getSelectionModel().getSelectedItem().getObj1(),
                this.community.getSelectionModel().getSelectedItem().getObj1(),
                this.gender.getSelectionModel().getSelectedItem().getObj1(),
                voterDetails.getObj1(),
                voterDetails.getObj3());

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
