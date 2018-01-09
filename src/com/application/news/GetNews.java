package com.application.news;

import com.application.rss.model.RSSFeed;
import com.application.rss.model.RSSMessage;
import com.application.rss.read.RSSParser;

import java.util.ArrayList;
import java.util.List;

public class GetNews {

    public static List<News> getNews(String RSSLink) {
        List<News> newsList = new ArrayList<>();

        RSSParser parser = new RSSParser(RSSLink);
        RSSFeed feed = parser.readFeed();
        for (RSSMessage message : feed.getMessages()) {
            newsList.add(new News(message.getTitle(), message.getDescription(),
                    message.getLink(), message.getDate()));
        }

        return newsList;
    }

}
