/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.controller;

import static ems.main.Ems.log;
import ems.model.MyModel;
import ems.model.MyModelConverter;
import ems.model.MyModelSimpleStringProperty;
import ems.task.UpdateStatus;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author vl
 */
public class StatusUpdateController implements Initializable {

    @FXML
    private ComboBox<MyModel> color;
    private final ObservableList<MyModel> colorData = FXCollections.observableArrayList();

    @FXML
    private TableView<MyModelSimpleStringProperty> statusUpdateTable;
    @FXML
    private TableColumn<MyModelSimpleStringProperty, Boolean> reportColumn0;
    @FXML
    private TableColumn reportColumn1;
    @FXML
    private TableColumn reportColumn2;
    @FXML
    private TableColumn reportColumn3;
    @FXML
    private TableColumn reportColumn4;
    @FXML
    private TableColumn reportColumn5;
    @FXML
    private TableColumn reportColumn6;
    @FXML
    private TableColumn reportColumn7;
    @FXML
    private TableColumn reportColumn8;
    @FXML
    private TableColumn reportColumn9;
    @FXML
    private TableColumn reportColumn10;
    @FXML
    private TableColumn reportColumn11;
    public static ObservableList<MyModelSimpleStringProperty> reportTableData = FXCollections.observableArrayList();

    public void initStatusUpdateDetails(List<MyModelSimpleStringProperty> statusUpdateDetails) {
        for (MyModelSimpleStringProperty reportDetail : statusUpdateDetails) {
            reportTableData.add(reportDetail);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Create checkbox
        CheckBox selectAll = new CheckBox();

//        reportColumn0.setCellValueFactory(new MyModelSimpleStringPropertyValueFactory());
//Make one column use checkboxes instead of text
        reportColumn0.setCellFactory(CheckBoxTableCell.forTableColumn(reportColumn0));
        //Change ValueFactory for each column
        reportColumn0.setCellValueFactory(new PropertyValueFactory<>("selected"));

        reportColumn1.setCellValueFactory(new PropertyValueFactory<>("obj1"));
        reportColumn2.setCellValueFactory(new PropertyValueFactory<>("obj2"));
        reportColumn3.setCellValueFactory(new PropertyValueFactory<>("obj3"));
        reportColumn4.setCellValueFactory(new PropertyValueFactory<>("obj4"));
        reportColumn5.setCellValueFactory(new PropertyValueFactory<>("obj5"));
        reportColumn6.setCellValueFactory(new PropertyValueFactory<>("obj6"));
        reportColumn7.setCellValueFactory(new PropertyValueFactory<>("obj7"));
        reportColumn8.setCellValueFactory(new PropertyValueFactory<>("obj8"));
        reportColumn9.setCellValueFactory(new PropertyValueFactory<>("obj9"));
        reportColumn10.setCellValueFactory(new PropertyValueFactory<>("obj10"));
        reportColumn11.setCellValueFactory(new PropertyValueFactory<>("obj11"));
        reportColumn1.setText("Ward No");
        reportColumn2.setText("Ward Sr No");
        reportColumn3.setText("New Sr No.");
        reportColumn4.setText("Name");
        reportColumn5.setText("Sex");
        reportColumn6.setText("Age");
        reportColumn7.setText("ID Card No.");
        reportColumn8.setText("Mobile No.");
        reportColumn9.setText("DOB");
        reportColumn10.setText("Language");
        reportColumn11.setText("Booth Name");

        //Use box as column header
        reportColumn0.setGraphic(selectAll);
        //Select all checkboxes when checkbox in header is pressed
        selectAll.setOnAction(e -> selectAllBoxes(e));
        statusUpdateTable.setEditable(true);
        statusUpdateTable.setItems(reportTableData);
        TableFilter tableFilter = new TableFilter(statusUpdateTable);

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
    }

    public static void selectAllBoxes(ActionEvent e) {

        //Iterate through all items in ObservableList
        for (MyModelSimpleStringProperty item : reportTableData) {
            //And change "selected" boolean
            item.setSelected(((CheckBox) e.getSource()).isSelected());
        }

    }

    @FXML
    private void updateAction(ActionEvent event) {
        String color = "";
        try {
            color = this.color.getSelectionModel().getSelectedItem().getObj1();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        System.out.println("color: " + color);
        if (color == null || color.equals("") || color.equals("0")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Color Code not selected!");
            alert.setContentText("Please choose a Color Code.");
            alert.showAndWait();
        } else {
            boolean isSelected = false;
            for (MyModelSimpleStringProperty item : reportTableData) {
                //And change "selected" boolean
                if (item.isSelected()) {
                    isSelected = true;
                    break;
                }
            }
            if (!isSelected) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Please choose Voters!");
                alert.setContentText("Please choose a voter whose color code is to be updated.");
                alert.showAndWait();
            } else {
                System.out.println("ok");
                UpdateStatus task = new UpdateStatus(null, null, reportTableData, color);
                new Thread(task).start();

            }
        }
    }
}
