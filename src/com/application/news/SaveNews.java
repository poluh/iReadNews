package com.application.news;

import com.application.file.WorkFile;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class SaveNews extends Component {

    private static final String SAVE_DIRECTORY = "SavedPages/";
    //private static final String SAVE_DIRECTORY = "";

    public static void saveNews(String newsLink, String newsName) {
        try {
            newsName = WorkFile.normalizedName(newsName);

            URL PageUrl;
            URLConnection GetConn;

            PageUrl = new URL(newsLink);
            GetConn = PageUrl.openConnection();
            GetConn.connect();

            InputStreamReader ReadIn = new InputStreamReader(GetConn.getInputStream());
            BufferedReader BufData = new BufferedReader(ReadIn);
            String htmlFileName = (SAVE_DIRECTORY + newsName + ".html");
            FileWriter FWriter = new FileWriter(htmlFileName);
            BufferedWriter BWriter = new BufferedWriter(FWriter);
            String UrlData;
            while ((UrlData = BufData.readLine()) != null) {
                BWriter.write(UrlData);
                BWriter.newLine();
            }
            BWriter.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New saved!");
            alert.setContentText("Congratulations!\nNews saved!");
            alert.showAndWait();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save error.");
            alert.setContentText("Save error.\nSomething went wrong...");
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
                            "konqueror", "netscape", "opera", "links", "lynx"};

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

    public static void openNews(Stage primaryStage) throws IOException, URISyntaxException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open News");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Text files (*.html)", "*.html");

        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {

            String url = file.toURI().toString();
            openDefaultBrowser(url);

        }
    }

    public static void dellAllNews() {
        String path = "SavedPages/";
        try {
            for (File myFile : new File(path).listFiles())
                if (myFile.isFile()) myFile.delete();
        } catch (Exception ignored) { }
    }
}
