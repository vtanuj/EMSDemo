/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.controller;

import ems.model.MyModelSimpleStringProperty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
    }

    public static void selectAllBoxes(ActionEvent e) {

        //Iterate through all items in ObservableList
        for (MyModelSimpleStringProperty item : reportTableData) {
            //And change "selected" boolean
            item.setSelected(((CheckBox) e.getSource()).isSelected());
        }

    }
}
