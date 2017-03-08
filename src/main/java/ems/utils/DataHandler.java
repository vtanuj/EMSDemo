/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.utils;

import ems.model.MyModel;
import ems.model.MyModelSimpleStringProperty;
import static ems.screens.HomeController.electionHistoryTableData;
import static ems.screens.HomeController.reportTableData;
import static ems.screens.HomeController.voterTableData;
import static ems.utils.Constants.PATH_TEMP_DB_;
import static ems.utils.Constants.Q_AGE_WISE;
import static ems.utils.Constants.Q_ALPHABETIC_WISE;
import static ems.utils.Constants.Q_BIRTHDAY_DAY_WISE;
import static ems.utils.Constants.Q_BIRTHDAY_WISE;
import static ems.utils.Constants.Q_ELECTION_HISTORY;
import static ems.utils.Constants.Q_ID_WISE;
import static ems.utils.Constants.Q_MOBILE_WISE;
import static ems.utils.Constants.Q_NAME_WISE;
import static ems.utils.Constants.Q_SR_WISE;
import static ems.utils.Constants.Q_SURNAME_WISE;
import static ems.utils.Constants.Q_WITHOUT_ID_CARD_WISE;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import org.apache.commons.lang3.StringUtils;

public class DataHandler {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + PATH_TEMP_DB_);
        } catch (Exception e) {
            System.err.println("getConnection: " + e.getMessage());
        }
        return con;
    }

    public static List<MyModel> getDataCastWiseCount() {
        List<MyModel> myModels = new LinkedList<>();
        String sqlQuery = "select ifnull(UPPER(trim(cast_nm)),'OTHERS') cast,count(*)'No' from e_details group "
                + " by UPPER(trim(cast_nm)) order by 1";
        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            while (rs.next()) {
                myModels.add(new MyModel(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            System.out.println("getDataCastWiseCount: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (s != null) {
                    s.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("getDataCastWiseCount: " + ex.getMessage());
            }
        }
        return myModels;
    }

    public static List<MyModel> getBoothList() {
        List<MyModel> myModels = new LinkedList<>();
        String sqlQuery = "select distinct booth_no, booth_id from booth_master order by 1";
        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            myModels.add(new MyModel("0", "Choose Booth No."));
            while (rs.next()) {
                myModels.add(new MyModel(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            System.out.println("getBoothList: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (s != null) {
                    s.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("getBoothList: " + ex.getMessage());
            }
        }
        return myModels;
    }

    public static List<MyModel> getWardList() {
        List<MyModel> myModels = new LinkedList<>();
        String sqlQuery = "select distinct ward_no from bmc_history order by 1";
        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            myModels.add(new MyModel("0", "Choose Ward No."));
            while (rs.next()) {
                myModels.add(new MyModel(rs.getString(1), rs.getString(1)));
            }
        } catch (Exception e) {
            System.out.println("getWardList: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (s != null) {
                    s.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("getWardList: " + ex.getMessage());
            }
        }
        return myModels;
    }

    public static void getReport(String... params) {
        String sqlQuery = "";
        Statement s = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            String paramss[] = params;
            String reportType = paramss[0];
            System.out.println("reportType|" + reportType + " ----> params|" + Arrays.toString(paramss));
            switch (reportType) {
                case "1":
                    sqlQuery = String.format(Q_AGE_WISE, paramss[1], paramss[2], paramss[3]);
                    break;
                case "2":
                    sqlQuery = String.format(Q_ALPHABETIC_WISE, paramss[1]);
                    break;
                case "3":
                    sqlQuery = Q_AGE_WISE + "" + paramss[1];
                    break;
                case "4":
                    sqlQuery = String.format(Q_BIRTHDAY_WISE, paramss[1], paramss[2]);
                    break;
                case "5":
                    sqlQuery = String.format(Q_BIRTHDAY_DAY_WISE, paramss[1], paramss[2]);
                    break;
                case "6":
                    sqlQuery = Q_AGE_WISE + "" + paramss[1];
                    break;
                case "7":
                    sqlQuery = Q_AGE_WISE + "" + paramss[1];
                    break;
                case "8":
                    sqlQuery = Q_AGE_WISE + "" + paramss[1];
                    break;
                case "9":
                    sqlQuery = Q_AGE_WISE + "" + paramss[1];
                    break;
                case "10":
                    sqlQuery = String.format(Q_MOBILE_WISE, paramss[1]);
                    break;
                case "11":
                    sqlQuery = Q_AGE_WISE + "" + paramss[1];
                    break;
                case "12":
                    sqlQuery = String.format(Q_SURNAME_WISE, paramss[1]);
                    break;
                case "13":
                    sqlQuery = String.format(Q_WITHOUT_ID_CARD_WISE, paramss[1]);
                    break;
                case "a":
                    sqlQuery = String.format(Q_NAME_WISE, paramss[1], paramss[2]);
                    break;
                case "b":
                    sqlQuery = String.format(Q_ID_WISE, paramss[1]);
                    break;
                case "c":
                    sqlQuery = String.format(Q_SR_WISE, paramss[1]);
                    break;
                case "ELECTION_HISTORY":
                    sqlQuery = String.format(Q_ELECTION_HISTORY, paramss[1]);
                    break;
            }
            con = getConnection();
            System.out.println("sqlQuery: " + sqlQuery);
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            if (reportType.equals("ELECTION_HISTORY")) {
                electionHistoryTableData.clear();
            } else if (StringUtils.isNumeric(reportType)) {
                reportTableData.clear();
            } else {
                voterTableData.clear();
            }
            while (rs.next()) {
                if (reportType.equals("ELECTION_HISTORY")) {
                    MyModelSimpleStringProperty entry = new MyModelSimpleStringProperty(rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4), "", "", "", "", "", "", "", "", "",
                            "", "", "");
                    electionHistoryTableData.add(entry);
                } else {
                    MyModelSimpleStringProperty entry = new MyModelSimpleStringProperty(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), "",
                            "", "", "", "");
                    if (StringUtils.isNumeric(reportType)) {
                        reportTableData.add(entry);
                    } else {
                        voterTableData.add(entry);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getReportException: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (s != null) {
                    s.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("getReport: " + ex.getMessage());
            }
        }
    }

    public static void exportData(String reportType, String exportType, File file) {
        try {
            boolean status = false;
            switch (exportType) {
                case "1":
                    status = csvDownload(file, reportTableData);
                    break;
                case "2":
                    break;
            }
            if (status) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Export successful!");
                alert.setContentText("Successfully exported the report.");
                ButtonType buttonTypeOne = new ButtonType("Open Folder");
                ButtonType buttonTypeTwo = new ButtonType("Open File");
                ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeOk);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne) {
                    MyUtils.openFolderWithFileSelected(file.getAbsolutePath());
                } else if (result.get() == buttonTypeTwo) {
                    MyUtils.openFile(file.getAbsolutePath());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failure");
                alert.setHeaderText("Export unsuccessful!");
                alert.setContentText("Unable to export the report.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
    }

    public static boolean csvDownload(File file, ObservableList<MyModelSimpleStringProperty> data) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String data1 : Constants.REPORT_COLUMN_HEADERS) {
                sb.append(data1).append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append("\r\n");
            for (MyModelSimpleStringProperty data1 : data) {
                sb.append(data1.getObj1()).append(",").append(data1.getObj2()).append(",")
                        .append(data1.getObj4()).append(",").append(data1.getObj5()).append(",")
                        .append(data1.getObj6()).append(",").append(data1.getObj7()).append(",")
                        .append(data1.getObj8()).append(",").append(data1.getObj9()).append(",")
                        .append(data1.getObj10()).append(",").append(data1.getObj11());
                sb.append("\r\n");
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
            fw.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
