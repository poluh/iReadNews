package com.application.action.event;

import com.application.App;
import com.application.file.WorkFile;
import com.application.news.GetNews;
import com.application.news.News;
import com.application.news.SaveNews;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;


public class ActionEvent {

    private static double width = 400;
    private static double height = 800;


    // Action event for button on main window
    // Created window with news
    public static void buttonEvent(Button btn, Stage primaryStage, TextField rssText) {
        btn.setOnAction((javafx.event.ActionEvent event) -> {
            if (WorkFile.checkFile()) {
                try {
                    String title = rssText.getText().substring(rssText.getText().indexOf(":/LINK/:") + 8);
                    String link = rssText.getText().substring(0, rssText.getText().indexOf(":/LINK/:"));

                    if (!Objects.equals(btn.getText(), title))
                        WorkFile.addRSSLink(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Stage newsStage = new Stage();

            newsStage.setTitle("News!");

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.BOTTOM_RIGHT);
            grid.setPadding(new Insets(20));
            grid.setPadding(new Insets(20));
            grid.setVgap(15);
            grid.setMaxSize(width, height);

            try {
                createObjects(grid, rssText.getText().substring(0,
                        rssText.getText().indexOf(":/LINK/:")), primaryStage);
            } catch (StringIndexOutOfBoundsException e) {
                createObjects(grid, rssText.getText(), primaryStage);
                try {
                    WorkFile.addRSSLink(rssText.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            ScrollPane originPane = new ScrollPane();
            originPane.setContent(grid);
            originPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            originPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            Scene scene = new Scene(originPane, width + 50, height);
            scene.getStylesheets().addAll(App.PATH_TO_STYLE);
            newsStage.setScene(scene);
            newsStage.show();

            primaryStage.close();
        });
    }

    // Active event for button "Add RSS" in subtract window and button "X"
    public static void buttonEvent(Button button, TextField RSSLink, Stage primaryStage) {
        button.setOnAction((javafx.event.ActionEvent event) -> {

            try {
                if (!Objects.equals(button.getText(), "X")) {
                    WorkFile.addRSSLink(RSSLink.getText());
                } else WorkFile.deleteRSSLink(RSSLink.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                App.createRSSLinksWindow(primaryStage, WorkFile.listRSSLinks());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private static void toBack(Button button, Stage primaryStage) {
        button.setOnAction((javafx.event.ActionEvent event) -> {
            try {
                App.createRSSLinksWindow(primaryStage, WorkFile.listRSSLinks());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void saveNews(Button button, String newsLink, String newsName) {
        button.setOnMouseClicked(event -> {
            SaveNews.saveNews(newsLink, newsName);
        });
    }

    private static void openNew(Button button, String link) {
        button.setOnMouseClicked(event -> {
            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();
            webEngine.load(link);

            Stage newStage = new Stage();
            VBox root = new VBox();
            root.setPadding(new Insets(5));
            root.setSpacing(5);
            root.getChildren().addAll(browser);

            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(App.PATH_TO_STYLE);

            newStage.setTitle("More info for new!");
            newStage.setScene(scene);
            newStage.show();
        });
    }

    public static void openNews(Button button, Stage primaryStage) {
        button.setOnAction(event -> {
            try {
                SaveNews.openNews(primaryStage);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public static void deleteAllNews(Button button) {
        button.setOnAction(event -> SaveNews.dellAllNews());
    }

    // Create news-window
    private static void createObjects(GridPane grid, String RSSLink, Stage primaryStage) {

        List<News> newsList = GetNews.getNews(RSSLink);

        int i = 0;

        Button back = new Button("Back to links");
        toBack(back, primaryStage);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().addAll(back);
        hbBtn.autosize();
        grid.add(hbBtn, 0, i);

        i += 2;

        for (News news : newsList) {

            Text newsTitle = new Text(news.getTitle());
            newsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            newsTitle.setWrappingWidth(width);

            Text newsDescription = new Text(news.getDescription());
            newsDescription.setWrappingWidth(width);

            Button newsDate = new Button(news.getDate());
            newsDate.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            Button newsLink = new Button("More info...");
            newsLink.setTextFill(Color.BLUE);

            openNew(newsLink, news.getLink());

            Button newsSave = new Button("Save this new!");
            newsSave.setTextFill(Color.RED);
            newsSave.setAlignment(Pos.BOTTOM_RIGHT);
            saveNews(newsSave, news.getLink(), news.getTitle());

            GridPane miniGrid = new GridPane();
            miniGrid.setHgap(20);

            miniGrid.add(newsLink, 0, 0);
            miniGrid.add(newsSave, 1, 0);

            grid.add(newsTitle, 0, i, 2, 1);
            grid.add(newsDescription, 0, i + 1);
            grid.add(miniGrid, 0, i + 2, 2, 1);

            i += 3;
        }

    }
}
