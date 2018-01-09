package com.application.news;

import com.application.rss.model.RSSFeed;
import com.application.rss.read.RSSParser;
import javafx.scene.image.Image;

public class FeedNews {

    private String title, link;
    private Image image;

    public FeedNews(String RSSLink) {
        RSSFeed feed = new RSSParser(RSSLink).readFeed();
        this.title = feed.getTitle();
        this.link = feed.getLink();
        this.image = feed.getImage();
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Image getImage() {
        return image;
    }
}
