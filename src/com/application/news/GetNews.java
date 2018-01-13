package com.application.news;

import com.application.rss.model.RSSFeed;
import com.application.rss.model.RSSMessage;
import com.application.rss.read.RSSParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetNews {

    private static Map<String, RSSParser> cacheRSSParsers = new HashMap<>();
    private static Map<String, RSSFeed> cacheRSSFeed = new HashMap<>();

    public static List<News> getNews(String RSSLink) {
        List<News> newsList = new ArrayList<>();

        RSSParser parser;
        RSSFeed feed;

        if (cacheRSSParsers.keySet().contains(RSSLink)) {
            feed = cacheRSSFeed.get(RSSLink);
            System.out.println("IT IN CACHE BATCH");
        } else {
            parser = new RSSParser(RSSLink);
            feed = parser.readFeed();
            cacheRSSParsers.put(RSSLink, parser);
            cacheRSSFeed.put(RSSLink, parser.readFeed());
        }

        for (RSSMessage message : feed.getMessages()) {
            newsList.add(new News(message.getTitle(), message.getDescription(),
                    message.getLink(), message.getDate()));
        }

        return newsList;
    }

}
