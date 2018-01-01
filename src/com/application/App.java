package com.application;

import com.application.action.event.ActionEvent;
import com.application.file.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class App extends Application {

    private static void createStartWindow(Stage primaryStage) {
        primaryStage.setTitle("News");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        createObjects(grid, primaryStage);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void createRSSLinksWindow(Stage primaryStage, List<String> file) {
        primaryStage.setTitle("Your RSS links.");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        createObjects(grid, primaryStage, file);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scrollPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        if (!WorkFile.checkFile()) {
            createStartWindow(primaryStage);
        }
        else {
            List<String> RSSLinks = WorkFile.listRSSLinks();
            createRSSLinksWindow(primaryStage, RSSLinks);
        }


    }

    // This method created object for start window
    private static void createObjects(GridPane grid, Stage primaryStage) {

        // Main Title
        Text sceneTitle = new Text("Welcome.");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 3, 1);

        // If user do first entry
        Text simpleTitle = new Text("It looks like you have never been here before.");
        grid.add(simpleTitle, 0, 1, 2, 1);

        Label rssLink = new Label("Enter RSS-link:");
        grid.add(rssLink, 0, 2);

        TextField rssText = new TextField();
        grid.add(rssText, 1, 2);

        Label pw = new Label("Password:\n(Can be empty)");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        Button btn = new Button("Get news!");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);

        ActionEvent.buttonEvent(btn, primaryStage, rssText);

    }

    // This methods created obj for base window, after added links RSS
    private static void createObjects(GridPane grid, Stage primaryStage, List<String> RSSlinks) {

        Label title = new Label("You RSS links:");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0);

        int i = 1;
        for (String link : RSSlinks) {
            if (!link.matches("\\s+")) {
                Button btn = new Button(link);
                TextField textField = new TextField(link);
                ActionEvent.buttonEvent(btn, primaryStage, textField);

                btn.autosize();
                HBox hbBtn = new HBox(10);
                hbBtn.setAlignment(Pos.BOTTOM_LEFT);
                hbBtn.getChildren().add(btn);
                grid.add(hbBtn, 0, i);

                Button btnDel = new Button("X");
                ActionEvent.buttonEvent(btnDel, new TextField(link), primaryStage);

                btnDel.autosize();
                HBox hbBtnDel = new HBox(10);
                hbBtnDel.setAlignment(Pos.BOTTOM_LEFT);
                hbBtnDel.getChildren().add(btnDel);
                grid.add(hbBtnDel, 1, i);
                i++;
            }
        }
        TextField rssField = new TextField();
        grid.add(rssField, 0, i + 1);

        Button addRSSLink = new Button("Add RSS.");
        ActionEvent.buttonEvent(addRSSLink, rssField, primaryStage);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(addRSSLink);
        grid.add(hbBtn, 1, i + 1);

    }


    public static void main(String[] args) {
        launch(args);
    }

}



