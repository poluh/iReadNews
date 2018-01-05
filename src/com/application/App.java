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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        grid.setAlignment(Pos.BOTTOM_RIGHT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        primaryStage.setTitle("Your RSS links.");
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scrollPane, 350, 400);
        scene.getStylesheets().add(PATH_TO_STYLE);

        primaryStage.getIcons().add(ICON);
        primaryStage.setScene(scene);
        primaryStage.show();

        createObjects(grid, primaryStage, file);
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


        Label title = new Label("You RSS links:");
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
                        ActionEvent.buttonEvent(buttonForDelNewsPortal, new TextField(link), primaryStage);
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
            ActionEvent.buttonEvent(buttonAddRSSLink, rssField, primaryStage);
            HBox hbBtnAddRSSLink = new HBox(10);
            hbBtnAddRSSLink.setAlignment(Pos.BOTTOM_LEFT);
            hbBtnAddRSSLink.getChildren().add(buttonAddRSSLink);

            Button openSaveNews = new Button("Go to saved news!");
            openSaveNews.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
            ActionEvent.openNews(openSaveNews, primaryStage);
            HBox hbBtnOPenSaveNews = new HBox(10);
            hbBtnOPenSaveNews.setAlignment(Pos.BOTTOM_LEFT);
            hbBtnOPenSaveNews.getChildren().add(openSaveNews);

            Button delAll = new Button("Del all saved news.");
            delAll.setFont(Font.font("Tahoma", FontWeight.NORMAL, 11));
            ActionEvent.deleteAllNews(delAll);
            HBox hbBtnDelAll = new HBox(10);
            hbBtnDelAll.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtnDelAll.getChildren().add(delAll);

            GridPane miniGrid = new GridPane();
            miniGrid.setHgap(10);
            miniGrid.setVgap(10);
            miniGrid.setMaxWidth(290);

            miniGrid.add(rssField, 0, 1, 2, 1);
            miniGrid.add(hbBtnAddRSSLink, 0, 2, 2, 1);
            miniGrid.add(hbBtnOPenSaveNews, 0, 3);
            miniGrid.add(hbBtnDelAll, 1, 3);
            miniGrid.setAlignment(Pos.CENTER);

            grid.add(miniGrid, 0, i[0] + 2);

        });
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



