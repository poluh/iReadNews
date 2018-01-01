package com.application;

import com.application.action.event.ActionEvent;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
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

    private void createObjects(GridPane grid, Stage primaryStage) {

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


    public static void main(String[] args) {
        launch(args);
    }

}



