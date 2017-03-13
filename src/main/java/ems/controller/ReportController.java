/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.controller;

import static ems.controller.HomeController.reportTableData;
import static ems.main.Ems.log;
import ems.model.MyModel;
import ems.model.MyModelSimpleStringProperty;
import static ems.util.Constants.IMAGE_FAVICON;
import static ems.util.Constants.TITLE_ABOUT;
import ems.util.DataHandler;
import ems.util.JavaFXUtils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author vl
 */
public class ReportController implements Initializable {

    List<MyModelSimpleStringProperty> reportDetails;

    @FXML
    private Button reportExportCSV;
    @FXML
    private Button reportExportExcel;
    @FXML
    private Button reportExportPDF;
    @FXML
    private TableView<MyModelSimpleStringProperty> reportTable;
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

    public void initReportDetails(List<MyModelSimpleStringProperty> reportDetails) {
        this.reportDetails = reportDetails;

        reportTableData.clear();
        for (MyModelSimpleStringProperty reportDetail : reportDetails) {
            reportTableData.add(reportDetail);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportExportCSV.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.FILE_EXCEL_ALT, 16, Color.GREEN));
        reportExportPDF.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.FILE_PDF_ALT, 16, Color.RED));
        reportExportExcel.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.FILE_EXCEL_ALT, 16, Color.GREEN));

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

        reportTable.setItems(reportTableData);
        TableFilter tableFilter = new TableFilter(reportTable);
    }

    @FXML
    private void onReportDetailsTableClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Node node = ((Node) event.getTarget()).getParent();
            TableRow row;
            if (node instanceof TableRow) {
                row = (TableRow) node;
            } else {
                // clicking on text part
                row = (TableRow) node.getParent();
            }
            try {
                MyModelSimpleStringProperty m = (MyModelSimpleStringProperty) row.getItem();
                MyModel voterDetails = DataHandler.getVoterDetails(m.getObj1(), m.getObj2());
                Stage voterDetailsStage = new Stage();
                voterDetailsStage.initModality(Modality.APPLICATION_MODAL);
                voterDetailsStage.setResizable(false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ems/fxml/VoterDetails.fxml"));
                Parent root = loader.load();
                VoterDetailsController controller = loader.<VoterDetailsController>getController();

                controller.initVoterDetails(voterDetails);

                Scene scene = new Scene(root);
                voterDetailsStage.setTitle(voterDetails.getObj5());
                voterDetailsStage.setScene(scene);
                voterDetailsStage.getIcons().add(new Image(IMAGE_FAVICON));
                voterDetailsStage.show();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

    }

}
