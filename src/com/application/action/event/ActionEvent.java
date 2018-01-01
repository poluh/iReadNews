package com.application.action.event;

import com.application.news.GetNews;
import com.application.news.News;
import com.application.text.filter.wrap.WrapText;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;



public class ActionEvent {

    private static double width = 400;
    private static double height = 800;

    public static void buttonEvent(Button btn, Stage primaryStage, TextField rssText) {
        btn.setOnAction((javafx.event.ActionEvent event) ->  {

            Stage newsStage = new Stage();

            newsStage.setTitle("News!");

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.BOTTOM_RIGHT);
            grid.setPadding(new Insets(20));
            grid.setPadding(new Insets(20));
            grid.setVgap(15);
            grid.setMaxSize(width, height);

            createObjects(grid, rssText.getText());


            ScrollPane originPane = new ScrollPane();
            originPane.setContent(grid);
            originPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            Scene scene = new Scene(originPane, width + 100, height);
            newsStage.setScene(scene);
            newsStage.show();

            primaryStage.close();
        });
    }

    private static void createObjects(GridPane grid, String RSSLink) {

        List<News> newsList = GetNews.getNews(RSSLink);

        int i = 0;
        for (News news : newsList) {
            Text newsTitle = new Text(news.getTitle());
            newsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            newsTitle.setWrappingWidth(width);

            Text newsDescription = new Text(news.getDescription());
            newsDescription.setWrappingWidth(width);

            Label newsDate = new Label(news.getDate());
            newsDate.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            Label newsLink = new Label("More info...");
            newsLink.setTextFill(Color.BLUE);


            grid.add(newsTitle, 0, i, 2, 1);
            grid.add(newsDescription, 0, i + 1);
            grid.add(newsLink, 0, i + 2, 2, 1);

            i += 3;
        }

    }
}
