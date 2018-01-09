package com.application.rss.model;



import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class RSSFeed {

    private final String title, description, link, date;
    private final Image image;

    private final List<RSSMessage> messages = new ArrayList<>();

    public RSSFeed(String title, String description, String link, String date, Image image) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public List<RSSMessage> getMessages() {
        return messages;
    }

    public Image getImage() {
        return image;
    }
}
