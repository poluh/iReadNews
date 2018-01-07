package com.application.server.ftp.connect;

import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

class ConnectServer {

    private final static String SAVE_FILENAME = "iReadNews.jar";

    static void connect(String LOGIN, String PASSWORD, String HOST, int PORT,
                        String DIR, int CURRENT_VERSION) {

        FTPClient ftp = new FTPClient();

        try {
            ftp.connect(HOST, PORT);
            ftp.login(LOGIN, PASSWORD);
            ftp.enterLocalPassiveMode();

            ftp.changeWorkingDirectory(DIR);

            final String FILENAME = ftp.listFiles()[2].getName();
            final int VERSION = Integer.parseInt(FILENAME.split("v")[1].split("\\.")[0]);

            if (VERSION > CURRENT_VERSION) {

                ftp.changeWorkingDirectory("/");

                OutputStream outputStream =
                        new BufferedOutputStream(new FileOutputStream(SAVE_FILENAME));
                ftp.retrieveFile(DIR + "/" + FILENAME, outputStream);
                outputStream.close();

            } else ftp.disconnect();

        } catch (IOException ex) {
            System.out.println("Error occurs in downloading files from ftp Server: " + ex.getMessage());
        } finally {
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
