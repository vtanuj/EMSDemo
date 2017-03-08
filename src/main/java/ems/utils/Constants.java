/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.utils;

import ems.model.MyModel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tanujv
 */
public class Constants {

    //About
    public static final String VENDOR_NAME = "Company Name";
    public static final String VERSION = "1.0.0.0";
    //Title
    public static final String TITLE_LOGIN = "Login Title";
    public static final String TITLE_HOME = "Home Title";
    public static final String TITLE_ABOUT = "About Title";
    //Header
    public static final String HEADER_SPLASH = "Header Splash";
    public static final String HEADER_LOGIN = "Header Login";
    public static final String HEADER_HOME = "Header Home";
    public static final String HEADER_ABOUT = "Header About";
    //Images
    public static final String IMAGE_FAVICON = "/ems/media/images/favicon.png";
    public static final String IMAGE_SPLASH = "/ems/media/images/splash.png";
    public static final String IMAGE_LOGO = "/ems/media/images/splash.png";
    //Paths
    public static final String PATH_TEMP = "./temp";
    public static final String PATH_AUTH_DB = PATH_TEMP + "/auth.dll";
    public static final String PATH_TEMP_DB = "/ems/temp/temp.dll";
    public static final String PATH_FONT = "/ems/media/font/fontawesome-webfont.ttf";
    public static final String PATH_TEMP_DB_ = PATH_TEMP + "/temp.dll";

    public static final List<MyModel> REPORTS_TYPE = new LinkedList<>();
    public static final List<MyModel> MONTHS = new LinkedList<>();
    public static final List<MyModel> COLORS = new LinkedList<>();

    static {
        REPORTS_TYPE.add(new MyModel("0", "Choose Report"));
        REPORTS_TYPE.add(new MyModel("1", "Age Wise"));
        REPORTS_TYPE.add(new MyModel("2", "Alphabetic Wise"));
        REPORTS_TYPE.add(new MyModel("3", "Area Wise"));
        REPORTS_TYPE.add(new MyModel("4", "Birthday List"));
        REPORTS_TYPE.add(new MyModel("5", "Birthday List Date Wise"));
        REPORTS_TYPE.add(new MyModel("6", "Booth Wise - Matdar Yadi"));
        REPORTS_TYPE.add(new MyModel("7", "Community Wise"));
        REPORTS_TYPE.add(new MyModel("8", "Color Code Booth Wise"));
        REPORTS_TYPE.add(new MyModel("9", "Color Code Wise"));
        REPORTS_TYPE.add(new MyModel("10", "Mobile Wise"));
        REPORTS_TYPE.add(new MyModel("11", "Section Wise"));
        REPORTS_TYPE.add(new MyModel("12", "Surname Wise"));
        REPORTS_TYPE.add(new MyModel("13", "Without ID Card List"));

        MONTHS.add(new MyModel("00", "Choose Month"));
        MONTHS.add(new MyModel("01", "January"));
        MONTHS.add(new MyModel("02", "February"));
        MONTHS.add(new MyModel("03", "March"));
        MONTHS.add(new MyModel("04", "April"));
        MONTHS.add(new MyModel("05", "May"));
        MONTHS.add(new MyModel("06", "June"));
        MONTHS.add(new MyModel("07", "July"));
        MONTHS.add(new MyModel("08", "August"));
        MONTHS.add(new MyModel("09", "September"));
        MONTHS.add(new MyModel("10", "October"));
        MONTHS.add(new MyModel("11", "November"));
        MONTHS.add(new MyModel("12", "December"));

        COLORS.add(new MyModel("0", "Choose Color"));
        COLORS.add(new MyModel("1", "Our"));
        COLORS.add(new MyModel("2", "Opposite"));
        COLORS.add(new MyModel("3", "Unpredictable"));
        COLORS.add(new MyModel("4", "Others"));
        COLORS.add(new MyModel("5", "All"));
    }

    public static final String[] REPORT_COLUMN_HEADERS = {"Ward No", "Ward Sr No", "New Sr No.", "Name", "Sex",
        "Age", "ID Card No.", "Mobile No.", "DOB", "Language", "Booth Name"};

    public static final String Q_AGE_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s and a.AGE > %s and a.AGE < %s "
            + "order by a.FirstNameEnglish";

    public static final String Q_ALPHABETIC_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s "
            + "order by a.FirstNameEnglish";

    public static final String Q_BIRTHDAY_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s and strftime('%%m', a.DOB)='%s' "
            + "order by a.FirstNameEnglish";

    public static final String Q_BIRTHDAY_DAY_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s and  strftime('%%m-%%d', a.DOB)=substr('%s',6,5) "
            + "order by a.FirstNameEnglish";

    public static final String Q_MOBILE_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s and length(trim(a.Mobile_no))=10 "
            + "order by a.FirstNameEnglish";

    public static final String Q_SURNAME_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s "
            + "order by a.SurNameEnglish";

    public static final String Q_WITHOUT_ID_CARD_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.booth_no=%s and  (a.CardNo is null or length(trim(a.CardNo))<>10) "
            + "order by a.FirstNameEnglish";

    public static final String Q_NAME_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.FirstNameEnglish like '%s%%' and a.SurNameEnglish like '%s%%' "
            + "order by a.FirstNameEnglish";

    public static final String Q_ID_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.CardNo like '%s%%' "
            + "order by a.FirstNameEnglish";

    public static final String Q_SR_WISE
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.WardSr_No,''),"
            + "IFNULL(a.slno,''),"
            + "trim(ifnull(a.SurNameEnglish,'')) || '  ' || trim(ifnull(a.FirstNameEnglish,'')) 'Name', "
            + "ifnull(a.SEX,''),"
            + "ifnull(a.AGE,''),"
            + "ifnull(a.CardNo,''),"
            + "ifnull(a.Mobile_no,''),"
            + "ifnull(strftime('%%d-%%m-%%Y', a.DOB),''),"
            + "ifnull(a.Cast_nm,''),"
            + "ifnull(b.booth_name_en,'') "
            + "from e_details a "
            + "left join booth_master b on a.booth_no=b.booth_no "
            + "where a.WardSr_No=%s "
            + "order by a.FirstNameEnglish";

    public static final String Q_ELECTION_HISTORY
            = "select "
            + "ifnull(a.ward_no,''),"
            + "ifnull(a.cnd_name,''),"
            + "IFNULL(a.party,''),"
            + "ifnull(a.t_vote,'') "
            + "from bmc_history a "
            + "where a.ward_no=%s "
            + "order by a.cnd_name";
}
