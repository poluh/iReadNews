package com.application.file;

import com.application.news.SaveNews;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class WorkFile {

    private static final String FILE_NAME = "DataBase/RSSDataBase/RSSLinks.txt";
    private static final String BUFF_FILE = "DataBase/RSSDataBase/RSSLinkBUFF.txt";
    private static final String POPULAR_RSS = "DataBase/RSSDataBase/RSSPopularLinks.txt";
    private static final String SETTINGS_FILE = "DataBase/settings.txt";

    public static boolean checkFile() {
        return (new File(FILE_NAME).exists());
    }

    private static void createFile() {
        if (!checkFile()) {
            new File(FILE_NAME);
        }
    }

    public static void addRSSLink(String link) throws IOException {
        createFile();
        FileWriter RSSFile = new FileWriter(new File(FILE_NAME), true);
        RSSFile.write(link + "\n");
        RSSFile.close();
    }

    public static List<String> listRSSLinks(String key) throws IOException {
        createFile();
        List<String> answer = new ArrayList<>();
        File file = Objects.equals(key, "0") ? new File(FILE_NAME) : new File(POPULAR_RSS);
        BufferedReader fin = new BufferedReader(new FileReader(file));
        String line;

        while ((line = fin.readLine()) != null) {
            answer.add(line);
            if (Objects.equals(key, "1")) {
                addRSSLink(line);
            }
        }
        return answer;
    }

    public static List<String> listNewsNames() throws IOException {
        List<String> answer = new ArrayList<>();
        File[] files = new File(SaveNews.SAVE_DIRECTORY).listFiles();

        assert files != null;
        for (File file : files) {
            answer.add(file.getName().replace(".html", "...").replace("-", " "));
        }

        return answer;
    }

    public static List<String> listSettings() throws IOException {
        List<String> answer = new ArrayList<>();
        File set = new File(SETTINGS_FILE);

        BufferedReader fin = new BufferedReader(new FileReader(set));
        String line;

        while ((line = fin.readLine()) != null) answer.add(line);
        return answer;
    }

    public static void deleteRSSLink(String delLink) throws IOException {
        File sourceFile = new File(FILE_NAME);
        File outputFile = new File(BUFF_FILE);

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
            return (newsName.replace(" ", "-").substring(0, 30));
    }

    public static void deleteSavedNews(String nameNews) {

        Arrays.stream(new File(SaveNews.SAVE_DIRECTORY).listFiles()).filter(file -> file.isFile() &&
                Objects.equals(file.toString(),
                        SaveNews.SAVE_DIRECTORY + "/" + nameNews + ".html")).forEach(File::delete);

    }

}
