package com.application.rss.read;

import com.application.rss.model.Feed;
import com.application.rss.model.FeedMessage;
import javafx.scene.control.Alert;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class RSSFeedParser {

    private static final String TITLE = "title", DESCRIPTION = "description", CHANNEL = "channel",
            LANGUAGE = "language", COPYRIGHT = "copyright", LINK = "link", AUTHOR = "author",
            ITEM = "item", PUB_DATE = "pubDate", GUID = "guid";

    private URL url;

    public RSSFeedParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RSS not found. (" + url + ")");
            alert.setContentText("Sorry.\nUnfortunately, we could not find the specified RSS-feed.");
            alert.showAndWait();
        }
    }

    private static void catchRuntimeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Runtime error.");
        alert.setContentText("Sorry.\nServer is not responding\n");
        alert.showAndWait();
    }

    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;

            // Set header values intial to the empty string
            String description = "", title = "", link = "", language = "",
                    copyright = "", author = "", pubdate = "", guid = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            // Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    switch (localPart) {
                        case ITEM:
                            if (isFeedHeader) {
                                isFeedHeader = false;
                                feed = new Feed(title, link, description, language,
                                        copyright, pubdate);
                            }
                            break;
                        case TITLE:
                            title = getCharacterData(event, eventReader);
                            break;
                        case DESCRIPTION:
                            description = getCharacterData(event, eventReader);
                            break;
                        case LINK:
                            link = getCharacterData(event, eventReader);
                            break;
                        case GUID:
                            guid = getCharacterData(event, eventReader);
                            break;
                        case LANGUAGE:
                            language = getCharacterData(event, eventReader);
                            break;
                        case AUTHOR:
                            author = getCharacterData(event, eventReader);
                            break;
                        case PUB_DATE:
                            pubdate = getCharacterData(event, eventReader);
                            break;
                        case COPYRIGHT:
                            copyright = getCharacterData(event, eventReader);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (Objects.equals(event.asEndElement().getName().getLocalPart(), ITEM)) {
                        FeedMessage message = new FeedMessage();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        message.setDate(pubdate.substring(0, pubdate.length() - 6));
                        feed.getMessages().add(message);
                    }
                }
            }
        } catch (XMLStreamException e) {
            catchRuntimeError();
        }
        return feed;
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            catchRuntimeError();
        }
        return null;
    }
}
