/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.utils;

import static ems.utils.Constants.PATH_AUTH_DB;
import static ems.utils.Constants.PATH_FONT;
import static ems.utils.Constants.PATH_TEMP_DB;
import static ems.utils.Constants.PATH_TEMP_DB_;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Font;

/**
 *
 * @author tanujv
 */
public class MyUtils {

    static {
        Font.loadFont(MyUtils.class.getResource(PATH_FONT).toExternalForm(), 10);
    }

    public static void createAuthDB() {
        String url = "jdbc:sqlite:" + PATH_AUTH_DB;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void copyTempDB() {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = MyUtils.class.getResourceAsStream(PATH_TEMP_DB);
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + PATH_TEMP_DB + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(PATH_TEMP_DB_);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                stream.close();
                resStreamOut.close();
            } catch (IOException ex) {
                Logger.getLogger(MyUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void openFile(String fileName) throws IOException {
        File file = new File(fileName);

        //first check if Desktop is supported by Platform or not
        if (!Desktop.isDesktopSupported()) {
            throw new IOException("Desktop is not supported");
        }

        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    public static void openFolderWithFileSelected(String fileName) throws IOException {
        String selectPath = "/select," + fileName;
        System.out.println("selectPath: " + selectPath);

        //START: Strip one SPACE among consecutive spaces
        LinkedList<String> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        boolean flag = true;

        for (int i = 0; i < selectPath.length(); i++) {
            if (i == 0) {
                sb.append(selectPath.charAt(i));
                continue;
            }

            if (selectPath.charAt(i) == ' ' && flag) {
                list.add(sb.toString());
                sb.setLength(0);
                flag = false;
                continue;
            }

            if (!flag && selectPath.charAt(i) != ' ') {
                flag = true;
            }

            sb.append(selectPath.charAt(i));
        }

        list.add(sb.toString());

        list.addFirst("explorer.exe");
    //END: Strip one SPACE among consecutive spaces

        //Output List
        for (String s : list) {
            System.out.println("string:" + s);
        }
        /*output of above loop

         string:explorer.exe
         string:/select,D:\GAME
         string:  OF
         string: Thrones

         */

        //Open in Explorer and Highlight
        Process p = new ProcessBuilder(list).start();
    }
}
