package com.application.news;

import com.application.rss.model.Feed;
import com.application.rss.read.RSSFeedParser;

public class FeedNews {

    private String titleString;

    public FeedNews(String RSSLink) {
        Feed feed = new RSSFeedParser(RSSLink).readFeed();
        this.titleString = feed.getTitle();
    }

    public String getTitleString() {
        return titleString;
    }
}
