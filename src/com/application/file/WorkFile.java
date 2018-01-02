package com.application.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkFile {

    private static final String FILENAME = "DataBase/RSSDataBase/RSSLinks.txt";
    private static final String BUFFFILE = "DataBase/RSSDataBase/RSSLinkBUFF.txt";

    public static boolean checkFile() {
        return new File(FILENAME).exists();
    }

    private static void createFile() {
        if (!checkFile()) {
            new File(FILENAME);
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

    public static void deleteRSSLink(String delLink) throws IOException {
        File sourceFile = new File(FILENAME);
        File outputFile = new File(BUFFFILE);

        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(delLink)) {
                writer.write(line);
                writer.newLine();
            }
        }
        reader.close();
        writer.close();
        sourceFile.delete();
        outputFile.renameTo(sourceFile);
    }

    public static String normalizedName(String newsName) {
        return newsName.substring(0, 20).replace(" ", "-");
    }


}
