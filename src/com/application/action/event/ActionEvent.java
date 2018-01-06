package com.application.action.event;

import com.application.App;
import com.application.file.WorkFile;
import com.application.news.GetNews;
import com.application.news.News;
import com.application.news.SaveNews;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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

            primaryStage.close();

            Stage newsStage = new Stage();

            newsStage.setTitle("News!");

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.BOTTOM_RIGHT);
            grid.setPadding(new Insets(20));
            grid.setPadding(new Insets(20));
            grid.setVgap(15);
            grid.setMaxSize(width, height);

            Button back = new Button("Back to links");
            back.setId("button-toBack");

            toBack(back, newsStage);
            closeNewsWindow(newsStage);

            ScrollPane originPane = new ScrollPane();
            originPane.setContent(grid);
            originPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            originPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            StackPane root = new StackPane();
            StackPane.setMargin(back, new Insets(30, 0, height - 30, 0));
            root.getChildren().addAll(originPane, back);

            Scene scene = new Scene(root, width + 50, height);
            scene.getStylesheets().addAll(App.PATH_TO_STYLE);
            newsStage.setScene(scene);
            newsStage.show();


            try {
                createObjects(grid, rssText.getText().substring(0,
                        rssText.getText().indexOf(":/LINK/:")), newsStage);
            } catch (StringIndexOutOfBoundsException e) {
                createObjects(grid, rssText.getText(), newsStage);
                try {
                    WorkFile.addRSSLink(rssText.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
    }

    // Create news-window
    private static void createObjects(GridPane grid, String RSSLink, Stage primaryStage) {

        List<News> newsList = GetNews.getNews(RSSLink);

        int i = 0;

        i += 2;

        for (News news : newsList) {

            Text newsTitle = new Text(news.getTitle() + "\nОпубликовано: " + news.getDate());
            Platform.runLater(() -> {
                newsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                newsTitle.setWrappingWidth(width);
            });

            Text newsDescription = new Text(news.getDescription());
            newsDescription.setWrappingWidth(width);

            Button newsDate = new Button(news.getDate());
            newsDate.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

            Button newsLink = new Button("More info...");
            newsLink.setTextFill(Color.BLUE);

            openNews(newsLink, news.getLink());

            Button newsSave = new Button("Save this new!");
            Platform.runLater(() -> {
                newsSave.setTextFill(Color.RED);
                newsSave.setAlignment(Pos.BOTTOM_RIGHT);
                saveNews(newsSave, news.getLink(), news.getTitle());
            });
            int finalI = i;
            Platform.runLater(() -> {
                GridPane miniGrid = new GridPane();
                miniGrid.setHgap(20);

                miniGrid.add(newsLink, 0, 0);
                miniGrid.add(newsSave, 1, 0);

                grid.add(newsTitle, 0, finalI, 2, 1);
                grid.add(newsDescription, 0, finalI + 1);
                grid.add(miniGrid, 0, finalI + 2, 2, 1);
            });

            i += 3;
            grid.impl_updatePeer();
        }

    }

    // Active event for button "Add RSS" in subtract window and button "X"
    public static void buttonEvent(Button button, TextField RSSLink, Stage primaryStage, String key) {
        button.setOnAction((javafx.event.ActionEvent event) -> Platform.runLater(() -> {
            String text = RSSLink.getText();
            if (!text.matches("\\s+") && !text.isEmpty()) {
                try {
                    if (!Objects.equals(button.getText(), "X") && !button.getText().isEmpty()) {
                        WorkFile.addRSSLink(RSSLink.getText());
                    } else if (Objects.equals(key, "0")) {
                        WorkFile.deleteRSSLink(RSSLink.getText());
                        try {
                            App.createRSSLinksWindow(primaryStage, WorkFile.listRSSLinks("0"));
                        } catch (NullPointerException e) {
                            WorkFile.deleteRSSLink(RSSLink.getText());
                            App.createRSSLinksWindow(primaryStage, WorkFile.listRSSLinks("0"));
                        }
                    } else {
                        WorkFile.deleteSavedNews(key);
                        App.createSavedPagesWindow(primaryStage, WorkFile.listNewsNames());
                    }

                } catch (IOException ignored) {
                    System.out.println("{Q");
                }
            }
        }));
    }

    public static void buttonPopularRSS(Button button, Stage primaryStage) {
        button.setOnAction((javafx.event.ActionEvent event) -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Append RSS.");
            alert.setHeaderText("Choose one of the suggested options.");
            alert.setContentText("You can go to the Yandex website " +
                    "and select those RSSs that match your interests," +
                    "or give yourself up to the developer's choice." +
                    " (lenta, fontanka, Google news)");

            ButtonType addYandex = new ButtonType("Set RSS in Yandex news");
            ButtonType addRecommended = new ButtonType("Set recommended RSS portal");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(addYandex, addRecommended, cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == addYandex) {
                SaveNews.openDefaultBrowser("https://news.yandex.ru/export.html");
            } else if (result.get() == addRecommended) {
                try {
                    App.createRSSLinksWindow(primaryStage, WorkFile.listRSSLinks("1"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void toBack() {
        try {
            Stage newStage = new Stage();
            App.createRSSLinksWindow(newStage, WorkFile.listRSSLinks("0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toBack(Button button, Stage primaryStage) {
        button.setOnAction((javafx.event.ActionEvent event) -> {
            primaryStage.close();
            toBack();
        });
    }

    private static void saveNews(Button button, String newsLink, String newsName) {
        button.setOnMouseClicked(event -> SaveNews.saveNews(newsLink, newsName));
    }

    private static void openNews(Button button, String link) {
        button.setOnMouseClicked(event -> SaveNews.openDefaultBrowser(link));
    }

    public static void openSavedNews(Button button, String newsName) {
        button.setOnAction(event -> {
            try {
                SaveNews.openNews(newsName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public static void openSavedNewsWindow(Button button, Stage primaryStage) {
        button.setOnAction(event -> {
            try {
                App.createSavedPagesWindow(primaryStage, WorkFile.listNewsNames());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void closeNewsWindow(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> toBack());
    }

    public static void deleteAllNews(Button button, Stage primaryStage) {
        button.setOnAction(event -> {
            SaveNews.dellAllNews();
            primaryStage.close();
            toBack();
        });
    }

}
