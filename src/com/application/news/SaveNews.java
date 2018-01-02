package com.application.news;

import com.application.file.WorkFile;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class SaveNews {

    public static void saveNews(String newsLink, String newsName) {
        try
        {
            newsName = WorkFile.normalizedName(newsName);

            URL PageUrl;
            URLConnection GetConn;

            PageUrl = new URL(newsLink);
            GetConn = PageUrl.openConnection();
            GetConn.connect();

            InputStreamReader ReadIn = new InputStreamReader(GetConn.getInputStream());
            BufferedReader BufData = new BufferedReader(ReadIn);
            String htmlFileName = ("SavedPages/" + newsName + ".html");
            FileWriter FWriter = new FileWriter(htmlFileName);
            BufferedWriter BWriter = new BufferedWriter(FWriter);
            String UrlData;
            while ((UrlData = BufData.readLine()) != null)
            {
                BWriter.write(UrlData);
                BWriter.newLine();
            }
            BWriter.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New saved!");
            alert.setContentText("Congratulations!\nNews saved!");
            alert.showAndWait();
        }
        catch(IOException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save error.");
            alert.setContentText("Save error.\nSomething went wrong...");
            alert.showAndWait();
        }
    }

    public static void openNews() {

    }
}
