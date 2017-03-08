/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.screens;

import ems.main.Ems;
import static ems.utils.Constants.IMAGE_FAVICON;
import static ems.utils.Constants.HEADER_HOME;
import java.net.URL;
import java.util.List;
import ems.model.MyModel;
import ems.model.MyModelConverter;
import ems.model.MyModelSimpleStringProperty;
import ems.task.Reports;
import static ems.utils.Constants.COLORS;
import ems.utils.DataHandler;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import static ems.utils.Constants.IMAGE_LOGO;
import static ems.utils.Constants.MONTHS;
import static ems.utils.Constants.REPORTS_TYPE;
import static ems.utils.Constants.TITLE_ABOUT;
import ems.utils.JavaFXUtils;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.control.table.TableFilter;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author tanujv
 */
public class HomeController implements Initializable {

    Stage dialog;
    @FXML
    private TreeView<String> treeView;
    @FXML
    private ImageView homeImage;
    @FXML
    private Label headerHome;

    //Election History
    @FXML
    private AnchorPane electionHistory;
    @FXML
    private Button electionHistoryGo;
    @FXML
    private Button electionHistoryClear;
    @FXML
    private ComboBox<MyModel> wardNo;
    private final ObservableList<MyModel> wardNoData = FXCollections.observableArrayList();
    @FXML
    private TableView<MyModelSimpleStringProperty> electionHistoryTable;
    @FXML
    private TableColumn electionHistoryColumn1;
    @FXML
    private TableColumn electionHistoryColumn2;
    @FXML
    private TableColumn electionHistoryColumn3;
    @FXML
    private TableColumn electionHistoryColumn4;
    public static ObservableList<MyModelSimpleStringProperty> electionHistoryTableData
            = FXCollections.observableArrayList();

    //dashboard
    @FXML
    private AnchorPane dashboard;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    private final ObservableList<String> barChartData = FXCollections.observableArrayList();
    @FXML
    private PieChart pieChart;
    private final ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    //Voter Search & Update
    @FXML
    private AnchorPane voterSearchUpdate;
    @FXML
    private Button voterGo;
    @FXML
    private Button voterClear;
    @FXML
    private RadioButton radioName;
    @FXML
    private RadioButton radioID;
    @FXML
    private RadioButton radioSr;
    @FXML
    private Label fNameLabel;
    @FXML
    private Label lNameLabel;
    @FXML
    private Label IDLabel;
    @FXML
    private TextField fNameTextField;
    @FXML
    private TextField lNameTextField;
    @FXML
    private TextField IDTextField;
    ToggleGroup group = new ToggleGroup();
    @FXML
    private TableView<MyModelSimpleStringProperty> voterTable;
    @FXML
    private TableColumn voterColumn1;
    @FXML
    private TableColumn voterColumn2;
    @FXML
    private TableColumn voterColumn3;
    @FXML
    private TableColumn voterColumn4;
    @FXML
    private TableColumn voterColumn5;
    @FXML
    private TableColumn voterColumn6;
    @FXML
    private TableColumn voterColumn7;
    @FXML
    private TableColumn voterColumn8;
    @FXML
    private TableColumn voterColumn9;
    @FXML
    private TableColumn voterColumn10;
    @FXML
    private TableColumn voterColumn11;
    public static ObservableList<MyModelSimpleStringProperty> voterTableData = FXCollections.observableArrayList();

    //Reports
    @FXML
    private AnchorPane reports;

