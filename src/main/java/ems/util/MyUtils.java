/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.util;

import static ems.main.Ems.log;
import static ems.util.Constants.PATH_AUTH_DB;
import static ems.util.Constants.PATH_FONT;
import static ems.util.Constants.PATH_LOCK_FILE;
import static ems.util.Constants.PATH_TEMP_DB;
import static ems.util.Constants.PATH_TEMP_DB_;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Font;

/**
 *
 * @author tanujv
 */
public class MyUtils {

    static {
        Font.loadFont(MyUtils.class.getResource(PATH_FONT).toExternalForm(), 10);
    }

    static File file;
    static FileChannel fileChannel;
    static FileLock lock;
    static boolean running = false;

    public static boolean checkIfAlreadyRunning() throws IOException {
        file = new File(PATH_LOCK_FILE);
        if (!file.exists()) {
            file.createNewFile();
            running = false;
        } else {
            file.delete();
        }

        fileChannel = new RandomAccessFile(file, "rw").getChannel();
        lock = fileChannel.tryLock();

        if (lock == null) {
            fileChannel.close();
            return true;
        }
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        return running;
    }

    public static void unlockFile() {
        try {
            if (lock != null) {
                lock.release();
            }
            fileChannel.close();
            file.delete();
            running = false;
        } catch (IOException e) {
            log.error("unlockFile" + e.getMessage());
        }
    }

    static class ShutdownHook extends Thread {

        @Override
        public void run() {
            unlockFile();
        }
    }

    public static void createAuthDB() {
        String url = "jdbc:sqlite:" + PATH_AUTH_DB;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.info("The driver name is " + meta.getDriverName());
                log.info("A new database has been created.");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
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
            log.error(ex.getMessage());
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
        log.info("selectPath: " + selectPath);

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
            log.info("string:" + s);
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

    public static void exportDB(File file) {
        try {
            boolean status = copyFileUsingStream(new File(PATH_TEMP_DB_), file);
            if (status) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Database exported successful!");
                alert.setContentText("Successfully exported the database.");
                ButtonType buttonTypeOne = new ButtonType("Open Folder");
                ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeOk);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne) {
                    MyUtils.openFolderWithFileSelected(file.getAbsolutePath());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failure");
                alert.setHeaderText("Database exported unsuccessful!");
                alert.setContentText("Unable to export the database.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
    }

    private static boolean copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            return true;
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        } finally {
            is.close();
            os.close();
        }
        return false;
    }

    public static boolean renameFile(File oldfile, File newfile) {
        return oldfile.renameTo(newfile);
    }

    public static void importDB(File file) {
        try {
            boolean renameStatus = renameFile(new File(PATH_TEMP_DB_), new File(PATH_TEMP_DB_ + "_" + DateUtils.dateTimePlain()));
            if (renameStatus) {
                boolean status = copyFileUsingStream(file, new File(PATH_TEMP_DB_));
                if (status) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Database import successful!");
                    alert.setContentText("Successfully exported the database.");
                    ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(buttonTypeOk);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Failure");
                    alert.setHeaderText("Database imported unsuccessfully!");
                    alert.setContentText("Unable to import the database.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failure");
                alert.setHeaderText("Database imported unsuccessfully!");
                alert.setContentText("Unable to import the database.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            JavaFXUtils.exceptionDialog(e);
        }
    }
}
