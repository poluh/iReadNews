package com.application.action.event;

import com.application.news.News;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class ActionEvent {

    public static void buttonEvent(Button btn, Stage primaryStage) {
        btn.setOnAction((javafx.event.ActionEvent event) ->  {

            Stage newsStage = new Stage();

            newsStage.setTitle("News!");

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));


            Scene scene = new Scene(grid, 400, 400);
            newsStage.setScene(scene);
            newsStage.show();

            primaryStage.close();
        });
    }

    private static void createObjects(GridPane grid, Stage primaryStage, News news) {

    }
}
