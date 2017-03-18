/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.util;

import static ems.main.Ems.log;
import ems.model.MyModel;
import ems.model.MyModelSimpleStringProperty;
import static ems.controller.HomeController.electionHistoryTableData;
import static ems.controller.HomeController.reportTableData;
import static ems.controller.HomeController.statusUpdateTableData;
import static ems.controller.HomeController.voterTableData;
import static ems.util.Constants.PATH_REPORT_1_;
import static ems.util.Constants.PATH_REPORT_2_;
import static ems.util.Constants.PATH_REPORT_3_;
import static ems.util.Constants.PATH_REPORT_4_;
import static ems.util.Constants.PATH_TEMP_DB_;
import static ems.util.Constants.Q_S_AGE_WISE;
import static ems.util.Constants.Q_S_ALPHABETIC_WISE;
import static ems.util.Constants.Q_S_AREA_WISE;
import static ems.util.Constants.Q_S_AREA_WISE_;
import static ems.util.Constants.Q_S_BIRTHDAY_DAY_WISE;
import static ems.util.Constants.Q_S_BIRTHDAY_WISE;
import static ems.util.Constants.Q_S_BOOTH_WISE;
import static ems.util.Constants.Q_S_BOOTH_WISE_;
import static ems.util.Constants.Q_S_COLOR_CODE_BOOTH_WISE;
import static ems.util.Constants.Q_S_COLOR_CODE_BOOTH_WISE_;
import static ems.util.Constants.Q_S_COLOR_CODE_WISE;
import static ems.util.Constants.Q_S_COLOR_CODE_WISE_;
import static ems.util.Constants.Q_S_COMMUNITY_STATUS;
import static ems.util.Constants.Q_S_COMMUNITY_STATUS_;
import static ems.util.Constants.Q_S_COMMUNITY_WISE;
import static ems.util.Constants.Q_S_COMMUNITY_WISE_;
import static ems.util.Constants.Q_S_DASHBOARD_CAST_WISE;
import static ems.util.Constants.Q_S_DASHBOARD_COLOR_WISE;
import static ems.util.Constants.Q_S_DASHBOARD_GENDER_WISE;
import static ems.util.Constants.Q_S_ELECTION_HISTORY;
import static ems.util.Constants.Q_S_GET_VOTER;
import static ems.util.Constants.Q_S_ID_WISE;
import static ems.util.Constants.Q_S_MOBILE_WISE;
import static ems.util.Constants.Q_S_NAME_WISE;
import static ems.util.Constants.Q_S_SECTION_WISE;
import static ems.util.Constants.Q_S_SETION_WISE_;
import static ems.util.Constants.Q_S_SR_WISE;
import static ems.util.Constants.Q_S_SURNAME_STATUS;
import static ems.util.Constants.Q_S_SURNAME_STATUS_;
import static ems.util.Constants.Q_S_SURNAME_WISE;
import static ems.util.Constants.Q_S_SURNAME_WISE_;
import static ems.util.Constants.Q_U_VOTER_DETAILS;
import static ems.util.Constants.Q_S_WITHOUT_ID_CARD_WISE;
import static ems.util.Constants.Q_U_VOTER_STATUS;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;