    @FXML
    private Label fromAgeLabel;
    @FXML
    private Label toAgeLabel;
    @FXML
    private TextField fromAgeTextField;
    @FXML
    private TextField toAgeTextField;
    @FXML
    private Label monthLabel;
    @FXML
    private ComboBox<MyModel> monthCombo;
    private final ObservableList<MyModel> monthData = FXCollections.observableArrayList();
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label colorLabel;
    @FXML
    private ComboBox<MyModel> colorCombo;
    private final ObservableList<MyModel> colorData = FXCollections.observableArrayList();
    @FXML
    private Button reportGo;
    @FXML
    private Button reportClear;
    @FXML
    private Button reportExportCSV;
    @FXML
    private Button reportExportPDF;
    @FXML
    private ComboBox<MyModel> reportType;
    private final ObservableList<MyModel> reportTypeData = FXCollections.observableArrayList();
    @FXML
    private ComboBox<MyModel> boothNo;
    private final ObservableList<MyModel> boothNoData = FXCollections.observableArrayList();
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        stage = (Stage) ap.getScene().getWindow();
        //Load Tree Items
        loadTreeItems();
        //Load Dashboard Values
        loadDashboard();
        //

        wardNo.setConverter(new MyModelConverter());
        List<MyModel> wardList = DataHandler.getWardList();
        for (MyModel report : wardList) {
            wardNoData.add(report);
        }
        wardNo.setItems(wardNoData);
        electionHistoryGo.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CHECK, 16, Color.GREEN));
        electionHistoryClear.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CLOSE, 16, Color.RED));

        electionHistoryColumn1.setCellValueFactory(new PropertyValueFactory<>("obj1"));
        electionHistoryColumn2.setCellValueFactory(new PropertyValueFactory<>("obj2"));
        electionHistoryColumn3.setCellValueFactory(new PropertyValueFactory<>("obj3"));
        electionHistoryColumn4.setCellValueFactory(new PropertyValueFactory<>("obj4"));

        electionHistoryTable.setItems(electionHistoryTableData);

        TableFilter filter3 = new TableFilter(electionHistoryTable);

        radioName.setUserData("a");
        radioID.setUserData("b");
        radioSr.setUserData("c");

        radioName.setToggleGroup(group);
        radioID.setToggleGroup(group);
        radioSr.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    String selectedData = group.getSelectedToggle().getUserData().toString();
                    switch (selectedData) {
                        case "a":
                            fNameLabel.setVisible(true);
                            lNameLabel.setVisible(true);
                            IDLabel.setVisible(false);

                            fNameTextField.setVisible(true);
                            lNameTextField.setVisible(true);
                            IDTextField.setVisible(false);
                            break;
                        case "b":
                            fNameLabel.setVisible(false);
                            lNameLabel.setVisible(false);
                            IDLabel.setVisible(true);
                            IDLabel.setText("ID Card No.:");
                            fNameTextField.setVisible(false);
                            lNameTextField.setVisible(false);
                            IDTextField.setVisible(true);
                            break;
                        case "c":
                            fNameLabel.setVisible(false);
                            lNameLabel.setVisible(false);
                            IDLabel.setVisible(true);
                            IDLabel.setText("Sr. No.:");
                            fNameTextField.setVisible(false);
                            lNameTextField.setVisible(false);
                            IDTextField.setVisible(true);
                            break;
                    }
                }
            }
        });
        voterGo.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CHECK, 16, Color.GREEN));
        voterClear.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CLOSE, 16, Color.RED));

        reportGo.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CHECK, 16, Color.GREEN));
        reportClear.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.CLOSE, 16, Color.RED));
        reportExportCSV.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.FILE_EXCEL_ALT, 16, Color.GREEN));
        reportExportPDF.setGraphic(JavaFXUtils.getGraphic("FontAwesome", FontAwesome.Glyph.FILE_PDF_ALT, 16, Color.RED));

        //Set Image
        homeImage.setImage(new Image(IMAGE_LOGO));
        //Set Header
        headerHome.setText(HEADER_HOME);

        monthCombo.setConverter(new MyModelConverter());
        for (MyModel report : MONTHS) {
            monthData.add(report);
        }
        monthCombo.setItems(monthData);

        colorCombo.setConverter(new MyModelConverter());
        for (MyModel report : COLORS) {
            colorData.add(report);
        }
        colorCombo.setItems(colorData);

        voterColumn1.setCellValueFactory(new PropertyValueFactory<>("obj1"));
        voterColumn2.setCellValueFactory(new PropertyValueFactory<>("obj2"));
        voterColumn3.setCellValueFactory(new PropertyValueFactory<>("obj3"));
        voterColumn4.setCellValueFactory(new PropertyValueFactory<>("obj4"));
        voterColumn5.setCellValueFactory(new PropertyValueFactory<>("obj5"));
        voterColumn6.setCellValueFactory(new PropertyValueFactory<>("obj6"));
        voterColumn7.setCellValueFactory(new PropertyValueFactory<>("obj7"));
        voterColumn8.setCellValueFactory(new PropertyValueFactory<>("obj8"));
        voterColumn9.setCellValueFactory(new PropertyValueFactory<>("obj9"));
        voterColumn10.setCellValueFactory(new PropertyValueFactory<>("obj10"));
        voterColumn11.setCellValueFactory(new PropertyValueFactory<>("obj11"));

        voterTable.setItems(voterTableData);

        TableFilter filter1 = new TableFilter(voterTable);

        reportType.setConverter(new MyModelConverter());
        for (MyModel report : REPORTS_TYPE) {
            reportTypeData.add(report);
        }
        reportType.setItems(reportTypeData);

        reportType.valueProperty().addListener(new ChangeListener<MyModel>() {

            @Override
            public void changed(ObservableValue<? extends MyModel> observable, MyModel oldValue, MyModel newValue) {

                switch (newValue.getObj1()) {
                    case "1":
                        fromAgeLabel.setVisible(true);
                        toAgeLabel.setVisible(true);
                        fromAgeTextField.setVisible(true);
                        toAgeTextField.setVisible(true);

                        monthLabel.setVisible(false);
                        monthCombo.setVisible(false);

                        dateLabel.setVisible(false);
                        datePicker.setVisible(false);

                        colorLabel.setVisible(false);
                        colorCombo.setVisible(false);
                        break;
                    case "4":
                        fromAgeLabel.setVisible(false);
                        toAgeLabel.setVisible(false);
                        fromAgeTextField.setVisible(false);
                        toAgeTextField.setVisible(false);

                        monthLabel.setVisible(true);
                        monthCombo.setVisible(true);

                        dateLabel.setVisible(false);
                        datePicker.setVisible(false);

                        colorLabel.setVisible(false);
                        colorCombo.setVisible(false);
                        break;
                    case "5":
                        fromAgeLabel.setVisible(false);
                        toAgeLabel.setVisible(false);
                        fromAgeTextField.setVisible(false);
                        toAgeTextField.setVisible(false);

                        monthLabel.setVisible(false);
                        monthCombo.setVisible(false);

                        dateLabel.setVisible(true);
                        datePicker.setVisible(true);

                        colorLabel.setVisible(false);
                        colorCombo.setVisible(false);
                        break;
                    case "8":
                    case "9":
                        fromAgeLabel.setVisible(false);
                        toAgeLabel.setVisible(false);
                        fromAgeTextField.setVisible(false);
                        toAgeTextField.setVisible(false);

                        monthLabel.setVisible(false);
                        monthCombo.setVisible(false);

                        dateLabel.setVisible(false);
                        datePicker.setVisible(false);

                        colorLabel.setVisible(true);
                        colorCombo.setVisible(true);
                        break;
                    default:
                        fromAgeLabel.setVisible(false);
                        toAgeLabel.setVisible(false);
                        fromAgeTextField.setVisible(false);
                        toAgeTextField.setVisible(false);

                        monthLabel.setVisible(false);
                        monthCombo.setVisible(false);

                        dateLabel.setVisible(false);
                        datePicker.setVisible(false);

                        colorLabel.setVisible(false);
                        colorCombo.setVisible(false);
                        break;
                }
            }
        });

        boothNo.setConverter(new MyModelConverter());
        List<MyModel> boothsNos = DataHandler.getBoothList();
        for (MyModel report : boothsNos) {
            boothNoData.add(report);
        }
        boothNo.setItems(boothNoData);

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

        reportTable.setItems(reportTableData);

        TableFilter filter = new TableFilter(reportTable);

    }

    @FXML
    private void onAboutClick(ActionEvent event) {
        try {
            Stage aboutStage = new Stage();
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/ems/screens/About.fxml"));
            Scene scene = new Scene(root);
            aboutStage.setTitle(TITLE_ABOUT);
            aboutStage.setScene(scene);
            aboutStage.setWidth(600);
            aboutStage.setHeight(250);
            aboutStage.getIcons().add(new Image(IMAGE_FAVICON));
            aboutStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Ems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCloseClick(ActionEvent event) {
        System.exit(0);
    }

    public void loadTreeItems() {
        try {
            TreeItem<String> rootNode = new TreeItem<>("Election Management System");
            rootNode.setExpanded(true);

            TreeItem<String> nodeItemA = new TreeItem<>("Election History");

            TreeItem<String> nodeItemB = new TreeItem<>("Voters");
            TreeItem<String> nodeItemB1 = new TreeItem<>("Search & Update");
            TreeItem<String> nodeItemB2 = new TreeItem<>("Status Update");
            nodeItemB.getChildren().addAll(nodeItemB1, nodeItemB2);

            TreeItem<String> nodeItemC = new TreeItem<>("Reports");
            TreeItem<String> nodeItemD = new TreeItem<>("Sync");
            TreeItem<String> nodeItemE = new TreeItem<>("Ward Management");
            TreeItem<String> nodeItemF = new TreeItem<>("SMS");
            TreeItem<String> nodeItemG = new TreeItem<>("Application Update");
            rootNode.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC, nodeItemD, nodeItemE,
                    nodeItemF, nodeItemG);

            treeView.setRoot(rootNode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadDashboard() {
        List<MyModel> list = DataHandler.getDataCastWiseCount();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (MyModel s : list) {
            pieChartData.add(new PieChart.Data(s.getObj1(), Double.valueOf(s.getObj2())));

            barChartData.add(s.getObj1());
            series.getData().add(new XYChart.Data<>(s.getObj1(), Double.valueOf(s.getObj2())));
        }

        xAxis.setCategories(barChartData);
        barChart.getData().add(series);

        pieChart.setTitle("Cast Wise");

        pieChartData.forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(data.getName(), " ", data.pieValueProperty(), "")
                )
        );
        pieChart.setData(pieChartData);

        for (final XYChart.Series<String, Number> series1 : barChart.getData()) {
            for (final XYChart.Data<String, Number> data : series1.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }
        }

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                for (XYChart.Series<String, Number> series : barChart.getData()) {
                                    for (XYChart.Data<String, Number> data : series.getData()) {
                                        data.setYValue(Math.random() * 1000);
                                    }
                                }
                            }
                        }
                ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.setAutoReverse(true);
        tl.play();

    }

    @FXML
    private void onTreeViewClick(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            String name = (String) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
            switch (name) {
                case "Election Management System":
                    dashboard.setVisible(true);
                    reports.setVisible(false);
                    voterSearchUpdate.setVisible(false);
                    electionHistory.setVisible(false);
                    break;
                case "Election History":
                    dashboard.setVisible(false);
                    reports.setVisible(false);
                    voterSearchUpdate.setVisible(false);
                    electionHistory.setVisible(true);
                    break;
                case "Search & Update":
                    dashboard.setVisible(false);
                    reports.setVisible(false);
                    voterSearchUpdate.setVisible(true);
                    electionHistory.setVisible(false);
                    break;
                case "Reports":
                    dashboard.setVisible(false);
                    reports.setVisible(true);
                    voterSearchUpdate.setVisible(false);
                    electionHistory.setVisible(false);
                    break;
                case "Sync":
                    break;
                case "Ward Management":
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Feature, not available yet!");
                    alert.setContentText("Voter Management features will be available on Election time.");
//                    // Get the Stage.
//                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//                    // Add a custom icon.
//                    stage.getIcons().add(new Image(this.getClass().getResource("login.png").toString()));
//                    alert.initOwner(stage);
                    alert.showAndWait();
                    break;
                case "SMS":
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Feature, not available yet!");
                    alert.setContentText("Voter Management features will be available on Election time.");
                    alert.showAndWait();
                    break;
                case "Application Update":
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Feature, not available yet!");
                    alert.setContentText("Voter Management features will be available on Election time.");
                    alert.showAndWait();
                    break;
            }
        }
    }

    @FXML
    private void onReportGoClick(ActionEvent event) {
        String reportType = null, boothNo = null;
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        try {
            try {
                reportType = this.reportType.getSelectionModel().getSelectedItem().getObj1();
            } catch (Exception e) {
            }
            try {
                boothNo = this.boothNo.getSelectionModel().getSelectedItem().getObj1();
            } catch (Exception e) {
            }
            if (reportType == null || reportType.equals("0")) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Report Type not selected!");
                alert.setContentText("Please choose a Report Type.");
                alert.showAndWait();
            } else if (boothNo == null || boothNo.equals("0")) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Booth No not selected!");
                alert.setContentText("Please choose a Booth No.");
                alert.showAndWait();
            } else {
                dialog = JavaFXUtils.dialog(dialog, window);
                Reports task = null;
                switch (reportType) {
                    case "1":
                        String fromAge = fromAgeTextField.getText();
                        String toAge = toAgeTextField.getText();
                        task = new Reports(dialog, window, reportType, boothNo, fromAge, toAge);
                        break;
                    case "2":
                    case "3":
                    case "6":
                    case "7":
                    case "10":
                    case "11":
                    case "12":
                    case "13":
                        task = new Reports(dialog, window, reportType, boothNo);
                        break;
                    case "4":
                        String month = null;
                        try {
                            month = this.monthCombo.getSelectionModel().getSelectedItem().getObj1();
                        } catch (Exception e) {
                        }
                        if (month == null || month.equals("00")) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Month not selected!");
                            alert.setContentText("Please choose a Month.");
                            alert.showAndWait();
                            return;
                        }
                        task = new Reports(dialog, window, reportType, boothNo, month);
                        break;
                    case "5":
                        String date = null;
                        try {
                            date = this.datePicker.getValue().toString();
                        } catch (Exception e) {
                        }
                        if (date == null) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Date not selected!");
                            alert.setContentText("Please select a Date.");
                            alert.showAndWait();
                            return;
                        }
                        task = new Reports(dialog, window, reportType, boothNo, date);
                        break;
                    case "8":
                    case "9":
                        String color = this.colorCombo.getSelectionModel().getSelectedItem().getObj1();
                        task = new Reports(dialog, window, reportType, boothNo, color);
                        break;
                }

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

                reportColumn1.setVisible(true);
                reportColumn2.setVisible(true);
                reportColumn3.setVisible(true);
                reportColumn4.setVisible(true);
                reportColumn5.setVisible(true);
                reportColumn6.setVisible(true);
                reportColumn7.setVisible(true);
                reportColumn8.setVisible(true);
                reportColumn9.setVisible(true);
                reportColumn10.setVisible(true);
                reportColumn11.setVisible(true);
                new Thread(task).start();
                JavaFXUtils.dim(window);
                dialog.show();
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
    }

    @FXML
    private void onReportClearClick(ActionEvent event) {
        reportType.getSelectionModel().selectFirst();
        boothNo.getSelectionModel().selectFirst();
        reportTableData.clear();
        reportColumn1.setText("");
        reportColumn2.setText("");
        reportColumn3.setText("");
        reportColumn4.setText("");
        reportColumn5.setText("");
        reportColumn6.setText("");
        reportColumn7.setText("");
        reportColumn8.setText("");
        reportColumn9.setText("");
        reportColumn10.setText("");
        reportColumn11.setText("");
        reportColumn1.setVisible(true);
        reportColumn2.setVisible(true);
        reportColumn3.setVisible(true);
        reportColumn4.setVisible(true);
        reportColumn5.setVisible(true);
        reportColumn6.setVisible(true);
        reportColumn7.setVisible(true);
        reportColumn8.setVisible(true);
        reportColumn9.setVisible(true);
        reportColumn10.setVisible(true);
        reportColumn11.setVisible(true);
    }

    @FXML
    private void onReportCSVDownload(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        String reportType = null;
        try {
            reportType = this.reportType.getSelectionModel().getSelectedItem().getObj1();
        } catch (Exception e) {
        }
        if (reportType == null || reportType.equals("0")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Report Type not selected!");
            alert.setContentText("Please choose a Report Type.");
            alert.showAndWait();
        } else if (reportTableData.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No data to export as CSV!");
            alert.setContentText("Data not available for CSV export.");
            alert.showAndWait();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.setInitialFileName(reportType + ".csv");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(window);
            DataHandler.exportData(reportType, "1", file);
        }
    }

    @FXML
    private void onReportPDFDownload(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        String reportType = null;
        try {
            reportType = this.reportType.getSelectionModel().getSelectedItem().getObj1();
        } catch (Exception e) {
        }
        if (reportType == null || reportType.equals("0")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Report Type not selected!");
            alert.setContentText("Please choose a Report Type.");
            alert.showAndWait();
        } else if (reportTableData.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No data to export as PDF!");
            alert.setContentText("Data not available for PDF export.");
            alert.showAndWait();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.setInitialFileName(reportType + ".pdf");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(window);
            DataHandler.exportData(reportType, "2", file);
        }
    }

    @FXML
    private void onVoterClearClick(ActionEvent event) {
        radioName.setSelected(true);
        radioName.requestFocus();
        voterTableData.clear();
        voterColumn1.setText("");
        voterColumn2.setText("");
        voterColumn3.setText("");
        voterColumn4.setText("");
        voterColumn5.setText("");
        voterColumn6.setText("");
        voterColumn7.setText("");
        voterColumn8.setText("");
        voterColumn9.setText("");
        voterColumn10.setText("");
        voterColumn11.setText("");
        voterColumn1.setVisible(true);
        voterColumn2.setVisible(true);
        voterColumn3.setVisible(true);
        voterColumn4.setVisible(true);
        voterColumn5.setVisible(true);
        voterColumn6.setVisible(true);
        voterColumn7.setVisible(true);
        voterColumn8.setVisible(true);
        voterColumn9.setVisible(true);
        voterColumn10.setVisible(true);
        voterColumn11.setVisible(true);
    }

    @FXML
    private void onVoterGoClick(ActionEvent event) {
        String selectedData = null;
        String firstName, lastName, idNumber;
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        try {
            try {
                selectedData = group.getSelectedToggle().getUserData().toString();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (selectedData == null || selectedData.equals("0")) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Search Type not selected!");
                alert.setContentText("Please select a Search Type.");
                alert.showAndWait();
            } else {
                Reports task = null;
                dialog = JavaFXUtils.dialog(dialog, window);
                voterColumn1.setText("Ward No");
                voterColumn2.setText("Ward Sr No");
                voterColumn3.setText("New Sr No.");
                voterColumn4.setText("Name");
                voterColumn5.setText("Sex");
                voterColumn6.setText("Age");
                voterColumn7.setText("ID Card No.");
                voterColumn8.setText("Mobile No.");
                voterColumn9.setText("DOB");
                voterColumn10.setText("Language");
                voterColumn11.setText("Booth Name");

                voterColumn1.setVisible(true);
                voterColumn2.setVisible(true);
                voterColumn3.setVisible(true);
                voterColumn4.setVisible(true);
                voterColumn5.setVisible(true);
                voterColumn6.setVisible(true);
                voterColumn7.setVisible(true);
                voterColumn8.setVisible(true);
                voterColumn9.setVisible(true);
                voterColumn10.setVisible(true);
                voterColumn11.setVisible(true);
                switch (selectedData) {
                    case "a":
                        firstName = fNameTextField.getText();
                        lastName = lNameTextField.getText();
                        if (firstName == null || firstName.isEmpty()) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("First Name missing!");
                            alert.setContentText("First Name cannot be empty.");
                            alert.showAndWait();
                            return;
                        }
                        task = new Reports(dialog, window, selectedData, firstName, lastName);
                        break;
                    case "b":
                    case "c":
                        idNumber = IDTextField.getText();
                        if (idNumber == null || idNumber.isEmpty()) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText((selectedData.equals("b") ? "ID" : "Sr.") + " Number missing!");
                            alert.setContentText((selectedData.equals("b") ? "ID" : "Sr.")
                                    + " Number cannot be empty.");
                            alert.showAndWait();
                            return;
                        } else if (idNumber.length() != 10) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText((selectedData.equals("b") ? "ID" : "Sr.") + " Number length incorrect!");
                            alert.setContentText((selectedData.equals("b") ? "ID" : "Sr.")
                                    + " Number length must be 10 digits.");
                            alert.showAndWait();
                            return;
                        }
                        task = new Reports(dialog, window, selectedData, idNumber);
                        break;
                }
                new Thread(task).start();
                JavaFXUtils.dim(window);
                dialog.show();
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
    }

    @FXML
    private void onVoterTableClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Node node = ((Node) event.getTarget()).getParent();
            TableRow row;
            if (node instanceof TableRow) {
                row = (TableRow) node;
            } else {
                // clicking on text part
                row = (TableRow) node.getParent();
            }
            MyModelSimpleStringProperty m = (MyModelSimpleStringProperty) row.getItem();
            System.out.println(m.getObj1());
            System.out.println(m.getObj2());
            System.out.println(m.getObj3());
            System.out.println(m.getObj4());
            System.out.println(m.getObj5());
            System.out.println(m.getObj6());
            System.out.println(m.getObj7());
            try {
                Stage voterDetailsStage = new Stage();
                voterDetailsStage.initModality(Modality.APPLICATION_MODAL);
                voterDetailsStage.setResizable(false);
                Parent root = FXMLLoader.load(getClass().getResource("/ems/screens/VoterDetails.fxml"));
                Scene scene = new Scene(root);
                voterDetailsStage.setTitle(TITLE_ABOUT);
                voterDetailsStage.setScene(scene);
//            voterDetailsStage.setWidth(600);
//            voterDetailsStage.setHeight(250);
                voterDetailsStage.getIcons().add(new Image(IMAGE_FAVICON));
                voterDetailsStage.show();
            } catch (Exception ex) {
                Logger.getLogger(Ems.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void onElectionHistoryGoClick(ActionEvent event) {
        String wardNo = null;
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        try {
            try {
                wardNo = this.wardNo.getSelectionModel().getSelectedItem().getObj1();
            } catch (Exception e) {
            }
            if (wardNo == null || wardNo.equals("0")) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Ward No not selected!");
                alert.setContentText("Please choose a Ward No.");
                alert.showAndWait();
            } else {
                electionHistoryColumn1.setText("Ward No");
                electionHistoryColumn2.setText("Councillor's Name");
                electionHistoryColumn3.setText("Party");
                electionHistoryColumn4.setText("Total Vote");

                electionHistoryColumn1.setVisible(true);
                electionHistoryColumn2.setVisible(true);
                electionHistoryColumn3.setVisible(true);
                electionHistoryColumn4.setVisible(true);

                dialog = JavaFXUtils.dialog(dialog, window);
                Reports task = new Reports(dialog, window, "ELECTION_HISTORY", wardNo);
                new Thread(task).start();
                JavaFXUtils.dim(window);
                dialog.show();
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
    }

    @FXML
    private void onElectionHistoryClearClick(ActionEvent event) {
        wardNo.getSelectionModel().selectFirst();
        electionHistoryTableData.clear();
        electionHistoryColumn1.setText("");
        electionHistoryColumn2.setText("");
        electionHistoryColumn3.setText("");
        electionHistoryColumn4.setText("");
        electionHistoryColumn1.setVisible(true);
        electionHistoryColumn2.setVisible(true);
        electionHistoryColumn3.setVisible(true);
        electionHistoryColumn4.setVisible(true);
    }
}
