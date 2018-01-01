package com.application.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkFile {

    private static final String FILENAME = "DataBase/RSSDataBase/RSSLinks.txt";
    public File file = new File(FILENAME);

    public static boolean checkFile() {
        return new File(FILENAME).exists();
    }

    private static void createFile() {
        if (!checkFile()) {
            File RSSLinks = new File(FILENAME);
        }
    }

    public static void addRSSLink(String link) throws IOException {
        createFile();
        FileWriter RSSFile = new FileWriter(new File(FILENAME), true);
        RSSFile.write(link + "\n");
        RSSFile.close();
    }

    public static List<String> listRSSLinks() throws IOException {

        List<String> answer = new ArrayList<>();
        File file = new File(FILENAME);
        BufferedReader fin = new BufferedReader(new FileReader(file));
        String line;

        while ((line = fin.readLine()) != null) answer.add(line);
        return answer;
    }


}
