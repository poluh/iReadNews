package com.application.news;

import com.application.file.WorkFile;
import javafx.scene.control.Alert;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

public class SaveNews {

    public static final String SAVE_DIRECTORY = "DataBase/SavedPages";

    public static void saveNews(String newsLink, String newsName) {
        try {
            newsName = WorkFile.normalizedName(newsName);

            URL news = new URL(newsLink);
            ReadableByteChannel byteChannel = Channels.newChannel(news.openStream());
            FileOutputStream savedFile = new FileOutputStream(SAVE_DIRECTORY + "/" + newsName + ".html");
            savedFile.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New saved!");
            alert.setContentText("Congratulations!\nNews saved!");
            alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save error.");
            alert.setContentText("Save error.\nSomething went wrong...\nLogs:\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    public static void openDefaultBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.contains("win")) {

                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else {
                if (os.contains("nix") || os.contains("nux")) {

                    String[] browsers = {"epiphany", "firefox", "mozilla",
                            "konqueror", "netscape", "opera", "links", "lynx", "safari"};

                    // "browser0 "URI" || browser1 "URI" ||..."
                    StringBuilder cmd = new StringBuilder();
                    for (int i = 0; i < browsers.length; i++)
                        cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                    rt.exec(new String[]{"sh", "-c", cmd.toString()});
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static void openNews(String newsName) throws FileNotFoundException {
        File htmlNews = new File(SAVE_DIRECTORY + "/" +
                newsName.replace("...", "").replace(" ", "-") + ".html");
        openDefaultBrowser(htmlNews.toURI().toString());
        System.out.println(htmlNews.getName());
        System.out.println(htmlNews.toURI().toString());

    }

    public static void dellAllNews() {
        String path = SAVE_DIRECTORY + "/";
        try {
            Arrays.stream(new File(path).listFiles()).filter(File::isFile).forEach(File::delete);
        } catch (Exception ignored) { }
    }
}
