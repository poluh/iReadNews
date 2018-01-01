package com.application.rss.tests;

import com.application.rss.model.Feed;
import com.application.rss.model.FeedMessage;
import com.application.rss.read.RSSFeedParser;

public class ReadTest {
    public static void main(String[] args) {
        RSSFeedParser parser = new RSSFeedParser(
                "https://news.yandex.ru/Saint_Petersburg/index.rss");
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message);

        }

    }
}
