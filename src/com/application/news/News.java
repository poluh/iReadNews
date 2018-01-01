package com.application.news;

import com.application.rss.model.Feed;
import com.application.rss.read.RSSFeedParser;

public class News {

    News() {
        RSSFeedParser parser = new RSSFeedParser("https://news.yandex.ru/Saint_Petersburg/index.rss");
        Feed feed = parser.readFeed();
        String title = feed.getTitle(), description = feed.getDescription(),
                link = feed.getLink(), date = feed.getPubDate();
    }

}
