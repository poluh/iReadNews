package com.application.action.event;

import com.application.news.GetNews;
import com.application.news.News;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;


public class ActionEvent {

    public static void buttonEvent(Button btn, Stage primaryStage, TextField rssText) {
        btn.setOnAction((javafx.event.ActionEvent event) ->  {

            Stage newsStage = new Stage();

            newsStage.setTitle("News!");

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            createObjects(grid, rssText.getText());

            Scene scene = new Scene(grid);
            newsStage.setScene(scene);
            newsStage.show();

            primaryStage.close();
        });
    }

    private static void createObjects(GridPane grid, String RSSLink) {

        List<News> newsList = GetNews.getNews(RSSLink);

        int i = 0;
        for (News news : newsList) {
            Label newsTitle = new Label(news.getTitle());
            newsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            Label newsDescription = new Label(news.getDescription());
            newsDescription.autosize();

            Label newsDate = new Label(news.getDate());
            newsDate.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            Label newsLink = new Label("Подробнее: " + news.getLink());

            grid.add(newsTitle, 0, i, 2, 1);
            grid.add(newsDate, 0, i, 2, 1);
            grid.add(newsDescription, 0, i + 1);
            grid.add(newsLink, 0, i + 2);
            i += 3;
        }

    }
}