public class DataHandler {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + PATH_TEMP_DB_);
        } catch (Exception e) {
            log.error("getConnection: " + e.getMessage());
        }
        return con;
    }

    public static List<MyModel> getDashboardData(String reportType) {
        List<MyModel> myModels = new LinkedList<>();
        String sqlQuery = "";
        switch (reportType) {
            case "1":
                sqlQuery = Q_S_DASHBOARD_CAST_WISE;
                break;
            case "2":
                sqlQuery = Q_S_DASHBOARD_GENDER_WISE;
                break;
            case "3":
                sqlQuery = Q_S_DASHBOARD_COLOR_WISE;
                break;
        }
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
            log.error("getDashboardData: " + e.getMessage());
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
                log.error("getDashboardData: " + ex.getMessage());
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
            log.error("getBoothList: " + e.getMessage());
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
                log.error("getBoothList: " + ex.getMessage());
            }
        }
        return myModels;
    }

    public static String getBoothName(String boothNumber) {
        String sqlQuery = "select ifnull(booth_no,'') || ' - ' || ifnull(booth_name,'') "
                + "from booth_master where booth_no=" + boothNumber;
        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            log.error("getBoothList: " + e.getMessage());
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
                log.error("getBoothList: " + ex.getMessage());
            }
        }
        return null;
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
            log.error("getWardList: " + e.getMessage());
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
                log.error("getWardList: " + ex.getMessage());
            }
        }
        return myModels;
    }

    public static List<MyModel> getCommunityList() {
        List<MyModel> myModels = new LinkedList<>();
        String sqlQuery = "select distinct cast_nm from e_details where cast_nm is not null and cast_nm <>'' order by 1";
        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            myModels.add(new MyModel("0", "Choose Community"));
            while (rs.next()) {
                myModels.add(new MyModel(rs.getString(1), rs.getString(1)));
            }
        } catch (Exception e) {
            log.error("getWardList: " + e.getMessage());
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
                log.error("getWardList: " + ex.getMessage());
            }
        }
        return myModels;
    }

    public static MyModel getVoterDetails(String wardNo, String wardSrNo) {
        String sqlQuery = String.format(Q_S_GET_VOTER, wardNo, wardSrNo);
        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            log.info("sqlQuery:" + sqlQuery);
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            while (rs.next()) {
                return new MyModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
                        rs.getString(17));
            }
        } catch (SQLException e) {
            log.error("getVoterDetails: " + e.getMessage());
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
                log.error("getBoothList: " + ex.getMessage());
            }
        }
        return null;
    }

    public static List<MyModelSimpleStringProperty> getReportDetails(String reportType, String... params) {
        List<MyModelSimpleStringProperty> reportDetails = new LinkedList<>();
        String sqlQuery = "";
        switch (reportType) {
            case "101":
                sqlQuery = String.format(Q_S_SURNAME_STATUS_, params[0]);
                break;
            case "102":
                sqlQuery = String.format(Q_S_COMMUNITY_STATUS_, params[0]);
                break;
            case "3":
                sqlQuery = String.format(Q_S_AREA_WISE_, params[0], params[1]);
                break;
            case "6":
                sqlQuery = String.format(Q_S_BOOTH_WISE_, params[1]);
                break;
            case "7":
                sqlQuery = String.format(Q_S_COMMUNITY_WISE_, params[0], params[1]);
                break;
            case "8":
                String colorCode = "";
                switch (params[1]) {
                    case "Our":
                        colorCode = "1";
                        break;
                    case "Opposite":
                        colorCode = "2";
                        break;
                    case "Unpredictable":
                        colorCode = "3";
                        break;
                    case "Others":
                        colorCode = "4";
                        break;
                    case "All":
                        colorCode = "5";
                        break;
                }
                sqlQuery = String.format(Q_S_COLOR_CODE_BOOTH_WISE_, params[0], colorCode, colorCode);
                break;
            case "9":
                colorCode = "";
                switch (params[1]) {
                    case "Our":
                        colorCode = "1";
                        break;
                    case "Opposite":
                        colorCode = "2";
                        break;
                    case "Unpredictable":
                        colorCode = "3";
                        break;
                    case "Others":
                        colorCode = "4";
                        break;
                    case "All":
                        colorCode = "5";
                        break;
                }
                sqlQuery = String.format(Q_S_COLOR_CODE_WISE_, colorCode, colorCode);
                break;
            case "11":
                sqlQuery = String.format(Q_S_SETION_WISE_, params[0], params[1]);
                break;
            case "12":
                sqlQuery = String.format(Q_S_SURNAME_WISE_, params[0], params[1]);
                break;
        }

        Connection con = getConnection();
        Statement s = null;
        ResultSet rs = null;
        try {
            log.info("sqlQuery:" + sqlQuery);
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            while (rs.next()) {
                reportDetails.add(new MyModelSimpleStringProperty(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), "", "", "", "", ""));
            }
        } catch (SQLException e) {
            log.error("getReportDetails: " + e.getMessage());
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
                log.error("getBoothList: " + ex.getMessage());
            }
        }
        return reportDetails;
    }

    public static boolean updateVoterDetails(String emailId, String mobileNo, String alternatMobileNo,
            String dob, String age, String community, String gender, String wardNo, String wardSrNo) {
        String sqlQuery = String.format(Q_U_VOTER_DETAILS, emailId, mobileNo, alternatMobileNo,
                dob, age, community, gender, wardNo, wardSrNo);
        Connection con = getConnection();
        Statement s = null;
        try {
            log.info("sqlQuery:" + sqlQuery);
            s = con.createStatement();
            int i = s.executeUpdate(sqlQuery);
            log.info("updateVoterDetails|" + i);
            return true;
        } catch (SQLException e) {
            log.error("updateVoterDetails: " + e.getMessage());
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                log.error("updateVoterDetails: " + ex.getMessage());
            }
        }
        return false;
    }

    public static boolean updateVoterStatus(ObservableList<MyModelSimpleStringProperty> list,
            String status) {
        String sqlQuery;
        Connection con = getConnection();
        Statement s = null;
        try {
            for (MyModelSimpleStringProperty list1 : list) {
                if (list1.isSelected()) {
                    sqlQuery = String.format(Q_U_VOTER_STATUS, status, list1.getObj1(), list1.getObj2());
                    log.info("sqlQuery:" + sqlQuery);
                    s = con.createStatement();
                    int i = s.executeUpdate(sqlQuery);
                    log.info("updateVoterDetails|" + i);
                }
            }
            return true;
        } catch (SQLException e) {
            log.error("updateVoterDetails: " + e.getMessage());
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                log.error("updateVoterDetails: " + ex.getMessage());
            }
        }
        return false;
    }

    public static void getReport(String... params) {
        String sqlQuery = "";
        Statement s = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            String paramss[] = params;
            String reportType = paramss[0];
            log.info("reportType|" + reportType + " ----> params|" + Arrays.toString(paramss));
            switch (reportType) {
                case "1":
                    sqlQuery = String.format(Q_S_AGE_WISE, paramss[1], paramss[2], paramss[3]);
                    break;
                case "2":
                    sqlQuery = String.format(Q_S_ALPHABETIC_WISE, paramss[1]);
                    break;
                case "3":
                    sqlQuery = String.format(Q_S_AREA_WISE, paramss[1]);
                    break;
                case "4":
                    sqlQuery = String.format(Q_S_BIRTHDAY_WISE, paramss[1], paramss[2]);
                    break;
                case "5":
                    sqlQuery = String.format(Q_S_BIRTHDAY_DAY_WISE, paramss[1], paramss[2]);
                    break;
                case "6":
                    sqlQuery = Q_S_BOOTH_WISE;
                    break;
                case "7":
                    sqlQuery = String.format(Q_S_COMMUNITY_WISE, paramss[1]);
                    break;
                case "8":
                    sqlQuery = String.format(Q_S_COLOR_CODE_BOOTH_WISE, paramss[1], paramss[2], paramss[2]);
                    break;
                case "9":
                    sqlQuery = String.format(Q_S_COLOR_CODE_WISE, paramss[2], paramss[2]);
                    break;
                case "10":
                    sqlQuery = String.format(Q_S_MOBILE_WISE, paramss[1]);
                    break;
                case "11":
                    sqlQuery = String.format(Q_S_SECTION_WISE, paramss[1]);
                    break;
                case "12":
                    sqlQuery = String.format(Q_S_SURNAME_WISE, paramss[1]);
                    break;
                case "13":
                    sqlQuery = String.format(Q_S_WITHOUT_ID_CARD_WISE, paramss[1]);
                    break;
                case "a":
                    sqlQuery = String.format(Q_S_NAME_WISE, paramss[1], paramss[2]);
                    break;
                case "b":
                    sqlQuery = String.format(Q_S_ID_WISE, paramss[1]);
                    break;
                case "c":
                    sqlQuery = String.format(Q_S_SR_WISE, paramss[1]);
                    break;
                case "ELECTION_HISTORY":
                    sqlQuery = String.format(Q_S_ELECTION_HISTORY, paramss[1]);
                    break;
                case "STATUS_UPDATE":
                    switch (paramss[1]) {
                        case "101":
                            sqlQuery = Q_S_SURNAME_STATUS;
                            break;
                        case "102":
                            sqlQuery = Q_S_COMMUNITY_STATUS;
                            break;
                    }
                    break;
            }
            con = getConnection();
            log.info("sqlQuery: " + sqlQuery);
            s = con.createStatement();
            rs = s.executeQuery(sqlQuery);
            if (reportType.equals("STATUS_UPDATE")) {
                statusUpdateTableData.clear();
            } else if (reportType.equals("ELECTION_HISTORY")) {
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
                    if (StringUtils.isNumeric(reportType) || reportType.equals("STATUS_UPDATE")) {
                        switch (reportType) {
                            case "3":
                            case "6":
                            case "7":
                            case "8":
                            case "9":
                            case "11":
                            case "12":
                                MyModelSimpleStringProperty entry = new MyModelSimpleStringProperty(
                                        rs.getString(1), rs.getString(2), "", "", "", "", "", "", "", "",
                                        "", "", "", "", "", "");
                                reportTableData.add(entry);
                                break;
                            case "STATUS_UPDATE":
                                entry = new MyModelSimpleStringProperty(
                                        rs.getString(1), rs.getString(2), "", "", "", "", "", "", "", "",
                                        "", "", "", "", "", "");
                                statusUpdateTableData.add(entry);
                                break;
                            default:
                                entry = new MyModelSimpleStringProperty(rs.getString(1), rs.getString(2),
                                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                                        rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), "",
                                        "", "", "", "");
                                reportTableData.add(entry);
                        }
                    } else {
                        MyModelSimpleStringProperty entry = new MyModelSimpleStringProperty(rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                                rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), "",
                                "", "", "", "");
                        voterTableData.add(entry);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getReportException: " + e.getMessage());
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
                log.error("getReport: " + ex.getMessage());
            }
        }
    }

    public static boolean exportData(File file, String[] params) {
        boolean status = false;
        try {
            switch (params[0]) {
                case "1":
                    status = csvDownload(file, reportTableData);
                    break;
                case "2":
                    status = pdfDownload(file, reportTableData, params[1], params[2]);
                    break;
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
        return status;
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
            log.error(e.getMessage());
        }
        return false;
    }

    public static boolean pdfDownload(File file, ObservableList<MyModelSimpleStringProperty> data, String reportType,
            String boothNumber)
            throws FileNotFoundException, JRException {
        //InputStream resourceAsStream = DataHandler.class.getResourceAsStream(PATH_REPORT_1);                
        Map parameters = new HashMap();
        InputStream resourceAsStream;
        switch (reportType) {
            case "1":
                parameters.put("parameter1", "अनु क्र.");
                parameters.put("parameter2", "न्यू अनु क्र.");
                parameters.put("parameter3", "एसी नं.");
                parameters.put("parameter4", "यादी क्र. ");
                parameters.put("parameter5", "अनु क्र.");
                parameters.put("parameter6", "मतदाराचे नाव");
                parameters.put("parameter7", "लिंग");
                parameters.put("parameter8", "वय");
                parameters.put("parameter9", "मोबाईल नं");
                parameters.put("parameter10", "वोटर आडी नं.");
                parameters.put("parameter11", "जन्म तारीख");
                parameters.put("parameter12", "पत्ता");

                parameters.put("reportName", "वय नुसार यादी");
                parameters.put("wardNo", boothNumber);
                parameters.put("boothLabel", "मतदान केंद्रचे नाव:");
                parameters.put("boothNumber", boothNumber);
                parameters.put("boothName", getBoothName(boothNumber));
                resourceAsStream = new FileInputStream(PATH_REPORT_1_);
                break;
            case "3":
                parameters.put("parameter1", "अनु क्र.");
                parameters.put("parameter2", "न्यू अनु क्र.");
                parameters.put("parameter3", "एसी नं.");
                parameters.put("parameter4", "यादी क्र. ");
                parameters.put("parameter5", "यादी अनु क्र.");
                parameters.put("parameter6", "मतदाराचे नाव");
                parameters.put("parameter7", "वय");
                parameters.put("parameter8", "लिंग");
                parameters.put("parameter9", "घर क्र.");                
                parameters.put("parameter10", "मोबाईल नं");
                parameters.put("parameter11", "जन्म तारीख");
                parameters.put("parameter12", "रिमार्क");

                parameters.put("reportName", "पत्ता नुसार यादी");
                parameters.put("wardNo", boothNumber);
                parameters.put("boothLabel", "मतदान केंद्रचे नाव:");
                parameters.put("boothNumber", boothNumber);
                parameters.put("boothName", getBoothName(boothNumber));
                resourceAsStream = new FileInputStream(PATH_REPORT_3_);
                break;
            case "8":
                parameters.put("parameter1", "अनु क्र.");
                parameters.put("parameter2", "न्यू अनु क्र.");
                parameters.put("parameter3", "एसी नं.");
                parameters.put("parameter4", "यादी क्र. ");
                parameters.put("parameter5", "यादी अनु क्र.");
                parameters.put("parameter6", "मतदाराचे नाव");
                parameters.put("parameter7", "वय");
                parameters.put("parameter8", "लिंग");
                parameters.put("parameter9", "घर क्र.");                
                parameters.put("parameter10", "मोबाईल नं");
                parameters.put("parameter11", "जन्म तारीख");
                parameters.put("parameter12", "रंग रिमार्क");

                parameters.put("reportName", "रंग नुसार यादी");
                parameters.put("wardNo", boothNumber);
                parameters.put("boothLabel", "मतदान केंद्रचे नाव:");
                parameters.put("boothNumber", boothNumber);
                parameters.put("boothName", getBoothName(boothNumber));
                resourceAsStream = new FileInputStream(PATH_REPORT_4_);
                break;
            case "12":
                parameters.put("parameter1", "अनु क्र.");
                parameters.put("parameter2", "मतदाराचे नाव");
                parameters.put("parameter3", "मोबाईल नं");
                parameters.put("parameter4", "लिंग");
                parameters.put("parameter5", "वय");
                parameters.put("parameter6", "घर क्र.");
                parameters.put("parameter7", "पत्ता");

                parameters.put("reportName", "आडनाव नुसार यादी");
                parameters.put("wardNo", boothNumber);
                parameters.put("boothLabel", "मतदान केंद्रचे नाव:");
                parameters.put("boothNumber", boothNumber);
                parameters.put("boothName", getBoothName(boothNumber));
                resourceAsStream = new FileInputStream(PATH_REPORT_2_);
                break;
            default:
                parameters.put("parameter1", "अनु क्र.");
                parameters.put("parameter2", "न्यू अनु क्र.");
                parameters.put("parameter3", "एसी नं.");
                parameters.put("parameter4", "यादी क्र. ");
                parameters.put("parameter5", "अनु क्र.");
                parameters.put("parameter6", "मतदाराचे नाव");
                parameters.put("parameter7", "लिंग");
                parameters.put("parameter8", "वय");
                parameters.put("parameter9", "मोबाईल नं");
                parameters.put("parameter10", "वोटर आडी नं.");
                parameters.put("parameter11", "जन्म तारीख");
                parameters.put("parameter12", "पत्ता");
                parameters.put("reportName", "यादी");
                parameters.put("wardNo", boothNumber);
                parameters.put("boothLabel", "मतदान केंद्रचे नाव:");
                parameters.put("boothNumber", boothNumber);
                parameters.put("boothName", getBoothName(boothNumber));
                resourceAsStream = new FileInputStream(PATH_REPORT_1_);
                break;
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, parameters, getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
        return true;
    }

}
