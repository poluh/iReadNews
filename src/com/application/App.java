package com.application;

import com.application.action.event.ActionEvent;
import com.application.file.CreateDirectory;
import com.application.file.WorkFile;
import com.application.news.FeedNews;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class App extends Application {

    public static String PATH_TO_STYLE = "style/style.css";
    private static String PATH_TO_ICON = "style/book-open.png";
    private static Image ICON = new Image(PATH_TO_ICON);

    private static void createStartWindow(Stage primaryStage) {

        primaryStage.setTitle("News");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        createObjects(grid, primaryStage);

        Scene scene = new Scene(grid, 400, 400);
        scene.getStylesheets().add(PATH_TO_STYLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void createRSSLinksWindow(Stage primaryStage, List<String> file) {


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        primaryStage.setTitle("Your RSS links.");
        primaryStage.getIcons().add(ICON);

        Scene scene = new Scene(scrollPane, 350, 400);
        scene.getStylesheets().add(PATH_TO_STYLE);

        primaryStage.setScene(scene);
        primaryStage.show();

        createObjects(grid, primaryStage, file);
    }

    public static void createSavedPagesWindow(Stage primaryStage, List<String> savedNewsList) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Button back = new Button("Back to links");
        ActionEvent.toBack(back, primaryStage);

        StackPane root = new StackPane();

        root.getChildren().addAll(scrollPane, back);
        StackPane.setMargin(back, new Insets(50, 0, 400, 0));

        Scene scene = new Scene(root, 350, 400);
        scene.getStylesheets().add(PATH_TO_STYLE);

        primaryStage.setTitle("Your saved pages.");
        primaryStage.getIcons().add(ICON);

        primaryStage.setScene(scene);
        primaryStage.show();

        createListOfButton(grid, primaryStage, savedNewsList);
    }

    // This method created object for start window

    private static void createObjects(GridPane grid, Stage primaryStage) {

        // Main Title
        Text sceneTitle = new Text("Welcome.");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 3, 1);

        // If user do first entry
        Text simpleTitle = new Text("It looks like you have never been here before.\n" +
                "Enter any link to start");
        grid.add(simpleTitle, 0, 1, 2, 1);

        Label rssLink = new Label("Enter RSS-link:");
        grid.add(rssLink, 0, 2);

        TextField rssText = new TextField();
        grid.add(rssText, 1, 2);

        Button buttonFirstEntry = new Button("Get news!");
        HBox hbBtnFirstEntry = new HBox(10);
        hbBtnFirstEntry.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnFirstEntry.getChildren().add(buttonFirstEntry);
        grid.add(hbBtnFirstEntry, 1, 5);
        ActionEvent.buttonEvent(buttonFirstEntry, primaryStage, rssText);

        Button buttonPopularRSS = new Button("Append popular RSS.");
        HBox hbBtnPopularRSS = new HBox(10);
        hbBtnPopularRSS.setAlignment(Pos.BOTTOM_LEFT);
        hbBtnPopularRSS.getChildren().addAll(buttonPopularRSS);
        grid.add(hbBtnPopularRSS, 0, 5);
        ActionEvent.buttonPopularRSS(buttonPopularRSS, primaryStage);

    }


    // This methods created obj for base window, after added links RSS

    private static void createObjects(GridPane grid, Stage primaryStage, List<String> RSSlinks) {


        Label title = new Label("Your RSS links:");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0);

        final int[] i = {1};
        Platform.runLater(()  -> {
            for (String link : RSSlinks) {
                if (!link.matches("\\s+") && !link.isEmpty()) {

                    Button buttonTitleNewsPortal = new Button(new FeedNews(link).getTitleString());
                    TextField textField = new TextField(link + ":/LINK/:" + new FeedNews(link).getTitleString());
                    HBox hbBtnTitleNewsPortal = new HBox(10);
                    Button buttonForDelNewsPortal = new Button("X");
                    HBox hbBtnDel = new HBox(10);
                    GridPane miniGrid = new GridPane();

                    Platform.runLater(() -> {
                        ActionEvent.buttonEvent(buttonTitleNewsPortal, primaryStage, textField);
                        ActionEvent.buttonEvent(buttonForDelNewsPortal, new TextField(link), primaryStage, "0");
                    });

                    Platform.runLater(() -> {
                        buttonTitleNewsPortal.setMinSize(250, 30);
                        buttonTitleNewsPortal.setMaxWidth(250);
                        buttonTitleNewsPortal.setWrapText(true);
                        buttonForDelNewsPortal.setMinSize(30, 30);
                    });

                    Platform.runLater(() -> {
                        hbBtnDel.getChildren().add(buttonForDelNewsPortal);
                        hbBtnTitleNewsPortal.getChildren().add(buttonTitleNewsPortal);
                        hbBtnTitleNewsPortal.setAlignment(Pos.BOTTOM_LEFT);
                        hbBtnDel.setAlignment(Pos.CENTER_LEFT);
                    });

                    int finalI = i[0];
                    Platform.runLater(() -> {

                        miniGrid.setHgap(10);
                        miniGrid.add(hbBtnTitleNewsPortal, 0, 0);
                        miniGrid.add(hbBtnDel, 1, 0);

                        grid.add(miniGrid, 0, finalI);
                    });

                    i[0]++;
                }
            }
        });

        Platform.runLater(() -> {
            TextField rssField = new TextField();
            rssField.setAlignment(Pos.BOTTOM_LEFT);
            rssField.setMaxWidth(290);

            Button buttonAddRSSLink = new Button("Add RSS.");
            buttonAddRSSLink.setMinWidth(290);
            ActionEvent.buttonEvent(buttonAddRSSLink, rssField, primaryStage, "0");
            HBox hbBtnAddRSSLink = new HBox(10);
            hbBtnAddRSSLink.setAlignment(Pos.BOTTOM_LEFT);
            hbBtnAddRSSLink.getChildren().add(buttonAddRSSLink);

            Button openSaveNews = new Button("Go to saved news!");
            openSaveNews.setMinWidth(290);
            ActionEvent.openSavedNewsWindow(openSaveNews, primaryStage);
            HBox hbBtnOPenSaveNews = new HBox(10);
            hbBtnOPenSaveNews.setAlignment(Pos.BOTTOM_CENTER);
            hbBtnOPenSaveNews.getChildren().add(openSaveNews);


            GridPane miniGrid = new GridPane();
            miniGrid.setHgap(10);
            miniGrid.setVgap(10);
            miniGrid.setMaxWidth(290);

            miniGrid.add(rssField, 0, 1, 2, 1);
            miniGrid.add(hbBtnAddRSSLink, 0, 2, 2, 1);
            miniGrid.add(hbBtnOPenSaveNews, 0, 3, 2, 1);
            miniGrid.setAlignment(Pos.CENTER);

            grid.add(miniGrid, 0, i[0] + 2);

        });
    }

    private static void createListOfButton(GridPane grid, Stage primaryStage, List<String> namesList) {

        int i = 3;
        for (String newsName : namesList) {

            Button news = new Button(newsName);
            news.setMinWidth(270);
            news.setAlignment(Pos.BOTTOM_CENTER);
            ActionEvent.openSavedNews(news, newsName);

            GridPane miniGrid = new GridPane();
            miniGrid.setHgap(10);

            Button delNews = new Button("X");
            ActionEvent.buttonEvent(delNews, new TextField("Check key"), primaryStage, newsName);

            miniGrid.add(news, 0, 0);
            miniGrid.add(delNews, 1, 0);

            grid.add(miniGrid, 0, i);
            i++;
        }

        if (i == 3) {
            Label label = new Label("Saved news not found :(");
            label.setFont(Font.font(25));
            HBox hBox = new HBox(label);
            hBox.setAlignment(Pos.BOTTOM_CENTER);
            grid.add(hBox, 0, 20);
        } else {
            Button dellAll = new Button("Clear cache");
            ActionEvent.deleteAllNews(dellAll, primaryStage);
            HBox hbDellAll = new HBox();
            hbDellAll.setAlignment(Pos.BOTTOM_CENTER);
            hbDellAll.getChildren().addAll(dellAll);
            hbDellAll.autosize();
            grid.add(hbDellAll, 0, i + 1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        if (!WorkFile.checkFile()) {
            createStartWindow(primaryStage);
        } else {
            List<String> RSSLinks = WorkFile.listRSSLinks("0");
            createRSSLinksWindow(primaryStage, RSSLinks);
        }
        primaryStage.getIcons().add(ICON);
    }

    public static void main(String[] args) throws IOException {
        CreateDirectory.createDirectory();
        launch(args);
    }

}



