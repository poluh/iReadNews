package com.application.file;

import com.application.news.SaveNews;

import java.io.*;


public class CreateDirectory {
    private final static String PATH_TO_DATABASE = "DataBase/RSSDataBase";
    private final static String PATH_TO_SAVEDPAGES = SaveNews.SAVE_DIRECTORY;
    private final static String PATH_TO_RSS_POPULAR_LINK = "DataBase/RSSDataBase/RSSPopularLinks.txt";

    public static void createDirectory() throws IOException {
        final File DATABASE = new File(PATH_TO_DATABASE);
        final File SAVEPAGES = new File(PATH_TO_SAVEDPAGES);
        final File outputFile = new File(PATH_TO_RSS_POPULAR_LINK);
        DATABASE.mkdirs();
        SAVEPAGES.mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write("https://lenta.ru/rss/news\n" +
                "http://www.fontanka.ru/fontanka.rss\n" +
                "https://news.google.com/news/rss/?ned=ru_ru&gl=RU&hl=ru");
        writer.close();
    }
}
