package com.application;

import com.application.action.event.ActionEvent;
import com.application.file.CreateDirectory;
import com.application.file.WorkFile;
import com.application.news.FeedNews;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        int i = 1;
        for (String link : RSSlinks) {
            Button titlePortal = new Button(new FeedNews(link).getTitleString());
            TextField textField = new TextField(link + ":/LINK/:" + new FeedNews(link).getTitleString());
            titlePortal.setMinWidth(250);
            titlePortal.setMaxWidth(250);
            titlePortal.setWrapText(true);
            ActionEvent.buttonEvent(titlePortal, primaryStage, textField);

            Button delPortal = new Button("X");
            ActionEvent.buttonEvent(delPortal, new TextField(link), primaryStage, "0");

            grid.add(titlePortal, 0, i);
            grid.add(delPortal, 1, i);
            i++;
        }

        TextField rssField = new TextField();

        Button addRSS = new Button("Add RSS link");
        addRSS.setMinWidth(290);
        ActionEvent.buttonEvent(addRSS, rssField, primaryStage, "0");

        Button goToSaved = new Button("Go to saved news");
        goToSaved.setMinWidth(290);
        ActionEvent.openSavedNewsWindow(goToSaved, primaryStage);

        grid.add(rssField, 0, i + 1, 2,1);
        grid.add(addRSS, 0, i + 2, 2, 1);
        grid.add(goToSaved, 0, i + 3, 2, 1);


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



