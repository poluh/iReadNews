package com.application.server.ftp.connect;

public class ConfigConnect {

    private final static String LOGIN = "";
    private final static String PASSWORD = "";
    private final static String HOST = "files..com";
    private final static int PORT = 21;
    private final static String DIR = "/tmp/updates";
    private final static int CURRENT_VERSION = 110;

    public static void connection() {
        ConnectServer.connect(LOGIN, PASSWORD, HOST, PORT, DIR, CURRENT_VERSION);
    }

}
