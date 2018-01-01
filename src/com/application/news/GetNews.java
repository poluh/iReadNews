package com.application.news;

import com.application.rss.model.Feed;
import com.application.rss.model.FeedMessage;
import com.application.rss.read.RSSFeedParser;

import java.util.ArrayList;
import java.util.List;

public class GetNews {

    public static List<News> getNews(String RSSLink) {
        List<News> newsList = new ArrayList<>();

        RSSFeedParser parser = new RSSFeedParser(RSSLink);
        Feed feed = parser.readFeed();
        for (FeedMessage message : feed.getMessages()) {
            newsList.add(new News(message.getTitle(), message.getDescription(),
                    message.getLink(), message.getDate()));
        }

        return newsList;
    }

}
