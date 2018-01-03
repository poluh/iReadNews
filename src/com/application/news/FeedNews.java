package com.application.news;

import com.application.rss.model.Feed;
import com.application.rss.read.RSSFeedParser;

public class FeedNews {

    private String titleString, pubDate;

    public FeedNews(String RSSLink) {
        Feed feed = new RSSFeedParser(RSSLink).readFeed();
        this.titleString = feed.getTitle();
        this.pubDate = feed.getPubDate();
    }

    public String getTitleString() {
        return titleString;
    }

    public String getPubDate() {
        return pubDate;
    }
}
